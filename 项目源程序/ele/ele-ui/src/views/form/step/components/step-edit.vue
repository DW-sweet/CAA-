<template>
  <a-form
    :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
    :wrapper-col="{ md: { span: 16 }, sm: { span: 24 } }"
  >
    <a-form-item label="付款账户" v-bind="validateInfos.account">
      <a-select
        allow-clear
        v-model:value="form.account"
        placeholder="请选择付款账户"
      >
        <a-select-option value="eleadmin@eclouds.com">
          eleadmin@eclouds.com
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="收款账户" v-bind="validateInfos.receiver">
      <a-input
        allow-clear
        v-model:value="form.receiver"
        placeholder="请输入收款账户"
      >
        <template #addonBefore>
          <a-select
            v-model:value="form.pay"
            style="width: 100px; margin: -5px -12px"
          >
            <a-select-option value="alipay">支付宝</a-select-option>
            <a-select-option value="wxpay">微信</a-select-option>
          </a-select>
        </template>
      </a-input>
    </a-form-item>
    <a-form-item label="收款人姓名" v-bind="validateInfos.name">
      <a-input
        allow-clear
        v-model:value="form.name"
        placeholder="请输入收款人姓名"
      />
    </a-form-item>
    <a-form-item label="转账金额" v-bind="validateInfos.amount">
      <a-input
        prefix="￥"
        allow-clear
        v-model:value.number="form.amount"
        placeholder="请输入转账金额"
      />
    </a-form-item>
    <a-form-item :wrapper-col="{ sm: { offset: 6 } }">
      <a-button type="primary" :loading="loading" @click="submit">
        下一步
      </a-button>
    </a-form-item>
  </a-form>
</template>

<script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import { Form } from 'ant-design-vue';
  import type { StepForm } from '../model';

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done', data: StepForm): void;
  }>();

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive<StepForm>({
    account: 'eleadmin@eclouds.com',
    receiver: 'test@example.com',
    pay: 'alipay',
    name: 'Alex',
    amount: 500
  });

  // 表单验证规则
  const rules = reactive({
    account: [
      {
        required: true,
        message: '请选择付款账户',
        type: 'string'
      }
    ],
    receiver: [
      {
        required: true,
        message: '请输入收款账户',
        type: 'string'
      }
    ],
    name: [
      {
        required: true,
        message: '请输入收款人姓名',
        type: 'string'
      }
    ],
    amount: [
      {
        required: true,
        message: '请输入合法金额数字',
        type: 'number'
      }
    ]
  });

  const { validate, validateInfos } = useForm(form, rules);

  /* 步骤一提交 */
  const submit = () => {
    validate()
      .then(() => {
        loading.value = true;
        setTimeout(() => {
          loading.value = false;
          emit('done', form);
        }, 300);
      })
      .catch(() => {});
  };
</script>
