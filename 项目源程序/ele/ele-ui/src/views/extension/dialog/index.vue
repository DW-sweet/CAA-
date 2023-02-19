<template>
  <div class="ele-body ele-body-card">
    <demo-modal />
    <multiple-modal />
  </div>
</template>

<script lang="ts" setup>
  import DemoModal from './components/demo-modal.vue';
  import MultipleModal from './components/multiple-modal.vue';
</script>

<script lang="ts">
  export default {
    name: 'ExtensionDialog'
  };
</script>
