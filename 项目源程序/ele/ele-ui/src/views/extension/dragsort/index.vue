<template>
  <div class="ele-body ele-body-card">
    <a-row :gutter="16">
      <a-col :lg="8" :md="24" :sm="24" :xs="24">
        <a-card title="列表拖拽排序" :bordered="false">
          <div class="demo-drag-list">
            <draggable
              v-model="list"
              item-key="id"
              :animation="300"
              handle=".sort-handle"
            >
              <template #item="{ element }">
                <div class="demo-drag-list-item ele-cell">
                  <menu-outlined class="sort-handle" />
                  <div class="ele-cell-content">{{ element.name }}</div>
                </div>
              </template>
            </draggable>
          </div>
        </a-card>
      </a-col>
      <a-col :lg="16" :md="24" :sm="24" :xs="24">
        <a-card title="列表相互拖拽" :bordered="false">
          <a-row :gutter="16">
            <a-col :span="12">
              <div class="demo-drag-list">
                <draggable
                  :list="list1"
                  item-key="id"
                  :animation="300"
                  handle=".sort-handle"
                  group="demoDragList"
                >
                  <template #item="{ element }">
                    <div class="demo-drag-list-item ele-cell">
                      <menu-outlined class="sort-handle" />
                      <div class="ele-cell-content">{{ element.name }}</div>
                    </div>
                  </template>
                </draggable>
              </div>
            </a-col>
            <a-col :span="12">
              <div class="demo-drag-list">
                <draggable
                  :list="list2"
                  item-key="id"
                  :animation="300"
                  handle=".sort-handle"
                  group="demoDragList"
                >
                  <template #item="{ element }">
                    <div class="demo-drag-list-item ele-cell">
                      <menu-outlined class="sort-handle" />
                      <div class="ele-cell-content">{{ element.name }}</div>
                    </div>
                  </template>
                </draggable>
              </div>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="16">
      <a-col :lg="8" :md="24" :sm="24" :xs="24">
        <a-card title="宫格拖拽排序" :bordered="false">
          <div class="demo-drag-grid">
            <draggable v-model="grid" item-key="id" :animation="300">
              <template #item="{ element }">
                <div class="demo-drag-grid-item">{{ element.name }}</div>
              </template>
            </draggable>
          </div>
        </a-card>
      </a-col>
      <a-col :lg="16" :md="24" :sm="24" :xs="24">
        <a-card title="宫格相互拖拽" :bordered="false">
          <a-row :gutter="16">
            <a-col :span="12">
              <div class="demo-drag-grid">
                <draggable
                  :list="grid1"
                  item-key="id"
                  :animation="300"
                  group="demoDragGrid"
                >
                  <template #item="{ element }">
                    <div class="demo-drag-grid-item">{{ element.name }}</div>
                  </template>
                </draggable>
              </div>
            </a-col>
            <a-col :span="12">
              <div class="demo-drag-grid">
                <draggable
                  :list="grid2"
                  item-key="id"
                  :animation="300"
                  group="demoDragGrid"
                >
                  <template #item="{ element }">
                    <div class="demo-drag-grid-item">{{ element.name }}</div>
                  </template>
                </draggable>
              </div>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { MenuOutlined } from '@ant-design/icons-vue';
  import draggable from 'vuedraggable';

  const list = ref([
    { id: 1, name: '项目0000001' },
    { id: 2, name: '项目0000002' },
    { id: 3, name: '项目0000003' },
    { id: 4, name: '项目0000004' },
    { id: 5, name: '项目0000005' }
  ]);

  const list1 = ref([
    { id: 1, name: '项目0000001' },
    { id: 2, name: '项目0000002' },
    { id: 3, name: '项目0000003' },
    { id: 4, name: '项目0000004' },
    { id: 5, name: '项目0000005' }
  ]);

  const list2 = ref([
    { id: 6, name: '项目0000006' },
    { id: 7, name: '项目0000007' },
    { id: 8, name: '项目0000008' },
    { id: 9, name: '项目0000009' },
    { id: 10, name: '项目0000010' }
  ]);

  const grid = ref([
    { id: 1, name: '项目0000001' },
    { id: 2, name: '项目0000002' },
    { id: 3, name: '项目0000003' },
    { id: 4, name: '项目0000004' },
    { id: 5, name: '项目0000005' }
  ]);

  const grid1 = ref([
    { id: 1, name: '项目0000001' },
    { id: 2, name: '项目0000002' },
    { id: 3, name: '项目0000003' },
    { id: 4, name: '项目0000004' },
    { id: 5, name: '项目0000005' }
  ]);

  const grid2 = ref([
    { id: 6, name: '项目0000006' },
    { id: 7, name: '项目0000007' },
    { id: 8, name: '项目0000008' },
    { id: 9, name: '项目0000009' },
    { id: 10, name: '项目0000010' }
  ]);
</script>

<script lang="ts">
  export default {
    name: 'ExtensionDragsort'
  };
</script>

<style scoped>
  /** 列表样式 */
  .demo-drag-list {
    border: 1px solid hsla(0, 0%, 60%, 0.2);
  }

  .demo-drag-list-item {
    line-height: 1;
    padding: 12px 16px;
  }

  .demo-drag-list-item + .demo-drag-list-item {
    border-top: 1px solid hsla(0, 0%, 60%, 0.2);
  }

  .demo-drag-list-item.sortable-chosen {
    background: hsla(0, 0%, 60%, 0.1);
  }

  .demo-drag-list-item .sort-handle {
    cursor: move;
    font-size: 16px;
  }

  /** 宫格样式 */
  .demo-drag-grid {
    border: 1px solid hsla(0, 0%, 60%, 0.2);
    padding: 16px 0 0 16px;
  }

  .demo-drag-grid-item {
    padding: 16px;
    margin: 0 16px 16px 0;
    display: inline-block;
    border: 1px solid hsla(0, 0%, 60%, 0.2);
    cursor: move;
  }

  .demo-drag-grid-item.sortable-chosen {
    background: hsla(0, 0%, 60%, 0.1);
  }
</style>
