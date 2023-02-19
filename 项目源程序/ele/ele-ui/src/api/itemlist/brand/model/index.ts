import { PageParam } from '@/api';

/**
 * 机构
 */
export interface Brand {
  // 品牌id
  brandId?: number;
  // 品牌名称
  brandName?: string;
  //品牌首字母
  alpha?: string;
  //品牌评分
  score?: string;
  //是否展示
  bshow?: string;


  // age?: number;
}

/**
 * 机构搜索条件
 */
export interface BrandParam extends PageParam {
  brandName?: string;
  // age?: number;
}
