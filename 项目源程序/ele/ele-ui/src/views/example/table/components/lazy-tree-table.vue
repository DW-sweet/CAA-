<template>
  <a-card :bordered="false" :body-style="{ padding: '10px 20px' }">
    <!-- 表格 -->
    <ele-pro-table
      ref="tableRef"
      title="树形表格懒加载"
      row-key="menuId"
      :columns="columns"
      :datasource="datasource"
      :need-page="false"
      :lazy-load="true"
      :expand-icon-column-index="1"
      :scroll="{ x: 600 }"
    />
  </a-card>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import type { EleProTable } from 'ele-admin-pro';
  import type {
    DatasourceFunction,
    ColumnItem
  } from 'ele-admin-pro/es/ele-pro-table';
  import { toDateString } from 'ele-admin-pro';
  import { listMenus } from '@/api/system/menu';

  // 表格实例
  const tableRef = ref<InstanceType<typeof EleProTable>>();

  // 表格列配置
  const columns = ref<ColumnItem[]>([
    {
      key: 'index',
      width: 48,
      align: 'center',
      fixed: 'left',
      hideInSetting: true,
      customRender: ({ index }) => index + (tableRef.value?.tableIndex ?? 0)
    },
    {
      title: '菜单名称',
      dataIndex: 'title',
      ellipsis: true
    },
    {
      title: '路由地址',
      dataIndex: 'path',
      ellipsis: true
    },
    {
      title: '组件路径',
      dataIndex: 'component',
      ellipsis: true
    },
    {
      title: '排序',
      dataIndex: 'sortNumber',
      width: 60
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      ellipsis: true,
      customRender: ({ text }) => toDateString(text)
    }
  ]);

  // 表格数据源
  const datasource: DatasourceFunction = ({ where, parent }) => {
    return listMenus({ ...where, parentId: parent?.menuId || 0 });
  };
</script>
