package com.eleadmin.animalAid.animal.entity;

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
@TableName("test_animal")
public class Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生id")
    @TableId(type = IdType.AUTO)
    private Integer animalId;

    @ApiModelProperty(value = "名称")
    private String animalName;

    @ApiModelProperty(value = "性别")
    private String gender;
//    @ApiModelProperty(value = "年龄")
//    private Integer age;

    @ApiModelProperty(value = "活动范围")
    private String arange;
    @ApiModelProperty(value = "是否绝育")
    private String ster;
    @ApiModelProperty(value = "是否领养")
    private String adopt;
    @ApiModelProperty(value = "领养人")
    private String master;

}
