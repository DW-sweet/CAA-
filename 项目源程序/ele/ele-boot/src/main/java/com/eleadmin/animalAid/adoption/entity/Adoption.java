package com.eleadmin.dashboard.adoption.entity;

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
@ApiModel(description = "领养")
@TableName("test_adoption")
public class Adoption implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "领养id")
    @TableId(type = IdType.AUTO)
    private Integer adoptionId;

    @ApiModelProperty(value = "名称")
    private String adoptionName;

    @ApiModelProperty(value = "动物id")
    private Integer animalid;

    @ApiModelProperty(value = "动物名字")
    private String animalname;

    @ApiModelProperty(value = "用户")
    private Integer userid;

    @ApiModelProperty(value = "用户名字")
    private String username;

    @ApiModelProperty(value = "状态")
    private String adoptionstatus;
}
