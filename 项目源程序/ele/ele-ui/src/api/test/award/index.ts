import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Award, AwardParam } from './model';

/**
 * 分页查询机构
 */
export async function pageAwards(params: AwardParam) {
  const res = await request.get<ApiResult<PageResult<Award>>>(
    '/test/award/page',
    { params }
  );
  if (res.data.code === 0) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 查询机构列表
 */
export async function listAwards(params?: AwardParam) {
  const res = await request.get<ApiResult<Award[]>>(
    '/test/award',
    { params }
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 添加机构
 */
export async function addAward(data: Award) {
  const res = await request.post<ApiResult<unknown>>(
    '/test/award',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 修改机构
 */
export async function updateAward(data: Award) {
  const res = await request.put<ApiResult<unknown>>(
    '/test/award',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 删除机构
 */
export async function removeAward(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/test/award/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeAwards(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/test/award/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
