import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Award {
  // 获奖id
  awardId?: number;
  // 获奖人名称
  awardName?: string;
  // 获奖人年龄
  age?: number;
  // 获奖项目
  program?: string;

  gender?: string;

  email?: string;

  college?: string;

  instructor?: string;

  racename?: string;

  money?: number;

  awarder?: string;

  grade?: string;

  serial?: string;

  way?: string;
}

/**
 * 机构搜索条件
 */
export interface AwardParam extends PageParam {
  awardName?: string;
}
