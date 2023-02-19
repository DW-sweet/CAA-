package com.eleadmin.itemlist.classify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.itemlist.classify.entity.Classify;
import com.eleadmin.itemlist.classify.mapper.ClassifyMapper;
import com.eleadmin.itemlist.classify.param.ClassifyParam;
import com.eleadmin.itemlist.classify.service.ClassifyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapper, Classify>
        implements ClassifyService {

    @Override
    public PageResult<Classify> pageRel(ClassifyParam param) {
        PageParam<Classify, ClassifyParam> page = new PageParam<>(param);
        page.setDefaultOrder("classify_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<Classify> listRel(ClassifyParam param) {
        PageParam<Classify, ClassifyParam> page = new PageParam<>(param);
        page.setDefaultOrder("classify_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public Classify getByIdRel(Integer classifyId) {
        ClassifyParam param = new ClassifyParam();
        param.setClassifyId(classifyId);
        return param.getOne(baseMapper.selectListRel(param));
    }

}
