package com.eleadmin.dashboard.adoption.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleadmin.dashboard.adoption.entity.Adoption;
import com.eleadmin.dashboard.adoption.param.AdoptionParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface AdoptionMapper extends BaseMapper<Adoption> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Adoption>
     */
    List<Adoption> selectPageRel(@Param("page") IPage<Adoption> page,
                                     @Param("param") AdoptionParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Adoption>
     */
    List<Adoption> selectListRel(@Param("param") AdoptionParam param);

}
