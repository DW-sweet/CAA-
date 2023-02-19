package com.eleadmin.itemlist.classify.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.itemlist.classify.entity.Classify;
import com.eleadmin.itemlist.classify.param.ClassifyParam;


import java.util.List;

/**
 * 分类Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface ClassifyService extends IService<Classify> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Classify>
     */
    PageResult<Classify> pageRel(ClassifyParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Classify>
     */
    List<Classify> listRel(ClassifyParam param);

    /**
     * 根据id查询
     *
     * @param classifyId 机构id
     * @return Classify
     */
    Classify getByIdRel(Integer classifyId);

}
