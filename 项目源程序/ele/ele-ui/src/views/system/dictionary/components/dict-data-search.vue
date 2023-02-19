<!-- 搜索表单 -->
<template>
  <a-row :gutter="16">
    <a-col :lg="6" :md="8" :sm="24" :xs="24">
      <a-input
        v-model:value.trim="where.keywords"
        placeholder="输入关键字搜索"
        allow-clear
      />
    </a-col>
    <a-col :lg="6" :md="8" :sm="24" :xs="24">
      <a-space size="middle">
        <a-button type="primary" @click="search">查询</a-button>
        <a-button type="primary" @click="add">新建</a-button>
        <a-button type="primary" danger @click="remove">删除</a-button>
      </a-space>
    </a-col>
  </a-row>
</template>

<script lang="ts" setup>
  import useSearch from '@/utils/use-search';
  import type { DictionaryDataParam } from '@/api/system/dictionary-data/model';

  const emit = defineEmits<{
    (e: 'search', where?: DictionaryDataParam): void;
    (e: 'add'): void;
    (e: 'remove'): void;
  }>();

  // 表单数据
  const { where } = useSearch<DictionaryDataParam>({
    keywords: ''
  });

  /* 搜索 */
  const search = () => {
    emit('search', where);
  };

  /* 添加 */
  const add = () => {
    emit('add');
  };

  /* 删除 */
  const remove = () => {
    emit('remove');
  };
</script>
