<template>
  <div class="ele-body ele-body-card">
    <!-- 顶部统计卡片 -->
    <a-row :gutter="16">
      <a-col :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" class="monitor-count-card">
          <ele-tag color="blue" shape="circle" size="large">
            <eye-filled />
          </ele-tag>
          <h1 class="monitor-count-card-num">3</h1>
          <div class="monitor-count-card-text">总访问人数</div>
          <ele-avatar-list :data="visitUsers" size="small" :max="4" />
        </a-card>
      </a-col>
      <a-col :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" class="monitor-count-card">
          <ele-tag color="orange" shape="circle" size="large">
            <fire-filled />
          </ele-tag>
          <h1 class="monitor-count-card-num">66</h1>
          <div class="monitor-count-card-text">点击量（近7天）</div>
          <div class="monitor-count-card-trend ele-text-success">
            <up-outlined />
            <span>70.5%</span>
          </div>
          <a-tooltip title="指标说明">
            <question-circle-outlined class="monitor-count-card-tips" />
          </a-tooltip>
        </a-card>
      </a-col>
      <a-col :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" class="monitor-count-card">
          <ele-tag color="red" shape="circle" size="large">
            <flag-filled />
          </ele-tag>
          <h1 class="monitor-count-card-num">82.0</h1>
          <div class="monitor-count-card-text">到达量（近7天）</div>
          <div class="monitor-count-card-trend ele-text-success">
            <up-outlined />
            <span>10.5%</span>
          </div>
        </a-card>
      </a-col>
      <a-col :md="6" :sm="12" :xs="12">
        <a-card :bordered="false" class="monitor-count-card">
          <ele-tag color="green" shape="circle" size="large">
            <thunderbolt-filled />
          </ele-tag>
          <h1 class="monitor-count-card-num">28.8 %</h1>
          <div class="monitor-count-card-text">转化率（近30天）</div>
          <div class="monitor-count-card-trend ele-text-success">
            <up-outlined />
            <span>65.8%</span>
          </div>
          <a-tooltip title="指标说明">
            <question-circle-outlined class="monitor-count-card-tips" />
          </a-tooltip>
        </a-card>
      </a-col>
    </a-row>
    <a-row :gutter="16">
      <!-- 用户分布 -->
      <a-col :lg="18" :md="24" :sm="24" :xs="24">
        <a-card :bordered="false" title="用户分布">
          <a-row>
            <a-col :sm="18" :xs="24">
              <v-chart
                ref="userCountMapChartRef"
                style="height: 485px"
                :option="userCountMapOption"
              />
            </a-col>
            <a-col :sm="6" :xs="24">
              <div style="padding: 10px 5px 0 0">
                <div
                  v-for="(item, index) in userCountDataRank"
                  :key="index"
                  class="monitor-user-count-item ele-cell"
                >
                  <div>{{ item.name }}</div>
                  <div class="ele-cell-content">
                    <a-progress
                      status="normal"
                      :show-info="false"
                      :percent="item.percent"
                    />
                  </div>
                  <div>{{ item.value }}</div>
                </div>
              </div>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
      <!-- 在线人数 -->
      <a-col :lg="6" :md="24" :sm="24" :xs="24">
        <a-row :gutter="16">
          <a-col :lg="24" :md="12" :sm="24" :xs="24">
            <a-card :bordered="false" title="在线人数">
              <div class="monitor-online-num-card">
                <div>{{ currentTime }}</div>
                <div class="monitor-online-num-title">
                  <ele-count-up :end-val="onlineNum" />
                </div>
                <div class="monitor-online-num-text">在线总人数</div>
                <a-badge status="processing" :text="updateTimeText" />
              </div>
            </a-card>
          </a-col>
          <a-col :lg="24" :md="12" :sm="24" :xs="24">
            <a-card
              :bordered="false"
              title="浏览器分布"
              :body-style="{ padding: '6px 0' }"
            >
              <v-chart
                ref="browserChartRef"
                style="height: 240px"
                :option="browserChartOption"
              />
            </a-card>
          </a-col>
        </a-row>
      </a-col>
      <!-- 用户评价 -->
      <a-col :lg="12" :md="24" :sm="24" :xs="24">
        <a-card :bordered="false" title="用户评价">
          <div class="ele-cell ele-cell-align-bottom">
            <div style="font-size: 51px; line-height: 1">4.5</div>
            <div class="ele-cell-content">
              <a-rate :value="userRate" disabled />
              <span style="color: #fadb14; margin-left: 8px">很棒</span>
            </div>
          </div>
          <div class="ele-cell" style="margin: 18px 0">
            <div
              style="font-size: 28px; line-height: 1"
              class="ele-text-placeholder"
            >
              -0%
            </div>
            <div class="ele-cell-content ele-text-small ele-text-secondary">
              当前没有评价波动
            </div>
          </div>
          <div class="ele-cell">
            <div class="ele-cell-content">
              <a-progress
                :percent="80"
                stroke-color="#52c41a"
                :show-info="false"
              />
            </div>
            <div class="monitor-evaluate-text">
              <star-filled />
              <span>5 : 8 人</span>
            </div>
          </div>
          <div class="ele-cell">
            <div class="ele-cell-content">
              <a-progress
                :percent="20"
                stroke-color="#1890ff"
                :show-info="false"
              />
            </div>
            <div class="monitor-evaluate-text">
              <star-filled />
              <span>4 : 2 人</span>
            </div>
          </div>
          <div class="ele-cell">
            <div class="ele-cell-content">
              <a-progress
                :percent="50"
                stroke-color="#faad14"
                :show-info="false"
              />
            </div>
            <div class="monitor-evaluate-text">
              <star-filled />
              <span>3 : 5 人</span>
            </div>
          </div>
          <div class="ele-cell">
            <div class="ele-cell-content">
              <a-progress
                :percent="40"
                stroke-color="#f5222d"
                :show-info="false"
              />
            </div>
            <div class="monitor-evaluate-text">
              <star-filled />
              <span>2 : 4 人</span>
            </div>
          </div>
          <div class="ele-cell">
            <div class="ele-cell-content">
              <a-progress :percent="0" :show-info="false" />
            </div>
            <div class="monitor-evaluate-text">
              <star-filled />
              <span>1 : 0 人</span>
            </div>
          </div>
        </a-card>
      </a-col>
      <!-- 用户满意度 -->
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-card :bordered="false" title="用户满意度">
          <div class="ele-cell ele-text-center">
            <div class="ele-cell-content" style="font-size: 24px">29</div>
            <div class="ele-cell-content">
              <div class="monitor-face-smile"><i></i></div>
              <div class="ele-text-secondary" style="margin-top: 8px">
                正面评论
              </div>
            </div>
            <h2 class="ele-cell-content ele-text-success">82%</h2>
          </div>
          <a-divider style="margin: 26px 0" />
          <div class="ele-cell ele-text-center">
            <div class="ele-cell-content" style="font-size: 24px">5</div>
            <div class="ele-cell-content">
              <div class="monitor-face-cry"><i></i></div>
              <div class="ele-text-secondary" style="margin-top: 8px">
                负面评论
              </div>
            </div>
            <h2 class="ele-cell-content ele-text-danger">8%</h2>
          </div>
        </a-card>
      </a-col>
      <!-- 用户活跃度 -->
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-card :bordered="false" title="用户活跃度">
          <div class="ele-cell" style="padding: 32px 0">
            <div class="ele-cell-content ele-text-center">
              <div class="monitor-progress-group">
                <a-progress
                  type="circle"
                  :percent="70"
                  stroke-color="#52c41a"
                  :show-info="false"
                  :width="161"
                />
                <a-progress
                  type="circle"
                  :percent="60"
                  stroke-color="#1890ff"
                  :show-info="false"
                  :width="121"
                  :stroke-width="5"
                />
                <a-progress
                  type="circle"
                  :percent="35"
                  stroke-color="#f5222d"
                  :show-info="false"
                  :width="91"
                  :stroke-width="4"
                />
              </div>
            </div>
            <div class="monitor-progress-legends">
              <div class="ele-text-small">
                <a-badge color="green" text="活跃率: 70%" />
              </div>
              <div class="ele-text-small">
                <a-badge color="blue" text="留存率: 60%" />
              </div>
              <div class="ele-text-small">
                <a-badge color="red" text="跳出率: 35%" />
              </div>
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, computed, provide, onBeforeUnmount } from 'vue';
  import { message } from 'ant-design-vue';
  import {
    QuestionCircleOutlined,
    EyeFilled,
    FireFilled,
    FlagFilled,
    ThunderboltFilled,
    UpOutlined,
    DownOutlined,
    StarFilled
  } from '@ant-design/icons-vue';
  import { toDateString } from 'ele-admin-pro';
  import { use, registerMap } from 'echarts/core';
  import type { EChartsCoreOption } from 'echarts/core';
  import { CanvasRenderer } from 'echarts/renderers';
  import { PieChart, MapChart } from 'echarts/charts';
  import {
    VisualMapComponent,
    GeoComponent,
    TooltipComponent,
    LegendComponent
  } from 'echarts/components';
  import VChart, { THEME_KEY } from 'vue-echarts';
  import { ChartTheme } from 'ele-admin-pro';
  import {
    getChinaMapData,
    getUserCountList,
    getBrowserCountList
  } from '@/api/dashboard/monitor';
  import type { UserCount } from '@/api/dashboard/monitor/model';
  import { onSizeChange } from '@/utils/on-size-change';

  use([
    CanvasRenderer,
    PieChart,
    MapChart,
    VisualMapComponent,
    GeoComponent,
    TooltipComponent,
    LegendComponent
  ]);

  provide(THEME_KEY, ChartTheme);

  interface VisitUserType {
    name: string;
    avatar: string;
  }

  //
  const userCountMapChartRef = ref<InstanceType<typeof VChart>>();
  const browserChartRef = ref<InstanceType<typeof VChart>>();

  // 访问人数
  const visitUsers = ref<VisitUserType[]>([]);

  // 在线总人数倒计时
  const updateTime = ref(5);

  // 用户分布前10名
  const userCountDataRank = ref<UserCount[]>([]);

  // 当前时间
  const currentTime = ref(toDateString(new Date(), 'HH:mm:ss'));

  // 在线人数
  const onlineNum = ref(228);

  // 在线人数更新定时器
  let onlineNumTimer: number;

  // 用户评分
  const userRate = ref(4.5);

  // 用户分布地图配置
  const userCountMapOption = reactive<EChartsCoreOption>({});

  // 浏览器分布饼图配置
  const browserChartOption = reactive<EChartsCoreOption>({});

  // 在线人数倒计时文字
  const updateTimeText = computed(() => updateTime.value + ' 秒后更新');

  /* 在线人数更新倒计时 */
  const startUpdateOnlineNum = () => {
    onlineNumTimer = window.setInterval(() => {
      currentTime.value = toDateString(new Date(), 'HH:mm:ss');
      if (updateTime.value === 1) {
        updateTime.value = 10;
        onlineNum.value = 100 + Math.round(Math.random() * 900);
      } else {
        updateTime.value--;
      }
    }, 1000);
  };

  /* 获取中国地图数据并注册地图 */
  const registerChinaMap = () => {
    getChinaMapData()
      .then((data) => {
        registerMap('china', data);
        getUserCountData();
      })
      .catch((e) => {
        message.error(e.message);
      });
  };

  /* 获取用户分布数据 */
  const getUserCountData = () => {
    getUserCountList()
      .then((data) => {
        const temp = data.sort((a, b) => b.value - a.value);
        const min = temp[temp.length - 1].value || 0;
        const max = temp[0].value || 1;
        //
        const list = temp.length > 10 ? temp.slice(0, 15) : temp;
        userCountDataRank.value = list.map((d) => {
          return {
            name: d.name,
            value: d.value,
            percent: (d.value / max) * 100
          };
        });
        //
        Object.assign(userCountMapOption, {
          tooltip: {
            trigger: 'item'
          },
          visualMap: {
            min: min,
            max: max,
            text: ['高', '低'],
            calculable: true,
            color: ['#1890FF', '#EBF3FF']
          },
          series: [
            {
              name: '用户数',
              label: {
                show: true
              },
              type: 'map',
              map: 'china',
              data: data
            }
          ]
        });
      })
      .catch((e) => {
        message.error(e.message);
      });
  };

  /* 获取用户浏览器分布数据 */
  const getBrowserCountData = () => {
    getBrowserCountList()
      .then((data) => {
        Object.assign(browserChartOption, {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            bottom: 5,
            itemWidth: 10,
            itemHeight: 10,
            icon: 'circle',
            data: data.map((d) => d.name)
          },
          series: [
            {
              type: 'pie',
              radius: ['45%', '70%'],
              center: ['50%', '43%'],
              label: {
                show: false
              },
              data: data
            }
          ]
        });
      })
      .catch((e) => {
        message.error(e.message);
      });
  };

  /* 重置所有图表尺寸 */
  const resizeCharts = () => {
    userCountMapChartRef.value?.resize();
    browserChartRef.value?.resize();
  };

  onSizeChange(() => {
    resizeCharts();
  });

  onBeforeUnmount(() => {
    // 销毁定时器
    if (onlineNumTimer) {
      clearInterval(onlineNumTimer);
    }
  });

  startUpdateOnlineNum();
  registerChinaMap();
  getBrowserCountData();

  visitUsers.value = [
    {
      name: '王艺洁',
      avatar:
        'https://cdn.eleadmin.com/20200609/c184eef391ae48dba87e3057e70238fb.jpg'
    },
    {
      name: '陈慧欣',
      avatar:
        'https://cdn.eleadmin.com/20200609/b6a811873e704db49db994053a5019b2.jpg'
    },
    {
      name: '李雄志',
      avatar:
        'https://cdn.eleadmin.com/20200609/948344a2a77c47a7a7b332fe12ff749a.jpg'
    },
    {
      name: '许胤韬',
      avatar:
        'https://cdn.eleadmin.com/20200609/f6bc05af944a4f738b54128717952107.jpg'
    },
    {
      name: '王有有',
      avatar:
        'https://cdn.eleadmin.com/20200609/2d98970a51b34b6b859339c96b240dcd.jpg'
    },
    {
      name: '管理员',
      avatar: 'https://cdn.eleadmin.com/20200610/avatar.jpg'
    }
  ];
</script>

<script lang="ts">
  export default {
    name: 'DashboardMonitor'
  };
</script>

<style scoped>
  /* 统计卡片 */
  .monitor-count-card {
    text-align: center;
  }

  .monitor-count-card .monitor-count-card-num {
    margin-top: 6px;
    font-size: 32px;
  }

  .monitor-count-card .monitor-count-card-text {
    font-size: 12px;
    margin: 8px 0;
    opacity: 0.8;
  }

  .monitor-count-card .monitor-count-card-trend {
    font-weight: bold;
    line-height: 26px;
  }

  .monitor-count-card .monitor-count-card-trend > .anticon {
    margin-right: 6px;
  }

  .monitor-count-card .monitor-count-card-tips {
    position: absolute;
    top: 16px;
    right: 16px;
    cursor: pointer;
    opacity: 0.6;
  }

  /* 人数分布 */
  .monitor-user-count-item {
    margin-bottom: 8px;
  }

  .monitor-user-count-item :deep(.ant-progress-inner) {
    background: none;
  }

  .monitor-user-count-item .ele-cell-content {
    padding-right: 10px;
  }

  /* 当前在线人数卡片 */
  .monitor-online-num-card {
    text-align: center;
  }

  .monitor-online-num-text {
    margin-bottom: 6px;
  }

  .monitor-online-num-title {
    font-size: 48px;
    margin-bottom: 13px;
  }

  @media screen and (max-width: 992px) {
    .monitor-online-num-card {
      padding: 22px 0;
    }
  }

  /* 用户评价 */
  .monitor-evaluate-text {
    width: 90px;
    flex-shrink: 0;
    white-space: nowrap;
    opacity: 0.8;
  }

  .monitor-evaluate-text > .anticon {
    font-size: 12px;
    margin: 0 6px 0 8px;
  }

  /* 笑脸、哭脸 */
  .monitor-face-smile,
  .monitor-face-cry {
    width: 50px;
    height: 50px;
    display: inline-block;
    background: #fbd971;
    border-radius: 50%;
    position: relative;
  }

  .monitor-face-smile > i,
  .monitor-face-smile:before,
  .monitor-face-smile:after,
  .monitor-face-cry > i,
  .monitor-face-cry:before,
  .monitor-face-cry:after {
    width: 28px;
    height: 28px;
    border-radius: 50%;
    transform: rotate(225deg);
    border: 3px solid #f0c419;
    border-right-color: transparent !important;
    border-bottom-color: transparent !important;
    position: absolute;
    bottom: 8px;
    left: 11px;
  }

  .monitor-face-smile:before,
  .monitor-face-smile:after,
  .monitor-face-cry:before,
  .monitor-face-cry:after {
    content: '';
    width: 12px;
    height: 12px;
    left: 8px;
    top: 14px;
    border-color: #f29c1f;
    transform: rotate(45deg);
  }

  .monitor-face-smile:after,
  .monitor-face-cry:after {
    left: auto;
    right: 8px;
  }

  .monitor-face-cry > i {
    transform: rotate(45deg);
    bottom: -6px;
  }

  /** 圆形进度条组合 */
  .monitor-progress-group {
    position: relative;
    display: inline-block;
  }

  .monitor-progress-group .ant-progress:not(:first-child) {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    margin-top: -1px;
  }

  .monitor-progress-legends > div + div {
    margin-top: 8px;
  }
</style>
