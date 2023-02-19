package com.eleadmin.itemlist.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.itemlist.item.entity.Item;
import com.eleadmin.itemlist.item.param.ItemParam;


import java.util.List;

/**
 * 商品Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface ItemService extends IService<Item> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Item>
     */
    PageResult<Item> pageRel(ItemParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Item>
     */
    List<Item> listRel(ItemParam param);

    /**
     * 根据id查询
     *
     * @param itemId 机构id
     * @return Item
     */
    Item getByIdRel(Integer itemId);

}
