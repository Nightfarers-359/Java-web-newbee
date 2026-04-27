-- 二、数据库设计1. 数据库表结构

-- 商品表
CREATE TABLE items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    category_id BIGINT,
    seller_id BIGINT,
    status TINYINT DEFAULT 1, -- 1:上架, 0:下架
    cover_image VARCHAR(500),
    images TEXT, -- JSON数组存储多图
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- 分类表
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    level INT DEFAULT 1,
    sort_order INT DEFAULT 0,
    is_visible BOOLEAN DEFAULT true
);

-- 商品属性表（用于筛选）
CREATE TABLE item_attributes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_id BIGINT,
    attr_key VARCHAR(50),
    attr_value VARCHAR(200),
    FOREIGN KEY (item_id) REFERENCES items(id)
);

-- 商品SKU表（规格）
CREATE TABLE item_skus (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_id BIGINT,
    sku_name VARCHAR(200), -- 如"红色 L码"
    price DECIMAL(10,2),
    stock INT DEFAULT 0,
    specs TEXT, -- JSON格式存储规格
    FOREIGN KEY (item_id) REFERENCES items(id)
);