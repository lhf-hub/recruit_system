<script setup lang="ts">
import { PropType } from 'vue'
import { ResumeInfo } from '../types'

defineProps({
  status: {
    type: String as PropType<ResumeInfo['status']>,
    required: true,
  },
})

const badgeColorMap: Record<ResumeInfo['status'], string> = {
  初审中: 'warning',
  笔试中: 'primary',
  面试中: 'primary',
  录用: 'success',
  淘汰: 'danger',
}
</script>

<template>
  <VaBadge square :color="badgeColorMap[$props.status]" :text="$props.status.toUpperCase()" />
</template>
