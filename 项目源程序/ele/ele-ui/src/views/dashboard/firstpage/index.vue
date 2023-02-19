<template>
    <a-carousel arrows>
      <template #prevArrow>
        <div class="custom-slick-arrow" style="left: 10px; z-index: 1">
          <left-circle-outlined />
        </div>
      </template>
      <template #nextArrow>
        <div class="custom-slick-arrow" style="right: 10px">
          <right-circle-outlined />
        </div>
      </template>
      <div><a-image src="https://w.wallhaven.cc/full/6o/wallhaven-6o38ww.jpg" style="right: 10px;width: 100%;height: 300px"></a-image></div>
      <div><a-image src="https://w.wallhaven.cc/full/e7/wallhaven-e7q82o.jpg" style="right: 10px;width: 100%;height: 300px"></a-image></div>
      <div><a-image src="https://w.wallhaven.cc/full/rd/wallhaven-rdlg27.jpg" style="right: 10px;width: 100%;height: 300px"></a-image></div>
      <div><a-image src="https://w.wallhaven.cc/full/4d/wallhaven-4d28k3.jpg" style="right: 10px;width: 100%;height: 300px"></a-image></div>
    </a-carousel>
  <a-row :gutter="16">
    <a-col
      v-for="(item, index) in data"
      :key="index"
      :lg="6"
      :md="8"
      :sm="12"
      :xs="12"
    >
      <a-card :bordered="false" hoverable style="margin-top: 16px">
        <template #cover>
          <img :src="item.cover" alt="" style="height: 200px; width: 100%"/>
        </template>
        <a-card-meta :title="item.title">
          <template #description>
            <div class="project-list-desc">
              {{ item.content }}
            </div>
          </template>
        </a-card-meta>
        <div class="ele-cell">
          <div class="ele-cell-content ele-text-secondary">
            {{ item.time }}
          </div>
<!--          <ele-avatar-list :data="item.users" size="small" />-->
        </div>
      </a-card>
    </a-col>
  </a-row>
</template>

<script lang="ts" setup>
  import { ref,defineComponent } from 'vue';

  interface UserType {
    name: string;
    avatar: string;
  }

interface DataType {
  title: string;
  content: string;
  time: string;
  cover: string;
  users: UserType[];
}

const data = ref<DataType[]>([]);

data.value = [
  {
    title: '小甲',
    content:
      '性格温和，叫声很小， 它乐意与人为伴，并用它特有的这种安宁的方式来表达。',
    time: '已领养',
    cover:
      'https://w.wallhaven.cc/full/k7/wallhaven-k7586d.jpg'
  },
  {
    title: '小乙',
    content:
      '除了拥有固定而聚，代表性的遗传特征之外，又具有丰富的变异性，如背毛色眼睛颜色等。',
    time: '未领养',
    cover:
      'https://w.wallhaven.cc/full/57/wallhaven-57ppv3.jpg'
  },
  {
    title: '小丙',
    content:
      '美国短毛猫素以体格魁梧，骨骼粗壮，肌肉发达，生性聪明，性格温顺著称，是短毛猫中的大体型猫。',
    time: '已领养',
    cover:
      'https://w.wallhaven.cc/full/rd/wallhaven-rdwq5j.jpg'
  },
  {
    title: '小丁',
    content:
      '波斯猫是最常见的长毛猫，波斯猫有一张讨人喜爱的面庞，长而华丽的背毛，优雅的举止，故有“猫中王子”、“ 王妃 ”之称，是世界上爱猫者最喜欢的纯种猫之一，占有极其重要的地位。',
    time: '已领养',
    cover:
      'https://w.wallhaven.cc/full/g7/wallhaven-g7vxvd.jpg'
  },
  {
    title: '小戊',
    content: '加菲猫有着浑圆的眼睛，大而圆的头部，两眼间距较阔，脖子短而粗，鼻子很短，因为鼻节较短泪腺发达容易流眼泪，性格温顺，十分亲人。',
    time: '未领养',
    cover:
      'https://w.wallhaven.cc/full/v9/wallhaven-v9o6l8.jpg'
  },
  {
    title: '小己',
    content:
      '头呈楔形，眼大而圆， 被毛 丰厚，四肢较长且富有肉感，尾长，身体柔软，毛色有重点色、手套色或双色等等。布偶猫较为 温顺 好静，对人友善。',
    time: '未领养',
    cover:
      'https://w.wallhaven.cc/full/z8/wallhaven-z8y3oy.jpg'
  },
  {
    title: '小庚',
    content:
      '它非常受百姓们喜欢，因为它有漂亮、厚实的皮毛，健康的身体。容易喂养，并且对捕捉老鼠十分在行。',
    time: '已领养',
    cover:
      'https://w.wallhaven.cc/full/q2/wallhaven-q2zd5d.jpg'
  },
  {
    title: '小辛',
    content:
      '暹罗猫能够较好适应主人当地的气候，且性格刚烈好动，机智灵活，好奇心特强，善解人意。',
    time: '未领养',
    cover:
      'https://w.wallhaven.cc/full/dp/wallhaven-dpx7zo.jpg'
  }
];
</script>

<script lang="ts">
  import { defineComponent } from 'vue';
  import { LeftCircleOutlined, RightCircleOutlined } from '@ant-design/icons-vue';
  export default {
    name: 'ListCardProject',
    components: {
      LeftCircleOutlined,
      RightCircleOutlined,
    },
  };
</script>

<style lang="less" scoped>
.project-list-desc {
  height: 44px;
  line-height: 22px;
  margin-bottom: 20px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
/* 轮播图样式 */
.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 300px;
  line-height: 160px;
  background: white;
  overflow: hidden;
}

.ant-carousel :deep(.slick-arrow.custom-slick-arrow) {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: orange;
  background-color: rgba(31, 45, 61, 0.11);
  opacity: 0.3;
  z-index: 1;
}
.ant-carousel :deep(.custom-slick-arrow:before) {
  display: none;
}
.ant-carousel :deep(.custom-slick-arrow:hover) {
  opacity: 0.5;
}

.ant-carousel :deep(.slick-slide h3) {
  color: orangered;
}
</style>
