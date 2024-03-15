<script setup lang="ts">
import { Statistic } from '../types'
import { onMounted } from 'vue'

const props = defineProps<{
  statistic: Statistic
}>()

const emit = defineEmits<{
  (event: 'getStatistic'): void
}>()

onMounted(() => {
  emit('getStatistic')
})
</script>

<template>
  <VaCard>
    <VaCardTitle
      text-color="primary"
      style="
        display: flex;
        justify-content: space-between;
        font-size: 20px;
        text-shadow: 0 0 2px grey;
        text-align: center;
        padding: 10px;
      "
    >
      <div style="display: flex; flex-direction: column; align-items: center">
        <span style="font-weight: bold; color: black; font-size: 28px">{{ statistic.resumeNum }}</span>
        <span style="color: gray; font-size: 20px">简历总数</span>
      </div>
      <div style="display: flex; flex-direction: column; align-items: center">
        <span style="font-weight: bold; color: black; font-size: 28px">{{ statistic.postNum }}</span>
        <span style="color: gray; font-size: 20px">岗位总数</span>
      </div>
      <div style="display: flex; flex-direction: column; align-items: center">
        <span style="font-weight: bold; color: black; font-size: 28px">{{ statistic.preRecruitsNum }}</span>
        <span style="color: gray; font-size: 20px">预招人数</span>
      </div>
      <div style="display: flex; flex-direction: column; align-items: center">
        <span style="font-weight: bold; color: black; font-size: 28px">{{ statistic.outNum }}</span>
        <span style="color: gray; font-size: 20px">淘汰人数</span>
      </div>
      <div style="display: flex; flex-direction: column; align-items: center">
        <span style="font-weight: bold; color: black; font-size: 28px">{{ statistic.trialNum }}</span>
        <span style="color: gray; font-size: 20px">资格筛选</span>
      </div>
      <div style="display: flex; flex-direction: column; align-items: center">
        <span style="font-weight: bold; color: black; font-size: 28px">{{ statistic.writtenNum }}</span>
        <span style="color: gray; font-size: 20px">笔试环节</span>
      </div>
      <div style="display: flex; flex-direction: column; align-items: center">
        <span style="font-weight: bold; color: black; font-size: 28px">{{ statistic.interviewNum }}</span>
        <span style="color: gray; font-size: 20px">面试环节</span>
      </div>
      <div style="display: flex; flex-direction: column; align-items: center">
        <span style="font-weight: bold; color: black; font-size: 28px">{{ statistic.hiredNum }}</span>
        <span style="color: gray; font-size: 20px">录用人数</span>
      </div>
    </VaCardTitle>
  </VaCard>
</template>

<style scoped lang="scss"></style>
