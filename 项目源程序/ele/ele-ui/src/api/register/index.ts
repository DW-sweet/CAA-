import request from '@/utils/request';
import type { ApiResult } from '@/api';
import type { RegisterParam, RegisterResult } from './model';
import { setToken } from '@/utils/token-util';

/**
 * 登录
 */
export async function Register(data: RegisterParam) {
  data.tenantId = 2; // 租户id
  const res = await request.post<ApiResult<RegisterResult>>('/register', data);

  if (res.data.code === 0) {
    setToken(res.data.data?.access_token);
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
