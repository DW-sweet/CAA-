package com.eleadmin.order.greturn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleadmin.order.greturn.entity.Greturn;
import com.eleadmin.order.greturn.param.GreturnParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface GreturnMapper extends BaseMapper<Greturn> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Greturn>
     */
    List<Greturn> selectPageRel(@Param("page") IPage<Greturn> page,
                                     @Param("param") GreturnParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Greturn>
     */
    List<Greturn> selectListRel(@Param("param") GreturnParam param);

}
