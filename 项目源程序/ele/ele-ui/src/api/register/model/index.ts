import type { User } from '../../system/user/model';
/**
 * 登录参数
 */
export interface RegisterParam {
  // 账号
  username?: string;
  // 密码
  password?: string;
  // 租户id
  tenantId?: number;
}

/**
 * 登录返回结果
 */
export interface RegisterResult {
  // token
  access_token?: string;
  // 用户信息
  user?: User;
}
