<template>
  <div class="ele-body ele-body-card">
    <a-card :bordered="false">
      <a-row>
        <a-col :md="8" :sm="24" :xs="24">
          <div class="ele-text-center">
            <div style="margin-bottom: 8px">进行中的任务</div>
            <h2>10 个任务</h2>
          </div>
        </a-col>
        <a-col :md="8" :sm="24" :xs="24">
          <div class="ele-text-center">
            <div style="margin-bottom: 8px">剩余任务</div>
            <h2>3 个任务</h2>
          </div>
        </a-col>
        <a-col :md="8" :sm="24" :xs="24">
          <div class="ele-text-center">
            <div style="margin-bottom: 8px">任务总耗时</div>
            <h2>120 个小时</h2>
          </div>
        </a-col>
      </a-row>
    </a-card>
    <a-card :bordered="false">
      <!-- 头部工具栏 -->
      <div class="ele-table-tool">
        <h6 class="ele-table-tool-title">复杂列表</h6>
        <a-space size="middle">
          <a-radio-group v-model:value="where.state" @change="query">
            <a-radio-button value="0">全部</a-radio-button>
            <a-radio-button value="1">进行中</a-radio-button>
            <a-radio-button value="2">已完成</a-radio-button>
          </a-radio-group>
          <a-input-search
            v-model:value="where.keyword"
            placeholder="请输入"
            style="width: 200px"
            @search="query"
          >
            <template #enterButton>
              <a-button>
                <template #icon>
                  <search-outlined />
                </template>
              </a-button>
            </template>
          </a-input-search>
        </a-space>
      </div>
      <a-button block type="dashed" @click="openEdit()">
        <template #icon>
          <plus-outlined />
        </template>
        <span>添加</span>
      </a-button>
      <!-- 数据列表 -->
      <a-spin :spinning="loading">
        <div style="min-height: 100px">
          <div v-for="item in data" :key="item.id">
            <div class="basic-list-item">
              <div class="ele-cell">
                <a-avatar :size="60" shape="square" :src="item.cover" />
                <div class="ele-cell-content">
                  <div class="ele-cell-title">{{ item.title }}</div>
                  <div class="ele-cell-desc">{{ item.content }}</div>
                </div>
              </div>
              <div class="basic-list-item-owner">
                <div>发布人</div>
                <div class="ele-text-secondary">{{ item.user }}</div>
              </div>
              <div class="basic-list-item-time">
                <div>开始时间</div>
                <div class="ele-text-secondary">{{ item.time }}</div>
              </div>
              <div class="basic-list-item-progress">
                <a-progress :status="item.status" :percent="item.progress" />
              </div>
              <div class="basic-list-item-tool">
                <a-space>
                  <a @click="openEdit(item)">编辑</a>
                  <a-divider type="vertical" />
                  <a-dropdown>
                    <a>
                      <span>更多<down-outlined class="ele-text-small" /></span>
                    </a>
                    <template #overlay>
                      <a-menu @click="(obj: any) => dropClick(obj.key, item)">
                        <a-menu-item key="share">分享</a-menu-item>
                        <a-menu-item key="remove">删除</a-menu-item>
                      </a-menu>
                    </template>
                  </a-dropdown>
                </a-space>
              </div>
            </div>
            <a-divider />
          </div>
        </div>
        <div class="ele-text-center" style="margin-top: 18px">
          <a-pagination
            :total="count"
            v-model:page-size="limit"
            show-quick-jumper
            v-model:current="page"
            @change="query"
          />
        </div>
      </a-spin>
    </a-card>
    <!-- 编辑弹窗 -->
    <ele-modal
      :width="460"
      v-model:visible="visible"
      :confirm-loading="submitLoading"
      :title="form.id ? '任务编辑' : '任务添加'"
      :body-style="{ paddingBottom: '8px' }"
      @ok="submit"
    >
      <a-form
        :label-col="{ md: { span: 5 }, sm: { span: 24 } }"
        :wrapper-col="{ md: { span: 19 }, sm: { span: 24 } }"
      >
        <a-form-item label="任务名称:" v-bind="validateInfos.title">
          <a-input
            allow-clear
            v-model:value="form.title"
            placeholder="请输入任务名称"
          />
        </a-form-item>
        <a-form-item label="开始时间:" v-bind="validateInfos.time">
          <a-date-picker
            show-time
            class="ele-fluid"
            v-model:value="form.time"
            placeholder="请选择开始时间"
            value-format="YYYY-MM-DD hh:mm:ss"
          />
        </a-form-item>
        <a-form-item label="负责人:" v-bind="validateInfos.user">
          <a-select
            allow-clear
            v-model:value="form.user"
            placeholder="请选择负责人"
          >
            <a-select-option value="SunSmile">SunSmile</a-select-option>
            <a-select-option value="Pojin">Pojin</a-select-option>
            <a-select-option value="SuperWill">SuperWill</a-select-option>
            <a-select-option value="Jasmine">Jasmine</a-select-option>
            <a-select-option value="Vast">Vast</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="任务描述:">
          <a-textarea
            :rows="4"
            v-model:value="form.content"
            placeholder="请输入任务描述"
          />
        </a-form-item>
      </a-form>
    </ele-modal>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, createVNode } from 'vue';
  import { Modal, Form, message } from 'ant-design-vue';
  import {
    SearchOutlined,
    PlusOutlined,
    DownOutlined,
    ExclamationCircleOutlined
  } from '@ant-design/icons-vue';

  const useForm = Form.useForm;

  interface ListItem {
    id?: number;
    title?: string;
    time?: string;
    user?: string;
    progress?: number;
    content?: string;
    cover?: string;
    status?: 'normal' | 'active' | 'success' | 'exception';
  }

  // 列表加载状态
  const loading = ref(false);

  // 列表数据
  const data = ref<ListItem[]>([]);

  // 搜索表单
  const where = reactive({
    state: '0',
    keyword: ''
  });

  // 第几页
  const page = ref(1);

  // 每页多少条
  const limit = ref(5);

  // 总数量
  const count = ref(0);

  // 编辑弹窗是否显示
  const visible = ref(false);

  // 编辑弹窗表单数据
  const form = reactive<ListItem>({
    id: undefined,
    title: 'Vue Router',
    time: undefined,
    user: '',
    content: ''
  });

  // 编辑弹窗表单验证规则
  const rules = reactive({
    title: [
      {
        required: true,
        message: '请输入任务名称',
        type: 'string'
      }
    ],
    time: [
      {
        required: true,
        message: '请选择开始时间',
        type: 'string'
      }
    ],
    user: [
      {
        required: true,
        message: '请选择负责人',
        type: 'string'
      }
    ]
  });

  const { resetFields, validate, validateInfos } = useForm(form, rules);

  // 编辑表单提交状态
  const submitLoading = ref(false);

  /* 查询数据 */
  const query = () => {
    loading.value = true;
    setTimeout(() => {
      loading.value = false;
      count.value = 25;
      data.value = [
        {
          id: 1,
          title: 'ElementUI',
          time: '2020-06-13 08:33:12',
          user: 'SunSmile',
          progress: 87,
          content:
            'Element，一套为开发者、设计师和产品经理准备的基于 Vue 2.0 的组件库，提供了配套设计资源，帮助你的网站快速成型。',
          cover:
            'https://cdn.eleadmin.com/20200609/c184eef391ae48dba87e3057e70238fb.jpg'
        },
        {
          id: 2,
          title: 'Vue.js',
          time: '2020-06-13 06:40:13',
          user: 'Pojin',
          progress: 100,
          content:
            'Vue 是一套用于构建用户界面的渐进式框架。与其它大型框架不同的是，Vue 被设计为可以自底向上逐层应用。',
          cover:
            'https://cdn.eleadmin.com/20200609/b6a811873e704db49db994053a5019b2.jpg'
        },
        {
          id: 3,
          title: 'Vuex',
          time: '2020-06-13 04:40:20',
          user: 'SuperWill',
          progress: 75,
          content:
            'Vuex 是一个专为 Vue.js 应用程序开发的状态管理模式。它采用集中式存储管理应用的所有组件的状态，并以相应的规则保证状态以一种可预测的方式发生变化。',
          cover:
            'https://cdn.eleadmin.com/20200609/948344a2a77c47a7a7b332fe12ff749a.jpg'
        },
        {
          id: 4,
          title: 'Vue Router',
          time: '2020-06-13 02:40:05',
          user: 'Jasmine',
          progress: 65,
          content:
            'Vue Router 是 Vue.js 官方的路由管理器。它和 Vue.js 的核心深度集成，让构建单页面应用变得易如反掌。',
          cover:
            'https://cdn.eleadmin.com/20200609/f6bc05af944a4f738b54128717952107.jpg'
        },
        {
          id: 5,
          title: 'Sass',
          time: '2020-06-13 00:40:58',
          user: 'Vast',
          progress: 45,
          status: 'exception',
          content: 'Sass 是世界上最成熟、稳定、强大的专业级 CSS 扩展语言。',
          cover:
            'https://cdn.eleadmin.com/20200609/2d98970a51b34b6b859339c96b240dcd.jpg'
        }
      ];
    }, 300);
  };

  /* 显示编辑弹窗 */
  const openEdit = (row?: ListItem) => {
    visible.value = true;
    resetFields();
    Object.keys(form).forEach((key) => {
      form[key] = row ? row[key] : undefined;
    });
  };

  /* 保存编辑 */
  const submit = () => {
    validate()
      .then(() => {
        submitLoading.value = true;
        setTimeout(() => {
          submitLoading.value = false;
          visible.value = false;
          message.success('保存成功');
          if (form.id) {
            // 保存修改
            Object.assign(
              data.value[data.value.findIndex((d) => d.id === form.id)],
              form
            );
          } else {
            // 保存添加
            data.value.push(
              Object.assign({}, form, {
                id: new Date().getTime(),
                cover:
                  'https://cdn.eleadmin.com/20200610/RZ8FQmZfHkcffMlTBCJllBFjEhEsObVo.jpg'
              })
            );
          }
        }, 300);
      })
      .catch(() => {});
  };

  /* 下拉菜单点击事件 */
  const dropClick = (key: string, item: ListItem) => {
    console.log(item);
    if (key === 'remove') {
      // 删除
      Modal.confirm({
        title: '提示',
        content: '确定删除该任务吗?',
        icon: createVNode(ExclamationCircleOutlined),
        maskClosable: true,
        onOk: () => {
          message.success('删除成功');
        }
      });
    } else if (key === 'share') {
      message.success('点击了分享');
    }
  };

  query();
</script>

<script lang="ts">
  export default {
    name: 'ListAdvanced'
  };
</script>

<style lang="less" scoped>
  /** 列表样式 */
  .basic-list-item {
    display: flex;
    align-items: center;
    padding: 16px 8px;

    .ele-cell {
      flex: 1;
    }

    .basic-list-item-owner {
      width: 80px;
      padding: 0 16px;
      flex-shrink: 0;
    }

    .basic-list-item-time {
      width: 160px;
      padding: 0 16px;
      flex-shrink: 0;
    }

    .ele-text-secondary {
      margin-top: 8px;
    }

    .basic-list-item-progress {
      width: 180px;
      flex-shrink: 0;
    }

    .basic-list-item-tool {
      padding: 0 16px;
    }
  }

  /* 响应式 */
  @media screen and (max-width: 992px) {
    .basic-list-item {
      .basic-list-item-owner,
      .basic-list-item-time,
      .basic-list-item-progress,
      .basic-list-item-tool {
        width: auto;
        padding: 0 12px;
      }

      .basic-list-item-progress {
        width: 100px;
      }
    }
  }

  @media screen and (max-width: 768px) {
    .basic-list-item {
      display: block;

      .basic-list-item-owner,
      .basic-list-item-time,
      .basic-list-item-progress,
      .basic-list-item-tool {
        width: auto;
        padding: 8px 0 0 0;
      }

      .ele-text-secondary {
        margin-top: 0;
        padding-left: 16px;
      }

      .basic-list-item-owner > div,
      .basic-list-item-time > div {
        display: inline-block;
      }

      .basic-list-item-tool {
        text-align: right;
      }
    }

    .ele-table-tool {
      .ant-input-search {
        display: none;
      }

      :deep(.ant-space-item) {
        margin: 0 !important;
      }
    }
  }
</style>
