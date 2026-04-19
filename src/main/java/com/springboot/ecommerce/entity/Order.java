package com.springboot.ecommerce.entity;

public class Order {
    private Long id;
    private Long sellerid;
    private Long buyerid;
    private Long productid;
    private Integer quantity;
    private Integer value;

    public Order() {}
    public Order(Long id, Long sellerid, Long buyerid, Long productid, Integer quantity, Integer value) {
        this.id = id;
        this.sellerid = sellerid;
        this.buyerid = buyerid;
        this.productid = productid;
        this.quantity = quantity;
        this.value = value;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSellerid() {
        return sellerid;
    }
    public void setSellerid(Long sellerid) {
        this.sellerid = sellerid;
    }
    public Long getBuyerid() {
        return buyerid;
    }
    public void setBuyerid(Long buyerid) {
        this.buyerid = buyerid;
    }
    public Long getProductid() {
        return productid;
    }
    public void setProductid(Long productid) {
        this.productid = productid;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
}
