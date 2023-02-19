package com.eleadmin.order.greturn.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.order.greturn.entity.Greturn;
import com.eleadmin.order.greturn.param.GreturnParam;
import com.eleadmin.order.greturn.service.GreturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学生控制器
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Api(tags = "学生管理")
@RestController
@RequestMapping("/api/order/greturn")
public class GreturnController extends BaseController {
    @Resource
    private GreturnService greturnService;

    @PreAuthorize("hasAuthority('order:greturn:list')")
    @OperationLog
    @ApiOperation("分页查询学生")
    @GetMapping("/page")
    public ApiResult<PageResult<Greturn>> page(GreturnParam param) {
        return success(greturnService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('order:greturn:list')")
    @OperationLog
    @ApiOperation("查询全部学生")
    @GetMapping()
    public ApiResult<List<Greturn>> list(GreturnParam param) {
        return success(greturnService.listRel(param));
    }

    @PreAuthorize("hasAuthority('order:greturn:list')")
    @OperationLog
    @ApiOperation("根据id查询学生")
    @GetMapping("/{id}")
    public ApiResult<Greturn> get(@PathVariable("id") Integer id) {
        return success(greturnService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('order:greturn:save')")
    @OperationLog
    @ApiOperation("添加学生")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Greturn greturn) {

        if (greturnService.count(new LambdaQueryWrapper<Greturn>()
                .eq(Greturn::getGreturnName, greturn.getGreturnName())) > 0) {
            return fail("学生名称已存在");
        }
        if (greturnService.save(greturn)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('order:greturn:update')")
    @OperationLog
    @ApiOperation("修改学生")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Greturn greturn) {
        if (greturn.getGreturnName() != null) {
            if (greturnService.count(new LambdaQueryWrapper<Greturn>()
                    .eq(Greturn::getGreturnName, greturn.getGreturnName())
                    .ne(Greturn::getGreturnId, greturn.getGreturnId())) > 0) {
                return fail("学生名称已存在");
            }
        }
        if (greturnService.updateById(greturn)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('order:greturn:remove')")
    @OperationLog
    @ApiOperation("删除学生")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (greturnService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('order:greturn:save')")
    @OperationLog
    @ApiOperation("批量添加学生")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Greturn> greturnList) {
        if (greturnService.saveBatch(greturnList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('order:greturn:update')")
    @OperationLog
    @ApiOperation("批量修改学生")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Greturn> batchParam) {
        if (batchParam.update(greturnService, Greturn::getGreturnId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('order:greturn:remove')")
    @OperationLog
    @ApiOperation("批量删除学生")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (greturnService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
