/**
 * 文件列表
 */
export interface FileData {
  // 文件名称
  name: string;
  // 文件地址
  url?: string;
  // 缩略图地址
  thumbnail?: string;
  // 文件大小
  length?: string;
  // 修改日期
  updateTime?: string;
  // 是否是文件夹
  isDirectory?: boolean;
  // 子级数据
  data?: FileData[];
}

/**
 * 文件列表查询参数
 */
export interface FileDataParam {
  // 文件夹
  directory?: string;
  // 排序字段
  sort?: string;
  // 排序方式
  order?: string;
}
