<!--<script setup lang="ts">-->
<!--import { Statistic_two } from '../types'-->

<!--const props = defineProps<{-->
<!--  statistic_two: {-->
<!--    type: Array<Statistic_two>-->
<!--    required: true-->
<!--  }-->
<!--}>()-->

<!--const emit = defineEmits<{-->
<!--  (event: 'getStatistic'): void-->
<!--}>()-->
<!--</script>-->

<!--<template>-->
<!--  <div class="card">-->
<!--    <div class="resume-card">-->
<!--      <div class="header">-->
<!--        <span class="bold">科研类 简历数量：{{ statistic_two[0].resumeNum }}</span>-->
<!--        <span>录用人数：{{ statistic_two[0].hiredNum }}</span>-->
<!--      </div>-->
<!--      <div class="info-section">-->
<!--        <div>预招人数：{{ statistic_two[0].preRcruitsNum }}</div>-->
<!--        <div>在初审中的简历数：{{ statistic_two[0].trailNum }}</div>-->
<!--        <div>在笔试中的简历数：{{ statistic_two[0].writtenNum }}</div>-->
<!--        <div>在面试中的简历数：{{ statistic_two[0].interviewNum }}</div>-->
<!--        <div>已淘汰的简历数：{{ statistic_two[0].outNum }}</div>-->
<!--        <div>专科简历数：{{ statistic_two[0].specialistNum }}</div>-->
<!--        <div>本科简历数：{{ statistic_two[0].undergraduateNum }}</div>-->
<!--        <div>硕士简历数：{{ statistic_two[0].masterNum }}</div>-->
<!--        <div>博士简历数：{{ statistic_two[0].doctorNum }}</div>-->
<!--      </div>-->
<!--      <div class="education">-->
<!--        <div class="bold">图书馆管理类 简历数量：</div>-->
<!--        <div>录用人数：{{ statistic_two[1].hiredNum }}</div>-->
<!--        <div>预招人数：{{ statistic_two[1].preRcruitsNum }}</div>-->
<!--        <div>在初审中的简历数：{{ statistic_two[1].trailNum }}</div>-->
<!--        <div>在笔试中的简历数：{{ statistic_two[1].writtenNum }}</div>-->
<!--        <div>在面试中的简历数：{{ statistic_two[1].interviewNum }}</div>-->
<!--        <div>已淘汰的简历数：{{ statistic_two[1].outNum }}</div>-->
<!--        <div>专科简历数：{{ statistic_two[1].specialistNum }}</div>-->
<!--        <div>-->
<!--          本科简历数：{{ statistic_two[1].undergraduateNum }}-->
<!--        </div>-->
<!--        <div>硕士简历数：{{ statistic_two[1].masterNum }}</div>-->
<!--        <div>博士简历数：{{ statistic_two[1].doctorNum }}</div>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<style scoped lang="scss">-->
<!--.card {-->
<!--  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; /* 更现代的字体 */-->
<!--  display: flex;-->
<!--  justify-content: center;-->
<!--  align-items: center;-->
<!--  height: 100vh;-->
<!--  background-color: #e0f7fa; /* 轻柔的背景色 */-->
<!--}-->
<!--.resume-card {-->
<!--  width: 600px;-->
<!--  background-color: #ffffff; /* 纯白背景色 */-->
<!--  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 更明显的阴影效果 */-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  padding: 20px;-->
<!--  border-radius: 10px; /* 圆角边框 */-->
<!--}-->
<!--.header,-->
<!--.education,-->
<!--.application-info,-->
<!--.other-info {-->
<!--  padding: 10px;-->
<!--  margin: 5px 0;-->
<!--}-->
<!--.header {-->
<!--  display: flex;-->
<!--  justify-content: space-between;-->
<!--  border-bottom: 2px solid #009688; /* 活泼的下划线颜色 */-->
<!--}-->
<!--.info-section {-->
<!--  margin: 10px 0;-->
<!--}-->
<!--.info-section > div {-->
<!--  margin-bottom: 5px;-->
<!--}-->
<!--.bold {-->
<!--  font-weight: bold;-->
<!--  color: #00796b; /* 字体颜色 */-->
<!--}-->
<!--</style>-->
