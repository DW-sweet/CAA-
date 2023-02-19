import request from '@/utils/request';
import type { ApiResult } from '@/api';
import type { Message } from './model';

/**
 * 获取消息列表
 */
export async function getUserMessage() {
  const res = await request.get<ApiResult<Message[]>>(
    'https://cdn.eleadmin.com/20200609/message.json'
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}
