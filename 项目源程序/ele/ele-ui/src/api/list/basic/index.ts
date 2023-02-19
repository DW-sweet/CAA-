import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { ListData, ListDataParam } from './model';

/**
 * 获取列表数据
 */
export async function queryList(params: ListDataParam) {
  const res = await request.get<ApiResult<PageResult<ListData>>>(
    'https://cdn.eleadmin.com/20200610/list-demo-basic.json',
    { params }
  );
  if (res.data.code === 0) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}
