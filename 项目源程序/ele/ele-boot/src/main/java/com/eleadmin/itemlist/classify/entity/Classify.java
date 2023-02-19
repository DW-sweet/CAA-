package com.eleadmin.itemlist.classify.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Data
@ApiModel(description = "分类")
@TableName("itemlist_classify")
public class Classify implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类id")
    @TableId(type = IdType.AUTO)
    private Integer classifyId;

    @ApiModelProperty(value = "名称")
    private String classifyName;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "导航")
    private String navigation;

    @ApiModelProperty(value = "设置")
    private String install;


}
