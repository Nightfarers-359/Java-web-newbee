@Data
public class ItemQueryDTO {
    @Min(1)
    private Integer page = 1;
    
    @Min(1) @Max(100)
    private Integer size = 20;
    
    private Long categoryId;
    
    @DecimalMin("0.00")
    private BigDecimal minPrice;
    
    @DecimalMin("0.00")
    private BigDecimal maxPrice;
    
    private String sortBy = "newest"; // newest, price_asc, price_desc, popular
}