package com.eleadmin.order.reasons.entity;

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
@ApiModel(description = "退货原因")
@TableName("reasons")
public class Reasons implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单号id")
    @TableId(type = IdType.AUTO)
    private Integer reasonsId;

    @ApiModelProperty(value = "名称")
    private String reasonsName;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "添加时间")
    private String time;

    @ApiModelProperty(value = "是否可用")
    private String yesno;

    @ApiModelProperty(value = "单号")
    private String code;
}
