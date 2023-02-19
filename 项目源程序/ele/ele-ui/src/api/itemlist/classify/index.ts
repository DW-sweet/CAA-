import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Classify, ClassifyParam } from './model';

/**
 * 分页查询分类
 */
export async function pageClassifys(params: ClassifyParam) {
  const res = await request.get<ApiResult<PageResult<Classify>>>(
    '/itemlist/classify/page',
    { params }
  );
  if (res.data.code === 0) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 查询分类列表
 */
export async function listClassifys(params?: ClassifyParam) {
  const res = await request.get<ApiResult<Classify[]>>(
    '/itemlist/classify',
    { params }
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 添加分类
 */
export async function addClassify(data: Classify) {
  const res = await request.post<ApiResult<unknown>>(
    '/itemlist/classify',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 修改分类
 */
export async function updateClassify(data: Classify) {
  const res = await request.put<ApiResult<unknown>>(
    '/itemlist/classify',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 删除分类
 */
export async function removeClassify(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/itemlist/classify/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeClassifys(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/itemlist/classify/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
