<!-- 搜索表单 -->
<template>
  <a-card :bordered="false" :body-style="{ paddingBottom: 0 }">
    <a-form
      :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
      :wrapper-col="{ md: { span: 18 }, sm: { span: 24 } }"
    >
      <a-row>
        <a-col :lg="8" :md="12" :sm="24" :xs="24">
          <a-form-item label="用户账号">
            <a-input
              v-model:value.trim="where.username"
              placeholder="请输入"
              allow-clear
            />
          </a-form-item>
        </a-col>
        <a-col :lg="8" :md="12" :sm="24" :xs="24">
          <a-form-item label="性别">
            <a-select
              v-model:value="where.sex"
              placeholder="请选择"
              allow-clear
            >
              <a-select-option value="1">男</a-select-option>
              <a-select-option value="2">女</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col v-if="searchExpand" :lg="8" :md="12" :sm="24" :xs="24">
          <a-form-item label="用户名">
            <a-input
              v-model:value.trim="where.nickname"
              placeholder="请输入"
              allow-clear
            />
          </a-form-item>
        </a-col>
        <a-col v-if="searchExpand" :lg="8" :md="12" :sm="24" :xs="24">
          <a-form-item label="手机号">
            <a-input
              v-model:value.trim="where.phone"
              placeholder="请输入"
              allow-clear
            />
          </a-form-item>
        </a-col>
        <a-col v-if="searchExpand" :lg="8" :md="12" :sm="24" :xs="24">
          <a-form-item label="状态">
            <a-select
              v-model:value="where.status"
              placeholder="请选择"
              allow-clear
            >
              <a-select-option :value="0">正常</a-select-option>
              <a-select-option :value="1">冻结</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :lg="8" :md="12" :sm="24" :xs="24">
          <a-form-item class="ele-text-right" :wrapper-col="{ span: 24 }">
            <a-space>
              <a-button type="primary" @click="search">查询</a-button>
              <a-button @click="reset">重置</a-button>
              <a @click="toggleExpand">
                <span v-if="searchExpand">
                  收起 <up-outlined class="ele-text-small" />
                </span>
                <span v-else>
                  展开 <down-outlined class="ele-text-small" />
                </span>
              </a>
            </a-space>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-card>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { DownOutlined, UpOutlined } from '@ant-design/icons-vue';
  import useSearch from '@/utils/use-search';
  import type { UserParam } from '@/api/system/user/model';

  const emit = defineEmits<{
    (e: 'search', where?: UserParam): void;
    (e: 'expand-change', expand: boolean): void;
  }>();

  // 表单数据
  const { where, resetFields } = useSearch<UserParam>({
    username: '',
    nickname: '',
    sex: undefined,
    phone: '',
    status: undefined
  });

  // 搜索表单是否展开
  const searchExpand = ref(false);

  /* 搜索 */
  const search = () => {
    emit('search', where);
  };

  /*  重置 */
  const reset = () => {
    resetFields();
    search();
  };

  /* 搜索展开/收起 */
  const toggleExpand = () => {
    searchExpand.value = !searchExpand.value;
    emit('expand-change', searchExpand.value);
  };
</script>
