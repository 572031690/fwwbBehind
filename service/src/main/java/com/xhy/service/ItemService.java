package com.xhy.service;

import com.xhy.domain.Item;
import com.xhy.vo.ItemVO;

import java.util.List;

public interface ItemService {
    //    查看所有
    List<Item> findAllItem(ItemVO itemVO);

    //  添加
    Integer addItem(Item item);

    //  修改
    Integer updateItem(Item item);

    //  删除
    Integer deleteItem(String itemid);

    //  根据ID查询
    Item findItemById(int startNum,int pageSize,String itemid);
}
