package com.eleadmin.order.caa_orderlist.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;


import java.util.List;

/**
 * 学生Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface OrderService extends IService<com.eleadmin.order.caa_orderlist.entity.Order> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Order>
     */
    PageResult<com.eleadmin.order.caa_orderlist.entity.Order> pageRel(com.eleadmin.order.caa_orderlist.param.OrderParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Order>
     */
    List<com.eleadmin.order.caa_orderlist.entity.Order> listRel(com.eleadmin.order.caa_orderlist.param.OrderParam param);

    /**
     * 根据id查询
     *
     * @param OrderId 机构id
     * @return Order
     */
    com.eleadmin.order.caa_orderlist.entity.Order getByIdRel(Integer OrderId);

}
