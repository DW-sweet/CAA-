package com.eleadmin.itemlist.brand.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.itemlist.brand.entity.Brand;
import com.eleadmin.itemlist.brand.mapper.BrandMapper;
import com.eleadmin.itemlist.brand.service.param.BrandParam;
import com.eleadmin.itemlist.brand.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand>
        implements BrandService {

    @Override
    public PageResult<Brand> pageRel(BrandParam param) {
        PageParam<Brand, BrandParam> page = new PageParam<>(param);
        page.setDefaultOrder("brand_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<Brand> listRel(BrandParam param) {
        PageParam<Brand, BrandParam> page = new PageParam<>(param);
        page.setDefaultOrder("brand_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public Brand getByIdRel(Integer brandId) {
        BrandParam param = new BrandParam();
        param.setBrandId(brandId);
        return param.getOne(baseMapper.selectListRel(param));
    }


}
