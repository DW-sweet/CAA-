<template>
  <div class="ele-body">
    <a-card :bordered="false" :body-style="{ padding: '10px 20px' }">
      <!-- 表格 -->
      <ele-pro-table
        ref="tableRef"
        title="案卷列表"
        row-key="piece_no"
        :columns="columns"
        :datasource="datasource"
        v-model:selection="selection"
        :scroll="{ x: 900 }"
        @done="onTableDone"
      >
        <template #toolkit>
          <a-button type="primary" @click="openFileSort">
            卷内文件调整
          </a-button>
        </template>
      </ele-pro-table>
    </a-card>
    <!-- 卷内文件调整弹窗 -->
    <file-sort v-model:visible="showFileSort" :documents="fileSortChoose" />
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { message } from 'ant-design-vue';
  import type { EleProTable } from 'ele-admin-pro';
  import type {
    DatasourceFunction,
    ColumnItem,
    EleProTableDone
  } from 'ele-admin-pro/es/ele-pro-table';
  import FileSort from './components/file-sort.vue';
  import { getPieceList } from '@/api/example/document';
  import { Piece } from '@/api/example/document/model';

  // 表格实例
  const tableRef = ref<InstanceType<typeof EleProTable>>();

  // 案卷数据
  const data = ref<Piece[]>([]);

  // 列表数据源
  const datasource: DatasourceFunction = ({ page, limit }) => {
    return getPieceList({ page, limit });
  };

  //
  const onTableDone: EleProTableDone<Piece> = (res) => {
    data.value = res.data;
  };

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
      title: '案卷档号',
      dataIndex: 'piece_no',
      sorter: true,
      ellipsis: true
    },
    {
      title: '案卷题名',
      dataIndex: 'title',
      sorter: true,
      ellipsis: true
    },
    {
      title: '年度',
      dataIndex: 'year',
      sorter: true,
      width: 100,
      ellipsis: true
    },
    {
      title: '保管期限',
      dataIndex: 'retention',
      sorter: true,
      width: 120,
      ellipsis: true
    },
    {
      title: '密级',
      dataIndex: 'secret',
      sorter: true,
      width: 100,
      ellipsis: true
    },
    {
      title: '档案类别',
      dataIndex: 'type',
      sorter: true,
      ellipsis: true
    },
    {
      title: '载体规格',
      dataIndex: 'carrier',
      sorter: true,
      width: 120,
      ellipsis: true
    }
  ]);

  // 表格选中数据
  const selection = ref<Piece[]>([]);

  // 是否显示卷内文件调整弹窗
  const showFileSort = ref(false);

  // 选中的案卷
  const fileSortChoose = ref<Piece[]>([]);

  /* 打开卷内文件调整弹窗 */
  const openFileSort = () => {
    if (selection.value.length < 2) {
      message.error('请至少选择两条数据');
      return;
    }
    // 实际项目用这一行
    /* fileSortChoose.value = selection.value.map((d) => {
      return Object.assign({}, d);
    }); */
    // 演示强制选前三个演示
    fileSortChoose.value = data.value.slice(0, 3);
    showFileSort.value = true;
  };
</script>

<script lang="ts">
  export default {
    name: 'ExampleDocument'
  };
</script>
