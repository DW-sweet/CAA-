<template>
  <div
    :class="[
      'login-wrapper',
      ['', 'login-form-right', 'login-form-left'][direction]
    ]"
  >
    <a-form class="login-form ele-bg-white">
      <h4>{{ t('login.title') }}</h4>
      <a-form-item v-bind="validateInfos.username">
        <a-input
          allow-clear
          size="large"
          v-model:value="form.username"
          :placeholder="t('login.username')"
        >
          <template #prefix>
            <user-outlined />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item v-bind="validateInfos.password">
        <a-input-password
          size="large"
          v-model:value="form.password"
          :placeholder="t('login.password')"
        >
          <template #prefix>
            <lock-outlined />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item v-bind="validateInfos.code">
        <div class="login-input-group">
          <a-input
            allow-clear
            size="large"
            v-model:value="form.code"
            :placeholder="t('login.code')"
          >
            <template #prefix>
              <safety-certificate-outlined />
            </template>
          </a-input>
          <a-button class="login-captcha" @click="changeCaptcha">
            <img v-if="captcha" :src="captcha" alt="" />
          </a-button>
        </div>
      </a-form-item>
      <a-form-item>
        <a-checkbox v-model:checked="form.remember">
          {{ t('login.remember') }}
        </a-checkbox>


        <router-link
          to="/forget"
          class="ele-pull-right"
          style="line-height: 22px"
        >
          {{ t('login.forget') }}
        </router-link>

        <router-link
          to="/register"
          class="ele-pull-right"
          style="line-height: 22px;
                  width: 50px "
        >
          {{ t('login.registering') }}
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
          {{ loading ? t('login.loading') : t('login.login') }}
        </a-button>

      </a-form-item>
      <div class="ele-text-center" style="padding-bottom: 32px">
        <qq-outlined class="login-oauth-icon" style="background: #3492ed" />
        <wechat-outlined class="login-oauth-icon" style="background: #4daf29" />
        <weibo-outlined class="login-oauth-icon" style="background: #cf1900" />
      </div>
    </a-form>
    <div class="login-copyright">
      copyright ?? 2021 eleadmin.com all rights reserved.
    </div>
    <!-- ??????????????? -->
    <div style="position: absolute; right: 30px; top: 20px; z-index: 999">
      <a-dropdown placement="bottomLeft" :overlay-style="{ minWidth: '120px' }">
        <global-outlined style="font-size: 18px; color: #fff" />
        <template #overlay>
          <a-menu :selected-keys="language" @click="changeLanguage">
            <a-menu-item key="en">English</a-menu-item>
            <a-menu-item key="zh_CN">????????????</a-menu-item>
            <a-menu-item key="zh_TW">????????????</a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </div>
    <!-- ???????????????????????? -->
    <div style="position: absolute; left: 30px; top: 20px; z-index: 999">
      <a-radio-group v-model:value="direction" size="small">
        <a-radio-button :value="2">??????</a-radio-button>
        <a-radio-button :value="0">??????</a-radio-button>
        <a-radio-button :value="1">??????</a-radio-button>
      </a-radio-group>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, computed, unref } from 'vue';
  import { useI18n } from 'vue-i18n';
  import { useRouter } from 'vue-router';
  import { Form, message } from 'ant-design-vue';
  import {
    UserOutlined,
    LockOutlined,
    SafetyCertificateOutlined,
    QqOutlined,
    WechatOutlined,
    WeiboOutlined,
    GlobalOutlined
  } from '@ant-design/icons-vue';
  import { getToken } from '@/utils/token-util';
  import { login, getCaptcha } from '@/api/login';

  const useForm = Form.useForm;

  const { currentRoute, replace } = useRouter();
  const { locale, t } = useI18n();

  // ???????????????, 0??????, 1??????, 2??????
  const direction = ref(0);
  // ????????????
  const loading = ref(false);
  // ????????????
  const form = reactive({
    username: 'admin',
    password: 'admin',
    code: '',
    remember: true
  });
  // ?????????base64??????
  const captcha = ref('');
  // ???????????????, ??????????????????
  const text = ref('');

  // ??????????????????
  const language = computed(() => [locale.value]);
  // ??????????????????
  const rules = computed(() => {
    return {
      username: [
        {
          required: true,
          message: t('login.username'),
          type: 'string',
          trigger: 'blur'
        }
      ],
      password: [
        {
          required: true,
          message: t('login.password'),
          type: 'string',
          trigger: 'blur'
        }
      ],
      code: [
        {
          required: true,
          message: t('login.code'),
          type: 'string',
          trigger: 'blur'
        }
      ]
    };
  });

  const { clearValidate, validate, validateInfos } = useForm(form, rules);

  /* ??????????????? */
  const goHome = () => {
    const { query } = unref(currentRoute);
    replace(String(query?.from || '/'));
  };

  /* ?????? */
  const submit = () => {
    validate().then(() => {
      if (form.code.toLowerCase() !== text.value) {
        message.error('???????????????');
        return;
      }
      loading.value = true;
      login(form)
        .then((msg) => {
          message.success(msg);
          goHome();
        })
        .catch((e: Error) => {
          message.error(e.message);
          loading.value = false;
        });
    });
  };

  /* ????????????????????? */
  const changeCaptcha = () => {
    // ???????????????????????????????????????base64???????????????, ????????????????????????????????????????????????????????????
    getCaptcha()
      .then((data) => {
        captcha.value = data.base64;
        // ?????????????????????????????????????????????key???????????????????????????????????????, ?????????key?????????, ???????????????????????????????????????
        text.value = data.text;
        // ?????????????????????, ????????????????????????
        form.code = data.text;
        clearValidate();
      })
      .catch((e) => {
        message.error(e.message);
      });
  };

  /* ???????????? */
  const changeLanguage = ({ key }) => {
    locale.value = key;
    localStorage.setItem('i18n-lang', key);
    clearValidate();
  };

  if (getToken()) {
    goHome();
  } else {
    changeCaptcha();
  }
</script>

<style lang="less" scoped>
  /* ?????? */
  .login-wrapper {
    padding: 48px 16px 0 16px;
    position: relative;
    box-sizing: border-box;
    background-image: url('@/assets/bg-login.jpg');
    background-repeat: no-repeat;
    background-size: cover;
    min-height: 100vh;

    &:before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0.2);
    }
  }

  /* ?????? */
  .login-form {
    width: 360px;
    margin: 0 auto;
    max-width: 100%;
    padding: 0 28px;
    box-sizing: border-box;
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
    border-radius: 2px;
    position: relative;
    z-index: 2;

    h4 {
      padding: 22px 0;
      text-align: center;
    }
  }

  .login-form-right .login-form {
    margin: 0 15% 0 auto;
  }

  .login-form-left .login-form {
    margin: 0 auto 0 15%;
  }

  /* ????????? */
  .login-input-group {
    display: flex;
    align-items: center;

    :deep(.ant-input-affix-wrapper) {
      flex: 1;
    }

    .login-captcha {
      width: 102px;
      height: 40px;
      margin-left: 10px;
      padding: 0;

      & > img {
        width: 100%;
        height: 100%;
      }
    }
  }

  /* ????????????????????? */
  .login-oauth-icon {
    color: #fff;
    padding: 5px;
    margin: 0 12px;
    font-size: 18px;
    border-radius: 50%;
    cursor: pointer;
  }

  /* ???????????? */
  .login-copyright {
    color: #eee;
    text-align: center;
    padding: 48px 0 22px 0;
    position: relative;
    z-index: 1;
  }

  /* ????????? */
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
