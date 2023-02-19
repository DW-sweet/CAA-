<template>
  <a-card
    :bordered="false"
    :body-style="{ padding: '10px 20px' }"
    @resizeColumn="handleResizeColumn"
  >
    <ele-pro-table
      ref="tableRef"
      title="可拖动改变列宽"
      row-key="userId"
      :columns="columns"
      :datasource="datasource"
      :scroll="{ x: 800 }"
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
  import { pageUsers } from '@/api/system/user';

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
      title: '用户账号',
      dataIndex: 'username',
      ellipsis: true,
      resizable: true,
      width: 150
    },
    {
      title: '用户名',
      dataIndex: 'nickname',
      ellipsis: true,
      resizable: true,
      width: 150
    },
    {
      title: '性别',
      dataIndex: 'sexName',
      width: 140,
      align: 'center'
    },
    {
      title: '手机号',
      dataIndex: 'phone',
      ellipsis: true,
      width: 110
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      ellipsis: true,
      customRender: ({ text }) => toDateString(text),
      width: 180
    }
  ]);

  // 表格数据源
  const datasource: DatasourceFunction = ({ page, limit, orders, filters }) => {
    return pageUsers({ ...orders, ...filters, page, limit });
  };

  const handleResizeColumn = (w: any, col: any) => {
    col.width = w;
  };
</script>
