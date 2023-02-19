<!-- 搜索表单 -->
<template>
  <a-form
    :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
    :wrapper-col="{ md: { span: 18 }, sm: { span: 24 } }"
  >
    <a-row>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="姓名">
          <a-input
            v-model:value.trim="where.awardName"
            placeholder="请输入姓名"
            allow-clear
          />
        </a-form-item>
      </a-col>


      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item class="ele-text-right" :wrapper-col="{ span: 24 }">
          <a-space>
            <a-button type="primary" @click="search">查询</a-button>
            <a-button @click="reset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-col>
    </a-row>
  </a-form>
</template>

<script lang="ts" setup>
import useSearch from '@/utils/use-search';
import type { AwardParam } from '@/api/test/award/model';

const emit = defineEmits<{
  (e: 'search', where?: AwardParam): void;
}>();

// 表单数据
const { where, resetFields } = useSearch<AwardParam>({
  awardName: ''
});

/* 搜索 */
const search = () => {
  emit('search', where);
};

/*  重置 */
const reset = () => {
  resetFields();
  search();
};
</script>
