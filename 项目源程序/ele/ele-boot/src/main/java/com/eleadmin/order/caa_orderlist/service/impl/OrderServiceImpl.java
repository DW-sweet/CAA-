package com.eleadmin.order.caa_orderlist.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<com.eleadmin.order.caa_orderlist.mapper.OrderMapper, com.eleadmin.order.caa_orderlist.entity.Order>
        implements com.eleadmin.order.caa_orderlist.service.OrderService {

    @Override
    public PageResult<com.eleadmin.order.caa_orderlist.entity.Order> pageRel(com.eleadmin.order.caa_orderlist.param.OrderParam param) {
        PageParam<com.eleadmin.order.caa_orderlist.entity.Order, com.eleadmin.order.caa_orderlist.param.OrderParam> page = new PageParam<>(param);
        page.setDefaultOrder("Order_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<com.eleadmin.order.caa_orderlist.entity.Order> listRel(com.eleadmin.order.caa_orderlist.param.OrderParam param) {
        PageParam<com.eleadmin.order.caa_orderlist.entity.Order, com.eleadmin.order.caa_orderlist.param.OrderParam> page = new PageParam<>(param);
        page.setDefaultOrder("Order_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public com.eleadmin.order.caa_orderlist.entity.Order getByIdRel(Integer OrderId) {
        com.eleadmin.order.caa_orderlist.param.OrderParam param = new com.eleadmin.order.caa_orderlist.param.OrderParam();
        param.setOrderId(OrderId);
        return param.getOne(baseMapper.selectListRel(param));
    }

}
