<template>
  <a-card :bordered="false" :body-style="{ padding: '10px 20px' }">
    <ele-pro-table
      ref="tableRef"
      title="合并单元格"
      row-key="id"
      :columns="columns"
      :datasource="datasource"
      :scroll="{ x: 800 }"
      :bordered="true"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'userName'">
          {{ record.userName }}
        </template>
      </template>
    </ele-pro-table>
  </a-card>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import type { EleProTable } from 'ele-admin-pro';
  import type {
    DatasourceFunction,
    ColumnItem
  } from 'ele-admin-pro/es/ele-pro-table';
  import { pageUserScores } from '@/api/example/table';
  import type { UserScore } from '@/api/example/table/model';

  // 表格实例
  const tableRef = ref<InstanceType<typeof EleProTable>>();

  // 表格列配置
  const columns = ref<ColumnItem[]>([
    {
      key: 'index',
      width: 48,
      align: 'center',
      hideInSetting: true,
      fixed: 'left',
      customRender: ({ index }) => index + (tableRef.value?.tableIndex ?? 0)
    },
    {
      title: '姓名',
      key: 'userName',
      customRender: ({ record }) => {
        return {
          props: {
            rowSpan: (record as UserScore).userNameRowSpan
          }
        };
      }
    },
    {
      title: '课程',
      dataIndex: 'courseName'
    },
    {
      title: '得分',
      dataIndex: 'score'
    }
  ]);

  // 表格数据源
  const datasource: DatasourceFunction = () => {
    return pageUserScores();
  };
</script>
