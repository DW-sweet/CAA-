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
      <a-form-item label="品牌名称" v-bind="validateInfos.commandName">
        <a-select
          ref="select"
          v-model:value="form.commandName"
          placeholder="请选择品牌"
          style="width: 120px"
        >
          <a-select-option value="卫仕">卫仕</a-select-option>
          <a-select-option value="江小傲">江小傲</a-select-option>
          <a-select-option value="素力高">素力高</a-select-option>
          <a-select-option value="麦复迪">麦复迪</a-select-option>
          <a-select-option value="力狼">力狼</a-select-option>
          <a-select-option value="阿飞和巴弟">阿飞和巴弟</a-select-option>
          <a-select-option value="瓜洲牧">瓜洲牧</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="排序">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="请输入排序"
          v-model:value="form.code"
        />
      </a-form-item>

      <a-form-item label="是否推荐" v-bind="validateInfos.yesno">
        <a-radio-group v-model:value="form.yesno">
          <a-radio value="是">是</a-radio>
          <a-radio value="否">否</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="推荐状态" v-bind="validateInfos.state">
        <a-select
          ref="select"
          v-model:value="form.state"
          placeholder="请选择推荐状态"
          style="width: 120px"
        >
          <a-select-option value="推荐中">推荐中</a-select-option>
          <a-select-option value="未推荐">未推荐</a-select-option>
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
    addCommand,
    updateCommand
  } from '@/api/order/command'

  import {Command} from "@/api/order/command/model";

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'update:visible', visible: boolean): void;
  }>();

  const props = defineProps<{
    // 弹窗是否打开
    visible: boolean;
    // 修改回显的数据
    data?: Command;
  }>();

  // 是否是修改
  const isUpdate = ref(false);

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive<Command>({
    commandId: undefined,
    commandName: '',
    code: '',
    yesno: '',
    state: ''
  });

  // 表单验证规则
  const rules = reactive({
    commandName: [
      {
        required: true,
        message: '品牌名称',
        type: 'string',
        trigger: 'blur'
      }
    ],
    code: [
      {
        required: true,
        message: '请输入排序',
        type: 'string',
        trigger: 'blur'
      }
    ],
    yes_no: [
      {
        required: true,
        message: '是否推荐',
        type: 'string',
        trigger: 'blur'
      }
    ],
    state: [
      {
        required: true,
        message: '推荐状态',
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
        const saveOrUpdate = isUpdate.value ? updateCommand : addCommand;
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
    name: 'CommandEdit'
  };
</script>
