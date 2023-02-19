package com.eleadmin.order.reasons.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.order.reasons.entity.Reasons;
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
@Api(tags = "退货原因")
@RestController
@RequestMapping("/api/order/reasons")
public class ReasonsController extends BaseController {
    @Resource
    private com.eleadmin.order.reasons.service.ReasonsService ReasonsService;

    @PreAuthorize("hasAuthority('order:reasons:list')")
    @OperationLog
    @ApiOperation("分页查询订单")
    @GetMapping("/page")
    public ApiResult<PageResult<Reasons>> page(com.eleadmin.order.reasons.param.ReasonsParam param) {
        return success(ReasonsService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('order:reasons:list')")
    @OperationLog
    @ApiOperation("查询全部订单")
    @GetMapping()
    public ApiResult<List<Reasons>> list(com.eleadmin.order.reasons.param.ReasonsParam param) {
        return success(ReasonsService.listRel(param));
    }

    @PreAuthorize("hasAuthority('order:reasons:list')")
    @OperationLog
    @ApiOperation("根据id查询订单")
    @GetMapping("/{id}")
    public ApiResult<Reasons> get(@PathVariable("id") Integer id) {
        return success(ReasonsService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('order:reasons:save')")
    @OperationLog
    @ApiOperation("添加订单")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Reasons reasons) {
        if (ReasonsService.count(new LambdaQueryWrapper<Reasons>()
                .eq(Reasons::getReasonsName, reasons.getReasonsName())) > 100) {
            return fail("退款原因已存在");
        }
        if (ReasonsService.save(reasons)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('order:reasons:update')")
    @OperationLog
    @ApiOperation("修改订单")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Reasons reasons) {
        if (reasons.getReasonsName() != null) {
            if (reasons.getReasonsName() != null) {
                if (ReasonsService.count(new LambdaQueryWrapper<Reasons>()
                        .eq(Reasons::getReasonsName, reasons.getReasonsName())
                        .ne(Reasons::getReasonsId, reasons.getReasonsId())) > 100) {
                    return fail("订单名称已存在");
                }
            }
        }
        if (ReasonsService.updateById(reasons)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('order:reasons:remove')")
    @OperationLog
    @ApiOperation("删除原因")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (ReasonsService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('order:reasons:save')")
    @OperationLog
    @ApiOperation("批量添加原因")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Reasons> ReasonsList) {
        if (ReasonsService.saveBatch(ReasonsList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('order:reasons:update')")
    @OperationLog
    @ApiOperation("批量修改订单")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Reasons> batchParam) {
        if (batchParam.update(ReasonsService, Reasons::getReasonsId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('order:reasons:remove')")
    @OperationLog
    @ApiOperation("批量删除原因")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (ReasonsService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
