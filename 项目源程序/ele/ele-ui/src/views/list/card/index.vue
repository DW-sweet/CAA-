<template>
  <div class="ele-body">
    <a-card :bordered="false" :body-style="{ padding: '0 24px' }">
      <h5 style="padding: 16px 0 24px 0">卡片列表</h5>
      <div style="max-width: 500px; margin: 0 auto">
        <a-input-search
          size="large"
          enter-button
          placeholder="请输入内容"
          v-model:value="keyword"
        />
      </div>
      <a-tabs
        :active-key="active"
        class="list-card-tabs"
        @tabClick="onTabClick"
      >
        <a-tab-pane key="/list/card/project" tab="项目" />
        <a-tab-pane key="/list/card/application" tab="应用" />
        <a-tab-pane key="/list/card/article" tab="文章" />
      </a-tabs>
    </a-card>
    <router-layout />
  </div>
</template>

<script lang="ts" setup>
  import { ref, unref, computed } from 'vue';
  import { useRouter } from 'vue-router';
  import RouterLayout from '@/components/RouterLayout/index.vue';

  const { currentRoute, push } = useRouter();

  const keyword = ref('');

  const active = computed(() => unref(currentRoute).path);

  const onTabClick = (key: any) => {
    push(key);
  };
</script>

<script lang="ts">
  export default {
    name: 'ListCard'
  };
</script>

<style lang="less" scoped>
  .list-card-tabs {
    margin-top: 24px;

    :deep(.ant-tabs-nav) {
      margin-bottom: 0;
    }

    :deep(.ant-tabs-nav:before) {
      display: none;
    }
  }
</style>
