@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    // 按分类查询
    Page<Item> findByCategoryId(Long categoryId, Pageable pageable);
    
    // 按状态查询
    List<Item> findByStatus(Integer status);
    
    // 名称模糊搜索
    Page<Item> findByNameContaining(String name, Pageable pageable);
    
    // 价格区间查询
    @Query("SELECT i FROM Item i WHERE i.price BETWEEN :minPrice AND :maxPrice")
    Page<Item> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, 
                                  @Param("maxPrice") BigDecimal maxPrice, 
                                  Pageable pageable);
    
    // 多条件查询
    @Query("SELECT i FROM Item i WHERE " +
           "(:categoryId IS NULL OR i.category.id = :categoryId) AND " +
           "(:minPrice IS NULL OR i.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR i.price <= :maxPrice) AND " +
           "(:status IS NULL OR i.status = :status)")
    Page<Item> findByConditions(@Param("categoryId") Long categoryId,
                                @Param("minPrice") BigDecimal minPrice,
                                @Param("maxPrice") BigDecimal maxPrice,
                                @Param("status") Integer status,
                                Pageable pageable);
}