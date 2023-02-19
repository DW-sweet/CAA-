package com.eleadmin.dashboard.adoption.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.dashboard.adoption.entity.Adoption;
import com.eleadmin.dashboard.adoption.mapper.AdoptionMapper;
import com.eleadmin.dashboard.adoption.param.AdoptionParam;
import com.eleadmin.dashboard.adoption.service.AdoptionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class AdoptionServiceImpl extends ServiceImpl<AdoptionMapper, Adoption>
        implements AdoptionService {

    @Override
    public PageResult<Adoption> pageRel(AdoptionParam param) {
        PageParam<Adoption, AdoptionParam> page = new PageParam<>(param);
        page.setDefaultOrder("adoption_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<Adoption> listRel(AdoptionParam param) {
        PageParam<Adoption, AdoptionParam> page = new PageParam<>(param);
        page.setDefaultOrder("adoption_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public Adoption getByIdRel(Integer adoptionId) {
        AdoptionParam param = new AdoptionParam();
        param.setAdoptionId(adoptionId);
        return param.getOne(baseMapper.selectListRel(param));
    }

}
