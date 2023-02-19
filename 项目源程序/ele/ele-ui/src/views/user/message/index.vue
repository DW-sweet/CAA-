<template>
  <div class="ele-body">
    <a-card :bordered="false" class="user-message-card">
      <div class="ele-cell ele-cell-align-top">
        <div class="message-menu-wrap">
          <a-menu :selected-keys="active" mode="inline">
            <a-menu-item key="all">
              <router-link to="/user/message">全部消息</router-link>
            </a-menu-item>
            <a-menu-item key="notice">
              <router-link to="/user/message?type=notice">系统通知</router-link>
            </a-menu-item>
            <a-menu-item key="message">
              <router-link to="/user/message?type=message">
                用户私信
              </router-link>
            </a-menu-item>
            <a-menu-item key="todo">
              <router-link to="/user/message?type=todo">代办事项</router-link>
            </a-menu-item>
          </a-menu>
        </div>
        <div class="ele-cell-content">
          <!-- 数据表格 -->
          <ele-pro-table
            ref="tableRef"
            row-key="id"
            :loading="loading"
            :datasource="data"
            :columns="columns"
            v-model:selection="selection"
            :scroll="{ x: 600 }"
            @refresh="reload"
          >
            <template #toolbar>
              <a-space>
                <a-button type="primary" @click="read">标记已读</a-button>
                <a-button type="primary" @click="readAll">全部已读</a-button>
                <a-button type="primary" danger @click="removeBatch">
                  删除消息
                </a-button>
              </a-space>
            </template>
            <template #bodyCell="{ column, record }">
              <template v-if="column.key === 'state'">
                <span
                  :class="['ele-text-warning', 'ele-text-info'][record.state]"
                >
                  {{ ['未读', '已读'][record.state] }}
                </span>
              </template>
              <template v-else-if="column.key === 'action'">
                <a-space>
                  <a @click="view(record)">查看</a>
                  <a-divider type="vertical" />
                  <a-popconfirm
                    title="确定要删除此消息吗？"
                    @confirm="remove(record)"
                  >
                    <a class="ele-text-danger">删除</a>
                  </a-popconfirm>
                </a-space>
              </template>
            </template>
          </ele-pro-table>
        </div>
      </div>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { createVNode, ref, watch, unref } from 'vue';
  import { useRouter } from 'vue-router';
  import { Modal, message } from 'ant-design-vue';
  import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
  import type { EleProTable } from 'ele-admin-pro';
  import { getUserMessage } from '@/api/user/message';
  import type { Message } from '@/api/user/message/model';
  import type { ColumnItem } from 'ele-admin-pro/es/ele-pro-table';

  const { currentRoute } = useRouter();

  // 表格实例
  const tableRef = ref<InstanceType<typeof EleProTable>>();

  // 导航选中
  const active = ref(['all']);

  // 加载状态
  const loading = ref(true);

  // 列表显示数据
  const data = ref<Message[]>([]);

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
      title: '标题内容',
      dataIndex: 'title',
      ellipsis: true
    },
    {
      title: '时间',
      dataIndex: 'time',
      ellipsis: true,
      width: 140,
      align: 'center'
    },
    {
      title: '状态',
      key: 'state',
      width: 90,
      align: 'center'
    },
    {
      title: '操作',
      key: 'action',
      width: 120,
      align: 'center',
      hideInSetting: true
    }
  ]);

  // 列表选中数据
  const selection = ref<Message[]>([]);

  // 全部数据
  let allData: Message[] = [];

  /* 切换消息类型 */
  const changeType = () => {
    if (active.value.includes('all')) {
      data.value = [...allData];
    } else {
      data.value = allData.filter((d) => active.value.includes(d.type));
    }
    selection.value = [];
  };

  /* 查询全部数据 */
  const reload = () => {
    loading.value = true;
    getUserMessage()
      .then((data) => {
        loading.value = false;
        allData = data;
        changeType();
      })
      .catch((e) => {
        loading.value = false;
        message.error(e.message);
      });
  };

  /* 查看 */
  const view = (row: Message) => {
    message.info(row.title);
  };

  /* 删除单个 */
  const remove = (row: Message) => {
    allData.splice(
      allData.findIndex((t) => t.id === row.id),
      1
    );
    message.success('删除成功');
    changeType();
  };

  /* 批量删除 */
  const removeBatch = () => {
    if (!selection.value.length) {
      message.error('请至少选择一条数据');
      return;
    }
    Modal.confirm({
      title: '提示',
      content: '确定要删除选中的消息吗?',
      icon: createVNode(ExclamationCircleOutlined),
      maskClosable: true,
      onOk: () => {
        allData = allData.filter(
          (d) => !selection.value.some((t) => t.id === d.id)
        );
        changeType();
      }
    });
  };

  /* 标记已读 */
  const read = () => {
    if (!selection.value.length) {
      message.error('请至少选择一条数据');
      return;
    }
    selection.value.forEach((d) => {
      const index = allData.findIndex((t) => t.id === d.id);
      allData[index].state = 1;
    });
    changeType();
  };

  /* 全部标记已读 */
  const readAll = () => {
    allData.forEach((d) => {
      d.state = 1;
    });
    changeType();
  };

  watch(
    currentRoute,
    (route) => {
      const { query } = unref(route);
      if (!query.type) {
        active.value = ['all'];
      } else if (typeof query.type === 'string') {
        active.value = [query.type];
      } else if (query.type.length && query.type[0]) {
        active.value = [query.type[0]];
      }
      changeType();
    },
    {
      immediate: true
    }
  );

  reload();
</script>

<script lang="ts">
  export default {
    name: 'UserMessage'
  };
</script>

<style lang="less" scoped>
  .user-message-card {
    :deep(.ant-card-body) {
      padding: 0;
    }

    .message-menu-wrap {
      width: 150px;
      display: flex;

      :deep(.ant-menu) {
        padding-top: 16px;
      }

      & + .ele-cell-content {
        padding: 24px;
        overflow: auto;
      }
    }
  }

  @media screen and (max-width: 768px) {
    .user-message-card .message-menu-wrap {
      display: none;
    }
  }
</style>
