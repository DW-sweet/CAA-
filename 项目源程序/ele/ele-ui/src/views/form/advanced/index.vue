<template>
  <a-page-header :ghost="false" title="复杂表单">
    <div class="ele-text-secondary">
      复杂表单常见于一次性输入和提交大批量数据的场景。
    </div>
  </a-page-header>
  <div class="ele-body ele-body-card" style="padding-bottom: 48px">
    <a-form
      :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
      :wrapper-col="{ md: { span: 18 }, sm: { span: 24 } }"
    >
      <a-card :bordered="false" title="仓库信息">
        <a-row :gutter="16">
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="仓库名" v-bind="validateInfos.name">
              <a-input
                allow-clear
                v-model:value="form.name"
                placeholder="请输入仓库名"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="仓库域名" v-bind="validateInfos.url">
              <a-input
                allow-clear
                addon-after=".com"
                addon-before="https://"
                v-model:value="form.url"
                placeholder="请输入"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item
              label="仓库管理员"
              v-bind="validateInfos.administrator"
            >
              <a-select
                allow-clear
                v-model:value="form.administrator"
                placeholder="请选择仓库管理员"
              >
                <a-select-option value="1">SunSmile</a-select-option>
                <a-select-option value="2">Jasmine</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="审批人" v-bind="validateInfos.approver">
              <a-select
                allow-clear
                v-model:value="form.approver"
                placeholder="请选择审批人"
              >
                <a-select-option value="1">SunSmile</a-select-option>
                <a-select-option value="2">Jasmine</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="生效日期" v-bind="validateInfos.datetime">
              <a-range-picker class="ele-fluid" v-model:value="form.datetime" />
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="仓库类型" v-bind="validateInfos.type">
              <a-select
                allow-clear
                v-model:value="form.type"
                placeholder="请选择仓库类型"
              >
                <a-select-option value="private">私密</a-select-option>
                <a-select-option value="public">公开</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
      <a-card :bordered="false" title="任务信息">
        <a-row :gutter="16">
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="任务名" v-bind="validateInfos.task">
              <a-input
                allow-clear
                v-model:value="form.task"
                placeholder="请输入任务名"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="任务表述" v-bind="validateInfos.description">
              <a-input
                allow-clear
                v-model:value="form.description"
                placeholder="请输入任务表述"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="执行人" v-bind="validateInfos.executor">
              <a-select
                allow-clear
                v-model:value="form.executor"
                placeholder="请选择执行人"
              >
                <a-select-option value="1">SunSmile</a-select-option>
                <a-select-option value="2">Jasmine</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="责任人" v-bind="validateInfos.officer">
              <a-select
                allow-clear
                v-model:value="form.officer"
                placeholder="请选择责任人"
              >
                <a-select-option value="1">SunSmile</a-select-option>
                <a-select-option value="2">Jasmine</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="提醒时间" v-bind="validateInfos.reminder">
              <a-time-picker
                class="ele-fluid"
                value-format="hh:mm:ss"
                v-model:value="form.reminder"
                placeholder="请选择提醒时间"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8" :md="12" :sm="24" :xs="24">
            <a-form-item label="任务类型" v-bind="validateInfos.taskType">
              <a-select
                allow-clear
                v-model:value="form.taskType"
                placeholder="请选择任务类型"
              >
                <a-select-option value="1">私密</a-select-option>
                <a-select-option value="2">公开</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
      <user-select />
      <!-- 底部工具栏 -->
      <div class="ele-bottom-tool">
        <div v-if="validMsg" class="ele-text-danger">
          <close-circle-outlined />
          <span>{{ validMsg }}</span>
        </div>
        <div class="ele-bottom-tool-actions">
          <a-button type="primary" :loading="loading" @click="submit">
            提交
          </a-button>
        </div>
      </div>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import { Form, message } from 'ant-design-vue';
  import { CloseCircleOutlined } from '@ant-design/icons-vue';
  import type { Dayjs } from 'dayjs';
  import UserSelect from './components/user-select.vue';

  const useForm = Form.useForm;

  // 加载状态
  const loading = ref(false);

  //
  interface FormType {
    name?: string;
    url?: string;
    administrator?: string;
    approver?: string;
    datetime?: [Dayjs, Dayjs];
    type?: string;
    task?: string;
    description?: string;
    executor?: string;
    officer?: string;
    reminder?: string;
    taskType?: string;
  }

  // 表单数据
  const form = reactive<FormType>({
    name: '',
    url: '',
    administrator: undefined,
    approver: undefined,
    datetime: [] as unknown as [Dayjs, Dayjs],
    type: undefined,
    task: '',
    description: '',
    executor: undefined,
    officer: undefined,
    reminder: undefined,
    taskType: undefined
  });

  // 表单验证规则
  const rules = reactive({
    name: [
      {
        required: true,
        message: '请输入仓库名',
        type: 'string'
      }
    ],
    url: [
      {
        required: true,
        message: '请输入仓库域名',
        type: 'string'
      }
    ],
    administrator: [
      {
        required: true,
        message: '请选择仓库管理员',
        type: 'string'
      }
    ],
    approver: [
      {
        required: true,
        message: '请选择审批人',
        type: 'string'
      }
    ],
    datetime: [
      {
        required: true,
        message: '请选择生效日期',
        type: 'array'
      }
    ],
    type: [
      {
        required: true,
        message: '请选择仓库类型',
        type: 'string'
      }
    ],
    task: [
      {
        required: true,
        message: '请输入任务名',
        type: 'string'
      }
    ],
    description: [
      {
        required: true,
        message: '请输入任务表述',
        type: 'string'
      }
    ],
    executor: [
      {
        required: true,
        message: '请选择执行人',
        type: 'string'
      }
    ],
    officer: [
      {
        required: true,
        message: '请选择责任人',
        type: 'string'
      }
    ],
    reminder: [
      {
        required: true,
        message: '请选择提醒时间',
        type: 'string'
      }
    ],
    taskType: [
      {
        required: true,
        message: '请选择任务类型',
        type: 'string'
      }
    ]
  });

  const { resetFields, validate, validateInfos } = useForm(form, rules);

  // 表单验证失败提示信息
  const validMsg = ref('');

  /* 表单提交 */
  const submit = () => {
    validate()
      .then(() => {
        validMsg.value = '';
        loading.value = true;
        setTimeout(() => {
          loading.value = false;
          message.success('提交成功');
          resetFields();
        }, 1000);
      })
      .catch((e: any) => {
        validMsg.value = ` 共有 ${e.errorFields.length} 项校验不通过`;
      });
  };
</script>

<script lang="ts">
  export default {
    name: 'FormAdvanced'
  };
</script>
