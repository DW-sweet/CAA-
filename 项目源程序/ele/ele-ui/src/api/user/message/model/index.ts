/**
 * 消息
 */
export interface Message {
  // 消息id
  id: number;
  // 标题
  title?: string;
  // 类型
  type: string;
  // 时间
  time?: string;
  // 状态
  state?: number;
}
