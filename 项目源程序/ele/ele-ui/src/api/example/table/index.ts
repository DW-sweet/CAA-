import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { UserScore } from './model';

export async function pageUserScores() {
  const res = await request.get<ApiResult<PageResult<UserScore>>>(
    'https://cdn.eleadmin.com/20200610/example-table-merge.json'
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}
