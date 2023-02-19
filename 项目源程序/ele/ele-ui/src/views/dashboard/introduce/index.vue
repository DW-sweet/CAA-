<template>
  <div id="bg_cat">
    <a-image-preview-group>
      <a-list
        :data-source="data"
        :loading="loading && page === 1"
        item-layout="vertical"
        size="large"
      >
        <template #renderItem="{ item, index }">
          <div id="words">
            <div id="title" style="font-size: 50px;color: #ffffff">
              {{ item.title }}
            </div>
            <a-list-item :key="index">
              <a-list-item-meta>
                <template #description>
                  <a-tag v-for="(tag, i) in item.tags" :key="i">
                    {{ tag }}
                  </a-tag>
                </template>
              </a-list-item-meta>
              <div class="ele-text-heading" style="font-size: 20px;color: #ffffff">
                {{ item.content }}
              </div>
            </a-list-item>
            <div class="bts">
              <form >
                <ul>
                  <div>
                    <a id="bt1" href="/animalAid">
                      动物管理
                    </a>
                  </div>
                  <div>
                    <a id="bt2" href="/itemlist">
                      商品管理
                    </a>
                  </div>
                  <div>
                    <a id="bt3" href="/order">
                      订单管理
                    </a>
                  </div>
                </ul>
              </form>
            </div>
          </div>
        </template>
      </a-list>
    </a-image-preview-group>
  </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import {
  LikeOutlined,
  StarOutlined,
  MessageOutlined
} from '@ant-design/icons-vue';

interface DataType {
  title: string;
  content: string;
  tags: string[];
}

const data = ref<DataType[]>([]);

const loading = ref(false);

const page = ref(2);

const query = () => {
  loading.value = true;
  setTimeout(() => {
    loading.value = false;
    page.value++;
    data.value = data.value.concat(data.value.slice(0, 3));
  }, 1000);
};

data.value = [
  {
    title: 'CAA系统简介',
    content:
      'CAA——CQNU Animal Aid（重庆师范大学流浪动物救助）系统，是一个为学校师生提供的线上流浪动物救助系统。',
    tags: ['系统', '流浪动物', '爱心帮帮团'],
  }
];
</script>

<script lang="ts">
export default {
  name: 'ListCardArticle'
};
</script>

<style lang="less" scoped>
@media screen and (max-width: 768px) {
  .ant-list-item :deep(.ant-list-item-extra .ant-image) {
    width: 100% !important;
  }
}
a-tag{
  opacity: 50%;
}
#bg_cat{
  background:url("https://w.wallhaven.cc/full/eo/wallhaven-eod6w8.jpg");
  opacity:70%;
  position:center;
  background-size:100% 100%;
  height: 700px;
  width: 100%;
  margin-top: 0;
}
#words{
  width: 100%;
  height: 80px;
  margin: 0px 870px;
}
.ele-text-heading{
  width: 300px;
}
#title{
  margin: 0px 20px;
}
.bts{
  margin: 20px -15px;
}
#bt1{
  margin: 20px 60px;
  font-size: 15px;
  position: relative;
  display: inline-block;
  padding: 12px 60px;
  text-decoration: none;
  color: white;
  border: 1px solid white;
  border-radius: 2px;
  background-color: transparent;
  font-family: Muli,'Helvetica Neue','Hiragino Sans GB','WenQuanYi Micro Hei','Microsoft Yahei',sans-serif !important;
}
#bt2{
  margin: 15px 60px;
  font-size: 15px;
  position: relative;
  display: inline-block;
  padding: 12px 60px;
  text-decoration: none;
  color: white;
  border: 1px solid white;
  border-radius: 2px;
  background-color: transparent;
  font-family: Muli,'Helvetica Neue','Hiragino Sans GB','WenQuanYi Micro Hei','Microsoft Yahei',sans-serif !important;
}
#bt3{
  margin: 25px 60px;
  font-size: 15px;
  position: relative;
  display: inline-block;
  padding: 12px 60px;
  text-decoration: none;
  color: white;
  border: 1px solid white;
  border-radius: 2px;
  background-color: transparent;
  font-family: Muli,'Helvetica Neue','Hiragino Sans GB','WenQuanYi Micro Hei','Microsoft Yahei',sans-serif !important;
}

</style>
