package com.eleadmin.itemlist.item.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.itemlist.item.entity.Item;
import com.eleadmin.itemlist.item.param.ItemParam;
import com.eleadmin.itemlist.item.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品控制器
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Api(tags = "商品管理")
@RestController
@RequestMapping("/api/itemlist/item")
public class ItemController extends BaseController {
    @Resource
    private ItemService itemService;

    @PreAuthorize("hasAuthority('itemlist:item:list')")
    @OperationLog
    @ApiOperation("分页查询商品")
    @GetMapping("/page")
    public ApiResult<PageResult<Item>> page(ItemParam param) {
        return success(itemService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('itemlist:item:list')")
    @OperationLog
    @ApiOperation("查询全部商品")
    @GetMapping()
    public ApiResult<List<Item>> list(ItemParam param) {
        return success(itemService.listRel(param));
    }

    @PreAuthorize("hasAuthority('itemlist:item:list')")
    @OperationLog
    @ApiOperation("根据id查询商品")
    @GetMapping("/{id}")
    public ApiResult<Item> get(@PathVariable("id") Integer id) {
        return success(itemService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('itemlist:item:save')")
    @OperationLog
    @ApiOperation("添加商品")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Item item) {

        if (itemService.count(new LambdaQueryWrapper<Item>()
                .eq(Item::getItemName, item.getItemName())) > 0) {
            return fail("商品名称已存在");
        }
        if (itemService.save(item)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('itemlist:item:update')")
    @OperationLog
    @ApiOperation("修改商品")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Item item) {
        if (item.getItemName() != null) {
            if (itemService.count(new LambdaQueryWrapper<Item>()
                    .eq(Item::getItemName, item.getItemName())
                    .ne(Item::getItemId, item.getItemId())) > 0) {
                return fail("商品名称已存在");
            }
        }
        if (itemService.updateById(item)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('itemlist:item:remove')")
    @OperationLog
    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (itemService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('itemlist:item:save')")
    @OperationLog
    @ApiOperation("批量添加商品")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Item> itemList) {
        if (itemService.saveBatch(itemList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('itemlist:item:update')")
    @OperationLog
    @ApiOperation("批量修改商品")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Item> batchParam) {
        if (batchParam.update(itemService, Item::getItemId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('itemlist:item:remove')")
    @OperationLog
    @ApiOperation("批量删除商品")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (itemService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
