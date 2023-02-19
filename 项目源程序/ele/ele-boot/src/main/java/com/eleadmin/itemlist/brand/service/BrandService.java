package com.eleadmin.itemlist.brand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.itemlist.brand.entity.Brand;
import com.eleadmin.itemlist.brand.service.param.BrandParam;


import java.util.List;

/**
 * 学生Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface BrandService extends IService<Brand> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Brand>
     */
    PageResult<Brand> pageRel(BrandParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Brand>
     */
    List<Brand> listRel(BrandParam param);

    /**
     * 根据id查询
     *
     * @param brandId 机构id
     * @return Brand
     */
    Brand getByIdRel(Integer brandId);

}
