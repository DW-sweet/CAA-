<template>
  <div class="ele-body">
    <a-card :bordered="false" :body-style="{ padding: 0 }">
      <div style="padding: 16px 16px 0 16px">
        <ele-toolbar>
          <a-space>
            <a-button type="primary">上传</a-button>
            <a-button type="primary">新建文件夹</a-button>
            <a-button
              danger
              type="primary"
              :disabled="!checked.length"
              class="hidden-xs-only"
            >
              删除
            </a-button>
          </a-space>
          <template #action>
            <!-- 搜索框 -->
            <div style="max-width: 240px">
              <a-input-search
                v-model:value="search"
                placeholder="搜索您的文件"
              />
            </div>
            <!-- 显示方式切换 -->
            <menu-outlined
              v-if="grid"
              class="ele-file-tool-btn"
              @click="toggleShowType"
            />
            <appstore-outlined
              v-else
              class="ele-file-tool-btn"
              @click="toggleShowType"
            />
          </template>
        </ele-toolbar>
        <!-- 文件目录面包屑 -->
        <div class="ele-file-breadcrumb-group ele-cell">
          <div class="ele-cell-content ele-cell">
            <div
              v-if="directory.length"
              class="ele-file-breadcrumb-back ele-text-primary"
              @click="back"
            >
              返回上一级
            </div>
            <div class="ele-file-breadcrumb-list ele-cell-content ele-cell">
              <div
                :class="[
                  'ele-file-breadcrumb-item ele-cell',
                  { 'ele-text-primary': directory.length }
                ]"
                @click="listAll"
              >
                <div class="ele-file-breadcrumb-item-title">全部文件</div>
                <right-outlined
                  v-if="directory.length"
                  class="ele-text-secondary"
                />
              </div>
              <div
                v-for="(item, index) in directory"
                :key="item"
                :class="[
                  'ele-file-breadcrumb-item ele-cell',
                  { 'ele-text-primary': index !== directory.length - 1 }
                ]"
                @click="listDir(index)"
              >
                <div class="ele-file-breadcrumb-item-title">{{ item }}</div>
                <right-outlined
                  v-if="index !== directory.length - 1"
                  class="ele-text-secondary"
                />
              </div>
            </div>
          </div>
          <div class="hidden-xs-only">已全部加载，共 {{ data.length }} 个</div>
        </div>
      </div>
      <a-spin :spinning="loading">
        <!-- 文件展示列表 -->
        <ele-file-list
          :data="data"
          :grid="grid"
          :sort="sort"
          :order="order"
          v-model:checked="checked"
          style="min-height: 400px; padding-bottom: 12px"
          @item-click="onItemClick"
          @sort-change="onSortChange"
        />
      </a-spin>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { message } from 'ant-design-vue';
  import {
    MenuOutlined,
    AppstoreOutlined,
    RightOutlined
  } from '@ant-design/icons-vue';
  import { getFileList } from '@/api/extension/file';
  import type { FileData } from '@/api/extension/file/model';
  import type { SortValue } from 'ele-admin-pro/es/ele-file-list/types';

  // 加载状态
  const loading = ref(true);

  // 是否网格展示
  const grid = ref(true);

  // 文件列表数据
  const data = ref<FileData[]>([]);

  // 选中数据
  const checked = ref<FileData[]>([]);

  // 文件目录面包屑数据
  const directory = ref<string[]>([]);

  // 搜索
  const search = ref('');

  // 排序字段
  const sort = ref('');

  // 排序方式
  const order = ref('');

  /* 查询文件列表 */
  const query = () => {
    data.value = [];
    checked.value = [];
    loading.value = true;
    getFileList({
      sort: sort.value,
      order: order.value,
      directory: directory.value.join('/')
    })
      .then((list) => {
        loading.value = false;
        data.value = list;
      })
      .catch((e) => {
        loading.value = false;
        message.error(e.message);
      });
  };

  /* item点击事件 */
  const onItemClick = (item: FileData) => {
    if (item.isDirectory) {
      // 文件夹
      directory.value.push(item.name);
      query();
    }
  };

  /* 返回上级 */
  const back = () => {
    directory.value.splice(directory.value.length - 1, 1);
    query();
  };

  /* 全部文件 */
  const listAll = () => {
    if (!directory.value.length) {
      return;
    }
    directory.value = [];
    query();
  };

  /* 回到指定目录 */
  const listDir = (index: number) => {
    if (index === directory.value.length - 1) {
      return;
    }
    directory.value = directory.value.slice(0, index + 1);
    query();
  };

  /* 文件列表排序方式改变 */
  const onSortChange = (obj: SortValue) => {
    order.value = obj.order;
    sort.value = obj.sort;
    query();
  };

  /* 显示方式切换 */
  const toggleShowType = () => {
    grid.value = !grid.value;
  };

  query();
</script>

<script lang="ts">
  export default {
    name: 'ExtensionFile'
  };
</script>

<style scoped>
  /* 图标按钮 */
  .ele-file-tool-btn {
    font-size: 20px;
    margin-left: 16px;
    cursor: pointer;
  }

  /* 文件目录面包屑 */
  .ele-file-breadcrumb-group {
    margin-bottom: 12px;
    line-height: 1;
  }

  .ele-file-breadcrumb-back {
    padding-right: 12px;
    border-right: 1px solid hsla(0, 0%, 60%, 0.3);
  }

  .ele-file-breadcrumb-back:hover,
  .ele-file-breadcrumb-item.ele-text-primary:hover
    > .ele-file-breadcrumb-item-title {
    text-decoration: underline;
    cursor: pointer;
  }

  .ele-file-breadcrumb-item .anticon {
    margin: 0 4px;
    font-size: 12px;
  }

  @media screen and (max-width: 768px) {
    .ele-table-tool > .ele-table-tool-title + div,
    .ele-file-breadcrumb-group > .ele-cell-content + div {
      display: none;
    }
  }
</style>
