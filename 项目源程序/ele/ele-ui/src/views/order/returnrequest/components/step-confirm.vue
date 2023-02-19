<template>
  <a-form
    class="ele-form-detail"
    :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
    :wrapper-col="{ md: { span: 16 }, sm: { span: 24 } }"
  >
    <a-alert
      closable
      show-icon
      type="info"
      message="请确认信息，提交后将无法修改"
    />
    <a-form-item label="订单编号" style="margin-top: 24px">
      {{ data.account }}
    </a-form-item>
    <a-form-item label="退款理由">{{ data.receiver }}</a-form-item>
    <a-form-item label="是否退货">{{ data.name }}</a-form-item>
    <a-form-item label="商品金额">
      <span style="font-size: 24px; line-height: 1">
        {{ data.amount }}
      </span>
      元
    </a-form-item>
    <a-divider style="margin: 20px 0 30px 0" />
    <a-form-item label="支付密码" v-bind="validateInfos.password">
      <a-input-password
        v-model:value="form.password"
        placeholder="请输入支付密码"
      />
    </a-form-item>
    <a-form-item :wrapper-col="{ sm: { offset: 6 } }" style="margin-top: 24px">
      <a-space size="middle">
        <a-button type="primary" :loading="loading" @click="submit">
          下一步
        </a-button>
        <a-button @click="back">上一步</a-button>
      </a-space>
    </a-form-item>
  </a-form>
</template>

<script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import { Form } from 'ant-design-vue';
  import type { StepForm } from '../model';

  const useForm = Form.useForm;

  defineProps<{
    data: StepForm;
  }>();

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'back'): void;
  }>();

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive({
    password: '123456'
  });

  // 表单验证规则
  const rules = reactive({
    password: [
      {
        required: true,
        message: '请输入支付密码'
      }
    ]
  });

  const { validate, validateInfos } = useForm(form, rules);

  const submit = () => {
    validate()
      .then(() => {
        loading.value = true;
        setTimeout(() => {
          loading.value = false;
          emit('done');
        }, 300);
      })
      .catch(() => {});
  };

  const back = () => {
    emit('back');
  };
</script>
