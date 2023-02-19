package com.eleadmin.test.award.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.test.award.entity.Award;
import com.eleadmin.test.award.param.AwardParam;
import com.eleadmin.test.award.service.AwardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "奖项管理")
@RestController
@RequestMapping("/api/test/award")
public class AwardController extends BaseController {
    @Resource
    private AwardService awardService;

    @PreAuthorize("hasAuthority('sys:award:list')")
    @OperationLog
    @ApiOperation("分页查询奖项")
    @GetMapping("/page")
    public ApiResult<PageResult<Award>> page(AwardParam param) {
        return success(awardService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('sys:award:list')")
    @OperationLog
    @ApiOperation("查询全部奖项")
    @GetMapping()
    public ApiResult<List<Award>> list(AwardParam param) {
        return success(awardService.listRel(param));
    }

    @PreAuthorize("hasAuthority('sys:award:list')")
    @OperationLog
    @ApiOperation("根据id查询奖项")
    @GetMapping("/{id}")
    public ApiResult<Award> get(@PathVariable("id") Integer id) {
        return success(awardService.getByIdRel(id));
    }

    @PreAuthorize("hasAuthority('sys:award:save')")
    @OperationLog
    @ApiOperation("添加奖项")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Award award) {

        if (awardService.count(new LambdaQueryWrapper<Award>()
                .eq(Award::getAwardName, award.getAwardName())) > 0) {
            return fail("机构名称已存在");
        }
        if (awardService.save(award)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('sys:award:update')")
    @OperationLog
    @ApiOperation("修改奖项")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Award award) {
        if (award.getAwardName() != null) {
            if (awardService.count(new LambdaQueryWrapper<Award>()
                    .eq(Award::getAwardName, award.getAwardName())
                    .ne(Award::getAwardId, award.getAwardId())) > 0) {
                return fail("机构名称已存在");
            }
        }
        if (awardService.updateById(award)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('sys:award:remove')")
    @OperationLog
    @ApiOperation("删除奖项")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (awardService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('sys:award:save')")
    @OperationLog
    @ApiOperation("批量添加奖项")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Award> awardList) {
        if (awardService.saveBatch(awardList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('sys:award:update')")
    @OperationLog
    @ApiOperation("批量修改奖项")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Award> batchParam) {
        if (batchParam.update(awardService, Award::getAwardId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('sys:award:remove')")
    @OperationLog
    @ApiOperation("批量删除奖项")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (awardService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
