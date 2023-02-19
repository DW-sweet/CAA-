package com.eleadmin.itemlist.brand.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.itemlist.brand.entity.Brand;
import com.eleadmin.itemlist.brand.service.param.BrandParam;
import com.eleadmin.itemlist.brand.service.BrandService;
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
@RequestMapping("/api/itemlist/brand")
public class BrandController extends BaseController {
    @Resource
    private BrandService brandService;

    @PreAuthorize("hasAuthority('itemlist:brand:list')")
    @OperationLog
    @ApiOperation("分页查询学生")
    @GetMapping("/page")
    public ApiResult<PageResult<Brand>> page(BrandParam param) {
        return success(brandService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('itemlist:brand:list')")
    @OperationLog
    @ApiOperation("查询全部学生")
    @GetMapping()
    public ApiResult<List<Brand>> list(BrandParam param) {
        return success(brandService.listRel(param));
    }

    @PreAuthorize("hasAuthority('itemlist:brand:list')")
    @OperationLog
    @ApiOperation("根据id查询学生")
    @GetMapping("/{id}")
    public ApiResult<Brand> get(@PathVariable("id") Integer id) {
        return success(brandService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('itemlist:brand:save')")
    @OperationLog
    @ApiOperation("添加学生")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Brand brand) {

        if (brandService.count(new LambdaQueryWrapper<Brand>()
                .eq(Brand::getBrandName, brand.getBrandName())) > 0) {
            return fail("学生名称已存在");
        }
        if (brandService.save(brand)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('itemlist:brand:update')")
    @OperationLog
    @ApiOperation("修改学生")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Brand brand) {
        if (brand.getBrandName() != null) {
            if (brandService.count(new LambdaQueryWrapper<Brand>()
                    .eq(Brand::getBrandName, brand.getBrandName())
                    .ne(Brand::getBrandId, brand.getBrandId())) > 0) {
                return fail("学生名称已存在");
            }
        }
        if (brandService.updateById(brand)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('itemlist:brand:remove')")
    @OperationLog
    @ApiOperation("删除学生")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (brandService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('itemlist:brand:save')")
    @OperationLog
    @ApiOperation("批量添加学生")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Brand> brandList) {
        if (brandService.saveBatch(brandList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('itemlist:brand:update')")
    @OperationLog
    @ApiOperation("批量修改学生")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Brand> batchParam) {
        if (batchParam.update(brandService, Brand::getBrandId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('itemlist:brand:remove')")
    @OperationLog
    @ApiOperation("批量删除学生")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (brandService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
