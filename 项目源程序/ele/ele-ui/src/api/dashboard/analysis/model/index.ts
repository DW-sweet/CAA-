/**
 * 支付笔数数据格式
 */
export interface PayNumData {
  // 日期
  date?: string;
  // 支付笔数
  value?: number;
}

/**
 * 销售量数据格式
 */
export interface SaleroomData {
  // 月份
  month?: string;
  // 销售量
  value?: number;
}

export interface SaleroomResult {
  list1: SaleroomData[];
  list2: SaleroomData[];
}

/**
 * 访问情况数据格式
 */
export interface VisitData {
  // 时间
  time?: string;
  // 访问量
  visits?: number;
  // 浏览量
  views?: number;
}

/**
 * 词云数据格式
 */
export interface CloudData {
  // 标题
  name: string;
  // 数量
  value: number;
}
