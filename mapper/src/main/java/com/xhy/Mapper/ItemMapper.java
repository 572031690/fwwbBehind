package com.xhy.Mapper;

import com.xhy.domain.Item;
import com.xhy.vo.ItemVO;

import java.util.List;

public interface ItemMapper {

    List<Item> findAll(ItemVO itemVO);

    /*查找所需名称*/
    List<Item> findItemName(String itemtype);

    Item findbyid(String itemid);

    Integer addItem(Item item);

    Integer updataItem(Item item);

    Integer deleteItem(Integer itemid);

}
