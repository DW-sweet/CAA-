import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Order, OrderParam } from './model';


/**
 * 分页查询
 */
export async function pageOrders(params: OrderParam) {
  const res = await request.get<ApiResult<PageResult<Order>>>(
    '/order/caa_orderlist/page',
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
export async function listOrders(params?: OrderParam) {
  const res = await request.get<ApiResult<Order[]>>(
    '/order/caa_orderlist',
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
export async function addOrder(data: Order) {
  const res = await request.post<ApiResult<unknown>>(
    '/order/caa_orderlist',
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
export async function updateOrder(data: Order) {
  const res = await request.put<ApiResult<unknown>>('/order/caa_orderlist', data);
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 删除
 */
export async function removeOrder(id?: number) {
  const res = await request.delete<ApiResult<unknown>>('/order/caa_orderlist/' + id);
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeOrders(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/order/caa_orderlist/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
