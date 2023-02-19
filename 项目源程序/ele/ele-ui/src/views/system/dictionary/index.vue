<template>
  <div class="ele-body ele-body-card">
    <a-row :gutter="16">
      <a-col :lg="6" :md="10" :sm="24" :xs="24">
        <a-card :bordered="false" :body-style="{ padding: '24px 16px' }">
          <!-- 表格 -->
          <ele-pro-table
            ref="tableRef"
            row-key="dictId"
            :columns="columns"
            :datasource="datasource"
            v-model:current="current"
            selection-type="radio"
            :row-selection="{ columnWidth: 32 }"
            :need-page="false"
            :toolkit="[]"
            height="calc(100vh - 270px)"
            @done="done"
          >
            <template #toolbar>
              <a-space size="middle">
                <a-button type="primary" @click="openEdit()">新建</a-button>
                <a-button
                  type="primary"
                  :disabled="!current"
                  @click="openEdit(current)"
                >
                  修改
                </a-button>
                <a-button
                  danger
                  type="primary"
                  :disabled="!current"
                  @click="remove"
                >
                  删除
                </a-button>
              </a-space>
            </template>
          </ele-pro-table>
        </a-card>
      </a-col>
      <a-col :lg="18" :md="14" :sm="24" :xs="24">
        <a-card :bordered="false" :body-style="{ paddingBottom: 0 }">
          <dict-data
            v-if="current && current.dictId"
            :dict-id="current.dictId"
          />
        </a-card>
      </a-col>
    </a-row>
  </div>
  <!-- 编辑弹窗 -->
  <dict-edit v-model:visible="showEdit" :data="editData" @done="reload" />
</template>

<script lang="ts" setup>
  import { createVNode, ref } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
  import type { EleProTable } from 'ele-admin-pro';
  import type {
    DatasourceFunction,
    ColumnItem,
    EleProTableDone
  } from 'ele-admin-pro/es/ele-pro-table';
  import DictData from './components/dict-data.vue';
  import DictEdit from './components/dict-edit.vue';
  import { listDictionaries, removeDictionary } from '@/api/system/dictionary';
  import type { Dictionary } from '@/api/system/dictionary/model';

  // 表格实例
  const tableRef = ref<InstanceType<typeof EleProTable>>();

  // 表格列配置
  const columns = ref<ColumnItem[]>([
    {
      key: 'index',
      width: 32,
      align: 'center',
      fixed: 'left',
      hideInSetting: true,
      customRender: ({ index }) => index + (tableRef.value?.tableIndex ?? 0)
    },
    {
      title: '字典名称',
      dataIndex: 'dictName'
    }
  ]);

  // 表格选中数据
  const current = ref<Dictionary>();

  // 是否显示编辑弹窗
  const showEdit = ref(false);

  // 编辑回显数据
  const editData = ref<Dictionary>();

  // 表格数据源
  const datasource: DatasourceFunction = () => {
    return listDictionaries();
  };

  /* 表格渲染完成回调 */
  const done: EleProTableDone<Dictionary> = (res) => {
    if (res.data?.length) {
      current.value = res.data[0];
    }
  };

  /* 刷新表格 */
  const reload = () => {
    tableRef?.value?.reload();
  };

  /* 打开编辑弹窗 */
  const openEdit = (row?: Dictionary) => {
    editData.value = row;
    showEdit.value = true;
  };

  /* 删除 */
  const remove = () => {
    Modal.confirm({
      title: '提示',
      content: '确定要删除选中的字典吗?',
      icon: createVNode(ExclamationCircleOutlined),
      maskClosable: true,
      onOk: () => {
        const hide = message.loading('请求中..', 0);
        removeDictionary(current.value?.dictId)
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
    name: 'SystemDictionary'
  };
</script>
