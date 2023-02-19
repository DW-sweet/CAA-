package com.eleadmin.animalAid.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.animalAid.animal.entity.Animal;
import com.eleadmin.animalAid.animal.service.param.AnimalParam;


import java.util.List;

/**
 * 学生Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface AnimalService extends IService<Animal> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Animal>
     */
    PageResult<Animal> pageRel(AnimalParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Animal>
     */
    List<Animal> listRel(AnimalParam param);

    /**
     * 根据id查询
     *
     * @param animalId 机构id
     * @return Animal
     */
    Animal getByIdRel(Integer animalId);

}
