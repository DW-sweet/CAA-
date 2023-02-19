package com.eleadmin.itemlist.classify.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.itemlist.classify.entity.Classify;
import com.eleadmin.itemlist.classify.param.ClassifyParam;
import com.eleadmin.itemlist.classify.service.ClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类控制器
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Api(tags = "分类管理")
@RestController
@RequestMapping("/api/itemlist/classify")
public class ClassifyController extends BaseController {
    @Resource
    private ClassifyService classifyService;

    @PreAuthorize("hasAuthority('itemlist:classify:list')")
    @OperationLog
    @ApiOperation("分页查询分类")
    @GetMapping("/page")
    public ApiResult<PageResult<Classify>> page(ClassifyParam param) {
        return success(classifyService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('itemlist:classify:list')")
    @OperationLog
    @ApiOperation("查询全部分类")
    @GetMapping()
    public ApiResult<List<Classify>> list(ClassifyParam param) {
        return success(classifyService.listRel(param));
    }

    @PreAuthorize("hasAuthority('itemlist:classify:list')")
    @OperationLog
    @ApiOperation("根据id查询分类")
    @GetMapping("/{id}")
    public ApiResult<Classify> get(@PathVariable("id") Integer id) {
        return success(classifyService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('itemlist:classify:save')")
    @OperationLog
    @ApiOperation("添加分类")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Classify classify) {

        if (classifyService.count(new LambdaQueryWrapper<Classify>()
                .eq(Classify::getClassifyName, classify.getClassifyName())) > 0) {
            return fail("分类名称已存在");
        }
        if (classifyService.save(classify)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('itemlist:classify:update')")
    @OperationLog
    @ApiOperation("修改分类")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Classify classify) {
        if (classify.getClassifyName() != null) {
            if (classifyService.count(new LambdaQueryWrapper<Classify>()
                    .eq(Classify::getClassifyName, classify.getClassifyName())
                    .ne(Classify::getClassifyId, classify.getClassifyId())) > 0) {
                return fail("分类名称已存在");
            }
        }
        if (classifyService.updateById(classify)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('itemlist:classify:remove')")
    @OperationLog
    @ApiOperation("删除分类")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (classifyService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('itemlist:classify:save')")
    @OperationLog
    @ApiOperation("批量添加分类")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Classify> classifyList) {
        if (classifyService.saveBatch(classifyList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('itemlist:classify:update')")
    @OperationLog
    @ApiOperation("批量修改分类")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Classify> batchParam) {
        if (batchParam.update(classifyService, Classify::getClassifyId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('itemlist:classify:remove')")
    @OperationLog
    @ApiOperation("批量删除分类")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (classifyService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
