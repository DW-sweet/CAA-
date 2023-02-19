package com.eleadmin.animalAid.animal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleadmin.animalAid.animal.entity.Animal;
import com.eleadmin.animalAid.animal.service.param.AnimalParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface AnimalMapper extends BaseMapper<Animal> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Animal>
     */
    List<Animal> selectPageRel(@Param("page") IPage<Animal> page,
                                     @Param("param") AnimalParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Animal>
     */
    List<Animal> selectListRel(@Param("param") AnimalParam param);

}
