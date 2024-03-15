<script setup lang="ts">
import { PropType } from 'vue'
import { Post } from '../types'

const avatarColor = (postName: string) => {
  const colors = ['primary', '#FFD43A', '#ADFF00', '#262824', 'danger']
  const index = postName.charCodeAt(0) % colors.length
  return colors[index]
}

defineProps({
  post: {
    type: Object as PropType<Post>,
    required: true,
  },
  size: {
    type: String,
    default: 'medium',
  },
})

const isUrl = (avatar: string) => {
  return avatar.startsWith('http') || avatar.startsWith('blob:')
}
</script>

<template>
  <VaAvatar
    :size="size"
    :src="isUrl(post.avatar) ? post.avatar : ''"
    :fallback-text="post.avatar || post.postName[0]"
    :color="avatarColor(post.postName)"
  />
</template>
