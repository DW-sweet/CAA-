import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Animal {
  // 动物id
  animalId?: number;
  // 动物名称
  animalName?: string;
  //性别
  gender?: string;
  //活动范围
  arange?: string;
  //是否绝育
  ster?: string;
  //是否领养
  adpot?: string;
  //领养人
  master?: string;


  // age?: number;
}

/**
 * 机构搜索条件
 */
export interface AnimalParam extends PageParam {
  animalName?: string;
  // age?: number;
}
