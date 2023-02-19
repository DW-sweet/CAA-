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
      <a-form-item label="名称" v-bind="validateInfos.classifyName">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="分类姓名"
          v-model:value="form.classifyName"
        />
      </a-form-item>

      <a-form-item label="数量" v-bind="validateInfos.quantity">
        <a-input-number
          class="ele-fluid"
          placeholder="请输入数量"
          v-model:value="form.quantity"
        />
      </a-form-item>

      <a-form-item label="单位" v-bind="validateInfos.unit">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="单位"
          v-model:value="form.unit"
        />
      </a-form-item>
      <a-form-item label="导航" v-bind="validateInfos.navigation">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="导航"
          v-model:value="form.navigation"
        />
      </a-form-item>
      <a-form-item label="设置" v-bind="validateInfos.install">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="设置"
          v-model:value="form.install"
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
    addClassify,
    updateClassify
  } from '@/api/test/classify'

  import {Classify} from "@/api/test/classify/model";

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'update:visible', visible: boolean): void;
  }>();

  const props = defineProps<{
    // 弹窗是否打开
    visible: boolean;
    // 修改回显的数据
    data?: Classify;
  }>();

  // 是否是修改
  const isUpdate = ref(false);

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive<Classify>({
    classifyId: undefined,
    classifyName: '',
    quantity: '',
    unit:'',
    navigation:'',
    install:''
  });

  // 表单验证规则
  const rules = reactive({
    classifyName: [
      {
        required: true,
        message: '请输入年龄',
        type: 'string',
        trigger: 'blur'
      }
    ],
    quantity: [
      {
        required: true,
        message: '请输入数量',
        type: 'number',
        trigger: 'blur'
      }
    ],
    unit: [
      {
        required: true,
        message: '请输入单位',
        type: 'string',
        trigger: 'blur'
      }
    ],
    navigation: [
      {
        required: true,
        message: '请输入导航',
        type: 'string',
        trigger: 'blur'
      }
    ],
    install: [
      {
        required: true,
        message: '请输入设置',
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
        const saveOrUpdate = isUpdate.value ? updateClassify : addClassify;
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
    name: 'ClassifyEdit'
  };
</script>
