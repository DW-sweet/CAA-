package com.eleadmin.order.command.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 学生
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Data
@ApiModel(description = "品牌推荐")
@TableName("command")
public class Command implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品牌id")
    @TableId(type = IdType.AUTO)
    private Integer commandId;

    @ApiModelProperty(value = "名称")
    private String commandName;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "是否可用")
    private String yesno;

    @ApiModelProperty(value = "排序")
    private String code;

    @ApiModelProperty(value = "推荐状态")
    private String state;
}
