import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Student {
  // 机构id
  studentId?: number;
  // 机构名称
  studentName?: string;

  age?:number;
}

/**
 * 机构搜索条件
 */
export interface StudentParam extends PageParam {
  studentName?: string;
  age?:number;
}
