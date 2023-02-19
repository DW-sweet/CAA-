import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Reasons {
  // 机构id
  reasonsId?: number;
  // 机构名称
  reasonsName?: string;

  age?:string;
  code?:string;
  time?:string;
  yesno?:string;
}

/**
 * 机构搜索条件
 */
export interface ReasonsParam extends PageParam {
  reasonsName?: string;
  age?:string;
  code?:string;
  time?:string;
  yesno?:string;
}
