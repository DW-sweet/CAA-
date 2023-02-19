package com.eleadmin.order.caa_orderlist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface OrderMapper extends BaseMapper<com.eleadmin.order.caa_orderlist.entity.Order> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Order>
     */
    List<com.eleadmin.order.caa_orderlist.entity.Order> selectPageRel(@Param("page") IPage<com.eleadmin.order.caa_orderlist.entity.Order> page,
                                                               @Param("param") com.eleadmin.order.caa_orderlist.param.OrderParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Order>
     */
    List<com.eleadmin.order.caa_orderlist.entity.Order> selectListRel(@Param("param") com.eleadmin.order.caa_orderlist.param.OrderParam param);

}
