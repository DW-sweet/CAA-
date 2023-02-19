<template>
  <div class="ele-body">
    <a-card :bordered="false">
      <!-- 搜索表单 -->
      <adoption-search @search="reload" />
      <!-- 表格 -->
      <ele-pro-table
        ref="tableRef"
        row-key="adoptionId"
        :columns="columns"
        :datasource="datasource"
        v-model:selection="selection"
        :scroll="{ x: 1000 }"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'nickname'">
            <router-link :to="'/system/adoption-info?id=' + record.adoptionId">
              {{ record.nickname }}
            </router-link>
          </template>
          <template v-else-if="column.key === 'roles'">
            <a-tag v-for="item in record.roles" :key="item.roleId" color="blue">
              {{ item.roleName }}
            </a-tag>
          </template>
          <template v-else-if="column.key === 'status'">
            <a-switch
              :checked="record.status === 0"
              @change="(checked: boolean) => editStatus(checked, record)"
            />
          </template>
          <template v-else-if="column.key === 'adoptionstatus'">
            <a-tag color="blue">
              {{ record.adoptionstatus }}
            </a-tag>
          </template>
        </template>
      </ele-pro-table>
    </a-card>
  </div>
  <!-- 编辑弹窗 -->
  <adoption-edit v-model:visible="showEdit" :data="current" @done="reload" />
</template>

<script lang="ts" setup>
  import { computed, createVNode, ref } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import {
    PlusOutlined,
    DeleteOutlined,
    UploadOutlined,
    ExclamationCircleOutlined
  } from '@ant-design/icons-vue';
  import type { EleProTable } from 'ele-admin-pro';
  import type {
    DatasourceFunction,
    ColumnItem
  } from 'ele-admin-pro/es/ele-pro-table';
  import { toDateString } from 'ele-admin-pro';
  import {
    pageAdoptions,
    removeAdoption,
    removeAdoptions
  } from '@/api/dashboard/adoption';
  import type { Adoption, AdoptionParam } from '@/api/dashboard/adoption/model';
  import { useUserStore } from '@/store/modules/user';

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
      title: '姓名',
      dataIndex: 'username',
      sorter: true
    },
    {
      title: '用户id',
      key: 'userid',
      dataIndex: 'userid',
      sorter: true
    },
    {
      title: '动物',
      key: 'animalname',
      dataIndex: 'animalname',
      sorter: true
    },
    {
      title: '动物id',
      key: 'animalid',
      dataIndex: 'animalid',
      sorter: true
    },
    {
      title: '审核状态',
      key: 'adoptionstatus',
      dataIndex: 'adoptionstatus'
    }
  ]);

  // 表格选中数据
  const selection = ref<Adoption[]>([]);

  // 当前编辑数据
  const current = ref<Adoption>();

  // 是否显示编辑弹窗
  const showEdit = ref(false);

  const userStore = useUserStore();
  const loginUser = computed(() => userStore.info ?? {});
  const userids = loginUser.value.userId;
  // 表格数据源
  var datasource: DatasourceFunction = async ({
    page,
    limit,
    where,
    orders
  }) => {
    var b = pageAdoptions({ ...where, ...orders, page, limit });
    console.log(b);
    var a = await b;
    var x = a['list'];

    for (var i = x.length - 1; i >= 0; i--) {
      if (x[i]['userid'] != userids) {
        x.splice(i, 1);
      }
    }
    return x;
  };
  /* 搜索 */
  const reload = (where?: AdoptionParam) => {
    selection.value = [];
    tableRef?.value?.reload({ page: 1, where: where });
  };
</script>

<script lang="ts">
  export default {
    name: 'TestAdoption'
  };
</script>
