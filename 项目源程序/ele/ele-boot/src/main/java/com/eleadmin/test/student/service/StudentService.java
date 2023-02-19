package com.eleadmin.test.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.test.student.entity.Student;
import com.eleadmin.test.student.param.StudentParam;


import java.util.List;

/**
 * 学生Service
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface StudentService extends IService<Student> {

    /**
     * 关联分页查询
     *
     * @param param 查询参数
     * @return PageResult<Student>
     */
    PageResult<Student> pageRel(StudentParam param);

    /**
     * 关联查询全部
     *
     * @param param 查询参数
     * @return List<Student>
     */
    List<Student> listRel(StudentParam param);

    /**
     * 根据id查询
     *
     * @param studentId 机构id
     * @return Student
     */
    Student getByIdRel(Integer studentId);

}
