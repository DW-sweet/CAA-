package com.eleadmin.test.award.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 奖项
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Data
@ApiModel(description = "获奖")
@TableName("test_award")
public class Award implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "奖项id")
    @TableId(type = IdType.AUTO)
    private Integer awardId;

    @ApiModelProperty(value = "名称")
    private String awardName;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "项目名")
    private String program;

    @ApiModelProperty(value = "比赛名")
    private String racename;

    @ApiModelProperty(value = "指导老师")
    private String instructor;

    @ApiModelProperty(value = "奖金")
    private Integer money;

    @ApiModelProperty(value = "学校")
    private String college;

    @ApiModelProperty(value = "举办单位")
    private String awarder;

    @ApiModelProperty(value = "等级")
    private String grade;

    @ApiModelProperty(value = "颁奖方式")
    private String way;

    @ApiModelProperty(value = "证书编号")
    private String serial;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
