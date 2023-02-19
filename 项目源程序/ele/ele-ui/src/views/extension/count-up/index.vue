<template>
  <div class="ele-body ele-body-card">
    <a-card title="滚动数字" :bordered="false">
      <h1 style="padding-left: 10px; margin-bottom: 15px">
        <ele-count-up
          :end-val="countUpVal"
          :options="countUpOptions"
          @ready="onCountUpReady"
        />
      </h1>
      <a-space>
        <a-button @click="startCountUp">重新开始</a-button>
        <a-button @click="updateCountUp">更新数字</a-button>
      </a-space>
    </a-card>
    <a-card title="自动更新" :bordered="false">
      <h1 style="padding: 20px 10px">
        <ele-count-up :end-val="countUpVal2" />
      </h1>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, onMounted, onBeforeUnmount } from 'vue';
  import type { CountUp } from 'countup.js';

  // countUp值
  const countUpVal = ref(6317);

  // countUp配置
  const countUpOptions = reactive({
    useEasing: true,
    useGrouping: true,
    separator: ',',
    decimal: '.',
    prefix: '',
    suffix: ''
  });

  // countUp实例
  let countUpIns: CountUp;

  //
  const countUpVal2 = ref(2345);

  //
  let countUpTimer: number;

  /* countUp渲染完成 */
  const onCountUpReady = (instance: CountUp) => {
    countUpIns = instance;
  };

  /* countUp重新开始 */
  const startCountUp = () => {
    if (!countUpIns) {
      return;
    }
    countUpIns.reset();
    countUpIns.start();
  };

  /* countUp更新 */
  const updateCountUp = () => {
    if (!countUpIns) {
      return;
    }
    countUpIns.update(1000 + Math.round(Math.random() * 9000));
  };

  onMounted(() => {
    countUpTimer = window.setInterval(() => {
      countUpVal2.value += 10 + Math.round(Math.random() * 300);
    }, 4000);
  });

  onBeforeUnmount(() => {
    if (countUpTimer) {
      clearInterval(countUpTimer);
    }
  });
</script>

<script lang="ts">
  export default {
    name: 'ExtensionCountUp'
  };
</script>
