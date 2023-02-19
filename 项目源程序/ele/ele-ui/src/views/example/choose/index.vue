<template>
  <div class="ele-body ele-body-card" style="padding-bottom: 48px">
    <a-card title="发布实训活动" :bordered="false">
      <a-form
        :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
        :wrapper-col="{ md: { span: 18 }, sm: { span: 24 } }"
      >
        <a-row :gutter="16">
          <a-col :md="8" :sm="24" :xs="24">
            <a-form-item label="实训名称:" v-bind="validateInfos.title">
              <a-input
                allow-clear
                v-model:value="form.title"
                placeholder="请输入实训名称"
              />
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24" :xs="24">
            <a-form-item label="起止日期:" v-bind="validateInfos.datetime">
              <a-range-picker v-model:value="form.datetime" class="ele-fluid" />
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24" :xs="24">
            <a-form-item label="实训地点:" v-bind="validateInfos.address">
              <a-select
                allow-clear
                v-model:value="form.address"
                placeholder="请选择地点"
              >
                <a-select-option value="1">地点一</a-select-option>
                <a-select-option value="2">地点二</a-select-option>
                <a-select-option value="3">地点三</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item
          label="实训内容:"
          v-bind="validateInfos.content"
          :label-col="{ md: { span: 2 }, sm: { span: 24 } }"
          :wrapper-col="{ md: { span: 22 }, sm: { span: 24 } }"
        >
          <a-textarea
            :rows="4"
            v-model:value="form.content"
            placeholder="请输入实训内容"
          />
        </a-form-item>
      </a-form>
    </a-card>
    <a-card title="选择实训班级:" :bordered="false">
      <a-row :gutter="16">
        <a-col :lg="12" :md="24" :sm="24" :xs="24">
          <!-- 未选择的班级数据表格 -->
          <ele-pro-table
            bordered
            :toolkit="[]"
            :columns="columns"
            row-key="classesId"
            sub-title="未选班级:"
            empty-text="已全部选择"
            tools-theme="default"
            :show-size-changer="false"
            :datasource="unChooseClass"
            :scroll="{ x: 400 }"
          >
            <template #toolkit>
              <a-button type="primary" @click="addAll">全部添加</a-button>
            </template>
            <template #bodyCell="{ column, record }">
              <template v-if="column.key === 'action'">
                <a-button size="small" type="primary" @click="addItem(record)">
                  添加
                </a-button>
              </template>
            </template>
          </ele-pro-table>
        </a-col>
        <a-col :lg="12" :md="24" :sm="24" :xs="24">
          <!-- 已选择的班级数据表格 -->
          <ele-pro-table
            bordered
            :toolkit="[]"
            :columns="columns"
            row-key="classesId"
            sub-title="已选班级:"
            emptyText="未选择班级"
            tools-theme="default"
            :show-size-changer="false"
            :datasource="chooseClasses"
            :scroll="{ x: 400 }"
          >
            <template #toolkit>
              <a-button danger type="primary" @click="removeAll">
                全部移除
              </a-button>
            </template>
            <template #bodyCell="{ column, record }">
              <template v-if="column.key === 'action'">
                <a-button
                  danger
                  size="small"
                  type="primary"
                  @click="removeItem(record)"
                >
                  移除
                </a-button>
              </template>
            </template>
          </ele-pro-table>
        </a-col>
      </a-row>
    </a-card>
    <!-- 底部工具栏 -->
    <div class="ele-bottom-tool">
      <div class="ele-bottom-tool-actions">
        <a-button type="primary" :loading="loading" @click="submit">
          提交
        </a-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, computed } from 'vue';
  import type { Dayjs } from 'dayjs';
  import { Form, message } from 'ant-design-vue';
  import { getAllClasses } from '@/api/example/choose';
  import type { Classes } from '@/api/example/choose/model';
  import type { ColumnItem } from 'ele-admin-pro/es/ele-pro-table';

  const useForm = Form.useForm;

  interface FormType {
    title?: string;
    datetime: [Dayjs, Dayjs];
    address?: string;
    content?: string;
  }

  // 表单数据
  const form = reactive<FormType>({
    title: '',
    datetime: [] as unknown as [Dayjs, Dayjs],
    address: undefined,
    content: ''
  });

  // 表单验证规则
  const rules = reactive({
    title: [
      {
        required: true,
        message: '请输入实训名称',
        type: 'string'
      }
    ],
    datetime: [
      {
        required: true,
        message: '请选择起止日期',
        type: 'array'
      }
    ],
    address: [
      {
        required: true,
        message: '请选择实训地点',
        type: 'string'
      }
    ],
    content: [
      {
        required: true,
        message: '请输入实训内容',
        type: 'string'
      }
    ]
  });

  //
  const { resetFields, validate, validateInfos } = useForm(form, rules);

  // 加载状态
  const loading = ref(false);

  // 全部实训班级
  const classes = ref<Classes[]>([]);

  // 表格列配置
  const columns = ref<ColumnItem[]>([
    {
      width: 90,
      title: '操作',
      key: 'action',
      align: 'center',
      fixed: 'left'
    },
    {
      title: '班级名称',
      dataIndex: 'classesName',
      ellipsis: true
    },
    {
      title: '专业',
      dataIndex: 'major',
      ellipsis: true
    },
    {
      title: '学院',
      dataIndex: 'college',
      ellipsis: true
    }
  ]);

  // 已选择的班级数据
  const chooseClasses = ref<Classes[]>([]);

  // 未选择的班级数据
  const unChooseClass = computed(() =>
    classes.value.filter((d) => chooseClasses.value.indexOf(d) === -1)
  );

  /* 获取全部实训班级 */
  const query = () => {
    getAllClasses()
      .then((data) => {
        classes.value = data;
      })
      .catch((e) => {
        message.error(e.message);
      });
  };

  query();

  /* 提交 */
  const submit = () => {
    validate()
      .then(() => {
        if (!chooseClasses.value.length) {
          message.error('请选择实训班级');
          return;
        }
        loading.value = true;
        setTimeout(() => {
          loading.value = false;
          message.success('提交成功');
          resetFields();
        }, 1000);
      })
      .catch(() => {});
  };

  /* 添加 */
  const addItem = (row: Classes) => {
    chooseClasses.value = [...chooseClasses.value, row];
  };

  /* 移除 */
  const removeItem = (row: Classes) => {
    const index = chooseClasses.value.indexOf(row);
    chooseClasses.value = chooseClasses.value.filter((_d, i) => i !== index);
  };

  /* 添加全部 */
  const addAll = () => {
    chooseClasses.value = [...unChooseClass.value];
  };

  /* 移除所有 */
  const removeAll = () => {
    chooseClasses.value = [];
  };
</script>

<script lang="ts">
  export default {
    name: 'ExampleChoose'
  };
</script>
