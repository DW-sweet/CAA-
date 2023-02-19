import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { FileRecord, FileRecordParam } from './model';

/**
 * 上传文件
 */
export async function uploadFile(file: File) {
  const formData = new FormData();
  formData.append('file', file);
  const res = await request.post<ApiResult<FileRecord>>(
    '/file/upload',
    formData
  );
  if (res.data.code === 0) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 分页查询文件上传记录
 */
export async function pageFiles(params: FileRecordParam) {
  const res = await request.get<ApiResult<PageResult<FileRecord>>>(
    '/file/page',
    {
      params
    }
  );
  if (res.data.code === 0) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}
