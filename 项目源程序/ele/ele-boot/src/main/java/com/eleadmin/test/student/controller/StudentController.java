package com.eleadmin.test.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.BatchParam;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.test.student.entity.Student;
import com.eleadmin.test.student.param.StudentParam;
import com.eleadmin.test.student.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学生控制器
 *
 * @author EleAdmin
 * @since 2020-03-14 11:29:04
 */
@Api(tags = "学生管理")
@RestController
@RequestMapping("/api/test/student")
public class StudentController extends BaseController {
    @Resource
    private StudentService studentService;

    @PreAuthorize("hasAuthority('test:student:list')")
    @OperationLog
    @ApiOperation("分页查询学生")
    @GetMapping("/page")
    public ApiResult<PageResult<Student>> page(StudentParam param) {
        return success(studentService.pageRel(param));
    }

    @PreAuthorize("hasAuthority('test:student:list')")
    @OperationLog
    @ApiOperation("查询全部学生")
    @GetMapping()
    public ApiResult<List<Student>> list(StudentParam param) {
        return success(studentService.listRel(param));
    }

    @PreAuthorize("hasAuthority('test:student:list')")
    @OperationLog
    @ApiOperation("根据id查询学生")
    @GetMapping("/{id}")
    public ApiResult<Student> get(@PathVariable("id") Integer id) {
        return success(studentService.getByIdRel(id));

    }
    @PreAuthorize("hasAuthority('test:student:save')")
    @OperationLog
    @ApiOperation("添加学生")
    @PostMapping()
    public ApiResult<?> add(@RequestBody Student student) {

        if (studentService.count(new LambdaQueryWrapper<Student>()
                .eq(Student::getStudentName, student.getStudentName())) > 0) {
            return fail("学生名称已存在");
        }
        if (studentService.save(student)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('test:student:update')")
    @OperationLog
    @ApiOperation("修改学生")
    @PutMapping()
    public ApiResult<?> update(@RequestBody Student student) {
        if (student.getStudentName() != null) {
            if (studentService.count(new LambdaQueryWrapper<Student>()
                    .eq(Student::getStudentName, student.getStudentName())
                    .ne(Student::getStudentId, student.getStudentId())) > 0) {
                return fail("学生名称已存在");
            }
        }
        if (studentService.updateById(student)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('test:student:remove')")
    @OperationLog
    @ApiOperation("删除学生")
    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (studentService.removeById(id)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('test:student:save')")
    @OperationLog
    @ApiOperation("批量添加学生")
    @PostMapping("/batch")
    public ApiResult<?> saveBatch(@RequestBody List<Student> studentList) {
        if (studentService.saveBatch(studentList)) {
            return success("添加成功");
        }
        return fail("添加失败");
    }

    @PreAuthorize("hasAuthority('test:student:update')")
    @OperationLog
    @ApiOperation("批量修改学生")
    @PutMapping("/batch")
    public ApiResult<?> updateBatch(@RequestBody BatchParam<Student> batchParam) {
        if (batchParam.update(studentService, Student::getStudentId)) {
            return success("修改成功");
        }
        return fail("修改失败");
    }

    @PreAuthorize("hasAuthority('test:student:remove')")
    @OperationLog
    @ApiOperation("批量删除学生")
    @DeleteMapping("/batch")
    public ApiResult<?> removeBatch(@RequestBody List<Integer> ids) {
        if (studentService.removeByIds(ids)) {
            return success("删除成功");
        }
        return fail("删除失败");
    }

}
