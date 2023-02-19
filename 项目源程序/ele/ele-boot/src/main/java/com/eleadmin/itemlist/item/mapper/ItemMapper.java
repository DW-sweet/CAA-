package com.eleadmin.itemlist.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleadmin.itemlist.item.entity.Item;
import com.eleadmin.itemlist.item.param.ItemParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface ItemMapper extends BaseMapper<Item> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Item>
     */
    List<Item> selectPageRel(@Param("page") IPage<Item> page,
                                     @Param("param") ItemParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Item>
     */
    List<Item> selectListRel(@Param("param") ItemParam param);

}
