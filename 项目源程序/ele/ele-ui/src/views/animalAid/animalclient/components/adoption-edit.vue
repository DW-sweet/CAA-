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

      <a-form-item label="名称" v-bind="validateInfos.animalname">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="动物姓名"
          v-model:value="form.animalname"
        />
      </a-form-item>

      <a-form-item label="名称" v-bind="validateInfos.username">
        <a-input
          allow-clear
          :maxlength="20"
          placeholder="人姓名"
          v-model:value="form.username"
        />
      </a-form-item>

    </a-form>
  </ele-modal>
</template>

<script lang="ts" setup>
import {ref, reactive, watch, computed} from 'vue';
  import { Form, message } from 'ant-design-vue';
  import { assignObject } from 'ele-admin-pro';
  import { addAdoption, updateAdoption } from '@/api/dashboard/adoption';
  import { Adoption } from '@/api/dashboard/adoption/model';
  import {useUserStore} from "@/store/modules/user";
import animal from "@/views/animalAid/animal/index.vue";

  const useForm = Form.useForm;

  const emit = defineEmits<{
    (e: 'done'): void;
    (e: 'update:visible', visible: boolean): void;
  }>();

  const props = defineProps<{
    // 弹窗是否打开
    visible: boolean;
    // 修改回显的数据
    data?: Adoption;
  }>();

  // 是否是修改
  const isUpdate = ref(false);

  // 提交状态
  const loading = ref(false);
  const userStore = useUserStore();
  const loginUser = computed(() => userStore.info ?? {});
  const userid = loginUser.value.userId;

  // 表单数据
  const form = reactive<Adoption>({
    adoptionId: undefined,
    //adoptionName: '',
    animalname: '',
    username: loginUser.value.username,
    userid: userid,
    adoptionstatus: '审核中'
  });

  // 表单验证规则
  const rules = reactive({

    animalname: [
      {
        required: false,
        message: '请输入的动物名字',
        type: 'string',
        trigger: 'blur'
      }
    ],
  });

  const { resetFields, validate, validateInfos } = useForm(form, rules);

  /* 保存编辑 */
  const save = () => {
    validate()
      .then(() => {
        loading.value = true;
        const saveOrUpdate = addAdoption;
        console.log(form);
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

</script>
<script lang="ts">
  export default {
    name: 'AdoptionEdit'
  };
</script>
