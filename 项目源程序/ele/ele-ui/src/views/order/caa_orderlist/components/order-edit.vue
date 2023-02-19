<!-- 字典编辑弹窗 -->
<template>
  <ele-modal
    :width="460"
    :visible="visible"
    :confirm-loading="loading"
    :title="isUpdate ? '修改字典' : '添加字典'"
    :body-style="{ paddingBottom: '8px' }"
    @update:visible="updateVisible"
    @ok="save"
  >
    <a-form
      :label-col="{ md: { span: 5 }, sm: { span: 24 } }"
      :wrapper-col="{ md: { span: 19 }, sm: { span: 24 } }"
    >
      <a-form-item label="订单编号" v-bind="validateInfos.orderNo">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="订单编号"
          v-model:value="form.orderNo"
        />
      </a-form-item>

      <a-form-item label="订单时间" v-bind="validateInfos.orderTime">
        <a-space direction="vertical" :size="12">
          <a-input
            v-model:value="form.orderTime"
            :bordered="true"
            type="date"
          />
        </a-space>
      </a-form-item>

      <a-form-item label="订单账户" v-bind="validateInfos.orderAccount">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="账户号"
          v-model:value="form.orderAccount"
        />
      </a-form-item>

      <a-form-item label="订单金额" v-bind="validateInfos.orderMoney">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="订单金额"
          v-model:value="form.orderMoney"
        />
      </a-form-item>

      <a-form-item label="订单状态" v-bind="validateInfos.orderState">
        <a-select
          ref="select"
          v-model:value="form.orderState"
          placeholder="订单状态"
          style="width: 120px"
        >
          <a-select-option value="待付款">待付款</a-select-option>
          <a-select-option value="待发货">待发货</a-select-option>
          <a-select-option value="已发货">已发货</a-select-option>
          <a-select-option value="已关闭">已关闭</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="订单来源" v-bind="validateInfos.orderSource">
        <a-select
          ref="select"
          v-model:value="form.orderSource"
          placeholder="订单来源"
          style="width: 120px"
        >
          <a-select-option value="APP订单">APP订单</a-select-option>
          <a-select-option value="小程序订单">小程序订单</a-select-option>
          <a-select-option value="网页订单">网页订单</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="商品名称" v-bind="validateInfos.orderName">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="商品姓名"
          v-model:value="form.orderName"
        />
      </a-form-item>

      <a-form-item label="支付方式" v-bind="validateInfos.payWay">
        <a-select
          ref="select"
          v-model:value="form.payWay"
          placeholder="支付方式"
          style="width: 120px"
        >
          <a-select-option value="微信">微信</a-select-option>
          <a-select-option value="支付宝">支付宝</a-select-option>
          <a-select-option value="网上银行">网上银行</a-select-option>
          <a-select-option value="未支付">未支付</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </ele-modal>
</template>

<script lang="ts" setup>
  import { ref, reactive, watch } from 'vue';
  import { Form, message } from 'ant-design-vue';
  import { assignObject } from 'ele-admin-pro';
  import {
    addOrder,
    updateOrder
  } from '@/api/order/caa_orderlist'

  import {Order} from "@/api/order/caa_orderlist/model";

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'update:visible', visible: boolean): void;
  }>();

  const props = defineProps<{
    // 弹窗是否打开
    visible: boolean;
    // 修改回显的数据
    data?: Order;
  }>();

  // 是否是修改
  const isUpdate = ref(false);

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive<Order>({orderId:undefined, orderNo:'', orderTime:'',orderAccount:'',orderMoney:'',orderState:'',
    orderSource:'',orderName:'',payWay:'',
  });

  // 表单验证规则
  const rules = reactive({
    orderNo: [
      {
        required: true,
        message: '请输入订单编号',
        type: 'string',
        trigger: 'blur'
      }
    ],
    orderTime: [
      {
        required: true,
        message: '请输入订单时间',
        type: 'string',
        trigger: 'blur'
      }
    ],
    orderAccount: [
      {
        required: true,
        message: '请输入订单账户',
        type: 'string',
        trigger: 'blur'
      }
    ],
    orderMoney: [
      {
        required: true,
        message: '请输入订单金额',
        type: 'string',
        trigger: 'blur'
      }
    ],
    orderState: [
      {
        required: true,
        message: '请选择订单状态',
        type: 'string',
        trigger: 'blur'
      }
    ],
    orderSource: [
      {
        required: true,
        message: '请选择订单来源',
        type: 'string',
        trigger: 'blur'
      }
    ],
    orderName: [
      {
        required: true,
        message: '请输入商品名称',
        type: 'string',
        trigger: 'blur'
      }
    ],
    payWay: [
      {
        required: true,
        message: '请选择支付方式',
        type: 'string',
        trigger: 'blur'
      }
    ]
  });

  const { resetFields, validate, validateInfos } = useForm(form, rules);

  /* 保存编辑 */
  const save = () => {
    validate()
      .then(() => {
        loading.value = true;
        const saveOrUpdate = isUpdate.value ? updateOrder : addOrder;
        saveOrUpdate(form)
          .then((msg) => {
            loading.value = false;
            message.success(msg);
            updateVisible(false);
            emit('done');
          })
          .catch((e) => {
            loading.value = false;
            message.error(e.message);
          });
      })
      .catch(() => {});
  };

  /* 更新visible */
  const updateVisible = (value: boolean) => {
    emit('update:visible', value);
  };

  watch(
    () => props.visible,
    (visible) => {
      if (visible) {
        if (props.data) {
          assignObject(form, props.data);
          isUpdate.value = true;
        } else {
          isUpdate.value = false;
        }
      } else {
        resetFields();
      }
    }
  );
</script>
<script lang="ts">
  export default {
    name: 'OrderEdit'
  };
</script>
