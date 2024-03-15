<template>
  <VaCard>
    <VaCardTitle>{{ title }}</VaCardTitle>
    <VaCardContent>
      <slot></slot>
    </VaCardContent>
    <VaCardActions>
      <VaButton @click="okButtonCallback" color="green">{{ okButtonText }}</VaButton>
      <VaButton @click="cancelButtonCallback" color="red">{{ cancelButtonText }}</VaButton>
    </VaCardActions>
  </VaCard>
</template>

<script setup lang="ts">
import { PropType } from 'vue'

defineProps({
  title: { type: String, required: true },
  okButtonText: { type: String, default: '确定' },
  cancelButtonText: { type: String, default: '取消' },
  okButtonCallback: { type: Function as PropType<(...args: any[]) => void>, default: () => {} },
  cancelButtonCallback: { type: Function as PropType<(...args: any[]) => void>, default: () => {} },
})
</script>

<style lang="scss" scoped></style>
