import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Greturn, GreturnParam } from './model';

/**
 * 分页查询学生
 */
export async function pageGreturns(params: GreturnParam) {
  const res = await request.get<ApiResult<PageResult<Greturn>>>(
    '/order/greturn/page',
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
export async function listGreturns(params?: GreturnParam) {
  const res = await request.get<ApiResult<Greturn[]>>(
    '/order/greturn',
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
export async function addGreturn(data: Greturn) {
  const res = await request.post<ApiResult<unknown>>(
    '/order/greturn',
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
export async function updateGreturn(data: Greturn) {
  const res = await request.put<ApiResult<unknown>>(
    '/order/greturn',
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
export async function removeGreturn(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/order/greturn/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeGreturns(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/order/greturn/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
