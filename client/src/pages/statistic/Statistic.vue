<template>
  <VaCard>
    <VaCardTitle
      text-color="primary"
      style="font-size: 30px; text-shadow: 0 0 2px grey; text-align: center; padding: 10px"
    >
      应聘统计
    </VaCardTitle>

    <NumberStatistic :statistic="statisticSource" @getStatistic="getStatistic" />
    <br />
    <DataSection :statistic_two="statisticSource_two" style="margin-bottom: 20px" @getStatistic="getStatistic_two" />
    <br />
    <Charts :statistic="statisticSource"  />
  </VaCard>
</template>
<script setup lang="ts">
import { Statistic, Statistic_two } from './types'
import DataSection from './cards/DataSection.vue'
import NumberStatistic from './cards/NumberStatistic.vue'
import { ref } from 'vue'
import bus from 'vue3-eventbus'
import Charts from './cards/Charts.vue'
import { application } from '../../netConfig'

const forceud = ref(true)

const statisticSource = ref<Statistic>({
  resumeNum: 0,
  postNum: 0,
  preRecruitsNum: 0,
  trialNum: 0,
  writtenNum: 0,
  interviewNum: 0,
  hiredNum: 0,
  outNum: 0,
  specialistNum: 0,
  undergraduateNum: 0,
  masterNum: 0,
  doctorNum: 0,
  nameNum: [
    {
      postND: '',
      resumeNum: 0,
    },
  ],
  departNum: [
    {
      postND: '',
      resumeNum: 0,
    },
  ],
})

const statisticSource_two = ref<Statistic_two[]>([
  {
    resumeNum: 0,
    postNum: 0,
    preRecruitsNum: 0,
    trialNum: 0,
    writtenNum: 0,
    interviewNum: 0,
    hiredNum: 0,
    outNum: 0,
    specialistNum: 0,
    undergraduateNum: 0,
    masterNum: 0,
    doctorNum: 0,
  },
  {
    resumeNum: 0,
    postNum: 0,
    preRecruitsNum: 0,
    trialNum: 0,
    writtenNum: 0,
    interviewNum: 0,
    hiredNum: 0,
    outNum: 0,
    specialistNum: 0,
    undergraduateNum: 0,
    masterNum: 0,
    doctorNum: 0,
  },
  {
    resumeNum: 0,
    postNum: 0,
    preRecruitsNum: 0,
    trialNum: 0,
    writtenNum: 0,
    interviewNum: 0,
    hiredNum: 0,
    outNum: 0,
    specialistNum: 0,
    undergraduateNum: 0,
    masterNum: 0,
    doctorNum: 0,
  },
  {
    resumeNum: 0,
    postNum: 0,
    preRecruitsNum: 0,
    trialNum: 0,
    writtenNum: 0,
    interviewNum: 0,
    hiredNum: 0,
    outNum: 0,
    specialistNum: 0,
    undergraduateNum: 0,
    masterNum: 0,
    doctorNum: 0,
  },
])

const getStatistic = async () => {
 
  const res = await application.statistics({ category:'' })
  if (res.data.code === 200) {
    statisticSource.value = res.data.data[0]
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}



const getStatistic_two = async () => {
  try {
    const res1 = await application.statistics({ category: '科研类' })
    if (res1.data.code === 200) {
      statisticSource_two.value[0] = res1.data.data[0]
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
    const res2 = await application.statistics({ category: '图书馆管理类' })
    if (res2.data.code === 200) {
      statisticSource_two.value[1] = res2.data.data[0]
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
    const res3 = await application.statistics({ category: '教学类' })
    if (res3.data.code === 200) {
      statisticSource_two.value[2] = res3.data.data[0]
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
    const res4 = await application.statistics({ category: '实验室管理类' })
    if (res4.data.code === 200) {
      statisticSource_two.value[3] = res4.data.data[0]
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
  } catch (e) {
    bus.emit('alert', { type: 'error', content: '网络错误' })
  }
}
</script>
