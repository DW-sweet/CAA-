<template>
  <div class="ele-body ele-body-card">
    <a-card title="修改菜单徽章数据" :bordered="false">
      <a-form
        style="max-width: 360px"
        :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
        :wrapper-col="{ md: { span: 18 }, sm: { span: 24 } }"
      >
        <a-form-item label="菜单">
          <a-tree-select
            :tree-data="menus"
            tree-default-expand-all
            placeholder="请选择菜单"
            v-model:value="path"
          />
        </a-form-item>
        <a-form-item label="徽章值">
          <a-input placeholder="请输入徽章值" v-model:value="badge" />
        </a-form-item>
        <a-form-item label="徽章颜色">
          <ele-color-picker
            size="large"
            :show-alpha="true"
            v-model:value="color"
          />
        </a-form-item>
        <a-form-item :wrapper-col="{ md: { offset: 6 } }">
          <a-button type="primary" @click="setBadge">更新</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { ref, computed } from 'vue';
  import { useUserStore } from '@/store/modules/user';
  import { message } from 'ant-design-vue';
  import { formatTreeData } from 'ele-admin-pro';

  const userStore = useUserStore();

  const menus = computed(() => {
    return formatTreeData(userStore.menus, (m) => {
      return Object.assign({}, m, {
        value: m.path,
        title: m.meta.title
      });
    });
  });

  const path = ref<string>();

  const badge = ref<string>();

  const color = ref<string>();

  const setBadge = () => {
    if (!path.value) {
      message.error('请选择菜单');
      return;
    }
    userStore.setMenuBadge(path.value, badge.value, color.value);
  };
</script>

<script lang="ts">
  export default {
    name: 'ExampleMenuBadge'
  };
</script>
