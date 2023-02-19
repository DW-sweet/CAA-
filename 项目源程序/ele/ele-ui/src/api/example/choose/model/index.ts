/**
 * 班级
 */
export interface Classes {
  // 班级id
  classesId?: number;
  // 班级名称
  classesName?: string;
  // 学院名称
  college?: string;
  // 专业名称
  major?: string;
}

/**
 * 班级查询参数
 */
export interface ClassesParam {
  classesId?: number;
  classesName?: string;
}
