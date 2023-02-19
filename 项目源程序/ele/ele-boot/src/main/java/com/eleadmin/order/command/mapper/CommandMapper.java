package com.eleadmin.order.command.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleadmin.order.command.entity.Command;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface CommandMapper extends BaseMapper<Command> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Order>
     */
    List<Command> selectPageRel(@Param("page") IPage<Command> page,
                                @Param("param") com.eleadmin.order.command.param.CommandParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Order>
     */
    List<Command> selectListRel(@Param("param") com.eleadmin.order.command.param.CommandParam param);

}
