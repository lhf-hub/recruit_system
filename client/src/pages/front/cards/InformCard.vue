<script setup lang="ts">
import VaTimelineItem from '../../../components/va-timeline-item.vue'
import { PropType } from 'vue'
interface InformData {
  newsId: number
  newsTitle: string
  time: string
  newsContent: string
  department: string
}

const emit = defineEmits(['showInformDetail'])

defineProps({
  data: {
    type: Array as PropType<InformData[]>,
    default: () => [],
  },
})

const showInformDetail = (item: InformData) => {
  console.log(item)
  emit('showInformDetail', item)
}
</script>

<template>
  <VaCard style="background-color: rgba(255, 255, 255, 0.7); overflow: auto; height: 45vh">
    <VaCardTitle class="flex justify-between">
      <h1 class="card-title text-secondary font-bold uppercase" style="font-size: x-large; color: #000000">通知公告</h1>
    </VaCardTitle>
    <VaCardContent>
      <table class="mt-4">
        <tbody>
          <template v-for="item in data" :key="item.newsId">
            <VaTimelineItem :date="`${item.time}`">
              <a class="my-link" @click="showInformDetail(item)">{{ item.newsTitle }}</a>
            </VaTimelineItem>
          </template>
        </tbody>
      </table>
    </VaCardContent>
  </VaCard>
</template>

<style scoped lang="scss">
.my-link {
  color: #000;
  text-decoration: none;
  &:hover {
    color: cornflowerblue;
    text-decoration: underline;
  }
}
</style>
