<template>
  <!-- 表格 -->
  <ele-pro-table
    ref="tableRef"
    row-key="userId"
    :columns="columns"
    :datasource="datasource"
    height="calc(100vh - 302px)"
    tool-class="ele-toolbar-form"
    :scroll="{ x: 1000 }"
  >
    <template #toolbar>
      <org-user-search @search="reload" @add="openEdit()" />
    </template>
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'roles'">
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
      <template v-else-if="column.key === 'action'">
        <a-space>
          <a @click="openEdit(record)">修改</a>
          <a-divider type="vertical" />
          <a-popconfirm title="确定要删除此用户吗？" @confirm="remove(record)">
            <a class="ele-text-danger">删除</a>
          </a-popconfirm>
        </a-space>
      </template>
    </template>
  </ele-pro-table>
  <!-- 编辑弹窗 -->
  <org-user-edit
    :data="current"
    v-model:visible="showEdit"
    :organization-list="organizationList"
    :organization-id="organizationId"
    @done="reload"
  />
</template>

<script lang="ts" setup>
  import { ref, watch } from 'vue';
  import { message } from 'ant-design-vue';
  import type { EleProTable } from 'ele-admin-pro';
  import type {
    DatasourceFunction,
    ColumnItem
  } from 'ele-admin-pro/es/ele-pro-table';
  import { toDateString } from 'ele-admin-pro';
  import OrgUserSearch from './org-user-search.vue';
  import OrgUserEdit from './org-user-edit.vue';
  import { pageUsers, removeUser, updateUserStatus } from '@/api/system/user';
  import type { User, UserParam } from '@/api/system/user/model';
  import type { Organization } from '@/api/system/organization/model';

  const props = defineProps<{
    // 机构id
    organizationId?: number;
    // 全部机构
    organizationList: Organization[];
  }>();

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
      sorter: true
    },
    {
      title: '用户名',
      dataIndex: 'nickname',
      sorter: true
    },
    {
      title: '性别',
      dataIndex: 'sexName',
      width: 80,
      align: 'center',
      sorter: true
    },
    {
      title: '手机号',
      dataIndex: 'phone',
      sorter: true
    },
    {
      title: '角色',
      key: 'roles'
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      sorter: true,
      ellipsis: true,
      customRender: ({ text }) => toDateString(text)
    },
    {
      title: '状态',
      key: 'status',
      sorter: true,
      width: 90,
      align: 'center'
    },
    {
      title: '操作',
      key: 'action',
      width: 120,
      align: 'center',
      hideInSetting: true,
      fixed: 'right'
    }
  ]);

  // 当前编辑数据
  const current = ref<User>();

  // 是否显示编辑弹窗
  const showEdit = ref(false);

  // 表格数据源
  const datasource: DatasourceFunction = ({ page, limit, where, orders }) => {
    return pageUsers({
      ...where,
      ...orders,
      page,
      limit,
      organizationId: props.organizationId
    });
  };

  /* 搜索 */
  const reload = (where?: UserParam) => {
    tableRef?.value?.reload({ page: 1, where: where });
  };

  /* 打开编辑弹窗 */
  const openEdit = (row?: User) => {
    current.value = row;
    showEdit.value = true;
  };

  /* 删除单个 */
  const remove = (row: User) => {
    const hide = message.loading('请求中..', 0);
    removeUser(row.userId)
      .then((msg) => {
        hide();
        message.success(msg);
        reload();
      })
      .catch((e) => {
        hide();
        message.error(e.message);
      });
  };

  /* 修改用户状态 */
  const editStatus = (checked: boolean, row: User) => {
    const status = checked ? 0 : 1;
    updateUserStatus(row.userId, status)
      .then((msg) => {
        row.status = status;
        message.success(msg);
      })
      .catch((e) => {
        message.error(e.message);
      });
  };

  // 监听机构id变化
  watch(
    () => props.organizationId,
    () => {
      reload();
    }
  );
</script>
