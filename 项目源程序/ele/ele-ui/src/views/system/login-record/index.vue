<template>
  <div class="ele-body">
    <a-card :bordered="false">
      <!-- 搜索表单 -->
      <login-record-search @search="reload" />
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
          <template v-if="column.key === 'loginType'">
            <a-tag :color="['green', 'red', '', 'orange'][record.loginType]">
              {{
                ['登录成功', '登录失败', '退出登录', '刷新TOKEN'][
                  record.loginType
                ]
              }}
            </a-tag>
          </template>
        </template>
      </ele-pro-table>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { message } from 'ant-design-vue';
  import XLSX from 'xlsx';
  import { DownloadOutlined } from '@ant-design/icons-vue';
  import type { EleProTable } from 'ele-admin-pro';
  import type {
    DatasourceFunction,
    ColumnItem
  } from 'ele-admin-pro/es/ele-pro-table';
  import { toDateString, exportSheet } from 'ele-admin-pro';
  import LoginRecordSearch from './components/login-record-search.vue';
  import {
    pageLoginRecords,
    listLoginRecords
  } from '@/api/system/login-record';
  import type { LoginRecordParam } from '@/api/system/login-record/model';

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
      sorter: true
    },
    {
      title: '用户名',
      dataIndex: 'nickname',
      sorter: true
    },
    {
      title: 'IP地址',
      dataIndex: 'ip',
      sorter: true,
      ellipsis: true
    },
    {
      title: '设备型号',
      dataIndex: 'device',
      sorter: true,
      ellipsis: true
    },
    {
      title: '操作系统',
      dataIndex: 'os',
      sorter: true,
      ellipsis: true
    },
    {
      title: '浏览器',
      dataIndex: 'browser',
      sorter: true,
      ellipsis: true
    },
    {
      title: '操作类型',
      key: 'loginType',
      dataIndex: 'loginType',
      sorter: true,
      width: 120,
      filters: [
        {
          text: '登录成功',
          value: 0
        },
        {
          text: '登录失败',
          value: 1
        },
        {
          text: '退出登录',
          value: 2
        },
        {
          text: '刷新TOKEN',
          value: 3
        }
      ],
      filterMultiple: false
    },
    {
      title: '备注',
      dataIndex: 'comments',
      sorter: true,
      ellipsis: true
    },
    {
      title: '登录时间',
      dataIndex: 'createTime',
      sorter: true,
      ellipsis: true,
      customRender: ({ text }) => toDateString(text)
    }
  ]);

  // 表格数据源
  const datasource: DatasourceFunction = ({
    page,
    limit,
    where,
    orders,
    filters
  }) => {
    return pageLoginRecords({
      ...where,
      ...orders,
      ...filters,
      page,
      limit
    });
  };

  /* 刷新表格 */
  const reload = (where?: LoginRecordParam) => {
    tableRef?.value?.reload({ page: 1, where: where });
  };

  /* 导出数据 */
  const exportData = () => {
    const array = [
      [
        '账号',
        '用户名',
        'IP地址',
        '设备型号',
        '操作系统',
        '浏览器',
        '操作类型',
        '备注',
        '登录时间'
      ]
    ];
    // 请求查询全部接口
    const hide = message.loading('请求中..', 0);
    listLoginRecords()
      .then((data) => {
        hide();
        data.forEach((d) => {
          array.push([
            d.username,
            d.nickname,
            d.ip,
            d.device,
            d.os,
            d.browser,
            ['登录成功', '登录失败', '退出登录', '刷新TOKEN'][d.loginType],
            d.comments,
            toDateString(d.createTime)
          ]);
        });
        exportSheet(XLSX, array, '登录日志');
      })
      .catch((e) => {
        hide();
        message.error(e.message);
      });
  };
</script>

<script lang="ts">
  export default {
    name: 'SystemLoginRecord'
  };
</script>
