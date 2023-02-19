<template>
  <div class="ele-body ele-body-card">
    <!-- 地图位置选择弹窗 -->
    <ele-map-picker
      v-model:visible="visible"
      :dark-mode="darkMode"
      :need-city="true"
      @done="onChoose"
    />
    <ele-map-picker
      v-model:visible="visible2"
      :dark-mode="darkMode"
      :need-city="true"
      :search-type="1"
      @done="onChoose"
    />
    <!-- 弹窗选择位置 -->
    <a-card title="弹窗选择位置" :bordered="false">
      <a-space>
        <a-button type="primary" @click="openPicker">POI检索模式</a-button>
        <a-button type="primary" @click="openPicker2">关键字检索模式</a-button>
      </a-space>
      <div style="margin-top: 12px">选择位置: {{ result.location }}</div>
      <div style="margin-top: 12px">详细地址: {{ result.address }}</div>
      <div style="margin-top: 12px">经 纬 度 : {{ result.lngAndLat }}</div>
    </a-card>
    <!-- 官网底部地图 -->
    <a-card title="官网底部地图" :bordered="false">
      <div ref="locationMapRef" style="height: 360px; max-width: 1000px"></div>
    </a-card>
    <!-- 轨迹展示及轨迹回放 -->
    <a-card title="轨迹展示及轨迹回放" :bordered="false">
      <div
        ref="trackMapRef"
        style="height: 360px; max-width: 1000px; margin-bottom: 16px"
      ></div>
      <a-space>
        <a-button type="primary" @click="startTrackAnim">开始动画</a-button>
        <a-button type="primary" @click="pauseTrackAnim">暂停动画</a-button>
        <a-button type="primary" @click="resumeTrackAnim">继续动画</a-button>
      </a-space>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, watch, onMounted, onUnmounted } from 'vue';
  import AMapLoader from '@amap/amap-jsapi-loader';
  import type { CenterPoint } from 'ele-admin-pro/es/ele-map-picker';
  import { useThemeStore } from '@/store/modules/theme';
  import { storeToRefs } from 'pinia';

  const themeStore = useThemeStore();
  const { darkMode } = storeToRefs(themeStore);

  // 是否显示地图选择弹窗
  const visible = ref(false);

  // 是否显示地图选择弹窗2
  const visible2 = ref(false);

  // 表单
  const result = reactive({
    location: '',
    address: '',
    lngAndLat: ''
  });

  //
  const locationMapRef = ref<HTMLElement | null>(null);
  const trackMapRef = ref<HTMLElement | null>(null);

  // 官网底部地图的实例
  let mapInsLocation: any;

  // 小车轨迹地图的实例
  let mapInsTrack: any;

  // 小车的marker
  let carMarker: any;

  // 轨迹路线
  const lineData = [
    [116.478935, 39.997761],
    [116.478939, 39.997825],
    [116.478912, 39.998549],
    [116.478912, 39.998549],
    [116.478998, 39.998555],
    [116.478998, 39.998555],
    [116.479282, 39.99856],
    [116.479658, 39.998528],
    [116.480151, 39.998453],
    [116.480784, 39.998302],
    [116.480784, 39.998302],
    [116.481149, 39.998184],
    [116.481573, 39.997997],
    [116.481863, 39.997846],
    [116.482072, 39.997718],
    [116.482362, 39.997718],
    [116.483633, 39.998935],
    [116.48367, 39.998968],
    [116.484648, 39.999861]
  ];

  /* 打开位置选择 */
  const openPicker = () => {
    visible.value = true;
  };

  /* 打开位置选择2 */
  const openPicker2 = () => {
    visible2.value = true;
  };

  /* 地图选择后回调 */
  const onChoose = (location: CenterPoint) => {
    console.log(location);
    result.location = `${location.city?.province}/${location.city?.city}/${location.city?.district}`;
    result.address = `${location.name} ${location.address}`;
    result.lngAndLat = `${location.lng},${location.lat}`;
    visible.value = false;
    visible2.value = false;
  };

  /* 渲染官网底部地图 */
  const renderLocationMap = () => {
    AMapLoader.load({
      key: '006d995d433058322319fa797f2876f5',
      version: '2.0',
      plugins: ['AMap.InfoWindow', 'AMap.Marker']
    })
      .then((AMap) => {
        // 渲染地图
        const option = {
          zoom: 13, // 初缩放级别
          center: [114.346084, 30.511215 + 0.005], // 初始中心点
          mapStyle: darkMode.value ? 'amap://styles/dark' : undefined
        };
        mapInsLocation = new AMap.Map(locationMapRef.value, option);
        // 创建信息窗体
        const infoWindow = new AMap.InfoWindow({
          content: `
            <div style="color: #333;">
              <div style="padding: 5px;font-size: 16px;">努力不挂科小组科技有限公司</div>
              <div style="padding: 0 5px;">地址: 重庆市沙坪坝区虎溪街道重庆师范大学</div>
              <div style="padding: 0 5px;">电话: 020-123456789</div>
            </div>
            <a
              class="ele-text-primary"
              style="padding: 8px 5px 0;text-decoration: none;display: inline-block;"
              href="//uri.amap.com/marker?position=114.346084,30.511215&name=努力不挂科小组科技有限公司"
              target="_blank">到这里去→
            </a>
          `
        });
        infoWindow.open(mapInsLocation, [114.346084, 30.511215]);
        const icon = new AMap.Icon({
          size: new AMap.Size(25, 34),
          image:
            '//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-red.png',
          imageSize: new AMap.Size(25, 34)
        });
        const marker = new AMap.Marker({
          icon: icon,
          position: [114.346084, 30.511215],
          offset: new AMap.Pixel(-12, -28)
        });
        marker.setMap(mapInsLocation);
        marker.on('click', () => {
          infoWindow.open(mapInsLocation);
        });
      })
      .catch((e) => {
        console.error(e);
      });
  };

  /* 渲染轨迹回放地图 */
  const renderTrackMap = () => {
    AMapLoader.load({
      key: '006d995d433058322319fa797f2876f5',
      version: '2.0',
      plugins: ['AMap.MoveAnimation', 'AMap.Marker', 'AMap.Polyline']
    })
      .then((AMap) => {
        // 渲染地图
        const option = {
          zoom: 17,
          center: [116.478935, 39.997761],

          mapStyle: darkMode.value ? 'amap://styles/dark' : undefined
        };
        mapInsTrack = new AMap.Map(trackMapRef.value, option);
        // 创建小车marker
        carMarker = new AMap.Marker({
          map: mapInsTrack,
          position: [116.478935, 39.997761],
          icon: 'https://a.amap.com/jsapi_demos/static/demo-center-v2/car.png',
          offset: new AMap.Pixel(-13, -26)
        });
        // 绘制轨迹
        new AMap.Polyline({
          map: mapInsTrack,
          path: lineData,
          showDir: true,
          strokeColor: '#2288FF', // 线颜色
          strokeOpacity: 1, // 线透明度
          strokeWeight: 6 // 线宽
          //strokeStyle: 'solid'  // 线样式
        });
        // 通过的轨迹
        const passedPolyline = new AMap.Polyline({
          map: mapInsTrack,
          showDir: true,
          strokeColor: '#44BB55', // 线颜色
          strokeOpacity: 1, // 线透明度
          strokeWeight: 6 // 线宽
        });
        // 小车移动回调
        carMarker.on('moving', (e) => {
          passedPolyline.setPath(e.passedPath);
        });
        // 地图自适应
        mapInsTrack.setFitView();
      })
      .catch((e) => {
        console.error(e);
      });
  };

  /* 开始轨迹回放动画 */
  const startTrackAnim = () => {
    if (carMarker) {
      carMarker.stopMove();
      carMarker.moveAlong(lineData, {
        duration: 200,
        autoRotation: true
      });
    }
  };

  /* 暂停轨迹回放动画 */
  const pauseTrackAnim = () => {
    if (carMarker) {
      carMarker.pauseMove();
    }
  };

  /* 继续开始轨迹回放动画 */
  const resumeTrackAnim = () => {
    if (carMarker) {
      carMarker.resumeMove();
    }
  };

  /* 渲染地图 */
  onMounted(() => {
    renderLocationMap();
    renderTrackMap();
  });

  /* 销毁地图 */
  onUnmounted(() => {
    if (mapInsLocation) {
      mapInsLocation.destroy();
    }
    if (mapInsTrack) {
      mapInsTrack.destroy();
    }
  });

  /* 同步框架暗黑模式切换 */
  watch(darkMode, () => {
    if (mapInsLocation) {
      if (darkMode.value) {
        mapInsLocation.setMapStyle('amap://styles/dark');
      } else {
        mapInsLocation.setMapStyle('amap://styles/normal');
      }
    }
    if (mapInsTrack) {
      if (darkMode.value) {
        mapInsTrack.setMapStyle('amap://styles/dark');
      } else {
        mapInsTrack.setMapStyle('amap://styles/normal');
      }
    }
  });
</script>

<script lang="ts">
  export default {
    name: 'ExtensionMap'
  };
</script>
