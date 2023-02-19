package com.eleadmin.dashboard.adoption.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.dashboard.adoption.entity.Adoption;
import com.eleadmin.dashboard.adoption.param.AdoptionParam;


import java.util.List;

/**
 * 学生Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface AdoptionService extends IService<Adoption> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Adoption>
     */
    PageResult<Adoption> pageRel(AdoptionParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Adoption>
     */
    List<Adoption> listRel(AdoptionParam param);

    /**
     * 根据id查询
     *
     * @param adoptionId 机构id
     * @return Adoption
     */
    Adoption getByIdRel(Integer adoptionId);

}
