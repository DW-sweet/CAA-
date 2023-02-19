import request from '@/utils/request';
import type { ApiResult, PageResult } from '@/api';
import type { Animal, AnimalParam } from './model';
import {Adoption} from "@/api/dashboard/adoption/model";

/**
 * 分页查询
 */
export async function pageAnimals(params: AnimalParam) {
  const res = await request.get<ApiResult<PageResult<Animal>>>(
    '/animalAid/animal/page',
    { params }
  );
  if (res.data.code === 0) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 查询列表
 */
export async function listAnimals(params?: AnimalParam) {
  const res = await request.get<ApiResult<Animal[]>>(
    '/animalAid/animal',
    { params }
  );
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 添加
 */
export async function addAnimal(data: Animal) {
  const res = await request.post<ApiResult<unknown>>(
    '/animalAid/animal',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 修改
 */
export async function updateAnimal(data: Animal) {
  const res = await request.put<ApiResult<unknown>>(
    '/animalAid/animal',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}

/**
 * 删除
 */
export async function removeAnimal(id?: number) {
  const res = await request.delete<ApiResult<unknown>>(
    '/animalAid/animal/' + id
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}


/**
 * 申请领养
 */
export async function adoptionAnimal(data: Adoption) {
  const res = await request.post<ApiResult<unknown>>(
    '/animalAid/adoption',
    data
  );
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));

}


/**
 * 批量删除用户
 */
export async function removeAnimals(data: (number | undefined)[]) {
  const res = await request.delete<ApiResult<unknown>>('/animalAid/animal/batch', {
    data
  });
  if (res.data.code === 0) {
    return res.data.message;
  }
  return Promise.reject(new Error(res.data.message));
}
