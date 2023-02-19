<!-- 搜索表单 -->
<template>
  <a-form
    :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
    :wrapper-col="{ md: { span: 18 }, sm: { span: 24 } }"
  >
    <a-row>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="品牌推荐">
          <a-select
            ref="select"
            v-model:value.trim="where.commandName"
            placeholder="请选择品牌"
            style="width: 150px"
          >
            <a-select-option value="卫仕">卫仕</a-select-option>
            <a-select-option value="江小傲">江小傲</a-select-option>
            <a-select-option value="素力高">素力高</a-select-option>
            <a-select-option value="麦复迪">麦复迪</a-select-option>
            <a-select-option value="力狼">力狼</a-select-option>
            <a-select-option value="阿飞和巴弟">阿飞和巴弟</a-select-option>
            <a-select-option value="瓜洲牧">瓜洲牧</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="推荐状态">
          <a-select
            ref="select"
            v-model:value.trim="where.state"
            placeholder="请选择推荐状态"
            style="width: 150px"
          >
            <a-select-option value="推荐中">推荐中</a-select-option>
            <a-select-option value="未推荐">未推荐</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item class="ele-text-right" :wrapper-col="{ span: 24 }">
          <a-space>
            <a-button type="primary" @click="search">查询</a-button>
            <a-button @click="reset">重置</a-button>
          </a-space>
        </a-form-item>
      </a-col>
    </a-row>
  </a-form>
</template>

<script lang="ts" setup>
  import useSearch from '@/utils/use-search';
  import type { CommandParam } from '@/api/order/command/model';

  const emit = defineEmits<{
    (e: 'search', where?: CommandParam): void;
  }>();

  // 表单数据
  const { where, resetFields } = useSearch<CommandParam>({
    commandName: '',
    state: ''
  });

  /* 搜索 */
  const search = () => {
    emit('search', where);
  };

  /*  重置 */
  const reset = () => {
    resetFields();
    search();
  };
</script>
