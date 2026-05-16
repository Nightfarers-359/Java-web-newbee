package com.project.platform.service;

import com.project.platform.DTO.ItemData;
import com.project.platform.DTO.ItemInfo;

public interface ItemService {
    ItemInfo getItemById(Integer id);

    ItemInfo getItemByName(String name);

    ItemInfo[] getItemlist();

    ItemInfo createItem(ItemData itemData);
    
    boolean deleteItem(Integer id);

    boolean updateItem(Integer id, ItemData itemData);

    boolean hideItem(Integer id);
}
