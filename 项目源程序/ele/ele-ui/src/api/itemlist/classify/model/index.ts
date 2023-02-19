import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Classify {
  // 机构id
  classifyId?: number;
  // 机构名称
  classifyName?: string;

  quantity?:number;
  unit?:string,
  navigation?:string,
  install?:string
}

/**
 * 机构搜索条件
 */
export interface ClassifyParam extends PageParam {
  classifyName?: string;
  age?:number;
}
