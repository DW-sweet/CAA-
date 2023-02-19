import { PageParam } from '@/api';

/**
 * 案卷
 */
export interface Piece {
  // 案卷id
  id?: number;
  // 案卷题名
  title?: string;
  // 案卷档号
  piece_no?: string;
  // 密级
  secret?: string;
  // 存放位置
  location?: string;
  // 案卷类型
  type?: string;
  // 保管期限
  retention?: string;
  // 载体类型
  carrier?: string;
  // 归档年度
  year?: string;
}

/**
 * 案卷查询参数
 */
export interface PieceParam extends PageParam {
  title?: string;
  piece_no?: string;
}

/**
 * 文档
 */
export interface Archive {
  // 文件题名
  title?: string;
  // 案卷档号
  piece_no?: string;
  // 文件档号
  archive_no?: string;
  // 密级
  secret?: string;
  // 存放位置
  location?: string;
  // 文件类型
  type?: string;
  // 保管期限
  retention?: string;
  // 载体类型
  carrier?: string;
  // 归档年度
  year?: string;
  // 排序号
  sort_number?: number;
}

/**
 * 文档查询参数
 */
export interface ArchiveParam {
  title?: string;
  piece_no?: string;
  archive_no?: string;
  piece_no_in?: (string | undefined)[];
}
