import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Adoption, AdoptionParam } from './model';

/**
 * 分页查询
 */
export async function pageAdoptions(params: AdoptionParam) {
  const res = await request.get<ApiResult<PageResult<Adoption>>>(
    '/dashboard/adoption/page',
    { params }
  );
  if (res.data.code === 0) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

export async function pageAdoptionsClient(params: AdoptionParam) {
  const res = await request.get<ApiResult<PageResult<Adoption>>>(
    '/dashboard/adoption/page',
    { params }
  );
  if (res.data.code === 0) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 查询列表
 */
export async function listAdoptions(params?: AdoptionParam) {
  const res = await request.get<ApiResult<Adoption[]>>(
    '/dashboard/adoption',
    { params }
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 添加
 */
export async function addAdoption(data: Adoption) {
  const res = await request.post<ApiResult<unknown>>(
    '/dashboard/adoption',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 修改
 */
export async function updateAdoption(data: Adoption) {
  const res = await request.put<ApiResult<unknown>>(
    '/dashboard/adoption',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 删除
 */
export async function removeAdoption(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/dashboard/adoption/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeAdoptions(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/dashboard/adoption/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
