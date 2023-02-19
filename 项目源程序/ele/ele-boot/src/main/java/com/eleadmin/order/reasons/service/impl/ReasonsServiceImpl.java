package com.eleadmin.order.reasons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.order.reasons.service.ReasonsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class ReasonsServiceImpl extends ServiceImpl<com.eleadmin.order.reasons.mapper.ReasonsMapper, com.eleadmin.order.reasons.entity.Reasons>
        implements ReasonsService {

    @Override
    public PageResult<com.eleadmin.order.reasons.entity.Reasons> pageRel(com.eleadmin.order.reasons.param.ReasonsParam param) {
        PageParam<com.eleadmin.order.reasons.entity.Reasons, com.eleadmin.order.reasons.param.ReasonsParam> page = new PageParam<>(param);
        page.setDefaultOrder("Reasons_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<com.eleadmin.order.reasons.entity.Reasons> listRel(com.eleadmin.order.reasons.param.ReasonsParam param) {
        PageParam<com.eleadmin.order.reasons.entity.Reasons, com.eleadmin.order.reasons.param.ReasonsParam> page = new PageParam<>(param);
        page.setDefaultOrder("Reasons_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public com.eleadmin.order.reasons.entity.Reasons getByIdRel(Integer ReasonsId) {
        com.eleadmin.order.reasons.param.ReasonsParam param = new com.eleadmin.order.reasons.param.ReasonsParam();
        param.setReasonsId(ReasonsId);
        return param.getOne(baseMapper.selectListRel(param));
    }

}
