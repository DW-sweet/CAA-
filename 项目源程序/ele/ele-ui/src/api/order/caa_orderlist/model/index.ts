import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Order {
  // 机构id
  orderId?: number;

  orderNo?:string;
  orderTime?:string;
  orderMoney?:string;
  orderState?:string;
  // 机构名称
  orderName?: string;
  orderAccount?:string;
  orderSource?:string;
  payWay?:string;

}

/**
 * 机构搜索条件
 */
export interface OrderParam extends PageParam {
  orderNo?:string;
  orderTime?:string;
  orderMoney?:string;
  orderState?:string;
  orderName?: string;
  orderAccount?:string;
  orderSource?:string;
  payWay?:string;
}
