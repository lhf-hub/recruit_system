<script setup lang="ts">
import { ResumeInfo } from '../types'

defineProps<{
  resume: ResumeInfo | null
}>()

defineEmits<{
  (event: 'close'): void
}>()
</script>

<template>
  <div class="card">
    <div class="resume-card">
      <div class="header">
        <span class="bold">姓名：{{ resume?.name }}</span>
        <span>职位申请ID：{{ resume?.applicationId }}</span>
      </div>
      <div class="info-section">
        <div>身份证号：{{ resume?.idNumber }}</div>
        <div>出生日期：{{ resume?.birthdate }}</div>
        <div>年龄：{{ resume?.age }}</div>
        <div>政治面貌：{{ resume?.politicsStatus }}</div>
        <div>电话号码：{{ resume?.phoneNumber }}</div>
        <div>居住地：{{ resume?.place }}</div>
      </div>
      <div class="education">
        <div class="bold">教育背景：</div>
        <div>学历：{{ resume?.education }}</div>
        <div>专业：{{ resume?.major }}</div>
        <div>学院：{{ resume?.academy }}</div>
      </div>
      <div class="application-info">
        <div class="bold">申请信息：</div>
        <div>申请时间：{{ resume?.applicationTime }}</div>
        <div>状态：{{ resume?.status }}</div>
        <div>笔试时间：{{ resume?.writtenTime }}</div>
        <div>笔试成绩：{{ resume?.writtenScore }}</div>
        <div>面试时间：{{ resume?.interviewTime }}</div>
        <div>岗位匹配度评分：{{ resume?.similarity }}%</div>
      </div>
      <div class="other-info">
        <div class="bold">其他信息：</div>
        <div>{{ resume?.otherInfo }}</div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.card {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; /* 更现代的字体 */
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #e0f7fa; /* 轻柔的背景色 */
}
.resume-card {
  width: 600px;
  background-color: #ffffff; /* 纯白背景色 */
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 更明显的阴影效果 */
  display: flex;
  flex-direction: column;
  padding: 20px;
  border-radius: 10px; /* 圆角边框 */
}
.header,
.education,
.application-info,
.other-info {
  padding: 10px;
  margin: 5px 0;
}
.header {
  display: flex;
  justify-content: space-between;
  border-bottom: 2px solid #009688; /* 活泼的下划线颜色 */
}
.info-section {
  margin: 10px 0;
}
.info-section > div {
  margin-bottom: 5px;
}
.bold {
  font-weight: bold;
  color: #00796b; /* 字体颜色 */
}
</style>
