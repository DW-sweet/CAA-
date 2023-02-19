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
      <a-form-item label="退货原因" v-bind="validateInfos.reasonsName">
        <a-select
          ref="select"
          v-model:value="form.reasonsName"
          placeholder="请选择类型"
          style="width: 120px"
        >
          <a-select-option value="质量问题">质量问题</a-select-option>
          <a-select-option value="7天无理由退货">7天无理由退货</a-select-option>
          <a-select-option value="数量/品牌拍错">数量/品牌拍错</a-select-option>
          <a-select-option value="不想要了">不想要了</a-select-option>
          <a-select-option value="其他">其他</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="单号">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="请输入单号"
          v-model:value="form.code"
        />
      </a-form-item>

      <a-form-item label="添加时间" v-bind="validateInfos.time">
        <a-space direction="vertical" :size="12">
          <a-input
            v-model:value="form.time"
            :bordered="true"
            type="date"
          />
        </a-space>
      </a-form-item>
      <a-form-item label="是否可用" v-bind="validateInfos.yesno">
        <a-radio-group v-model:value="form.yesno">
          <a-radio value="是">是</a-radio>
          <a-radio value="否">否</a-radio>
        </a-radio-group>
      </a-form-item>
    </a-form>
  </ele-modal>
</template>

<script lang="ts" setup>
  import { ref, reactive, watch } from 'vue';
  import { Form, message } from 'ant-design-vue';
  import { assignObject } from 'ele-admin-pro';
  import {
    addReasons,
    updateReasons
  } from '@/api/order/reasons'

  import {Reasons} from "@/api/order/reasons/model";

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'update:visible', visible: boolean): void;
  }>();

  const props = defineProps<{
    // 弹窗是否打开
    visible: boolean;
    // 修改回显的数据
    data?: Reasons;
  }>();

  // 是否是修改
  const isUpdate = ref(false);

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive<Reasons>({
    reasonsId: undefined,
    reasonsName: '',
    code: '',
    time: '',
    yesno: ''
  });

  // 表单验证规则
  const rules = reactive({
    reasonsName: [
      {
        required: true,
        message: '退货原因类型',
        type: 'string',
        trigger: 'blur'
      }
    ],
    code: [
      {
        required: true,
        message: '请输入单号',
        type: 'string',
        trigger: 'blur'
      }
    ],
    time: [
      {
        required: true,
        message: '请选择退货日期',
        type: 'string',
        trigger: 'blur'
      }
    ],
    yes_no: [
      {
        required: true,
        message: '是否可用',
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
        const saveOrUpdate = isUpdate.value ? updateReasons : addReasons;
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
    name: 'ReasonsEdit'
  };
</script>
