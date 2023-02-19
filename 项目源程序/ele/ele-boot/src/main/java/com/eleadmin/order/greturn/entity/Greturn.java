package com.eleadmin.order.greturn.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Data
@ApiModel(description = "学生")
@TableName("order_greturn")
public class Greturn implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableId(type = IdType.AUTO)
    private Integer greturnId;

    @ApiModelProperty(value = "商品名称")
    private String greturnName;


    @ApiModelProperty(value = "退款金额")
    private Double money;

    @ApiModelProperty(value = "服务单号")
    private Integer rnumber;

    @ApiModelProperty(value = "退款用户")
    private String ruser;

    @ApiModelProperty(value = "状态")
    private String state;


}
