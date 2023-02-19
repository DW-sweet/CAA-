package com.eleadmin.itemlist.classify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleadmin.itemlist.classify.entity.Classify;
import com.eleadmin.itemlist.classify.param.ClassifyParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface ClassifyMapper extends BaseMapper<Classify> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Classify>
     */
    List<Classify> selectPageRel(@Param("page") IPage<Classify> page,
                                     @Param("param") ClassifyParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Classify>
     */
    List<Classify> selectListRel(@Param("param") ClassifyParam param);

}
