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
      <a-form-item label="名称" v-bind="validateInfos.awardName">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="学生姓名"
          v-model:value="form.awardName"
        />
      </a-form-item>

      <a-form-item label="年龄" v-bind="validateInfos.age">
        <a-input-number
          :min="0"
          :max="150"
          class="ele-fluid"
          placeholder="请输入年龄"
          v-model:value="form.age"
        />
      </a-form-item>

      <a-form-item label="性别" v-bind="validateInfos.gender">
        <a-radio-group v-model:value="form.gender">
          <a-radio :style="radioStyle" value="男">男</a-radio>
          <a-radio :style="radioStyle" value="女">女</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="邮箱" v-bind="validateInfos.email">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="邮箱"
          v-model:value="form.email"
        />
      </a-form-item>

      <a-form-item label="学校" v-bind="validateInfos.college">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="学校"
          v-model:value="form.college"
        />
      </a-form-item>

      <a-form-item label="日期">
        <a-space direction="vertical" :size="12">
          <a-date-picker v-model:value="value3" :format="monthFormat" picker="month" />
        </a-space>
      </a-form-item>

      <a-form-item label="指导老师" v-bind="validateInfos.instructor">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="指导老师"
          style="width: 120px"
          v-model:value="form.instructor"
        />
      </a-form-item>



      <a-form-item label="获奖项目" v-bind="validateInfos.program">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="获奖项目"
          v-model:value="form.program"
        />
      </a-form-item>

      <a-form-item label="比赛名称" v-bind="validateInfos.racename">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="比赛名称"
          v-model:value="form.racename"
        />
      </a-form-item>

      <a-form-item label="奖项等级" v-bind="validateInfos.grade">
        <a-select
          v-model:value="form.grade"
          style="width: 120px"
        >
          <a-select-option value="市级">市级</a-select-option>
          <a-select-option value="省级">省级</a-select-option>
          <a-select-option value="国家级">国家级</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="奖金金额" v-bind="validateInfos.money">
        <a-input-number
          :min="0"
          :max="100000"
          class="ele-fluid"
          placeholder="请输入奖金金额"
          v-model:value="form.money"
        />
      </a-form-item>

      <a-form-item label="举办单位" v-bind="validateInfos.awarder">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="举办单位"
          v-model:value="form.awarder"
        />
      </a-form-item>



      <a-form-item label="证书编号" v-bind="validateInfos.serial">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="证书编号"
          v-model:value="form.serial"
        />
      </a-form-item>

      <a-form-item label="授奖方式" v-bind="validateInfos.way">
        <a-radio-group v-model:value="form.way">
          <a-radio-button value="线上颁奖">线上网络颁奖</a-radio-button>
          <a-radio-button value="线下表彰">线下表彰大会</a-radio-button>
        </a-radio-group>
        <!--
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="授奖方式"
          v-model:value="form.way"
        />
        -->
      </a-form-item>


    </a-form>
  </ele-modal>
</template>

<script lang="ts" setup>
import { ref, reactive, watch } from 'vue';
import { Form, message } from 'ant-design-vue';
import { assignObject } from 'ele-admin-pro';
import {
  addAward,
  updateAward
} from '@/api/test/award'

import {Award} from "@/api/test/award/model";

const useForm = Form.useForm;

const emit = defineEmits<{
  (e: 'done'): void;
  (e: 'update:visible', visible: boolean): void;
}>();

const props = defineProps<{
  // 弹窗是否打开
  visible: boolean;
  // 修改回显的数据
  data?: Award;
}>();

// 是否是修改
const isUpdate = ref(false);

// 提交状态
const loading = ref(false);

// 表单数据
const form = reactive<Award>({
  awardId: undefined,
  awardName: '',
  age: '',
  gender:'',
  email:'',
  college:'',
  instructor:'',
  program: '',
  racename: '',
  money: '',
  awarder: '',
  grade: '',
  serial:'',
  way: '',
});

// 表单验证规则
const rules = reactive({
  awardName: [
    {
      required: true,
      message: '请输入姓名',
      type: 'string',
      trigger: 'blur'
    }
  ],
  age: [
    {
      required: true,
      message: '请输入年龄',
      type: 'number',
      trigger: 'blur'
    }
  ],
  gender: [
    {
      required: true,
      message: '请输入性别',
      type: 'string',
      trigger: 'blur'
    }
  ],
  email: [
    {
      required: true,
      message: '请输入邮箱',
      type: 'string',
      trigger: 'blur'
    }
  ],
  college: [
    {
      required: true,
      message: '请输入学校',
      type: 'string',
      trigger: 'blur'
    }
  ],
  instructor: [
    {
      required: true,
      message: '请输入指导老师',
      type: 'string',
      trigger: 'blur'
    }
  ],
  program: [
    {
      required: true,
      message: '请输入项目',
      type: 'string',
      trigger: 'blur'
    }
  ],
  racename: [
    {
      required: true,
      message: '请输入比赛名',
      type: 'string',
      trigger: 'blur'
    }
  ],
  money: [
    {
      required: true,
      message: '请输入获奖金额',
      type: 'number',
      trigger: 'blur'
    }
  ],
  awarder: [
    {
      required: true,
      message: '请输入颁奖方',
      type: 'string',
      trigger: 'blur'
    }
  ],
  grade: [
    {
      required: true,
      message: '请输入等级',
      type: 'string',
      trigger: 'blur'
    }
  ],
  serial: [
    {
      required: true,
      message: '请输入证书编号',
      type: 'string',
      trigger: 'blur'
    }
  ],
  way: [
    {
      required: true,
      message: '请输入颁奖方式',
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
      const saveOrUpdate = isUpdate.value ? updateAward : addAward;
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
  name: 'AwardEdit'
};
</script>
