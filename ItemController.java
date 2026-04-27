@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    
    private final ItemService itemService;
    
    // 商品列表
    @GetMapping("/list")
    public ResponseEntity<PageResult<ItemVO>> getItems(@Valid ItemQueryDTO queryDTO) {
        Page<ItemVO> items = itemService.getItems(queryDTO);
        return ResponseEntity.ok(PageResult.success(items));
    }
    
    // 商品详情
    @GetMapping("/{id}")
    public ResponseEntity<Result<ItemDetailVO>> getItemDetail(@PathVariable Long id) {
        ItemDetailVO detail = itemService.getItemDetail(id);
        return ResponseEntity.ok(Result.success(detail));
    }
    
    // 搜索商品
    @GetMapping("/search")
    public ResponseEntity<PageResult<ItemVO>> searchItems(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        Page<ItemVO> result = itemService.searchItems(keyword, page, size);
        return ResponseEntity.ok(PageResult.success(result));
    }
    
    // 获取分类
    @GetMapping("/categories")
    public ResponseEntity<Result<List<CategoryVO>>> getCategories() {
        List<CategoryVO> categories = itemService.getCategoryTree();
        return ResponseEntity.ok(Result.success(categories));
    }
    
    // 添加商品（需要卖家权限）
    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Result<ItemVO>> addItem(@Valid @RequestBody ItemCreateDTO dto,
                                                   @AuthenticationPrincipal UserDetails user) {
        Long sellerId = Long.parseLong(user.getUsername());
        Item item = itemService.addItem(dto, sellerId);
        return ResponseEntity.ok(Result.success(convertToVO(item)));
    }
    
    // 更新商品
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Result<ItemVO>> updateItem(@PathVariable Long id,
                                                      @Valid @RequestBody ItemUpdateDTO dto,
                                                      @AuthenticationPrincipal UserDetails user) {
        Long sellerId = Long.parseLong(user.getUsername());
        Item item = itemService.updateItem(id, dto, sellerId);
        return ResponseEntity.ok(Result.success(convertToVO(item)));
    }
    
    // 删除商品
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Result<Void>> deleteItem(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserDetails user) {
        Long sellerId = Long.parseLong(user.getUsername());
        itemService.deleteItem(id, sellerId);
        return ResponseEntity.ok(Result.success());
    }
}