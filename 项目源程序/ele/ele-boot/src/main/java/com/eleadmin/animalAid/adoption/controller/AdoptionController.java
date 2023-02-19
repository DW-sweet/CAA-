package com.eleadmin.dashboard.adoption.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.dashboard.adoption.entity.Adoption;
import com.eleadmin.dashboard.adoption.param.AdoptionParam;
import com.eleadmin.dashboard.adoption.service.AdoptionService;
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
@RequestMapping("/api/dashboard/adoption")
public class AdoptionController extends BaseController {
    @Resource
    private AdoptionService adoptionService;

    @PreAuthorize("hasAuthority('dashboard:adoption:list')")
    @OperationLog
    @ApiOperation("分页查询学生")
    @GetMapping("/page")
    public ApiResult<PageResult<Adoption>> page(AdoptionParam param) {
        return success(adoptionService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('dashboard:adoption:list')")
    @OperationLog
    @ApiOperation("查询全部学生")
    @GetMapping()
    public ApiResult<List<Adoption>> list(AdoptionParam param) {
        return success(adoptionService.listRel(param));
    }

    @PreAuthorize("hasAuthority('dashboard:adoption:list')")
    @OperationLog
    @ApiOperation("根据id查询学生")
    @GetMapping("/{id}")
    public ApiResult<Adoption> get(@PathVariable("id") Integer id) {
        return success(adoptionService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('dashboard:adoption:save')")
    @OperationLog
    @ApiOperation("添加学生")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Adoption adoption) {

        if (adoptionService.count(new LambdaQueryWrapper<Adoption>()
                .eq(Adoption::getAdoptionName, adoption.getAdoptionName())) > 0) {
            return fail("名称已存在");
        }
        if (adoptionService.save(adoption)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('dashboard:adoption:update')")
    @OperationLog
    @ApiOperation("修改学生")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Adoption adoption) {
        if (adoption.getAdoptionName() != null) {
            if (adoptionService.count(new LambdaQueryWrapper<Adoption>()
                    .eq(Adoption::getAdoptionName, adoption.getAdoptionName())
                    .ne(Adoption::getAdoptionId, adoption.getAdoptionId())) > 0) {
                return fail("学生名称已存在");
            }
        }
        if (adoptionService.updateById(adoption)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('dashboard:adoption:remove')")
    @OperationLog
    @ApiOperation("删除学生")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (adoptionService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('dashboard:adoption:save')")
    @OperationLog
    @ApiOperation("批量添加学生")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Adoption> adoptionList) {
        if (adoptionService.saveBatch(adoptionList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('dashboard:adoption:update')")
    @OperationLog
    @ApiOperation("批量修改学生")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Adoption> batchParam) {
        if (batchParam.update(adoptionService, Adoption::getAdoptionId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('dashboard:adoption:remove')")
    @OperationLog
    @ApiOperation("批量删除学生")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (adoptionService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
