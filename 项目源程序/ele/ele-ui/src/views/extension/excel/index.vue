<template>
  <div class="ele-body ele-body-card">
    <excel-export />
    <excel-import />
  </div>
</template>

<script lang="ts" setup>
  import ExcelExport from './components/excel-export.vue';
  import ExcelImport from './components/excel-import.vue';
</script>

<script lang="ts">
  export default {
    name: 'ExtensionExcel'
  };
</script>
