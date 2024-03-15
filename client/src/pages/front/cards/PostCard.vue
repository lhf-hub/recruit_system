<template>
  <div>
    <VaCard
      color="rgba(255, 255, 255, 0.8)"
      class="card"
      style="display: flex; flex-direction: column; justify-content: space-between"
    >
      <VaCardTitle style="display: flex; justify-content: space-between">
        <div style="display: flex; justify-content: space-between; align-items: center">
          <span style="color: #222222; font-size: 20px">{{ data.postName }}</span>
          <span
            v-if="data.postState === '招聘中'"
            style="background: #d8f9e5; color: #1dc165; padding: 1.5px 6px 0 6px; margin-left: 5px; height: 14px"
          >
            招聘中
          </span>
          <span
            v-else
            style="background: #f8f8f8; color: #7a7a7a; padding: 1.5px 6px 0 6px; margin-left: 5px; height: 14px"
          >
            已结束
          </span>
        </div>
        <VaDivider></VaDivider>
        <span style="color: #fe574a; font-size: 20px">{{
          data.minSalary / 1000 + '-' + data.maxSalary / 1000 + 'k'
        }}</span>
      </VaCardTitle>
      <VaCardContent>
        <span class="tag">{{ data.educationRequirement }}</span>
        <span class="tag">{{ data.ageRequirement + '岁以上' }}</span>
        <span class="tag">{{ '招聘' + data.numberRequirement + '人' }}</span>
      </VaCardContent>
      <VaCardTitle style="display: flex; justify-content: space-between">
        <span style="color: #666666; font-size: 15px">{{ data.department }}</span>
        <VaDivider></VaDivider>
        <span style="color: #666666; font-size: 15px">{{ data.category }}</span>
      </VaCardTitle>
    </VaCard>
  </div>
</template>

<script lang="ts" setup>
import { PropType } from 'vue'

interface PostData {
  postId: number
  postName: string
  department: string
  educationRequirement: string
  minSalary: number
  maxSalary: number
  endTime: string
  postState: string
  category: string
  ageRequirement: number
  numberRequirement: number
}
defineProps({
  data: {
    type: Object as PropType<PostData>,
    default: null,
  },
})
</script>

<style scoped>
.card {
  box-shadow: 3px 3px rgba(0, 0, 0, 0.3);
  margin: 20px;
  padding: 16px 20px;
  width: 360px;
  height: 170px;
  transition: all linear 0.1s;
}

.card:hover {
  cursor: pointer;
  width: 380px;
  height: 180px;
  transform: scale(1.1);
  transition: all linear 0.1s;
}

.card:active {
  width: 375px;
  height: 175px;
  transform: scale(1);
  transition: all linear 0.1s;
}

.tag {
  padding: 2px 10px;
  background-color: rgba(100, 100, 100, 0.2);
  border-radius: 2px;
  margin-left: 5px;
  margin-right: 5px;
}
</style>
