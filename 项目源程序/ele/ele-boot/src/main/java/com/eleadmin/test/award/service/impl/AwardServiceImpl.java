package com.eleadmin.test.award.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.test.award.entity.Award;
import com.eleadmin.test.award.mapper.AwardMapper;
import com.eleadmin.test.award.param.AwardParam;
import com.eleadmin.test.award.service.AwardService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 奖项Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award>
        implements AwardService {

    @Override
    public PageResult<Award> pageRel(AwardParam param) {
        PageParam<Award, AwardParam> page = new PageParam<>(param);
        page.setDefaultOrder("award_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<Award> listRel(AwardParam param) {
        PageParam<Award, AwardParam> page = new PageParam<>(param);
        page.setDefaultOrder("award_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public Award getByIdRel(Integer awardId) {
        AwardParam param = new AwardParam();
        param.setAwardId(awardId);
        return param.getOne(baseMapper.selectListRel(param));
    }

}
