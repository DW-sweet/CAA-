package com.eleadmin.test.award.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.test.award.entity.Award;
import com.eleadmin.test.award.param.AwardParam;


import java.util.List;

/**
 * 奖项Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface AwardService extends IService<Award> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Award>
     */
    PageResult<Award> pageRel(AwardParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Award>
     */
    List<Award> listRel(AwardParam param);

    /**
     * 根据id查询
     *
     * @param awardId 机构id
     * @return Award
     */
    Award getByIdRel(Integer awardId);

}
