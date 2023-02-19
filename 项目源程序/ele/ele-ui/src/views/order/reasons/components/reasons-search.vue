<!-- 搜索表单 -->
<template>
  <a-form
    :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
    :wrapper-col="{ md: { span: 18 }, sm: { span: 24 } }"
  >
    <a-row>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="退货原因">
          <a-select
            ref="select"
            v-model:value.trim="where.reasonsName"
            placeholder="请选择类型"
            style="width: 150px"
          >
            <a-select-option value=""></a-select-option>
            <a-select-option value="质量问题">质量问题</a-select-option>
            <a-select-option value="7天无理由退货">7天无理由退货</a-select-option>
            <a-select-option value="数量/品牌拍错">数量/品牌拍错</a-select-option>
            <a-select-option value="不想要了">不想要了</a-select-option>
            <a-select-option value="其他">其他</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="添加时间">
          <a-space direction="vertical" :size="12">
            <a-input
              v-model:value.trim="where.time"
              :bordered="true"
              type="date"
            />
          </a-space>
        </a-form-item>
      </a-col>

      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="单号">
          <a-input
            :min="0"
            :max="9999"
            class="ele-fluid"
            v-model:value.trim="where.code"
            placeholder="请输入单号"
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
  import type { ReasonsParam } from '@/api/order/reasons/model';

  const emit = defineEmits<{
    (e: 'search', where?: ReasonsParam): void;
  }>();

  // 表单数据
  const { where, resetFields } = useSearch<ReasonsParam>({
    reasonsName: '',
    code: ''
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
