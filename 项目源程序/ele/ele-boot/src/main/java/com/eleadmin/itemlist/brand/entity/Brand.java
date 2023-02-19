package com.eleadmin.itemlist.brand.entity;

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
@TableName("itemlist_brand")
public class Brand implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生id")
    @TableId(type = IdType.AUTO)
    private Integer brandId;

    @ApiModelProperty(value = "名称")
    private String brandName;

//    @ApiModelProperty(value = "年龄")
//    private Integer age;

    @ApiModelProperty(value = "品牌首字母")
    private String alpha;

    @ApiModelProperty(value = "是否展示")
    private String bshow;

    @ApiModelProperty(value = "评分")
    private Integer score;

}
