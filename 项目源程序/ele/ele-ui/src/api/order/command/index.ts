import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Command, CommandParam } from './model';

/**
 * 分页查询学生
 */
export async function pageCommands(params: CommandParam) {
  const res = await request.get<ApiResult<PageResult<Command>>>(
    '/order/command/page',
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
export async function listCommands(params?: CommandParam) {
  const res = await request.get<ApiResult<Command[]>>(
    '/order/command',
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
export async function addCommand(data: Command) {
  const res = await request.post<ApiResult<unknown>>(
    '/order/command',
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
export async function updateCommand(data: Command) {
  const res = await request.put<ApiResult<unknown>>(
    '/order/command',
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
export async function removeCommand(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/order/command/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 批量删除用户
 */
export async function removeCommands(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/order/command/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
