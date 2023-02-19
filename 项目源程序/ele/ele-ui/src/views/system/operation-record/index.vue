<template>
  <div class="ele-body">
    <a-card :bordered="false">
      <!-- 搜索表单 -->
      <operation-record-search @search="reload" />
      <!-- 表格 -->
      <ele-pro-table
        ref="tableRef"
        row-key="id"
        :columns="columns"
        :datasource="datasource"
        :scroll="{ x: 900 }"
      >
        <template #toolbar>
          <a-space>
            <a-button type="primary" @click="exportData">
              <template #icon>
                <download-outlined />
              </template>
              <span>导出</span>
            </a-button>
          </a-space>
        </template>
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <a-tag :color="['green', 'red'][record.status]">
              {{ ['正常', '异常'][record.status] }}
            </a-tag>
          </template>
          <template v-else-if="column.key === 'action'">
            <a @click="openDetail(record)">详情</a>
          </template>
        </template>
      </ele-pro-table>
    </a-card>
  </div>
  <!-- 详情弹窗 -->
  <operation-record-detail v-model:visible="showInfo" :data="current" />
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { message } from 'ant-design-vue';
  import { DownloadOutlined } from '@ant-design/icons-vue';
  import type { EleProTable } from 'ele-admin-pro';
  import type {
    DatasourceFunction,
    ColumnItem
  } from 'ele-admin-pro/es/ele-pro-table';
  import XLSX from 'xlsx';
  import { toDateString, exportSheet } from 'ele-admin-pro';
  import OperationRecordSearch from './components/operation-record-search.vue';
  import OperationRecordDetail from './components/operation-record-detail.vue';
  import {
    pageOperationRecords,
    listOperationRecords
  } from '@/api/system/operation-record';
  import type {
    OperationRecord,
    OperationRecordParam
  } from '@/api/system/operation-record/model';

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
      title: '账号',
      dataIndex: 'username',
      sorter: true,
      ellipsis: true
    },
    {
      title: '用户名',
      dataIndex: 'nickname',
      sorter: true,
      ellipsis: true
    },
    {
      title: '操作模块',
      dataIndex: 'module',
      sorter: true,
      ellipsis: true
    },
    {
      title: '操作功能',
      dataIndex: 'description',
      sorter: true,
      ellipsis: true
    },
    {
      title: '请求地址',
      dataIndex: 'url',
      sorter: true,
      ellipsis: true
    },
    {
      title: '请求方式',
      dataIndex: 'requestMethod',
      sorter: true,
      width: 100,
      align: 'center'
    },
    {
      title: '状态',
      key: 'status',
      dataIndex: 'status',
      sorter: true,
      width: 100,
      filters: [
        {
          text: '正常',
          value: 0
        },
        {
          text: '异常',
          value: 1
        }
      ],
      filterMultiple: false,
      align: 'center'
    },
    {
      title: '耗时',
      dataIndex: 'spendTime',
      sorter: true,
      width: 100,
      customRender: ({ text }) => text / 1000 + 's'
    },
    {
      title: '操作时间',
      dataIndex: 'createTime',
      sorter: true,
      width: 146,
      ellipsis: true,
      customRender: ({ text }) => toDateString(text),
      align: 'center'
    },
    {
      title: '操作',
      key: 'action',
      width: 90,
      align: 'center',
      hideInSetting: true,
      fixed: 'right'
    }
  ]);

  // 当前选中数据
  const current = ref<OperationRecord>({
    module: '',
    description: '',
    url: '',
    requestMethod: '',
    method: '',
    params: '',
    result: '',
    error: '',
    spendTime: 0,
    os: '',
    device: '',
    browser: '',
    ip: '',
    status: 0,
    createTime: '',
    nickname: '',
    username: ''
  });

  // 是否显示查看弹窗
  const showInfo = ref(false);

  // 表格数据源
  const datasource: DatasourceFunction = ({
    page,
    limit,
    where,
    orders,
    filters
  }) => {
    return pageOperationRecords({
      ...where,
      ...orders,
      ...filters,
      page,
      limit
    });
  };

  /* 刷新表格 */
  const reload = (where?: OperationRecordParam) => {
    tableRef?.value?.reload({ page: 1, where: where });
  };

  /* 详情 */
  const openDetail = (row: OperationRecord) => {
    current.value = row;
    showInfo.value = true;
  };

  /* 导出数据 */
  const exportData = () => {
    const array = [
      [
        '账号',
        '用户名',
        '操作模块',
        '操作功能',
        '请求地址',
        '请求方式',
        '状态',
        '耗时',
        '操作时间'
      ]
    ];
    // 请求查询全部(不分页)的接口
    const hide = message.loading('请求中..', 0);
    listOperationRecords()
      .then((data) => {
        hide();
        data.forEach((d) => {
          array.push([
            d.username,
            d.nickname,
            d.module,
            d.description,
            d.url,
            d.requestMethod,
            ['正常', '异常'][d.status],
            d.spendTime / 1000 + 's',
            toDateString(d.createTime)
          ]);
        });
        exportSheet(XLSX, array, '操作日志');
      })
      .catch((e) => {
        hide();
        message.error(e.message);
      });
  };
</script>

<script lang="ts">
  export default {
    name: 'SystemOperationRecord'
  };
</script>
