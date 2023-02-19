import request from '@/utils/request';
import type { ApiResult } from '@/api';
import type { Classes, ClassesParam } from './model';

/**
 * 获取全部的班级数据
 */
export async function getAllClasses(params?: ClassesParam) {
  const res = await request.get<ApiResult<Classes[]>>(
    'https://cdn.eleadmin.com/20200610/classes.json',
    { params }
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}
