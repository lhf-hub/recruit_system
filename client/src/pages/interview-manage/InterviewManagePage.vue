<script setup lang="ts">
import { onMounted, ref } from 'vue'
import ResumeCards from './widgets/ResumeCards.vue'
import { ResumeInfo } from './types'
import DisplayResumeForm from './widgets/DisplayResumeForm.vue'
import axios from 'axios'
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
      if (item.status == '面试中') {
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

// 录用
const hireResume = async (resume: ResumeInfo) => {
  try {
    const response = await application.updateIn({ applicationId: resume.applicationId })
    if (response.data.code !== 200) {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
      return
    } else {
      bus.emit('alert', { type: 'success', content: '录用成功' })
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
    <VaCardTitle text-color="primary" class="page-title">面试管理</VaCardTitle>
    <VaCardContent>
      <ResumeCards
        :resumes="resumes"
        :loading="isLoading"
        @view="showResumeDetails"
        @delete="deleteResume"
        @hire="hireResume"
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
  </VaCard>
</template>

<style scoped>
.page-title {
  font-size: 30px;
  text-shadow: 0 0 2px grey;
}
</style>
