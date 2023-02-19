package com.eleadmin.order.greturn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.order.greturn.entity.Greturn;
import com.eleadmin.order.greturn.mapper.GreturnMapper;
import com.eleadmin.order.greturn.param.GreturnParam;
import com.eleadmin.order.greturn.service.GreturnService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class GreturnServiceImpl extends ServiceImpl<GreturnMapper, Greturn>
        implements GreturnService {

    @Override
    public PageResult<Greturn> pageRel(GreturnParam param) {
        PageParam<Greturn, GreturnParam> page = new PageParam<>(param);
        page.setDefaultOrder("greturn_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<Greturn> listRel(GreturnParam param) {
        PageParam<Greturn, GreturnParam> page = new PageParam<>(param);
        page.setDefaultOrder("greturn_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public Greturn getByIdRel(Integer greturnId) {
        GreturnParam param = new GreturnParam();
        param.setGreturnId(greturnId);
        return param.getOne(baseMapper.selectListRel(param));
    }

}
