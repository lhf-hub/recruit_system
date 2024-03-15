<template>
  <Transition name="alert">
    <div
      v-show="value"
      class="va-alert"
      :style="{
        backgroundColor:
          type == 'success' ? '#28a745' : type == 'info' ? '#17a2b8' : type == 'warning' ? '#ffc107' : '#dc3545',
      }"
    >
      <VaIcon
        :name="
          type == 'success' ? 'check_circle' : type == 'info' ? 'info' : type == 'warning' ? 'warning' : 'dangerous'
        "
      ></VaIcon>
      <slot></slot>
    </div>
  </Transition>
</template>

<script lang="ts" setup>
import { defineProps } from 'vue'

defineProps<{
  type: 'success' | 'info' | 'warning' | 'error'
  value: boolean
}>()
</script>

<style scoped>
.va-alert {
  z-index: 999;
  color: white;
  position: fixed;
  font-size: medium;
  top: 20%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.alert-enter-active,
.alert-leave-active {
  transition: opacity 0.5s ease-in-out;
}
.alert-enter,
.alert-leave-to {
  opacity: 0;
}
</style>
