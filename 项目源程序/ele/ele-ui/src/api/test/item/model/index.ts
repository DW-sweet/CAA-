import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Item {
  // 机构id
  itemId?: number;
  // 机构名称
  itemName?: string;
  photo?: string,
  price?: number,
  store?: number,
  sell?: number,
  label?: string
}

/**
 * 机构搜索条件
 */
export interface ItemParam extends PageParam {
  itemName?: string;
  age?:number;
}
