<template>
  <a-form
    :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
    :wrapper-col="{ md: { span: 16 }, sm: { span: 24 } }"
  >
    <a-form-item label="订单编号" v-bind="validateInfos.account">
      <a-select
        allow-clear
        v-model:value="form.account"
        placeholder="请选择订单编号"
      >
        <a-select-option value="0619568495312545548771235">
          0619568495312545548771235
        </a-select-option>
        <a-select-option value="0619568495331548754855621">
          0619568495331548754855621
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="退款理由" v-bind="validateInfos.receiver">
      <a-input
        allow-clear
        v-model:value="form.receiver"
        placeholder="请输入具体理由"
      >
        <template #addonBefore>
          <a-select
            v-model:value="form.pay"
            style="width: 150px; margin: -5px -12px"
          >
            <a-select-option value="七天无理由">七天无理由</a-select-option>
            <a-select-option value="拍错了不想要了">拍错了、不想要了</a-select-option>
            <a-select-option value="数量/品牌拍错">数量/品牌拍错了</a-select-option>
            <a-select-option value="质量问题">质量问题</a-select-option>
          </a-select>
        </template>
      </a-input>
    </a-form-item>
    <a-form-item label="是否退货" v-bind="validateInfos.name">
      <a-select
        allow-clear
        v-model:value="form.name"
        placeholder="请选择退款方式"
      >
        <a-select-option value="退货退款">
          退货退款
        </a-select-option>
        <a-select-option value="仅退款">
          仅退款
        </a-select-option>
        <a-select-option value="换货">
          换货
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="商品金额" v-bind="validateInfos.amount">
      <a-input
        prefix="￥"
        allow-clear
        v-model:value.number="form.amount"
        placeholder="请输入商品金额"
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
    account: '0619568495312545548771235',
    receiver: '请填写具体理由',
    pay: '七天无理由',
    name: '退货退款',
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
