import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Piece, PieceParam, Archive, ArchiveParam } from './model';

/**
 * 获取案卷列表
 */
export async function getPieceList(params: PieceParam) {
  const res = await request.get<ApiResult<PageResult<Piece>>>(
    'https://cdn.eleadmin.com/20200610/document.json',
    { params }
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 获取卷内文件列表
 */
export async function getArchiveList(params: ArchiveParam) {
  const res = await request.get<ApiResult<Archive[]>>(
    'https://cdn.eleadmin.com/20200610/archive.json',
    { params }
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}
