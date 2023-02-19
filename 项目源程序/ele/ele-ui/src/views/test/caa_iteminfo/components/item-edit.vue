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
      <a-form-item label="商品名称" v-bind="validateInfos.itemName">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="商品名称"
          v-model:value="form.itemName"
        />
      </a-form-item>

      <a-form-item label="图片" v-bind="validateInfos.photo">
        <a-input
          class="ele-fluid"
          placeholder="图片"
          v-model:value="form.photo"
        />
      </a-form-item>

      <a-form-item label="价格" v-bind="validateInfos.price">
        <a-input-number
          :min="0"
          :max="2000000"
          class="ele-fluid"
          placeholder="价格"
          v-model:value="form.price"
        />
      </a-form-item>

      <a-form-item label="库存" v-bind="validateInfos.store">
        <a-input-number
          :min="0"
          :max="2000000"
          class="ele-fluid"
          placeholder="库存"
          v-model:value="form.store"
        />
      </a-form-item>

      <a-form-item label="销量" v-bind="validateInfos.sell">
        <a-input-number
          :min="0"
          :max="2000000"
          class="ele-fluid"
          placeholder="销量"
          v-model:value="form.sell"
        />
      </a-form-item>

      <a-form-item label="标签" v-bind="validateInfos.label">
        <a-input
          class="ele-fluid"
          placeholder="标签"
          v-model:value="form.label"
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
    addItem,
    updateItem
  } from '@/api/test/item'

  import {Item} from "@/api/test/item/model";

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'update:visible', visible: boolean): void;
  }>();

  const props = defineProps<{
    // 弹窗是否打开
    visible: boolean;
    // 修改回显的数据
    data?: Item;
  }>();

  // 是否是修改
  const isUpdate = ref(false);

  // 提交状态
  const loading = ref(false);

  // 表单数据
  const form = reactive<Item>({
    itemId: undefined,
    itemName: '',
    photo:'',
    price:'',
    store:'',
    sell:'',
    label:'',
  });

  // 表单验证规则
  const rules = reactive({
    itemName: [
      {
        required: true,
        message: '请输入年龄',
        type: 'string',
        trigger: 'blur'
      }
    ],
    photo: [
      {
        required: false,
        message: '请输入图片',
        type: 'string',
      }
    ],
    price: [
      {
        required: false,
        message: '请输入价格',
        type: 'number',
        trigger: 'blur'
      }
    ],
    store: [
      {
        required: false,
        message: '请输入库存',
        type: 'number',
        trigger: 'blur'
      }
    ],
    sell: [
      {
        required: false,
        message: '请输入销量',
        type: 'number',
        trigger: 'blur'
      }
    ],
    label: [
      {
        required: false,
        message: '请输入图片',
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
        const saveOrUpdate = isUpdate.value ? updateItem : addItem;
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
    name: 'ItemEdit'
  };
</script>
