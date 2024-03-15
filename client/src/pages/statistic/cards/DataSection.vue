<template>
  <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-4">
    <DataSectionItem
      v-for="metric in dashboardMetrics"
      :key="metric.id"
      :title="metric.title"
      :value="metric.value"
      :change-text="metric.changeText"
      :up="metric.changeDirection === 'up'"
      :icon-background="metric.iconBackground"
      :icon-color="metric.iconColor"
      @click="showModalDetail(metric.id)"
    >
      <template #icon>
        <VaIcon :name="metric.icon" size="large" />
      </template>
    </DataSectionItem>
  </div>
  <!--  <CategoryDetail :statistic_two="categoryDetail" />-->
</template>

<script lang="ts" setup>
import { computed, onMounted, ref } from 'vue'
import { useColors } from 'vuestic-ui'
import DataSectionItem from './DataSectionItem.vue'
import { Statistic_two } from '../types'
// import CategoryDetail from './CategoryDetail.vue'

interface DashboardMetric {
  id: string
  title: string
  value: string
  icon: string
  changeText: string
  changeDirection: 'up' | 'down'
  iconBackground: string
  iconColor: string
}
//
// const categoryDetail = ref<Statistic_two>({
//   resumeNum: 0,
//   postNum: 0,
//   preRcruitsNum: 0,
//   trialNum: 0,
//   writtenNum: 0,
//   interviewNum: 0,
//   hiredNum: 0,
//   outNum: 0,
//   specialistNum: 0,
//   undergraduateNum: 0,
//   masterNum: 0,
//   doctorNum: 0,
// })

const showModalDetail = (id: string) => {
  console.log('showModalDetail', id)
}
const props = defineProps<{
  statistic_two: Array<Statistic_two>
}>()

const emit = defineEmits<{
  (event: 'getStatistic'): void
}>()

onMounted(() => {
  emit('getStatistic')
})

const { getColor } = useColors()

const dashboardMetrics = computed<DashboardMetric[]>(() => [
  {
    id: 'keyan',
    title: '科研类 简历数量',
    value: props.statistic_two[0].resumeNum,
    icon: 'folder_shared',
    changeText: props.statistic_two[0].hiredNum,
    changeDirection: 'down',
    iconBackground: getColor('success'),
    iconColor: getColor('on-success'),
  },
  {
    id: 'tushu',
    title: '图书馆管理类 简历数量',
    value: props.statistic_two[1].resumeNum,
    icon: 'folder_shared',
    changeText: props.statistic_two[1].hiredNum,
    changeDirection: 'down',
    iconBackground: getColor('info'),
    iconColor: getColor('on-info'),
  },
  {
    id: 'jiaoxue',
    title: '教学类 简历数量',
    value: props.statistic_two[2].resumeNum,
    icon: 'folder_shared',
    changeText: props.statistic_two[2].hiredNum,
    changeDirection: 'down',
    iconBackground: getColor('danger'),
    iconColor: getColor('on-danger'),
  },
  {
    id: 'shiyan',
    title: '实验室管理类 简历数量',
    value: props.statistic_two[3].resumeNum,
    icon: 'folder_shared',
    changeText: props.statistic_two[3].hiredNum,
    changeDirection: 'down',
    iconBackground: getColor('warning'),
    iconColor: getColor('on-warning'),
  },
])
</script>
