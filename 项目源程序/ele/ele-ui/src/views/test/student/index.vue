<template>
  <div class="ele-body">
    <a-card :bordered="false">
      <!-- 搜索表单 -->
      <student-search @search="reload" />
      <!-- 表格 -->
      <ele-pro-table
        ref="tableRef"
        row-key="studentId"
        :columns="columns"
        :datasource="datasource"
        v-model:selection="selection"
        :scroll="{ x: 1000 }"
      >
        <template #toolbar>
          <a-space>
            <a-button type="primary" @click="openEdit()">
              <template #icon>
                <plus-outlined />
              </template>
              <span>新建</span>
            </a-button>
            <a-button type="primary" danger @click="removeBatch">
              <template #icon>
                <delete-outlined />
              </template>
              <span>删除</span>
            </a-button>

          </a-space>
        </template>
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'nickname'">
            <router-link :to="'/system/student-info?id=' + record.studentId">
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
          <template v-else-if="column.key === 'action'">
            <a-space>
              <a @click="openEdit(record)">修改</a>
              <a-divider type="vertical" />
              <a-divider type="vertical" />
              <a-popconfirm
                title="确定要删除此用户吗？"
                @confirm="remove(record)"
              >
                <a class="ele-text-danger">删除</a>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </ele-pro-table>
    </a-card>
  </div>
  <!-- 编辑弹窗 -->
  <student-edit v-model:visible="showEdit" :data="current" @done="reload" />

</template>

<script lang="ts" setup>
  import { createVNode, ref } from 'vue';
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
  import StudentSearch from './components/student-search.vue';
  import StudentEdit from './components/student-edit.vue';
  import {
    pageStudents,
    removeStudent,
    removeStudents
  } from '@/api/test/student';
  import type { Student, StudentParam } from '@/api/test/student/model';

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
      dataIndex: 'studentName',
      sorter: true
    },
    {
      title: '年龄',
      key: 'age',
      dataIndex: 'age',
      sorter: true
    },
    {
      title: '操作',
      key: 'action',
      width: 200,
      align: 'center',
      hideInSetting: true
    }
  ]);

  // 表格选中数据
  const selection = ref<Student[]>([]);

  // 当前编辑数据
  const current = ref<Student>();

  // 是否显示编辑弹窗
  const showEdit = ref(false);


  // 表格数据源
  const datasource: DatasourceFunction = ({ page, limit, where, orders }) => {
    return pageStudents({ ...where, ...orders, page, limit });
  };

  /* 搜索 */
  const reload = (where?: StudentParam) => {
    selection.value = [];
    tableRef?.value?.reload({ page: 1, where: where });
  };

  /* 打开编辑弹窗 */
  const openEdit = (row?: Student) => {
    current.value = row;
    showEdit.value = true;
  };
  
  /* 删除单个 */
  const remove = (row: Student) => {
    const hide = message.loading('请求中..', 0);
    removeStudent(row.studentId)
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

  /* 批量删除 */
  const removeBatch = () => {
    if (!selection.value.length) {
      message.error('请至少选择一条数据');
      return;
    }
    Modal.confirm({
      title: '提示',
      content: '确定要删除选中的用户吗?',
      icon: createVNode(ExclamationCircleOutlined),
      maskClosable: true,
      onOk: () => {
        const hide = message.loading('请求中..', 0);
        removeStudents(selection.value.map((d) => d.studentId))
          .then((msg) => {
            hide();
            message.success(msg);
            reload();
          })
          .catch((e) => {
            hide();
            message.error(e.message);
          });
      }
    });
  };

</script>

<script lang="ts">
  export default {
    name: 'TestStudent'
  };
</script>
