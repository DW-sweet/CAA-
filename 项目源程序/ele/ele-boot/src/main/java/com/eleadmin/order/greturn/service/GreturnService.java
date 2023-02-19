package com.eleadmin.order.greturn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.order.greturn.entity.Greturn;
import com.eleadmin.order.greturn.param.GreturnParam;


import java.util.List;

/**
 * 学生Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface GreturnService extends IService<Greturn> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Greturn>
     */
    PageResult<Greturn> pageRel(GreturnParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Greturn>
     */
    List<Greturn> listRel(GreturnParam param);

    /**
     * 根据id查询
     *
     * @param greturnId 机构id
     * @return Greturn
     */
    Greturn getByIdRel(Integer greturnId);

}
