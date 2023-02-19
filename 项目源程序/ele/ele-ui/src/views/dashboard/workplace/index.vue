<template>
  <div class="ele-body ele-body-card">
    <!-- 顶部卡片 -->
    <a-card :bordered="false" :body-style="{ padding: '20px' }">
      <div class="ele-cell workplace-user-card">
        <div class="ele-cell-content ele-cell">
          <a-avatar :size="68" :src="loginUser.avatar" />
          <div class="ele-cell-content">
            <h4 class="ele-elip">
              早安, {{ loginUser.nickname }}, 开始您一天的工作吧!
            </h4>
            <div class="ele-elip ele-text-secondary">
              <cloud-outlined />
              <em>今日多云转阴，18℃ - 22℃，出门记得穿外套哦~</em>
            </div>
          </div>
        </div>
        <div class="workplace-count-group">
          <div class="workplace-count-item">
            <div class="workplace-count-header">
              <ele-tag color="blue" shape="circle" size="small">
                <appstore-filled />
              </ele-tag>
              <span class="workplace-count-name">项目数</span>
            </div>
            <h2>3</h2>
          </div>
          <div class="workplace-count-item">
            <div class="workplace-count-header">
              <ele-tag color="orange" shape="circle" size="small">
                <check-square-outlined />
              </ele-tag>
              <span class="workplace-count-name">待办项</span>
            </div>
            <h2>6 / 24</h2>
          </div>
          <div class="workplace-count-item">
            <div class="workplace-count-header">
              <ele-tag color="green" shape="circle" size="small">
                <bell-filled />
              </ele-tag>
              <span class="workplace-count-name">消息</span>
            </div>
            <h2>1,689</h2>
          </div>
        </div>
      </div>
    </a-card>
    <!-- 快捷方式块 -->
    <a-row :gutter="16">
      <a-col :lg="3" :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" hoverable :body-style="{ padding: 0 }">
          <router-link to="/system/user" class="app-link-block">
            <user-outlined class="app-link-icon" />
            <div class="app-link-title">用户</div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :lg="3" :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" hoverable :body-style="{ padding: 0 }">
          <router-link to="/dashboard/analysis" class="app-link-block">
            <shopping-cart-outlined
              class="app-link-icon"
              style="color: #95de64"
            />
            <div class="app-link-title">分析</div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :lg="3" :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" hoverable :body-style="{ padding: 0 }">
          <router-link to="/list/card/project" class="app-link-block">
            <fund-projection-screen-outlined
              class="app-link-icon"
              style="color: #ff9c6e"
            />
            <div class="app-link-title">商品</div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :lg="3" :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" hoverable :body-style="{ padding: 0 }">
          <router-link to="/list/basic" class="app-link-block">
            <file-search-outlined
              class="app-link-icon"
              style="color: #b37feb"
            />
            <div class="app-link-title">订单</div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :lg="3" :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" hoverable :body-style="{ padding: 0 }">
          <router-link to="/list/advanced" class="app-link-block">
            <credit-card-outlined
              class="app-link-icon"
              style="color: #ffd666"
            />
            <div class="app-link-title">票据</div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :lg="3" :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" hoverable :body-style="{ padding: 0 }">
          <router-link to="/user/message" class="app-link-block">
            <mail-outlined class="app-link-icon" style="color: #5cdbd3" />
            <div class="app-link-title">消息</div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :lg="3" :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" hoverable :body-style="{ padding: 0 }">
          <router-link to="/extension/tag" class="app-link-block">
            <tags-outlined class="app-link-icon" style="color: #ff85c0" />
            <div class="app-link-title">标签</div>
          </router-link>
        </a-card>
      </a-col>
      <a-col :lg="3" :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" hoverable :body-style="{ padding: 0 }">
          <router-link to="/user/profile" class="app-link-block">
            <control-outlined class="app-link-icon" style="color: #ffc069" />
            <div class="app-link-title">配置</div>
          </router-link>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts" setup>
  import { ref, computed } from 'vue';
  import {
    CloudOutlined,
    AppstoreFilled,
    CheckSquareOutlined,
    BellFilled,
    UserOutlined,
    ShoppingCartOutlined,
    FundProjectionScreenOutlined,
    FileSearchOutlined,
    CreditCardOutlined,
    MailOutlined,
    TagsOutlined,
    ControlOutlined,
    MenuOutlined,
    TrophyOutlined
  } from '@ant-design/icons-vue';
  import draggable from 'vuedraggable';
  import { useUserStore } from '@/store/modules/user';
  import type { ColumnsType } from 'ant-design-vue/es/table';

  interface Activitie {
    title: string;
    time: string;
    color?: string;
  }

  interface Task {
    id: number;
    priority: number;
    taskName: string;
    status: number;
  }

  interface Project {
    id: number;
    projectName: string;
    status: number;
    startDate: string;
    endDate: string;
    progress: number;
  }

  interface User {
    name: string;
    introduction: string;
    status: number;
    avatar: string;
  }

  const userStore = useUserStore();

  // 最新动态数据
  const activities = ref<Activitie[]>([]);
  // 我的任务数据
  const taskList = ref<Task[]>([]);
  // 项目进度数据
  const projectList = ref<Project[]>([]);
  // 小组成员数据
  const userList = ref<User[]>([]);

  const projectColumns = ref<ColumnsType>([
    {
      key: 'index',
      align: 'center',
      width: 48,
      customRender: ({ index }) => index + 1,
      fixed: 'left'
    },
    {
      title: '项目名称',
      key: 'projectName',
      ellipsis: true
    },
    {
      title: '开始时间',
      dataIndex: 'startDate'
    },
    {
      title: '结束时间',
      dataIndex: 'endDate'
    },
    {
      title: '状态',
      key: 'status',
      align: 'center',
      width: 90
    },
    {
      title: '进度',
      key: 'progress',
      align: 'center',
      width: 180
    }
  ]);

  // 当前登录用户信息
  const loginUser = computed(() => userStore.info ?? {});

  // 查询最新动态
  const queryActivities = () => {
    activities.value = [
      {
        title: 'SunSmile 解决了bug 登录提示操作失败',
        time: '20:30',
        color: 'gray'
      },
      {
        title: 'Jasmine 解决了bug 按钮颜色与设计不符',
        time: '19:30',
        color: 'gray'
      },
      {
        title: '项目经理 指派了任务 解决项目一的bug',
        time: '18:30'
      },
      {
        title: '项目经理 指派了任务 解决项目二的bug',
        time: '17:30'
      },
      {
        title: '项目经理 指派了任务 解决项目三的bug',
        time: '16:30'
      },
      {
        title: '项目经理 指派了任务 解决项目四的bug',
        time: '15:30',
        color: 'gray'
      },
      {
        title: '项目经理 指派了任务 解决项目五的bug',
        time: '14:30',
        color: 'gray'
      },
      {
        title: '项目经理 指派了任务 解决项目六的bug',
        time: '12:30',
        color: 'gray'
      },
      {
        title: '项目经理 指派了任务 解决项目七的bug',
        time: '11:30'
      },
      {
        title: '项目经理 指派了任务 解决项目八的bug',
        time: '10:30',
        color: 'gray'
      },
      {
        title: '项目经理 指派了任务 解决项目九的bug',
        time: '09:30',
        color: 'green'
      },
      {
        title: '项目经理 指派了任务 解决项目十的bug',
        time: '08:30',
        color: 'red'
      }
    ];
  };

  // 查询我的任务
  const queryTaskList = () => {
    taskList.value = [
      {
        id: 1,
        priority: 1,
        taskName: '解决项目一的bug',
        status: 0
      },
      {
        id: 2,
        priority: 2,
        taskName: '解决项目二的bug',
        status: 0
      },
      {
        id: 3,
        priority: 2,
        taskName: '解决项目三的bug',
        status: 1
      },
      {
        id: 4,
        priority: 3,
        taskName: '解决项目四的bug',
        status: 1
      },
      {
        id: 5,
        priority: 3,
        taskName: '解决项目五的bug',
        status: 2
      },
      {
        id: 6,
        priority: 3,
        taskName: '解决项目六的bug',
        status: 2
      }
    ];
  };

  // 查询项目进度
  const queryProjectList = () => {
    projectList.value = [
      {
        id: 1,
        projectName: '项目0000001',
        status: 0,
        startDate: '2020-03-01',
        endDate: '2020-06-01',
        progress: 30
      },
      {
        id: 2,
        projectName: '项目0000002',
        status: 0,
        startDate: '2020-03-01',
        endDate: '2020-08-01',
        progress: 10
      },
      {
        id: 3,
        projectName: '项目0000003',
        status: 1,
        startDate: '2020-01-01',
        endDate: '2020-05-01',
        progress: 60
      },
      {
        id: 4,
        projectName: '项目0000004',
        status: 1,
        startDate: '2020-06-01',
        endDate: '2020-10-01',
        progress: 0
      },
      {
        id: 5,
        projectName: '项目0000005',
        status: 2,
        startDate: '2020-01-01',
        endDate: '2020-03-01',
        progress: 100
      },
      {
        id: 6,
        projectName: '项目0000006',
        status: 3,
        startDate: '2020-01-01',
        endDate: '2020-03-01',
        progress: 100
      },
      {
        id: 7,
        projectName: '项目0000007',
        status: 3,
        startDate: '2020-01-01',
        endDate: '2020-03-01',
        progress: 100
      }
    ];
  };

  // 查询小组成员
  const queryUserList = () => {
    userList.value = [
      {
        name: 'SunSmile',
        introduction: 'UI设计师、交互专家',
        status: 0,
        avatar:
          'https://cdn.eleadmin.com/20200609/c184eef391ae48dba87e3057e70238fb.jpg'
      },
      {
        name: '你的名字很好听',
        introduction: '前端工程师',
        status: 0,
        avatar:
          'https://cdn.eleadmin.com/20200609/b6a811873e704db49db994053a5019b2.jpg'
      },
      {
        name: '全村人的希望',
        introduction: '前端工程师',
        status: 0,
        avatar:
          'https://cdn.eleadmin.com/20200609/948344a2a77c47a7a7b332fe12ff749a.jpg'
      },
      {
        name: 'Jasmine',
        introduction: '产品经理、项目经理',
        status: 1,
        avatar:
          'https://cdn.eleadmin.com/20200609/f6bc05af944a4f738b54128717952107.jpg'
      },
      {
        name: '酷酷的大叔',
        introduction: '组长、后端工程师',
        status: 1,
        avatar:
          'https://cdn.eleadmin.com/20200609/2d98970a51b34b6b859339c96b240dcd.jpg'
      }
    ];
  };

  queryActivities();
  queryTaskList();
  queryProjectList();
  queryUserList();
</script>

<script lang="ts">
  export default {
    name: 'DashboardWorkplace'
  };
</script>

<style lang="less" scoped>
  /** 用户卡片 */
  .workplace-user-card {
    .ele-cell-content {
      overflow: hidden;
    }

    h4 {
      margin-bottom: 6px;
    }
  }

  .workplace-count-group {
    white-space: nowrap;
    text-align: right;
    flex-shrink: 0;
  }

  .workplace-count-item {
    display: inline-block;
    margin: 0 4px 0 24px;
  }

  .workplace-count-name {
    margin-left: 8px;
  }

  @media screen and (max-width: 992px) {
    .workplace-count-item {
      margin: 0 2px 0 12px;
    }
  }

  @media screen and (max-width: 768px) {
    .workplace-user-card {
      display: block;
    }

    .workplace-count-group {
      margin-top: 8px;
    }
  }

  /** 快捷方式 */
  .app-link-block {
    padding: 12px;
    text-align: center;
    display: block;
    color: inherit;

    .app-link-icon {
      color: #69c0ff;
      font-size: 30px;
      margin: 6px 0 10px 0;
    }
  }

  /** 时间轴 */
  .ele-scrollbar-hover
    :deep(.ant-timeline-item-last > .ant-timeline-item-content) {
    min-height: auto;
  }

  /** 本月目标 */
  .workplace-goal-group {
    padding: 48px 0;
    text-align: center;
    position: relative;

    .workplace-goal-content {
      position: absolute;
      top: 90px;
      left: 0;
      width: 100%;
    }

    .workplace-goal-num {
      font-size: 40px;
    }
  }

  /** 小组成员 */
  .user-list-item {
    padding: 16px 18px;

    & + .user-list-item {
      border-top: 1px solid hsla(0, 0%, 60%, 0.15);
    }
  }

  /** 表格拖拽 */
  .ant-table-tbody tr.sortable-chosen {
    background: hsla(0, 0%, 60%, 0.1) !important;
  }

  .ant-table-tbody tr.sortable-chosen td {
    background: none !important;
  }
</style>
