package com.eleadmin.order.command.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.order.command.entity.Command;

import java.util.List;

/**
 * 学生Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface CommandService extends IService<Command> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Command>
     */
    PageResult<Command> pageRel(com.eleadmin.order.command.param.CommandParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Command>
     */
    List<Command> listRel(com.eleadmin.order.command.param.CommandParam param);

    /**
     * 根据id查询
     *
     * @param CommandId 机构id
     * @return Command
     */
    Command getByIdRel(Integer CommandId);

}
