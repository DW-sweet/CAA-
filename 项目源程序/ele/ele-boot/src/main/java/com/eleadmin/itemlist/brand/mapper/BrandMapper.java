package com.eleadmin.itemlist.brand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleadmin.itemlist.brand.entity.Brand;
import com.eleadmin.itemlist.brand.service.param.BrandParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Brand>
     */
    List<Brand> selectPageRel(@Param("page") IPage<Brand> page,
                                     @Param("param") BrandParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Brand>
     */
    List<Brand> selectListRel(@Param("param") BrandParam param);

}
