import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Command {
  // 机构id
  commandId?: number;
  // 机构名称
  commandName?: string;
  code?:string;
  yesno?:string;
  state?:string;
}

/**
 * 机构搜索条件
 */
export interface CommandParam extends PageParam {
  commandName?: string;
  code?:string;
  yesno?:string;
  state?:string;
}
