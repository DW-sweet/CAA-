<template>
  <div class="ele-body ele-body-card">
    <a-row :gutter="16">
      <a-col :lg="6" :md="24" :sm="24" :xs="24">
        <a-card :bordered="false" :body-style="{ padding: '0 0 16px 0' }">
          <div style="padding: 30px 16px 16px 16px">
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
          </div>
          <div
            style="overflow: auto; height: calc(100vh - 215px); padding: 0 16px"
          >
            <a-tree
              :tree-data="(data as any)"
              v-model:expanded-keys="expandedRowKeys"
              v-model:selected-keys="selectedRowKeys"
              @select="onTreeSelect"
            />
          </div>
        </a-card>
      </a-col>
      <a-col :lg="18" :md="24" :sm="24" :xs="24">
        <a-card
          :bordered="false"
          :body-style="{ paddingBottom: 0, minHeight: 'calc(100vh - 121px)' }"
        >
          <org-user-list
            v-if="current"
            :organization-list="data"
            :organization-id="current.organizationId"
          />
        </a-card>
      </a-col>
    </a-row>
  </div>
  <!-- 编辑弹窗 -->
  <org-edit
    v-model:visible="showEdit"
    :data="editData"
    :organization-list="data"
    :organization-id="current?.organizationId"
    @done="query"
  />
</template>

<script lang="ts" setup>
  import { createVNode, ref } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
  import { toTreeData, eachTreeData } from 'ele-admin-pro';
  import OrgUserList from './components/org-user-list.vue';
  import OrgEdit from './components/org-edit.vue';
  import {
    listOrganizations,
    removeOrganization
  } from '@/api/system/organization';
  import type { Organization } from '@/api/system/organization/model';

  // 加载状态
  const loading = ref(true);

  // 树形数据
  const data = ref<Organization[]>([]);

  // 树展开的key
  const expandedRowKeys = ref<number[]>([]);

  // 树选中的key
  const selectedRowKeys = ref<number[]>([]);

  // 选中数据
  const current = ref<Organization>();

  // 是否显示表单弹窗
  const showEdit = ref(false);

  // 编辑回显数据
  const editData = ref<Organization>();

  /* 查询 */
  const query = () => {
    loading.value = true;
    listOrganizations()
      .then((list) => {
        loading.value = false;
        const eks: number[] = [];
        list.forEach((d) => {
          d.key = d.organizationId;
          d.value = d.organizationId;
          d.title = d.organizationName;
          if (typeof d.key === 'number') {
            eks.push(d.key);
          }
        });
        expandedRowKeys.value = eks;
        data.value = toTreeData({
          data: list,
          idField: 'organizationId',
          parentIdField: 'parentId'
        });
        if (list.length) {
          if (typeof list[0].key === 'number') {
            selectedRowKeys.value = [list[0].key];
          }
          current.value = list[0];
        } else {
          selectedRowKeys.value = [];
          current.value = undefined;
        }
      })
      .catch((e) => {
        loading.value = false;
        message.error(e.message);
      });
  };

  /* 选择数据 */
  const onTreeSelect = () => {
    eachTreeData(data.value, (d) => {
      if (typeof d.key === 'number' && selectedRowKeys.value.includes(d.key)) {
        current.value = d;
        return false;
      }
    });
  };

  /* 打开编辑弹窗 */
  const openEdit = (item?: Organization) => {
    editData.value = item;
    showEdit.value = true;
  };

  /* 删除 */
  const remove = () => {
    Modal.confirm({
      title: '提示',
      content: '确定要删除选中的机构吗?',
      icon: createVNode(ExclamationCircleOutlined),
      maskClosable: true,
      onOk: () => {
        const hide = message.loading('请求中..', 0);
        removeOrganization(current.value?.organizationId)
          .then((msg) => {
            hide();
            message.success(msg);
            query();
          })
          .catch((e) => {
            hide();
            message.error(e.message);
          });
      }
    });
  };

  query();
</script>

<script lang="ts">
  export default {
    name: 'SystemOrganization'
  };
</script>
