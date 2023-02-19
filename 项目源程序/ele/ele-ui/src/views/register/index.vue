<template>
  <div
    :class="[
      'login-wrapper',
      ['', 'login-form-right', 'login-form-left'][direction]
    ]"
  >
    <a-form class="login-form ele-bg-white">
      <h4>注册</h4>
      <a-form-item v-bind="validateInfos.username">
        <a-input
          placeholder="请输入用户名"
          v-model:value="form.username"
          allow-clear
          size="large"
        >
          <template #prefix>
            <mobile-outlined />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item v-bind="validateInfos.password">
        <a-input-password
          placeholder="请输入登录密码"
          v-model:value="form.password"
          size="large"
        >
          <template #prefix>
            <lock-outlined />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item v-bind="validateInfos.password2">
        <a-input-password
          placeholder="请再次输入登录密码"
          v-model:value="form.password2"
          size="large"
        >
          <template #prefix>
            <key-outlined />
          </template>
        </a-input-password>
      </a-form-item>

      <a-form-item>
        <router-link
          to="/login"
          class="ele-pull-right"
          style="line-height: 22px"
        >
          返回登录
        </router-link>
      </a-form-item>
      <a-form-item>
        <a-button
          block
          size="large"
          type="primary"
          :loading="loading"
          @click="submit"
        >
          注册
        </a-button>
      </a-form-item>
    </a-form>
    <div class="login-copyright">
      copyright © 2021 eleadmin.com all rights reserved.
    </div>
  </div>
  <!-- 实际项目去掉这段 -->
  <div style="position: absolute; left: 30px; top: 20px; z-index: 999">
    <a-radio-group v-model:value="direction" size="small">
      <a-radio-button :value="2">居左</a-radio-button>
      <a-radio-button :value="0">居中</a-radio-button>
      <a-radio-button :value="1">居右</a-radio-button>
    </a-radio-group>
  </div>
</template>


<script lang="ts" setup>
import { ref, reactive, watch } from 'vue';
import { Form, message } from 'ant-design-vue';
import type { RuleObject } from 'ant-design-vue/es/form';
import { useRouter } from 'vue-router';
import { emailReg, phoneReg } from 'ele-admin-pro';
import type { User } from '@/api/system/user/model';
import type { Dayjs } from 'dayjs';
import {Register} from "@/api/register";

const useForm = Form.useForm;
const { push } = useRouter();
const emit = defineEmits<{
  (e: 'done'): void;
  (e: 'update:visible', visible: boolean): void;
}>();

const props = defineProps<{
  // 弹窗是否打开
  visible: boolean;
  // 修改回显的数据
  data?: User;
}>();

// 是否是修改
const isUpdate = ref(false);

// 提交状态
const loading = ref(false);

// 表单数据
const form = reactive<User>({
  userId: undefined,
  username: '',
  password: '',
});

// 出生日期
const birthday = ref<Dayjs>();

// 表单验证规则
const rules = reactive({
  username: [
    {
      required: true,
      type: 'string',
      trigger: 'blur',
    }
  ],
  password: [
    {
      required: true,
      type: 'string',
      trigger: 'blur',
      validator: async (_rule: RuleObject, value: string) => {
        if (isUpdate.value || /^[\S]{5,18}$/.test(value)) {
          return Promise.resolve();
        }
        return Promise.reject('密码必须为5-18位非空白字符');
      }
    }
  ]
});

const { resetFields, validate, validateInfos } = useForm(form, rules);

/* 提交 */
const submit = () => {
  validate().then(() => {
    loading.value = true;
    Register(form)
      .then((msg) => {
        message.success(msg);
        push('/login');
      })
      .catch((e: Error) => {
        message.error(e.message);
        loading.value = false;
      });
  });
};

</script>


<style scoped>
  /* 背景 */
  .login-wrapper {
    padding: 48px 16px 0 16px;
    position: relative;
    box-sizing: border-box;
    background-image: url('@/assets/bg-login.jpg');
    background-repeat: no-repeat;
    background-size: cover;
    min-height: 100vh;
  }

  .login-wrapper:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.2);
  }

  /* 卡片 */
  .login-form {
    width: 360px;
    margin: 0 auto;
    max-width: 100%;
    padding: 0 28px 16px 28px;
    box-sizing: border-box;
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
    border-radius: 2px;
    position: relative;
    z-index: 2;
  }

  .login-form-right .login-form {
    margin: 0 15% 0 auto;
  }

  .login-form-left .login-form {
    margin: 0 auto 0 15%;
  }

  .login-form h4 {
    padding: 22px 0;
    text-align: center;
  }

  /* 验证码 */
  .login-input-group {
    display: flex;
    align-items: center;
  }

  .login-input-group :deep(.ant-input-affix-wrapper) {
    flex: 1;
  }

  .login-input-group .login-captcha {
    width: 102px;
    height: 40px;
    margin-left: 10px;
    padding: 0;
  }

  .login-input-group .login-captcha > img {
    width: 100%;
    height: 100%;
  }

  /* 第三方登录图标 */
  .login-oauth-icon {
    color: #fff;
    padding: 5px;
    margin: 0 12px;
    font-size: 18px;
    border-radius: 50%;
    cursor: pointer;
  }

  /* 底部版权 */
  .login-copyright {
    color: #eee;
    text-align: center;
    padding: 48px 0 22px 0;
    position: relative;
    z-index: 1;
  }

  /* 响应式 */
  @media screen and (min-height: 640px) {
    .login-wrapper {
      padding-top: 0;
    }

    .login-form {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translateX(-50%);
      margin-top: -230px;
    }

    .login-form-right .login-form,
    .login-form-left .login-form {
      left: auto;
      right: 15%;
      transform: translateX(0);
      margin: -230px auto auto auto;
    }

    .login-form-left .login-form {
      right: auto;
      left: 15%;
    }

    .login-copyright {
      position: absolute;
      left: 0;
      right: 0;
      bottom: 0;
    }
  }

  @media screen and (max-width: 768px) {
    .login-form-right .login-form,
    .login-form-left .login-form {
      left: 50%;
      right: auto;
      margin-left: 0;
      margin-right: auto;
      transform: translateX(-50%);
    }
  }
</style>
