@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final RedisTemplate<String, Object> redisTemplate;
    
    // 获取商品列表（支持分页、筛选、排序）
    public Page<ItemVO> getItems(ItemQueryDTO queryDTO) {
        Pageable pageable = PageRequest.of(
            queryDTO.getPage() - 1, 
            queryDTO.getSize(),
            Sort.by(getSortOrder(queryDTO.getSortBy()))
        );
        
        Page<Item> items = itemRepository.findByConditions(
            queryDTO.getCategoryId(),
            queryDTO.getMinPrice(),
            queryDTO.getMaxPrice(),
            1, // 只查询上架商品
            pageable
        );
        
        return items.map(this::convertToVO);
    }
    
    // 获取商品详情
    public ItemDetailVO getItemDetail(Long id) {
        // 先从缓存获取
        String cacheKey = "item:" + id;
        ItemDetailVO cached = (ItemDetailVO) redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return cached;
        }
        
        // 数据库查询
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("商品不存在"));
        
        // 增加浏览量
        incrementViewCount(id);
        
        // 转换为VO
        ItemDetailVO vo = convertToDetailVO(item);
        
        // 放入缓存，设置过期时间
        redisTemplate.opsForValue().set(cacheKey, vo, 30, TimeUnit.MINUTES);
        
        return vo;
    }
    
    // 商品搜索
    public Page<ItemVO> searchItems(String keyword, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return itemRepository.findByNameContaining(keyword, pageable)
            .map(this::convertToVO);
    }
    
    // 获取分类树
    public List<CategoryVO> getCategoryTree() {
        List<Category> allCategories = categoryService.getAllCategories();
        return buildCategoryTree(allCategories, 0L);
    }
    
    // 添加商品（卖家）
    public Item addItem(ItemCreateDTO dto, Long sellerId) {
        Item item = Item.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .stock(dto.getStock())
            .category(categoryService.getById(dto.getCategoryId()))
            .sellerId(sellerId)
            .status(1)
            .coverImage(dto.getCoverImage())
            .images(JsonUtil.toJson(dto.getImages()))
            .build();
        
        return itemRepository.save(item);
    }
    
    // 更新商品
    public Item updateItem(Long id, ItemUpdateDTO dto, Long sellerId) {
        Item item = getItemByIdAndSeller(id, sellerId);
        
        BeanUtils.copyProperties(dto, item, "id", "sellerId");
        if (dto.getImages() != null) {
            item.setImages(JsonUtil.toJson(dto.getImages()));
        }
        
        // 清除缓存
        redisTemplate.delete("item:" + id);
        
        return itemRepository.save(item);
    }
    
    // 删除商品（软删除）
    public void deleteItem(Long id, Long sellerId) {
        Item item = getItemByIdAndSeller(id, sellerId);
        item.setStatus(0);
        itemRepository.save(item);
        
        // 清除缓存
        redisTemplate.delete("item:" + id);
    }
    
    // 私有方法
    private Sort.Order getSortOrder(String sortBy) {
        if ("price_asc".equals(sortBy)) {
            return Sort.Order.asc("price");
        } else if ("price_desc".equals(sortBy)) {
            return Sort.Order.desc("price");
        } else if ("newest".equals(sortBy)) {
            return Sort.Order.desc("createdAt");
        } else if ("popular".equals(sortBy)) {
            return Sort.Order.desc("viewCount");
        }
        return Sort.Order.desc("createdAt"); // 默认按时间倒序
    }
    
    private void incrementViewCount(Long itemId) {
        String key = "item:view:" + itemId;
        redisTemplate.opsForValue().increment(key, 1);
        
        // 每10次浏览量同步到数据库
        Long count = (Long) redisTemplate.opsForValue().get(key);
        if (count % 10 == 0) {
            itemRepository.incrementViewCount(itemId, 10);
        }
    }
}