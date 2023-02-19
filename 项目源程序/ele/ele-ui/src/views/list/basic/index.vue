<template>
  <div class="ele-body ele-body-card">
    <!-- 搜索表单 -->
    <user-search @search="reload" @expand-change="onExpandChange" />
    <a-card :bordered="false">
      <!-- 提示信息 -->
      <a-alert type="info" show-icon style="margin-bottom: 16px">
        <template #message>
          <span>
            已选择
            <b class="ele-text-primary">{{ selection.length }}</b>
            项数据<em></em>
          </span>
          <span>
            其中冻结状态的用户有
            <b>{{ selection.filter((d) => d.status === 1).length }} 个</b>
            <em></em><em></em>
          </span>
          <a @click="clearChoose">清空</a>
        </template>
      </a-alert>
      <!-- 表格 -->
      <ele-pro-table
        ref="tableRef"
        row-key="userId"
        title="基础列表"
        :bordered="bordered"
        :striped="striped"
        :tools-theme="toolDefault ? 'default' : 'none'"
        :height="tableHeight"
        :full-height="fixedHeight ? 'calc(100vh - 168px)' : undefined"
        :columns="columns"
        :datasource="datasource"
        v-model:selection="selection"
        :custom-row="customRow"
        :scroll="{ x: 1000 }"
        @done="onDone"
      >
        <!-- 表头工具按钮 -->
        <template #toolkit>
          <a-space size="middle" style="flex-wrap: wrap">
            <div class="list-tool-item">
              <span>边框</span>
              <a-switch v-model:checked="bordered" size="small" />
            </div>
            <a-divider type="vertical" />
            <div class="list-tool-item">
              <span>斑马线</span>
              <a-switch v-model:checked="striped" size="small" />
            </div>
            <a-divider type="vertical" />
            <div class="list-tool-item">
              <span>表头背景</span>
              <a-switch v-model:checked="toolDefault" size="small" />
            </div>
            <a-divider type="vertical" />
            <div class="list-tool-item">
              <span>固定高度</span>
              <a-switch v-model:checked="fixedHeight" size="small" />
            </div>
            <a-divider type="vertical" />
            <a-button type="primary" @click="openEdit()" class="ele-btn-icon">
              <template #icon>
                <plus-outlined />
              </template>
              <span>新建</span>
            </a-button>
            <a-dropdown :disabled="!selection.length">
              <a-button class="ele-btn-icon">
                <span>批量操作 <down-outlined /></span>
              </a-button>
              <template #overlay>
                <a-menu @click="onDropClick">
                  <a-menu-item key="del">批量删除</a-menu-item>
                  <a-menu-item key="edit">批量修改</a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
            <a-divider type="vertical" />
          </a-space>
        </template>
        <!-- 自定义列 -->
        <template #bodyCell="{ column, record }">
          <!-- 用户名列 -->
          <template v-if="column.key === 'nickname'">
            <router-link
              :to="'/list/user-info/' + record.userId"
              @click.stop=""
            >
              {{ record.nickname }}
            </router-link>
          </template>
          <!-- 状态列 -->
          <template v-else-if="column.key === 'status'">
            <a-badge
              :status="(['processing', 'error'][record.status] as any)"
              :text="['正常', '冻结'][record.status]"
            />
          </template>
          <!-- 操作列 -->
          <template v-else-if="column.key === 'action'">
            <a-space>
              <a @click.stop="openEdit(record)">修改</a>
              <a-divider type="vertical" />
              <a class="ele-text-danger" @click.stop="remove(record)">删除</a>
            </a-space>
          </template>
        </template>
        <!-- 自定义筛选dropdown -->
        <template
          #customFilterDropdown="{
            column,
            setSelectedKeys,
            confirm,
            clearFilters
          }"
        >
          <!-- 用户名 -->
          <template v-if="column.key === 'nickname'">
            <nickname-filter
              :setSelectedKeys="setSelectedKeys"
              :confirm="confirm"
              :clearFilters="clearFilters"
            />
          </template>
        </template>
      </ele-pro-table>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { ref, computed } from 'vue';
  import { message } from 'ant-design-vue';
  import { DownOutlined, PlusOutlined } from '@ant-design/icons-vue';
  import type { EleProTable } from 'ele-admin-pro';
  import type {
    DatasourceFunction,
    ColumnItem,
    EleProTableDone
  } from 'ele-admin-pro/es/ele-pro-table';
  import { toDateString, messageLoading } from 'ele-admin-pro';
  import UserSearch from './components/search.vue';
  import NicknameFilter from './components/nickname-filter.vue';
  import { pageUsers } from '@/api/system/user';
  import type { User, UserParam } from '@/api/system/user/model';

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
      sorter: true,
      ellipsis: true
    },
    {
      title: '用户名',
      key: 'nickname',
      dataIndex: 'nickname',
      sorter: true,
      customFilterDropdown: true,
      ellipsis: true
    },
    {
      title: '组织机构',
      dataIndex: 'organizationName',
      sorter: true,
      hideInTable: true,
      ellipsis: true
    },
    {
      title: '手机号',
      dataIndex: 'phone',
      sorter: true,
      ellipsis: true
    },
    {
      title: '性别',
      dataIndex: 'sexName',
      width: 80,
      align: 'center',
      sorter: true,
      filters: [
        {
          text: '男',
          value: '男'
        },
        {
          text: '女',
          value: '女'
        }
      ],
      filterMultiple: false,
      ellipsis: true
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
      dataIndex: 'status',
      sorter: true,
      width: 90,
      align: 'center',
      ellipsis: true
    },
    {
      title: '操作',
      key: 'action',
      width: 140,
      align: 'center',
      hideInSetting: true
    }
  ]);

  // 表格选中数据
  const selection = ref<User[]>([]);

  // 表格数据源
  const datasource: DatasourceFunction = ({
    page,
    limit,
    where,
    orders,
    filters
  }) => {
    return pageUsers({
      ...where,
      ...orders,
      ...filters,
      page,
      limit
    });
  };

  // 表格数据请求完成事件
  const onDone: EleProTableDone<User> = ({ data }) => {
    // 回显id为25、24、22的数据的复选框
    const ids = [25, 24, 22];
    selection.value = data.filter((d) => ids.includes(d.userId));
  };

  // 自定义行属性
  const customRow = (record: User) => {
    return {
      // 行点击事件
      onClick: () => {
        if (selection.value.some((d) => d.userId === record.userId)) {
          selection.value = selection.value.filter(
            (d) => d.userId !== record.userId
          );
        } else {
          selection.value = selection.value.concat([record]);
        }
      }
    };
  };

  /* 刷新表格 */
  const reload = (where?: UserParam) => {
    selection.value = [];
    tableRef?.value?.reload({ page: 1, where });
  };

  /* 清空选择 */
  const clearChoose = () => {
    selection.value = [];
  };

  /* 编辑 */
  const openEdit = (row?: User) => {
    if (!row) {
      message.info('点击了新建');
    } else {
      console.log(row);
      message.info('点击了修改');
    }
  };

  /* 删除 */
  const remove = (row: User) => {
    console.log(row);
    const hide = messageLoading({
      content: '请求中...',
      duration: 0,
      mask: true
    });
    setTimeout(() => {
      hide();
      message.info('点击了删除');
    }, 1500);
  };

  /* 下拉按钮点击 */
  const onDropClick = ({ key }) => {
    if (key === 'del') {
      message.info('点击了批量删除');
    } else if (key === 'edit') {
      message.info('点击了批量修改');
    }
  };

  // 表格是否显示边框
  const bordered = ref(false);

  // 表格是否斑马纹
  const striped = ref(false);

  // 表头工具栏风格
  const toolDefault = ref(false);

  // 表格固定高度
  const fixedHeight = ref(false);

  // 搜索是否展开
  const searchExpand = ref(false);

  // 表格高度
  const tableHeight = computed(() => {
    return fixedHeight.value
      ? searchExpand.value
        ? 'calc(100vh - 618px)'
        : 'calc(100vh - 562px)'
      : undefined;
  });

  // 搜索展开改变事件
  const onExpandChange = (value: boolean) => {
    searchExpand.value = value;
  };
</script>

<script lang="ts">
  export default {
    name: 'ListBasic'
  };
</script>

<style lang="less" scoped>
  .list-tool-item {
    & > span {
      vertical-align: middle;
      margin-right: 6px;
      opacity: 0.9;
    }
  }
</style>
