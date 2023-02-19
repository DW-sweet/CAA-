import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Greturn {
  // 商品id
  greturnId?: number;
  // 商品名称
  greturnName?: string;
  //服务单号
  rnumber?: number;
  //退款用户
  ruser?: string;
  //退款金额
  money?: number;
  //状态
  state?: string;
}

/**
 * 机构搜索条件
 */
export interface GreturnParam extends PageParam {
  greturnName?: string;
  // age?:number;
}
