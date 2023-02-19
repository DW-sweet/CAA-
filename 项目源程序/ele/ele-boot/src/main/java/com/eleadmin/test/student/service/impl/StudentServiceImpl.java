package com.eleadmin.test.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eleadmin.common.core.web.PageParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.test.student.entity.Student;
import com.eleadmin.test.student.mapper.StudentMapper;
import com.eleadmin.test.student.param.StudentParam;
import com.eleadmin.test.student.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service实现
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    @Override
    public PageResult<Student> pageRel(StudentParam param) {
        PageParam<Student, StudentParam> page = new PageParam<>(param);
        page.setDefaultOrder("student_name");
        return new PageResult<>(baseMapper.selectPageRel(page, param), page.getTotal());
    }

    @Override
    public List<Student> listRel(StudentParam param) {
        PageParam<Student, StudentParam> page = new PageParam<>(param);
        page.setDefaultOrder("student_name");
        return page.sortRecords(baseMapper.selectListRel(param));
    }

    @Override
    public Student getByIdRel(Integer studentId) {
        StudentParam param = new StudentParam();
        param.setStudentId(studentId);
        return param.getOne(baseMapper.selectListRel(param));
    }

}
