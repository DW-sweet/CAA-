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
      <a-form-item label="服务单号" v-bind="validateInfos.rnumber">
        <a-input-number
          :min="0"
          :max="150"
          class="ele-fluid"
          placeholder="服务单号"
          v-model:value="form.rnumber"
        />
      </a-form-item>
      <a-form-item label="产品名称" v-bind="validateInfos.greturnName">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="产品名称"
          v-model:value="form.greturnName"
        />
      </a-form-item>
      <a-form-item label="退款用户" v-bind="validateInfos.ruser">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="退款用户"
          v-model:value="form.ruser"
        />
      </a-form-item>

      <a-form-item label="退款金额" v-bind="validateInfos.money">
        <a-input-number
          :min="0"
          :max="150"
          class="ele-fluid"
          placeholder="退款金额"
          v-model:value="form.money"
        />
      </a-form-item>
      <a-form-item label="退款状态" v-bind="validateInfos.state">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="退款状态"
          v-model:value="form.state"
        />
      </a-form-item>
    </a-form>
  </ele-modal>
</template>

<script lang="ts" setup>
  import { ref, reactive, watch } from 'vue';
  import { Form, message } from 'ant-design-vue';
  import { assignObject } from 'ele-admin-pro';
  import {
    addGreturn,
    updateGreturn
  } from '@/api/order/greturn'

  import {Greturn} from "@/api/order/greturn/model";

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'update:visible', visible: boolean): void;
  }>();

  const props = defineProps<{
    // 弹窗是否打开
    visible: boolean;
    // 修改回显的数据
    data?: Greturn;
  }>();

  // 是否是修改
  const isUpdate = ref(false);

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive<Greturn>({
    greturnId: undefined,
    greturnName: '',
    state: '',
    money: '',
    ruser: '',
    rnumber: '',
  });

  // 表单验证规则
  const rules = reactive({
    greturnName: [
      {
        required: true,
        message: '请输入年龄',
        type: 'string',
        trigger: 'blur'
      }
    ],
    state: [
      {
        required: true,
        message: '请输入状态',
        type: 'string',
        trigger: 'blur'
      }
    ],
    ruser: [
      {
        required: true,
        message: '请输入用户',
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
        const saveOrUpdate = isUpdate.value ? updateGreturn : addGreturn;
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
    name: 'GreturnEdit'
  };
</script>
