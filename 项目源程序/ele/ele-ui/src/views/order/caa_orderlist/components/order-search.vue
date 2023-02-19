<!-- 搜索表单 -->
<template>
  <a-form
    :label-col="{ md: { span: 6 }, sm: { span: 24 } }"
    :wrapper-col="{ md: { span: 18 }, sm: { span: 24 } }"
  >
    <a-row>
      <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="订单编号">
          <a-input
            v-model:value.trim="where.orderNo"
            placeholder="请输入订单编号"
            allow-clear
          />
        </a-form-item>
<!--      </a-col>-->

<!--      <a-col :lg="6" :md="12" :sm="24" :xs="24">-->
        <a-form-item label="商品姓名">
          <a-input
            v-model:value.trim="where.orderName"
            placeholder="请输入商品姓名"
            allow-clear
          />
        </a-form-item>
      </a-col>

     <a-col :lg="6" :md="12" :sm="24" :xs="24">
        <a-form-item label="用户名">
          <a-input
            v-model:value.trim="where.orderAccount"
            placeholder="请输入用户名"
            allow-clear
          />
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
  import type { OrderParam } from '@/api/order/caa_orderlist/model';

  const emit = defineEmits<{
    (e: 'search', where?: OrderParam): void;
  }>();

  // 表单数据
  const { where, resetFields } = useSearch<OrderParam>({
    orderNo: '', orderName: '', orderAccount: '',
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
