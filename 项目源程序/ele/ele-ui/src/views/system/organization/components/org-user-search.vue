<!-- 搜索表单 -->
<template>
  <a-row :gutter="16">
    <a-col :lg="6" :md="8" :sm="24" :xs="24">
      <a-input
        v-model:value.trim="where.username"
        placeholder="请输入用户账号"
        allow-clear
      />
    </a-col>
    <a-col :lg="6" :md="8" :sm="24" :xs="24">
      <a-input
        v-model:value.trim="where.nickname"
        placeholder="请输入用户名"
        allow-clear
      />
    </a-col>
    <a-col :lg="6" :md="8" :sm="24" :xs="24">
      <a-space size="middle">
        <a-button type="primary" @click="search">查询 </a-button>
        <a-button type="primary" @click="add">新建 </a-button>
      </a-space>
    </a-col>
  </a-row>
</template>

<script lang="ts" setup>
  import useSearch from '@/utils/use-search';
  import type { UserParam } from '@/api/system/user/model';

  const emit = defineEmits<{
    (e: 'search', where?: UserParam): void;
    (e: 'add'): void;
  }>();

  // 表单数据
  const { where } = useSearch<UserParam>({
    username: '',
    nickname: ''
  });

  /* 搜索 */
  const search = () => {
    emit('search', where);
  };

  /*  添加 */
  const add = () => {
    emit('add');
  };
</script>
