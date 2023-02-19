package com.eleadmin.order.command.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.order.command.entity.Command;
import com.eleadmin.order.command.service.CommandService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class CommandServiceImpl extends ServiceImpl<com.eleadmin.order.command.mapper.CommandMapper, Command>
        implements CommandService {

    @Override
    public PageResult<Command> pageRel(com.eleadmin.order.command.param.CommandParam param) {
        PageParam<Command, com.eleadmin.order.command.param.CommandParam> page = new PageParam<>(param);
        page.setDefaultOrder("Command_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<Command> listRel(com.eleadmin.order.command.param.CommandParam param) {
        PageParam<Command, com.eleadmin.order.command.param.CommandParam> page = new PageParam<>(param);
        page.setDefaultOrder("Command_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public Command getByIdRel(Integer CommandId) {
        com.eleadmin.order.command.param.CommandParam param = new com.eleadmin.order.command.param.CommandParam();
        param.setCommandId(CommandId);
        return param.getOne(baseMapper.selectListRel(param));
    }

}
