package com.project.platform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.platform.DTO.ItemData;
import com.project.platform.DTO.ItemInfo;
import com.project.platform.entity.Item;
import com.project.platform.mapper.ItemMapper;
import com.project.platform.service.ItemService;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Override
    public ItemInfo getItemById(Integer id) {
        Item item = this.getById(id);
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }
        return new ItemInfo(item.getId(), item.getName(),
                item.getDescription(), item.getPrice(),
                item.getStock(), item.getCategory(), item.getImageUrl());
    }

    @Override
    public ItemInfo getItemByName(String name) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Item item = this.getOne(queryWrapper);
        if (item == null) {
            return null;
        }
        return new ItemInfo(item.getId(), item.getName(),
                item.getDescription(), item.getPrice(),
                item.getStock(), item.getCategory(), item.getImageUrl());

    }

    @Override
    public ItemInfo[] getItemlist() {
        List<Item> items = this.list();
        if (items.isEmpty()) {
            return new ItemInfo[0];
        }
        ItemInfo[] itemInfos = new ItemInfo[items.size()];
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            itemInfos[i] = new ItemInfo(item.getId(), item.getName(),
                    item.getDescription(), item.getPrice(),
                    item.getStock(), item.getCategory(), item.getImageUrl());
        }
        return itemInfos;
    }

    @Override
    public ItemInfo createItem(ItemData itemData) {
        ItemInfo checkname = getItemByName(itemData.getName());
        if (checkname != null) {
            throw new IllegalArgumentException("Item name already exists");
        }
        Item item = new Item();
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        item.setPrice(itemData.getPrice());
        item.setStock(itemData.getStock());
        item.setCategory(itemData.getCategory());
        item.setImageUrl(itemData.getImage());
        item.setOwnerId(itemData.getOwnerId());
        boolean result = this.save(item);
        if (result) {
            return getItemByName(itemData.getName());
        } else {
            throw new IllegalArgumentException("Failed to create item");
        }
    }

    @Override
    public boolean deleteItem(Integer id) {
        boolean result = this.removeById(id);
        return result;
    }

    @Override
    public boolean updateItem(Integer id, ItemData itemData) {
        Item item = this.getById(id);
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        item.setPrice(itemData.getPrice());
        item.setStock(itemData.getStock());
        item.setCategory(itemData.getCategory());
        item.setImageUrl(itemData.getImage());
        item.setUpdatedAt(null);
        boolean result = this.updateById(item);
        return result;
    }

    @Override
    public boolean hideItem(Integer id) {
        Item item = this.getById(id);
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }
        if (!item.isHidden()) {
            throw new IllegalArgumentException("Item is already hidden");
        }
        item.setHidden(true);
        item.setUpdatedAt(null);
        boolean result = this.updateById(item);
        return result;
    }
}
