<script setup lang="ts">
import { onMounted, ref } from 'vue'
import ResumeCards from './widgets/ResumeCards.vue'
import { ResumeInfo } from './types'
import DisplayResumeForm from './widgets/DisplayResumeForm.vue'
import bus from 'vue3-eventbus'
import { application } from '../../netConfig'

const selectResume = ref<ResumeInfo | null>(null)
const doShowResumeFormModal = ref(false)

const isLoading = ref(false)
const resumes = ref<ResumeInfo[]>([])

const getResumes = async () => {
  isLoading.value = true
  try {
    resumes.value = []
    const res = await application.query({ name: '' })
    if (res.data.code !== 200) {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
      return
    }
    res.data.data.forEach((item: ResumeInfo) => {
      if (item.status == '笔试中') {
        resumes.value.push(item)
      }
    })
  } catch (e) {
    bus.emit('alert', { type: 'error', content: '网络错误' })
  }
  isLoading.value = false
}


onMounted(() => {
  getResumes()
})

const showResumeDetails = (resume: ResumeInfo) => {
  selectResume.value = resume
  doShowResumeFormModal.value = true
}

// 淘汰
const deleteResume = async (resume: ResumeInfo) => {
  try {
    const response = await application.updateOut({ applicationId: resume.applicationId })
    if (response.data.code !== 200) {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
      return
    } else {
      bus.emit('alert', { type: 'success', content: '淘汰成功' })
    }
  } catch (e) {
    bus.emit('alert', { type: 'error', content: '网络错误' })
  }
  await getResumes()
}

const editedItem = ref<{
  applicationId: number
  writtenTime: Date
  writtenDetailTime: Date
  interviewTime: Date
  interviewDetailTime: Date
  writtenScore: number
}>({
  applicationId: 0,
  writtenTime: new Date(Date.now()),
  interviewTime: new Date(Date.now()),
  writtenDetailTime: new Date(Date.now()),
  interviewDetailTime: new Date(Date.now()),
  writtenScore: 0,
})

const doShowInterviewModal = ref(false)
const openInterviewModal = (resume: ResumeInfo) => {
  editedItem.value = {
    applicationId: resume.applicationId,
    writtenTime: new Date(resume.writtenTime ?? Date.now()),
    writtenDetailTime: new Date(Date.now()),
    interviewTime: new Date(resume.interviewTime ?? Date.now()),
    interviewDetailTime: new Date(Date.now()),
    writtenScore: resume.writtenScore,
  }
  doShowInterviewModal.value = true
}
// 面试
const interviewResume = async () => {
  if (editedItem.value.writtenScore <= 0 || editedItem.value.writtenScore > 100) {
    bus.emit('alert', { type: 'warning', content: '请填写正确的笔试成绩' })
    return
  }
  if (!(editedItem.value?.interviewTime && editedItem.value?.interviewDetailTime)) {
    bus.emit('alert', { type: 'warning', content: '请完整填写面试时间' })
    return
  }
  if (new Date(editedItem.value?.interviewTime.toLocaleString()) < new Date(editedItem.value?.writtenTime.toLocaleString())) {
    bus.emit('alert', { type: 'warning', content: '面试时间不能早于笔试时间' + ' '
        +editedItem.value?.writtenTime.toLocaleString().split(' ')[0].replace(/\//g, '-') +
        ' ' +
        editedItem.value?.writtenDetailTime.toLocaleString().split(' ')[1].replace(/\//g, '-') })
    return
  }
  if (
    new Date(
      `${editedItem.value?.interviewTime.toLocaleString().split(' ')[0]} ${editedItem.value?.interviewDetailTime.toLocaleString().split(' ')[1]}`,
    ) < new Date(Date.now())
  ) {
    bus.emit('alert', { type: 'warning', content: '面试时间不能早于当前时间' })
    return
  }
  try {
    const response = await application.updateIn({
      applicationId: editedItem.value.applicationId,
      interviewTime: `${editedItem.value.interviewTime.toLocaleString().split(' ')[0].replace(/\//g, "-")} ${editedItem.value?.interviewDetailTime
        .toLocaleString()
        .split(' ')[1]
        .replace(/\//g, "-")}`,
      writtenScore: editedItem.value.writtenScore,
    })
    if (response.data.code !== 200) {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
      return
    } else {
      bus.emit('alert', { type: 'success', content: '约面试成功' })
      doShowInterviewModal.value = false
    }
  } catch (e) {
    bus.emit('alert', { type: 'error', content: '网络错误' })
  }
  await getResumes()
}

// 笔试
</script>

<template>
  <VaCard>
    <VaCardTitle text-color="primary" class="page-title">笔试管理</VaCardTitle>
    <VaCardContent>
      <ResumeCards
        :resumes="resumes"
        :loading="isLoading"
        @view="showResumeDetails"
        @delete="deleteResume"
        @interview="openInterviewModal"
      />
    </VaCardContent>

    <VaModal
      v-slot="{ cancel }"
      v-model="doShowResumeFormModal"
      size="medium"
      close-button
      stateful
      hide-default-actions
    >
      <DisplayResumeForm :resume="selectResume" @close="cancel" />
    </VaModal>

    <VaModal
      v-model="doShowInterviewModal"
      hide-default-actions
      size="auto"
      close-button
      stateful
      @ok="interviewResume"
      @cancel="doShowInterviewModal = false"
    >
      <span style="font-weight: bold; font-size: x-large">请填写信息</span>
      <div style="display: flex; justify-content: center; flex-direction: column; gap: 20px; padding: 20px">
        <div>
          <span style="color: #006eff; font-weight: bold">笔试成绩</span>
          <VaInput v-model="editedItem.writtenScore" type="number" />
        </div>
        <div>
          <span style="color: #006eff; font-weight: bold">面试时间</span><br />
          <VaDateInput v-model="editedItem.interviewTime" />
          <VaTimeInput v-model="editedItem.interviewDetailTime" />
        </div>
      </div>
      <template #footer>
        <div style="display: flex; align-items: end; gap: 8px">
          <VaButton preset="secondary" text-color="#999" @click="doShowInterviewModal = false">取消</VaButton>
          <VaButton @click="interviewResume">确定</VaButton>
        </div>
      </template>
    </VaModal>
  </VaCard>
</template>

<style scoped>
.page-title {
  font-size: 30px;
  text-shadow: 0 0 2px grey;
}
</style>
