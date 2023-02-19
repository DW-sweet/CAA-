/**
 * 列表数据格式
 */
export interface ListData {
  // 数据id
  id?: number;
  // 名称
  name?: string;
  // 描述
  desc?: string;
  // 调用次数
  callTimes?: number;
  // 上次调用时间
  lastTime?: string;
  // 状态
  state?: number;
}

/**
 * 列表查询参数
 */
export interface ListDataParam {
  // 名称
  name?: string;
  // 描述
  desc?: string;
  // 调用次数
  callTimes?: number;
  // 上次调用时间
  lastTime?: string;
  // 状态
  state?: number;
}
