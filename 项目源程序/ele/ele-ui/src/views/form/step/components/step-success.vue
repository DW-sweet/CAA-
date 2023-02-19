<template>
  <div>
    <a-result title="操作成功" status="success" sub-title="预计两小时内到账">
      <a-form
        class="ele-form-detail"
        :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
        :wrapper-col="{ md: { span: 16 }, sm: { span: 24 } }"
      >
        <a-form-item label="付款账户">{{ data.account }}</a-form-item>
        <a-form-item label="收款账户">{{ data.receiver }}</a-form-item>
        <a-form-item label="收款人姓名">{{ data.name }}</a-form-item>
        <a-form-item label="转账金额">
          <span style="font-size: 24px; line-height: 1">
            {{ data.amount }}
          </span>
          元
        </a-form-item>
      </a-form>
      <template #extra>
        <a-space size="middle">
          <a-button type="primary" @click="back"> 再转一笔 </a-button>
          <a-button>查看账单</a-button>
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
