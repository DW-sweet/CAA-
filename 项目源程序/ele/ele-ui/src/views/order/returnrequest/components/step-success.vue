<template>
  <div>
    <a-result title="申请成功" status="success" sub-title="将在48小时内处理,请耐心等待">
      <a-form
        class="ele-form-detail"
        :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
        :wrapper-col="{ md: { span: 16 }, sm: { span: 24 } }"
      >
        <a-form-item label="订单编号">{{ data.account }}</a-form-item>
        <a-form-item label="退款理由">{{ data.receiver }}</a-form-item>
        <a-form-item label="是否退货">{{ data.name }}</a-form-item>
        <a-form-item label="商品金额">
          <span style="font-size: 24px; line-height: 1">
            {{ data.amount }}
          </span>
          元
        </a-form-item>
      </a-form>
      <template #extra>
        <a-space size="middle">
          <a-button type="primary" @click="back"> 返回 </a-button>
          <a-button>查看订单</a-button>
        </a-space>
      </template>
    </a-result>
  </div>
</template>

<script lang="ts" setup>
  import type { StepForm } from '../model';

  defineProps<{
    data: StepForm;
  }>();

  const emit = defineEmits<{
    (e: 'back'): void;
  }>();

  const back = () => {
    emit('back');
  };
</script>
