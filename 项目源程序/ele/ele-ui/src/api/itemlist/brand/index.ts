import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Brand, BrandParam } from './model';

/**
 * 分页查询学生
 */
export async function pageBrands(params: BrandParam) {
  const res = await request.get<ApiResult<PageResult<Brand>>>(
    '/itemlist/brand/page',
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
export async function listBrands(params?: BrandParam) {
  const res = await request.get<ApiResult<Brand[]>>(
    '/itemlist/brand',
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
export async function addBrand(data: Brand) {
  const res = await request.post<ApiResult<unknown>>(
    '/itemlist/brand',
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
export async function updateBrand(data: Brand) {
  const res = await request.put<ApiResult<unknown>>(
    '/itemlist/brand',
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
export async function removeBrand(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/itemlist/brand/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeBrands(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/itemlist/brand/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
