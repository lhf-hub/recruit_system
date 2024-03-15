<template>
  <div :style="{ height: height + 'px', display: 'flex', float: 'left' }">
    <VaImage src="/logo.png" style="width: 300px" fit="contain" />
  </div>
</template>

<script lang="ts" setup>
defineProps({
  height: { type: Number, default: 80 },
})
</script>
