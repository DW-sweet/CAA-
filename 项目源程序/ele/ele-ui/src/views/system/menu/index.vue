<template>
  <div class="ele-body">
    <a-card :bordered="false">
      <!-- 搜索表单 -->
      <menu-search @search="reload" />
      <!-- 表格 -->
      <ele-pro-table
        ref="tableRef"
        row-key="menuId"
        :columns="columns"
        :datasource="datasource"
        :parse-data="parseData"
        :need-page="false"
        :expand-icon-column-index="1"
        :expanded-row-keys="expandedRowKeys"
        :scroll="{ x: 1200 }"
        @done="onDone"
        @expand="onExpand"
      >
        <template #toolbar>
          <a-space>
            <a-button type="primary" @click="openEdit()">
              <template #icon>
                <plus-outlined />
              </template>
              <span>新建</span>
            </a-button>
            <a-button @click="expandAll">展开全部</a-button>
            <a-button @click="foldAll">折叠全部</a-button>
          </a-space>
        </template>
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'menuType'">
            <a-tag v-if="isExternalLink(record.path)" color="orange">
              外链
            </a-tag>
            <a-tag v-else-if="isExternalLink(record.component)" color="green">
              内链
            </a-tag>
            <a-tag v-else :color="['blue', ''][record.menuType]">
              {{ ['菜单', '按钮'][record.menuType] }}
            </a-tag>
          </template>
          <template v-else-if="column.key === 'title'">
            <component v-if="record.icon" :is="record.icon" />
            <span style="padding-left: 8px">{{ record.title }}</span>
          </template>
          <template v-else-if="column.key === 'action'">
            <a-space>
              <a @click="openEdit(undefined, record.menuId)">添加</a>
              <a-divider type="vertical" />
              <a @click="openEdit(record)">修改</a>
              <a-divider type="vertical" />
              <a-popconfirm
                @confirm="remove(record)"
                title="确定要删除此菜单吗？"
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
  <menu-edit
    v-model:visible="showEdit"
    :data="current"
    :parent-id="parentId"
    :menu-list="menuData"
    @done="reload"
  />
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { message } from 'ant-design-vue';
  import { PlusOutlined } from '@ant-design/icons-vue';
  import type {
    DatasourceFunction,
    ColumnItem,
    EleProTableDone
  } from 'ele-admin-pro/es/ele-pro-table';
  import MenuSearch from './components/menu-search.vue';
  import {
    toDateString,
    isExternalLink,
    toTreeData,
    eachTreeData,
    EleProTable
  } from 'ele-admin-pro';
  import MenuEdit from './components/menu-edit.vue';
  import { listMenus, removeMenu } from '@/api/system/menu';
  import type { Menu, MenuParam } from '@/api/system/menu/model';
  import 'ele-admin-pro/es/ele-pro-table/style';

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
      title: '菜单名称',
      key: 'title',
      sorter: true,
      ellipsis: true
    },
    {
      title: '路由地址',
      dataIndex: 'path',
      sorter: true,
      ellipsis: true
    },
    {
      title: '组件路径',
      dataIndex: 'component',
      sorter: true,
      ellipsis: true
    },
    {
      title: '权限标识',
      dataIndex: 'authority',
      sorter: true,
      ellipsis: true
    },
    {
      title: '排序',
      dataIndex: 'sortNumber',
      sorter: true,
      width: 90
    },
    {
      title: '可见',
      dataIndex: 'hide',
      sorter: true,
      customRender: ({ text }) => ['是', '否'][text],
      width: 90
    },
    {
      title: '类型',
      key: 'menuType',
      sorter: true,
      width: 90
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      sorter: true,
      ellipsis: true,
      customRender: ({ text }) => toDateString(text)
    },
    {
      title: '操作',
      key: 'action',
      width: 200,
      align: 'center',
      hideInSetting: true
    }
  ]);

  // 当前编辑数据
  const current = ref<Menu>();

  // 是否显示编辑弹窗
  const showEdit = ref(false);

  // 上级菜单id
  const parentId = ref<number>();

  // 菜单数据
  const menuData = ref<Menu[]>([]);

  // 表格展开的行
  const expandedRowKeys = ref<number[]>([]);

  // 表格数据源
  const datasource: DatasourceFunction = ({ where }) => {
    return listMenus({ ...where });
  };

  /* 数据转为树形结构 */
  const parseData = (data: Menu[]) => {
    return toTreeData({
      data: data.map((d) => {
        return { ...d, key: d.menuId, value: d.menuId };
      }),
      idField: 'menuId',
      parentIdField: 'parentId'
    });
  };

  /* 表格渲染完成回调 */
  const onDone: EleProTableDone<Menu> = ({ data }, _curr, _count) => {
    menuData.value = data;
  };

  /* 刷新表格 */
  const reload = (where?: MenuParam) => {
    tableRef?.value?.reload({ where: where });
  };

  /* 打开编辑弹窗 */
  const openEdit = (row?: Menu, id?: number) => {
    current.value = row;
    parentId.value = id;
    showEdit.value = true;
  };

  /* 删除单个 */
  const remove = (row: Menu) => {
    if (row.children?.length) {
      message.error('请先删除子节点');
      return;
    }
    const hide = message.loading('请求中..', 0);
    removeMenu(row.menuId)
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

  /* 展开全部 */
  const expandAll = () => {
    let keys: number[] = [];
    eachTreeData(menuData.value, (d) => {
      if (d.children && d.children.length && d.menuId) {
        keys.push(d.menuId);
      }
    });
    expandedRowKeys.value = keys;
  };

  /* 折叠全部 */
  const foldAll = () => {
    expandedRowKeys.value = [];
  };

  /* 点击展开图标时触发 */
  const onExpand = (expanded: boolean, record: Menu) => {
    if (expanded) {
      expandedRowKeys.value = [
        ...expandedRowKeys.value,
        record.menuId as number
      ];
    } else {
      expandedRowKeys.value = expandedRowKeys.value.filter(
        (d) => d !== record.menuId
      );
    }
  };
</script>

<script lang="ts">
  import * as MenuIcons from '@/layout/menu-icons';

  export default {
    name: 'SystemMenu',
    components: MenuIcons
  };
</script>
