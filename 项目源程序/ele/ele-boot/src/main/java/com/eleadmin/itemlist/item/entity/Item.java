package com.eleadmin.itemlist.item.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Data
@ApiModel(description = "商品")
@TableName("test_item")
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableId(type = IdType.AUTO)
    private Integer itemId;

    @ApiModelProperty(value = "名称")
    private String itemName;

    @ApiModelProperty(value = "图片")
    private String photo;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "库存")
    private String store;

    @ApiModelProperty(value = "销量")
    private String sell;

    @ApiModelProperty(value = "标签")
    private String label;



}
