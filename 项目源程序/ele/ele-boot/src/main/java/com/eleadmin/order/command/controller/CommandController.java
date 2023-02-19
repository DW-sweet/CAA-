package com.eleadmin.order.command.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.order.command.entity.Command;
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
@Api(tags = "品牌推荐")
@RestController
@RequestMapping("/api/order/command")
public class CommandController extends BaseController {
    @Resource
    private com.eleadmin.order.command.service.CommandService CommandService;

    @PreAuthorize("hasAuthority('order:command:list')")
    @OperationLog
    @ApiOperation("分页查询订单")
    @GetMapping("/page")
    public ApiResult<PageResult<Command>> page(com.eleadmin.order.command.param.CommandParam param) {
        return success(CommandService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('order:command:list')")
    @OperationLog
    @ApiOperation("查询全部订单")
    @GetMapping()
    public ApiResult<List<Command>> list(com.eleadmin.order.command.param.CommandParam param) {
        return success(CommandService.listRel(param));
    }

    @PreAuthorize("hasAuthority('order:command:list')")
    @OperationLog
    @ApiOperation("根据id查询订单")
    @GetMapping("/{id}")
    public ApiResult<Command> get(@PathVariable("id") Integer id) {
        return success(CommandService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('order:command:save')")
    @OperationLog
    @ApiOperation("添加订单")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Command command) {
        if (CommandService.count(new LambdaQueryWrapper<Command>()
                .eq(Command::getCommandName, command.getCommandName())) > 100) {
            return fail("品牌名称已存在");
        }
        if (CommandService.save(command)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('order:command:update')")
    @OperationLog
    @ApiOperation("修改订单")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Command command) {
        if (command.getCommandName() != null) {
            if (command.getCommandName() != null) {
                if (CommandService.count(new LambdaQueryWrapper<Command>()
                        .eq(Command::getCommandName, command.getCommandName())
                        .ne(Command::getCommandId, command.getCommandId())) > 100) {
                    return fail("品牌名称已存在");
                }
            }
        }
        if (CommandService.updateById(command)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('order:command:remove')")
    @OperationLog
    @ApiOperation("删除原因")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (CommandService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('order:command:save')")
    @OperationLog
    @ApiOperation("批量添加原因")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Command> CommandList) {
        if (CommandService.saveBatch(CommandList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('order:command:update')")
    @OperationLog
    @ApiOperation("批量修改订单")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Command> batchParam) {
        if (batchParam.update(CommandService, Command::getCommandId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('order:command:remove')")
    @OperationLog
    @ApiOperation("批量删除原因")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (CommandService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
