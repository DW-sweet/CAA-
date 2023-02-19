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
<!--      <a-form-item label="年龄" v-bind="validateInfos.age">-->
<!--        <a-input-number-->
<!--          :min="0"-->
<!--          :max="150"-->
<!--          class="ele-fluid"-->
<!--          placeholder="请输入年龄"-->
<!--          v-model:value="form.age"-->
<!--        />-->
<!--      </a-form-item>-->

      <a-form-item label="品牌名称" v-bind="validateInfos.brandName">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="品牌名称"
          v-model:value="form.brandName"
        />
      </a-form-item>

      <a-form-item label="品牌首字母" v-bind="validateInfos.alpha">
        <a-select
          ref="select"
          v-model:value="form.alpha"
          style="width: 120px"
          @focus="focus"
          @change="handleChange"
        >
          <a-select-option value="A">A</a-select-option>
          <a-select-option value="B">B</a-select-option>
          <a-select-option value="C">C</a-select-option>
          <a-select-option value="D">D</a-select-option>
          <a-select-option value="E">E</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="是否展示" v-bind="validateInfos.bshow">
        <a-radio-group v-model:value="form.bshow" name="radioGroup">
          <a-radio value="是">是</a-radio>
          <a-radio value="否">否</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="评分" v-bind="validateInfos.score">
        <a-input-number
          :min="0"
          :max="150"
          class="ele-fluid"
          placeholder="商品评分"
          v-model:value="form.score"
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
    addBrand,
    updateBrand
  } from '@/api/itemlist/brand'

  import {Brand} from "@/api/itemlist/brand/model";

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'update:visible', visible: boolean): void;
  }>();

  const props = defineProps<{
    // 弹窗是否打开
    visible: boolean;
    // 修改回显的数据
    data?: Brand;
  }>();

  // 是否是修改
  const isUpdate = ref(false);

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive<Brand>({
    brandId: undefined,
    brandName: '',
    // age: '',
    alpha: '',
    score: '',
    bshow: '',
  });

  // 表单验证规则
  const rules = reactive({
    brandName: [
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
        const saveOrUpdate = isUpdate.value ? updateBrand : addBrand;
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
    name: 'BrandEdit'
  };
</script>
