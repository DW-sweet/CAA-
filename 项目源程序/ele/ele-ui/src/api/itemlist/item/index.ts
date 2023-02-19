import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Item, ItemParam } from './model';

/**
 * 分页查询学生
 */
export async function pageItems(params: ItemParam) {
  const res = await request.get<ApiResult<PageResult<Item>>>(
    '/itemlist/item/page',
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
export async function listItems(params?: ItemParam) {
  const res = await request.get<ApiResult<Item[]>>(
    '/itemlist/item',
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
export async function addItem(data: Item) {
  const res = await request.post<ApiResult<unknown>>(
    '/itemlist/item',
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
export async function updateItem(data: Item) {
  const res = await request.put<ApiResult<unknown>>(
    '/itemlist/item',
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
export async function removeItem(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/itemlist/item/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeItems(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/itemlist/item/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
