import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Adoption {
  // 机构id
  adoptionId?: number;
  // 机构名称
  adoptionName?: string;
  animalname?: string;
  username?: string;
  userid?: number;
  adoptionstatus?: string;
}

/**
 * 机构搜索条件
 */
export interface AdoptionParam extends PageParam {
  adoptionName?: string;
  adoptionstatus?: string;

}
