package com.eleadmin.order.caa_orderlist.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.order.caa_orderlist.entity.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单控制器
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Api(tags = "订单列表")
@RestController
@RequestMapping("/api/order/caa_orderlist")
public class OrderController extends BaseController {
    @Resource
    private com.eleadmin.order.caa_orderlist.service.OrderService OrderService;

    @PreAuthorize("hasAuthority('order:caa_orderlist:list')")
    @OperationLog
    @ApiOperation("分页查询订单")
    @GetMapping("/page")
    public ApiResult<PageResult<com.eleadmin.order.caa_orderlist.entity.Order>> page(com.eleadmin.order.caa_orderlist.param.OrderParam param) {
        return success(OrderService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('order:caa_orderlist:list')")
    @OperationLog
    @ApiOperation("查询全部订单")
    @GetMapping()
    public ApiResult<List<com.eleadmin.order.caa_orderlist.entity.Order>> list(com.eleadmin.order.caa_orderlist.param.OrderParam param) {
        return success(OrderService.listRel(param));
    }

    @PreAuthorize("hasAuthority('order:caa_orderlist:list')")
    @OperationLog
    @ApiOperation("根据id查询订单")
    @GetMapping("/{id}")
    public ApiResult<com.eleadmin.order.caa_orderlist.entity.Order> get(@PathVariable("id") Integer id) {
        return success(OrderService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('order:caa_orderlist:save')")
    @OperationLog
    @ApiOperation("添加订单")
    @PostMapping()
    public ApiResult<?> add(@RequestBody com.eleadmin.order.caa_orderlist.entity.Order order) {
        if (OrderService.count(new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderName, order.getOrderName())) > 0) {
            return fail("订单名称已存在");
        }
        if (OrderService.save(order)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('order:caa_orderlist:update')")
    @OperationLog
    @ApiOperation("修改订单")
    @PutMapping()
    public ApiResult<?> update(@RequestBody com.eleadmin.order.caa_orderlist.entity.Order order) {
        if (order.getOrderName() != null) {
            if (order.getOrderName() != null) {
                if (OrderService.count(new LambdaQueryWrapper<Order>()
                        .eq(Order::getOrderName, order.getOrderName())
                        .ne(Order::getOrderId, order.getOrderId())) > 0) {
                    return fail("订单名称已存在");
                }
            }
        }
        if (OrderService.updateById(order)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('order:caa_orderlist:remove')")
    @OperationLog
    @ApiOperation("删除订单")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (OrderService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('order:caa_orderlist:save')")
    @OperationLog
    @ApiOperation("批量添加订单")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<com.eleadmin.order.caa_orderlist.entity.Order> OrderList) {
        if (OrderService.saveBatch(OrderList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('order:caa_orderlist:update')")
    @OperationLog
    @ApiOperation("批量修改订单")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<com.eleadmin.order.caa_orderlist.entity.Order> batchParam) {
        if (batchParam.update(OrderService, com.eleadmin.order.caa_orderlist.entity.Order::getOrderId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('order:caa_orderlist:remove')")
    @OperationLog
    @ApiOperation("批量删除订单")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (OrderService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
