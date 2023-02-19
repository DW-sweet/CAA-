<template>
  <a-card title="导出excel" :bordered="false">
    <!-- 表格 -->
    <ele-pro-table
      bordered
      row-key="key"
      :columns="columns"
      :datasource="data"
      :need-page="false"
      tools-theme="default"
      v-model:selection="selection"
      :toolkit="['size', 'columns', 'fullscreen']"
      :scroll="{ x: 800 }"
    >
      <template #toolbar>
        <a-space>
          <a-button type="primary" @click="exportBas">导出excel</a-button>
          <a-button type="primary" @click="exportAdv">导出带合并</a-button>
          <a-button type="primary" @click="exportSel">导出选中</a-button>
        </a-space>
      </template>
    </ele-pro-table>
  </a-card>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import XLSX from 'xlsx';
  import { message } from 'ant-design-vue';
  import { exportSheet } from 'ele-admin-pro';
  import type { ColumnItem } from 'ele-admin-pro/es/ele-pro-table';
  //
  interface UserType {
    key: number;
    username: string;
    amount: number;
    province: string;
    city: string;
    zone: string;
    street: string;
    address: string;
  }

  // 表格数据
  const data = ref<UserType[]>([
    {
      key: 1,
      username: '王艺洁',
      amount: 18,
      province: '浙江',
      city: '杭州',
      zone: '西湖区',
      street: '西溪街道',
      address: '西溪花园30栋1单元'
    },
    {
      key: 2,
      username: '李小四',
      amount: 39,
      province: '江苏',
      city: '苏州',
      zone: '姑苏区',
      street: '丝绸路',
      address: '天墅之城9幢2单元'
    },
    {
      key: 3,
      username: '王小五',
      amount: 8,
      province: '江西',
      city: '南昌',
      zone: '青山湖区',
      street: '艾溪湖办事处',
      address: '中兴和园1幢3单元'
    },
    {
      key: 4,
      username: '赵小六',
      amount: 16,
      province: '福建',
      city: '泉州',
      zone: '丰泽区',
      street: '南洋街道',
      address: '南洋村6幢1单元'
    },
    {
      key: 5,
      username: '孙小七',
      amount: 12,
      province: '重庆',
      city: '重庆',
      zone: '沙坪坝区',
      street: '虎溪街道',
      address: '重庆师范大学'
    },
    {
      key: 6,
      username: '周小八',
      amount: 11,
      province: '安徽',
      city: '黄山',
      zone: '黄山区',
      street: '汤口镇',
      address: '温泉村21号'
    }
  ]);

  // 表格列配置
  const columns = ref<ColumnItem[]>([
    {
      key: 'index',
      width: 48,
      align: 'center',
      fixed: 'left',
      hideInSetting: true,
      customRender: ({ index }) => index + 1
    },
    {
      title: '用户名',
      dataIndex: 'username',
      align: 'center'
    },
    {
      title: '地址',
      key: 'cityAddress',
      children: [
        {
          title: '省',
          dataIndex: 'province',
          align: 'center'
        },
        {
          title: '市',
          dataIndex: 'city',
          align: 'center'
        },
        {
          title: '区',
          dataIndex: 'zone',
          align: 'center'
        },
        {
          title: '街道',
          dataIndex: 'street',
          align: 'center'
        },
        {
          title: '详细地址',
          dataIndex: 'address',
          align: 'center'
        }
      ]
    },
    {
      title: '金额',
      dataIndex: 'amount',
      align: 'center'
    }
  ]);

  // 选中数据
  const selection = ref<UserType[]>([]);

  /* 导出excel */
  const exportBas = () => {
    const array: (string | number)[][] = [
      ['用户名', '省', '市', '区', '街道', '详细地址', '金额']
    ];
    data.value.forEach((d) => {
      array.push([
        d.username,
        d.province,
        d.city,
        d.zone,
        d.street,
        d.address,
        d.amount
      ]);
    });
    exportSheet(XLSX, array, '用户数据');
  };

  /* 导出带单元格合并 */
  const exportAdv = () => {
    const array: (string | number | null)[][] = [
      ['用户名', '地址', null, null, null, null, '金额'],
      [null, '省', '市', '区', '街道', '详细地址', null]
    ];
    data.value.forEach((d) => {
      array.push([
        d.username,
        d.province,
        d.city,
        d.zone,
        d.street,
        d.address,
        d.amount
      ]);
    });
    const sheet = XLSX.utils.aoa_to_sheet(array);
    sheet['!merges'] = [
      { s: { r: 0, c: 1 }, e: { r: 0, c: 5 } }, // 合并第0行第1列到第0行第5列
      { s: { r: 0, c: 0 }, e: { r: 1, c: 0 } }, // 合并第0行第0列到第1行第0列
      { s: { r: 0, c: 6 }, e: { r: 1, c: 6 } } // 合并第0行第6列到第1行第6列
    ];
    exportSheet(XLSX, sheet, '用户数据');
  };

  /* 导出选中数据 */
  const exportSel = () => {
    if (selection.value.length === 0) {
      message.error('请至少选择一条数据');
      return;
    }
    const array: (string | number)[][] = [
      ['用户名', '省', '市', '区', '街道', '详细地址', '金额']
    ];
    selection.value.forEach((d) => {
      array.push([
        d.username,
        d.province,
        d.city,
        d.zone,
        d.street,
        d.address,
        d.amount
      ]);
    });
    exportSheet(XLSX, array, '用户数据');
  };
</script>
