import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Student, StudentParam } from './model';

/**
 * 分页查询学生
 */
export async function pageStudents(params: StudentParam) {
  const res = await request.get<ApiResult<PageResult<Student>>>(
    '/test/student/page',
    { params }
  );
  if (res.data.code === 0) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 查询学生列表
 */
export async function listStudents(params?: StudentParam) {
  const res = await request.get<ApiResult<Student[]>>(
    '/test/student',
    { params }
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 添加学生
 */
export async function addStudent(data: Student) {
  const res = await request.post<ApiResult<unknown>>(
    '/test/student',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 修改学生
 */
export async function updateStudent(data: Student) {
  const res = await request.put<ApiResult<unknown>>(
    '/test/student',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 删除学生
 */
export async function removeStudent(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/test/student/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeStudents(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/test/student/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
