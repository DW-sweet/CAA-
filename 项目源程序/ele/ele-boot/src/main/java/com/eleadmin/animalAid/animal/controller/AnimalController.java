package com.eleadmin.animalAid.animal.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.animalAid.animal.entity.Animal;
import com.eleadmin.animalAid.animal.service.param.AnimalParam;
import com.eleadmin.animalAid.animal.service.AnimalService;
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
@RequestMapping("/api/animalAid/animal")
public class AnimalController extends BaseController {
    @Resource
    private AnimalService animalService;

    @PreAuthorize("hasAuthority('animalAid:animal:list')")
    @OperationLog
    @ApiOperation("分页查询学生")
    @GetMapping("/page")
    public ApiResult<PageResult<Animal>> page(AnimalParam param) {
        return success(animalService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('animalAid:animal:list')")
    @OperationLog
    @ApiOperation("查询全部学生")
    @GetMapping()
    public ApiResult<List<Animal>> list(AnimalParam param) {
        return success(animalService.listRel(param));
    }

    @PreAuthorize("hasAuthority('animalAid:animal:list')")
    @OperationLog
    @ApiOperation("根据id查询学生")
    @GetMapping("/{id}")
    public ApiResult<Animal> get(@PathVariable("id") Integer id) {
        return success(animalService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('animalAid:animal:save')")
    @OperationLog
    @ApiOperation("添加学生")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Animal animal) {

        if (animalService.count(new LambdaQueryWrapper<Animal>()
                .eq(Animal::getAnimalName, animal.getAnimalName())) > 0) {
            return fail("学生名称已存在");
        }
        if (animalService.save(animal)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('animalAid:animal:update')")
    @OperationLog
    @ApiOperation("修改学生")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Animal animal) {
        if (animal.getAnimalName() != null) {
            if (animalService.count(new LambdaQueryWrapper<Animal>()
                    .eq(Animal::getAnimalName, animal.getAnimalName())
                    .ne(Animal::getAnimalId, animal.getAnimalId())) > 0) {
                return fail("学生名称已存在");
            }
        }
        if (animalService.updateById(animal)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('animalAid:animal:remove')")
    @OperationLog
    @ApiOperation("删除学生")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (animalService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('animalAid:animal:save')")
    @OperationLog
    @ApiOperation("批量添加学生")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Animal> animalList) {
        if (animalService.saveBatch(animalList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('animalAid:animal:update')")
    @OperationLog
    @ApiOperation("批量修改学生")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Animal> batchParam) {
        if (batchParam.update(animalService, Animal::getAnimalId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('animalAid:animal:remove')")
    @OperationLog
    @ApiOperation("批量删除学生")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (animalService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
