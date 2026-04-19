package com.springboot.ecommerce.entity;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer stock;
    private Long sellerid;

    public Product(){}

    public Product(Long id, String name, Integer price, Integer stock, Long sellerid) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.sellerid = sellerid;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Long getSellerid() {
        return sellerid;
    }
    public void setSellerid(Long sellerid) {
        this.sellerid = sellerid;
    }
}
