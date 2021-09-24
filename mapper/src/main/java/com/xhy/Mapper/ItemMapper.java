package com.xhy.Mapper;

import com.xhy.domain.Item;

import java.util.List;

public interface ItemMapper {

    List<Item> findAll(String itemtype);

    Item findbyid(String itemid);

    Integer addItem(Item item);

    Integer updataItem(Item item);

    Integer deleteItem(String itemid);

}
