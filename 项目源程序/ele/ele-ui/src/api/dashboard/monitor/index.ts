import request from '@/utils/request';
import type { ApiResult } from '@/api';
import type { UserCount, BrowserCount } from './model';

/**
 * 获取中国地图geo数据
 */
export async function getChinaMapData() {
  const res = await request.get<any>(
    'https://cdn.eleadmin.com/20200610/china-provinces.geo.json'
  );
  if (res.data) {
    return res.data;
  }
  return Promise.reject(new Error('获取地图数据失败'));
}

/**
 * 获取用户分布数据
 */
export async function getUserCountList() {
  const res = await request.get<ApiResult<UserCount[]>>(
    'https://cdn.eleadmin.com/20200610/monitor-user-count.json'
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 获取用户浏览器分布数据
 */
export async function getBrowserCountList() {
  const res = await request.get<ApiResult<BrowserCount[]>>(
    'https://cdn.eleadmin.com/20200610/monitor-browser-count.json'
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}
