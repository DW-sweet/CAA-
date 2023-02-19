package com.eleadmin.order.reasons.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;

import java.util.List;

/**
 * 学生Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface ReasonsService extends IService<com.eleadmin.order.reasons.entity.Reasons> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Reasons>
     */
    PageResult<com.eleadmin.order.reasons.entity.Reasons> pageRel(com.eleadmin.order.reasons.param.ReasonsParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Reasons>
     */
    List<com.eleadmin.order.reasons.entity.Reasons> listRel(com.eleadmin.order.reasons.param.ReasonsParam param);

    /**
     * 根据id查询
     *
     * @param ReasonsId 机构id
     * @return Reasons
     */
    com.eleadmin.order.reasons.entity.Reasons getByIdRel(Integer ReasonsId);

}
