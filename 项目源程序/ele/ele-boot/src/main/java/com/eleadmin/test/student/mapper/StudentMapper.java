package com.eleadmin.test.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eleadmin.test.student.entity.Student;
import com.eleadmin.test.student.param.StudentParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 分页查询
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return List<Student>
     */
    List<Student> selectPageRel(@Param("page") IPage<Student> page,
                                     @Param("param") StudentParam param);

    /**
     * 查询全部
     *
     * @param param 查询参数
     * @return List<Student>
     */
    List<Student> selectListRel(@Param("param") StudentParam param);

}
