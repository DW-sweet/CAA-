package com.eleadmin.test.student.entity;

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
@TableName("test_student")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生id")
    @TableId(type = IdType.AUTO)
    private Integer studentId;

    @ApiModelProperty(value = "名称")
    private String studentName;


    @ApiModelProperty(value = "年龄")
    private Integer age;
}
