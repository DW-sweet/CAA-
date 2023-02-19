<template>
  <div class="ele-body ele-body-card">
    <!-- 顶部统计快 -->
    <a-row :gutter="16">
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-card class="analysis-chart-card" :bordered="false">
          <div class="ele-text-secondary ele-cell">
            <div class="ele-cell-content">总销售额</div>
            <a-tooltip title="指标说明">
              <question-circle-outlined />
            </a-tooltip>
          </div>
          <h1 class="analysis-chart-card-num">¥ 660</h1>
          <div class="analysis-chart-card-content" style="padding-top: 16px">
            <a-space size="middle">
              <span>
                周同比12% <caret-up-outlined class="ele-text-danger" />
              </span>
              <span>
                日同比11% <caret-down-outlined class="ele-text-success" />
              </span>
            </a-space>
          </div>
          <a-divider />
          <div>日销售额 ￥123</div>
        </a-card>
      </a-col>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-card class="analysis-chart-card" :bordered="false">
          <div class="ele-text-secondary ele-cell">
            <div class="ele-cell-content">访问量</div>
            <ele-tag color="red">日</ele-tag>
          </div>
          <h1 class="analysis-chart-card-num">46</h1>
          <v-chart
            ref="visitChartRef"
            style="height: 40px"
            :option="visitChartOption"
          />
          <a-divider />
          <div>日访问量 1,234</div>
        </a-card>
      </a-col>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-card class="analysis-chart-card" :bordered="false">
          <div class="ele-text-secondary ele-cell">
            <div class="ele-cell-content">支付笔数</div>
            <ele-tag color="blue">月</ele-tag>
          </div>
          <h1 class="analysis-chart-card-num">6</h1>
          <v-chart
            ref="payNumChartRef"
            style="height: 40px"
            :option="payNumChartOption"
          />
          <a-divider />
          <div>转化率 60%</div>
        </a-card>
      </a-col>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-card class="analysis-chart-card" :bordered="false">
          <div class="ele-text-secondary ele-cell">
            <div class="ele-cell-content">领养数</div>
            <ele-tag color="green">周</ele-tag>
          </div>
          <h1 class="analysis-chart-card-num">78%</h1>
          <div class="analysis-chart-card-content" style="padding-top: 16px">
            <a-progress
              :percent="78"
              :show-info="false"
              stroke-color="#13c2c2"
              status="active"
            />
          </div>
          <a-divider />
          <a-space size="middle">
            <span>
              周同比12% <caret-up-outlined class="ele-text-danger" />
            </span>
            <span>
              日同比11% <caret-down-outlined class="ele-text-success" />
            </span>
          </a-space>
        </a-card>
      </a-col>
    </a-row>
    <!-- 销售额、访问量 -->
    <a-card :bordered="false" :body-style="{ padding: 0 }">
      <a-tabs
        v-model:activeKey="saleSearch.type"
        size="large"
        class="monitor-card-tabs"
        @change="onSaleTypeChange"
      >
        <a-tab-pane tab="销售额" key="saleroom" />
        <a-tab-pane tab="访问量" key="visits" />
        <template #rightExtra>
          <a-space size="middle" class="analysis-tabs-extra">
            <a-radio-group v-model:value="saleSearch.dateType">
              <a-radio-button value="1">今天</a-radio-button>
              <a-radio-button value="2">本周</a-radio-button>
              <a-radio-button value="3">本月</a-radio-button>
              <a-radio-button value="4">本年</a-radio-button>
            </a-radio-group>
            <a-range-picker
              v-model:value="saleSearch.datetime"
              :show-time="{ format: 'HH:mm' }"
              format="YYYY-MM-DD HH:mm"
            />
          </a-space>
        </template>
      </a-tabs>
      <div style="padding-bottom: 10px">
        <a-row :gutter="16">
          <a-col :lg="17" :md="16" :sm="24" :xs="24">
            <div
              v-if="saleSearch.type === 'saleroom'"
              class="demo-monitor-title"
            >
              销售量趋势
            </div>
            <div v-else class="demo-monitor-title">访问量趋势</div>
            <v-chart
              ref="saleChartRef"
              style="height: 320px"
              :option="saleChartOption"
            />
          </a-col>
          <a-col :lg="7" :md="8" :sm="24" :xs="24">
            <div v-if="saleSearch.type === 'saleroom'">
              <div class="demo-monitor-title">门店销售额排名</div>
              <div
                v-for="(item, index) in saleroomRankData"
                :key="index"
                class="demo-monitor-rank-item ele-cell"
              >
                <ele-tag
                  shape="circle"
                  :color="index < 3 ? '#314659' : ''"
                  style="border: none"
                >
                  {{ index + 1 }}
                </ele-tag>
                <div class="ele-cell-content">{{ item.name }}</div>
                <div class="ele-text-secondary">{{ item.value }}</div>
              </div>
            </div>
            <div v-else>
              <div class="demo-monitor-title">门店访问量排名</div>
              <div
                v-for="(item, index) in visitsRankData"
                :key="index"
                class="demo-monitor-rank-item ele-cell"
              >
                <ele-tag
                  shape="circle"
                  :color="index < 3 ? '#314659' : ''"
                  style="border: none"
                >
                  {{ index + 1 }}
                </ele-tag>
                <div class="ele-cell-content">{{ item.name }}</div>
                <div class="ele-text-secondary">{{ item.value }}</div>
              </div>
            </div>
          </a-col>
        </a-row>
      </div>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, provide } from 'vue';
  import { message } from 'ant-design-vue';
  import {
    QuestionCircleOutlined,
    CaretUpOutlined,
    CaretDownOutlined
  } from '@ant-design/icons-vue';
  import dayjs from 'dayjs';
  import type { Dayjs } from 'dayjs';
  import { use } from 'echarts/core';
  import type { EChartsCoreOption } from 'echarts/core';
  import { CanvasRenderer } from 'echarts/renderers';
  import { LineChart, BarChart } from 'echarts/charts';
  import {
    GridComponent,
    TooltipComponent,
    LegendComponent
  } from 'echarts/components';
  import VChart, { THEME_KEY } from 'vue-echarts';
  import 'echarts-wordcloud';
  import { ChartTheme, wordCloudColor } from 'ele-admin-pro';
  import {
    getPayNumList,
    getSaleroomList,
    getVisitHourList,
    getWordCloudList
  } from '@/api/dashboard/analysis';
  import type { SaleroomData } from '@/api/dashboard/analysis/model';
  import { onSizeChange } from '@/utils/on-size-change';

  use([
    CanvasRenderer,
    LineChart,
    BarChart,
    GridComponent,
    TooltipComponent,
    LegendComponent
  ]);

  provide(THEME_KEY, ChartTheme);

  //
  const visitChartRef = ref<InstanceType<typeof VChart>>();
  const payNumChartRef = ref<InstanceType<typeof VChart>>();
  const saleChartRef = ref<InstanceType<typeof VChart>>();
  const visitHourChartRef = ref<InstanceType<typeof VChart>>();
  const hotSearchChartRef = ref<InstanceType<typeof VChart>>();

  // 访问量折线图配置
  const visitChartOption = reactive<EChartsCoreOption>({});

  // 支付笔数柱状图配置
  const payNumChartOption = reactive<EChartsCoreOption>({});

  // 销售额柱状图配置
  const saleChartOption = reactive<EChartsCoreOption>({});

  // 最近1小时访问情况折线图配置
  const visitHourChartOption = reactive<EChartsCoreOption>({});

  // 词云图表配置
  const hotSearchChartOption = reactive<EChartsCoreOption>({});

  // 门店销售排名数据
  const saleroomRankData = ref([
    { name: '重师大 1 号店', value: '240' },
    { name: '重师大 2 号店', value: '230' },
    { name: '重师大 3 号店', value: '220' },
    { name: '重师大 4 号店', value: '210' },
    { name: '重师大 5 号店', value: '200' },
    { name: '重师大 6 号店', value: '150' },
    { name: '重师大 7 号店', value: '80' }
  ]);

  // 门店访问排名数据
  const visitsRankData = ref([
    { name: '重师大 1 号店', value: '240' },
    { name: '重师大 2 号店', value: '230' },
    { name: '重师大 3 号店', value: '220' },
    { name: '重师大 4 号店', value: '210' },
    { name: '重师大 5 号店', value: '200' },
    { name: '重师大 6 号店', value: '150' },
    { name: '重师大 7 号店', value: '80' }
  ]);

  // 销售量趋势数据
  const saleroomData1 = ref<SaleroomData[]>([]);

  // 访问量趋势数据
  const saleroomData2 = ref<SaleroomData[]>([]);

  interface SaleSearchType {
    type: string;
    dateType: string;
    datetime: [Dayjs, Dayjs];
  }

  // 销售量搜索参数
  const saleSearch = reactive<SaleSearchType>({
    type: 'saleroom',
    dateType: '1',
    datetime: [
      dayjs('2022-06-10 10:44', 'YYYY-MM-DD HH:mm'),
      dayjs('2022-0-19 13:00', 'YYYY-MM-DD HH:mm')
    ]
  });

  /* 获取支付笔数数据 */
  const getPayNumData = () => {
    getPayNumList()
      .then((data) => {
        Object.assign(visitChartOption, {
          color: '#975fe5',
          tooltip: {
            trigger: 'axis',
            formatter:
              '<i class="ele-chart-dot" style="background: #975fe5;"></i>{b0}: {c0}'
          },
          grid: {
            top: 10,
            bottom: 0,
            left: 0,
            right: 0
          },
          xAxis: [
            {
              show: false,
              type: 'category',
              boundaryGap: false,
              data: data.map((d) => d.date)
            }
          ],
          yAxis: [
            {
              show: false,
              type: 'value',
              splitLine: {
                show: false
              }
            }
          ],
          series: [
            {
              type: 'line',
              smooth: true,
              symbol: 'none',
              areaStyle: {
                opacity: 0.5
              },
              data: data.map((d) => d.value)
            }
          ]
        });

        Object.assign(payNumChartOption, {
          tooltip: {
            trigger: 'axis',
            formatter:
              '<i class="ele-chart-dot" style="background: #3aa1ff;"></i>{b0}: {c0}'
          },
          grid: {
            top: 10,
            bottom: 0,
            left: 0,
            right: 0
          },
          xAxis: [
            {
              show: false,
              type: 'category',
              data: data.map((d) => d.date)
            }
          ],
          yAxis: [
            {
              show: false,
              type: 'value',
              splitLine: {
                show: false
              }
            }
          ],
          series: [
            {
              type: 'bar',
              data: data.map((d) => d.value)
            }
          ]
        });
      })
      .catch((e) => {
        message.error(e.message);
      });
  };

  /* 获取销售量数据 */
  const getSaleroomData = () => {
    getSaleroomList()
      .then((data) => {
        saleroomData1.value = data.list1;
        saleroomData2.value = data.list2;
        onSaleTypeChange();
      })
      .catch((e) => {
        message.error(e.message);
      });
  };

  /* 获取最近1小时访问情况数据 */
  const getVisitHourData = () => {
    getVisitHourList()
      .then((data) => {
        Object.assign(visitHourChartOption, {
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['浏览量', '访问量'],
            right: 20
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: data.map((d) => d.time)
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '浏览量',
              type: 'line',
              smooth: true,
              symbol: 'none',
              areaStyle: {
                opacity: 0.5
              },
              data: data.map((d) => d.views)
            },
            {
              name: '访问量',
              type: 'line',
              smooth: true,
              symbol: 'none',
              areaStyle: {
                opacity: 0.5
              },
              data: data.map((d) => d.visits)
            }
          ]
        });
      })
      .catch((e) => {
        message.error(e.message);
      });
  };

  /* 获取词云数据 */
  const getWordCloudData = () => {
    getWordCloudList()
      .then((data) => {
        Object.assign(hotSearchChartOption, {
          tooltip: {
            show: true
          },
          series: [
            {
              type: 'wordCloud',
              width: '100%',
              height: '100%',
              sizeRange: [12, 24],
              gridSize: 6,
              textStyle: {
                color: wordCloudColor
              },
              emphasis: {
                textStyle: {
                  shadowBlur: 8,
                  shadowColor: 'rgba(0, 0, 0, .15)'
                }
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

  /* 销售量tab选择改变事件 */
  const onSaleTypeChange = () => {
    if (saleSearch.type === 'saleroom') {
      Object.assign(saleChartOption, {
        tooltip: {
          trigger: 'axis'
        },
        xAxis: [
          {
            type: 'category',
            data: saleroomData1.value.map((d) => d.month)
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            type: 'bar',
            data: saleroomData1.value.map((d) => d.value)
          }
        ]
      });
    } else {
      Object.assign(saleChartOption, {
        tooltip: {
          trigger: 'axis'
        },
        xAxis: [
          {
            type: 'category',
            data: saleroomData2.value.map((d) => d.month)
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            type: 'bar',
            data: saleroomData2.value.map((d) => d.value)
          }
        ]
      });
    }
  };

  /* 重置所有图表尺寸 */
  const resizeCharts = () => {
    visitChartRef.value?.resize();
    payNumChartRef.value?.resize();
    saleChartRef.value?.resize();
    visitHourChartRef.value?.resize();
    hotSearchChartRef.value?.resize();
  };

  onSizeChange(() => {
    resizeCharts();
  });

  getPayNumData();
  getSaleroomData();
  getVisitHourData();
  getWordCloudData();
</script>

<script lang="ts">
  export default {
    name: 'DashboardAnalysis'
  };
</script>

<style scoped>
  /* 统计卡片 */
  .analysis-chart-card :deep(.ant-card-body) {
    padding: 16px 22px 12px 22px;
  }

  .analysis-chart-card-num {
    font-size: 30px;
  }

  .analysis-chart-card-content {
    height: 40px;
  }

  .analysis-chart-card :deep(.ant-divider) {
    margin: 12px 0;
  }

  /* 销售额、访问量 */
  .monitor-card-tabs :deep(.ant-tabs-nav) {
    padding: 0 16px 0 4px;
  }

  .monitor-card-tabs :deep(.ant-tabs-tab) {
    padding-left: 2px;
    padding-right: 2px;
    margin: 0 12px !important;
  }

  .demo-monitor-title {
    padding: 6px 20px;
  }

  .demo-monitor-rank-item {
    padding: 0 20px;
    margin-top: 18px;
  }

  @media screen and (max-width: 880px) {
    .analysis-tabs-extra {
      display: none;
    }
  }
</style>
