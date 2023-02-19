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
      <a-form-item label="名称" v-bind="validateInfos.animalName">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="学生姓名"
          v-model:value="form.animalName"
        />
      </a-form-item>

<!--      <a-form-item label="年龄" v-bind="validateInfos.age">-->
<!--        <a-input-number-->
<!--          :min="0"-->
<!--          :max="150"-->
<!--          class="ele-fluid"-->
<!--          placeholder="请输入年龄"-->
<!--          v-model:value="form.age"-->
<!--        />-->
<!--      </a-form-item>-->
      <a-form-item label="性别" v-bind="validateInfos.gender">
        <a-radio-group v-model:value="form.gender" name="radioGroup">
          <a-radio value="母">母</a-radio>
          <a-radio value="公">公</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="活动范围" v-bind="validateInfos.arange">
        <a-select
          ref="select"
          v-model:value="form.arange"
          style="width: 120px"
          @focus="focus"
          @change="handleChange"
        >
          <a-select-option value="惠风苑">惠风苑</a-select-option>
          <a-select-option value="清风苑">清风苑</a-select-option>
          <a-select-option value="图书馆">图书馆</a-select-option>
          <a-select-option value="南门">南门</a-select-option>
          <a-select-option value="不确定">不确定</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="是否绝育" v-bind="validateInfos.ster">
        <a-radio-group v-model:value="form.ster" name="radioGroup">
          <a-radio value="已绝育">已绝育</a-radio>
          <a-radio value="未绝育">未绝育</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="是否领养" v-bind="validateInfos.adopt">
        <a-radio-group v-model:value="form.adopt" name="radioGroup">
          <a-radio value="是">是</a-radio>
          <a-radio value="否">否</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="领养人" v-bind="validateInfos.master">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="领养人"
          v-model:value="form.master"
        />
      </a-form-item>


    </a-form>
  </ele-modal>
</template>

<script lang="ts" setup>
import { ref, reactive, watch,defineComponent,toRefs } from 'vue';
import { Form, message } from 'ant-design-vue';
import type { CascaderProps } from 'ant-design-vue';
import { assignObject } from 'ele-admin-pro';
  import {
    addAnimal,
    updateAnimal
  } from '@/api/animalAid/animal'

  import {Animal} from "@/api/animalAid/animal/model";

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'update:visible', visible: boolean): void;
  }>();

  const props = defineProps<{
    // 弹窗是否打开
    visible: boolean;
    // 修改回显的数据
    data?: Animal;
  }>();

  // 是否是修改
  const isUpdate = ref(false);

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive<Animal>({
    animalId: undefined,
    animalName: '',
    // age: '',
    gender: '',
    arange: '',
    ster: '',
    adopt: '',
    master: '',
  });

  // 表单验证规则
  const rules = reactive({
    animalName: [
      {
        required: true,
        message: '请输入姓名',
        type: 'string',
        trigger: 'blur'
      }
    ]
    // age: [
    //   {
    //     required: true,
    //     message: '请输入年龄',
    //     type: 'number',
    //     trigger: 'blur'
    //   }
    // ]
  });

  const { resetFields, validate, validateInfos } = useForm(form, rules);

  /* 保存编辑 */
  const save = () => {
    validate()
      .then(() => {
        loading.value = true;
        const saveOrUpdate = isUpdate.value ? updateAnimal : addAnimal;
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
    name: 'AnimalEdit'
  };
</script>
