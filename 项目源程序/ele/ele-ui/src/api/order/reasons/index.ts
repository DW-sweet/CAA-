import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Reasons, ReasonsParam } from './model';

/**
 * 分页查询学生
 */
export async function pageReasonss(params: ReasonsParam) {
  const res = await request.get<ApiResult<PageResult<Reasons>>>(
    '/order/reasons/page',
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
export async function listReasonss(params?: ReasonsParam) {
  const res = await request.get<ApiResult<Reasons[]>>(
    '/order/reasons',
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
export async function addReasons(data: Reasons) {
  const res = await request.post<ApiResult<unknown>>(
    '/order/reasons',
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
export async function updateReasons(data: Reasons) {
  const res = await request.put<ApiResult<unknown>>(
    '/order/reasons',
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
export async function removeReasons(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/order/reasons/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeReasonss(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/order/reasons/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
