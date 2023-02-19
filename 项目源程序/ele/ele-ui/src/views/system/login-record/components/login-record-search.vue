<!-- 搜索表单 -->
<template>
  <a-form
    :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
    :wrapper-col="{ md: { span: 18 }, sm: { span: 24 } }"
  >
    <a-row>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="用户账号">
          <a-input
            v-model:value.trim="where.username"
            placeholder="请输入"
            allow-clear
          />
        </a-form-item>
      </a-col>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="用户名">
          <a-input
            v-model:value.trim="where.nickname"
            placeholder="请输入"
            allow-clear
          />
        </a-form-item>
      </a-col>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="登录时间">
          <a-range-picker
            v-model:value="dateRange"
            value-format="YYYY-MM-DD"
            class="ele-fluid"
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
  import { ref } from 'vue';
  import type { Dayjs } from 'dayjs';
  import useSearch from '@/utils/use-search';
  import type { LoginRecordParam } from '@/api/system/login-record/model';

  const emit = defineEmits<{
    (e: 'search', where?: LoginRecordParam): void;
  }>();

  // 表单数据
  const { where, resetFields } = useSearch<LoginRecordParam>({
    username: '',
    nickname: ''
  });

  // 日期范围选择
  const dateRange = ref<[Dayjs, Dayjs]>([] as unknown as [Dayjs, Dayjs]);

  /* 搜索 */
  const search = () => {
    const [d1, d2] = dateRange.value;
    emit('search', {
      ...where,
      createTimeStart: d1 ? d1 + ' 00:00:00' : '',
      createTimeEnd: d2 ? d2 + ' 23:59:59' : ''
    });
  };

  /*  重置 */
  const reset = () => {
    resetFields();
    dateRange.value = [] as unknown as [Dayjs, Dayjs];
    search();
  };
</script>
