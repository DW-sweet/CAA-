package com.eleadmin.test.award.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleadmin.test.award.entity.Award;
import com.eleadmin.test.award.param.AwardParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 奖项Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface AwardMapper extends BaseMapper<Award> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Award>
     */
    List<Award> selectPageRel(@Param("page") IPage<Award> page,
                                     @Param("param") AwardParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Award>
     */
    List<Award> selectListRel(@Param("param") AwardParam param);

}
