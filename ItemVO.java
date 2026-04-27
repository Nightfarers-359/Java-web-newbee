@Data
public class ItemVO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String coverImage;
    private List<String> images;
    private String categoryName;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createdAt;
}