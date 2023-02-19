package com.eleadmin.animalAid.animal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.animalAid.animal.entity.Animal;
import com.eleadmin.animalAid.animal.mapper.AnimalMapper;
import com.eleadmin.animalAid.animal.service.param.AnimalParam;
import com.eleadmin.animalAid.animal.service.AnimalService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class AnimalServiceImpl extends ServiceImpl<AnimalMapper, Animal>
        implements AnimalService {

    @Override
    public PageResult<Animal> pageRel(AnimalParam param) {
        PageParam<Animal, AnimalParam> page = new PageParam<>(param);
        page.setDefaultOrder("animal_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<Animal> listRel(AnimalParam param) {
        PageParam<Animal, AnimalParam> page = new PageParam<>(param);
        page.setDefaultOrder("animal_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public Animal getByIdRel(Integer animalId) {
        AnimalParam param = new AnimalParam();
        param.setAnimalId(animalId);
        return param.getOne(baseMapper.selectListRel(param));
    }


}
