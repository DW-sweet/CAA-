package com.eleadmin.order.caa_orderlist.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@ApiModel(description = "订单")
@TableName("caa_orderlist")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableId(type = IdType.AUTO)
    private Integer OrderId;

    //新增
    @ApiModelProperty(value = "订单编号")
    private Integer OrderNo;

    @ApiModelProperty(value = "订单时间")
    private String OrderTime;

    @ApiModelProperty(value = "订单账户")
    private String OrderAccount;

    @ApiModelProperty(value = "订单账户")
    private String OrderMoney;

    @ApiModelProperty(value = "订单状态")
    private String OrderState;

    @ApiModelProperty(value = "订单来源")
    private String OrderSource;

    @ApiModelProperty(value = "支付方式")
    private String PayWay;
    /////
    @ApiModelProperty(value = "名称")
    private String OrderName;
}
