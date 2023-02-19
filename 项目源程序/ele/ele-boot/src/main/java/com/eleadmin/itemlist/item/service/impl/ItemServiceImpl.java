package com.eleadmin.itemlist.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.itemlist.item.entity.Item;
import com.eleadmin.itemlist.item.mapper.ItemMapper;
import com.eleadmin.itemlist.item.param.ItemParam;
import com.eleadmin.itemlist.item.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item>
        implements ItemService {

    @Override
    public PageResult<Item> pageRel(ItemParam param) {
        PageParam<Item, ItemParam> page = new PageParam<>(param);
        page.setDefaultOrder("item_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<Item> listRel(ItemParam param) {
        PageParam<Item, ItemParam> page = new PageParam<>(param);
        page.setDefaultOrder("item_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public Item getByIdRel(Integer itemId) {
        ItemParam param = new ItemParam();
        param.setItemId(itemId);
        return param.getOne(baseMapper.selectListRel(param));
    }

}
