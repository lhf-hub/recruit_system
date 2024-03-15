<script setup lang="ts">
import { PropType } from 'vue'
import { ResumeInfo } from '../types'
import ProjectStatusBadge from '../components/StatusBadge.vue'

defineProps({
  resumes: {
    type: Array as PropType<ResumeInfo[]>,
    required: true,
  },
  loading: {
    type: Boolean,
    required: true,
  },
})

defineEmits<{
  (event: 'delete', resume: ResumeInfo): void
  (event: 'view', resume: ResumeInfo): void
  (event: 'interview', resume: ResumeInfo): void // 面试
}>()
</script>

<template>
  <VaInnerLoading
    v-if="resumes.length > 0 || loading"
    :loading="loading"
    class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 min-h-[4rem]"
  >
    <VaCard
      v-for="resume in resumes"
      :key="resume.userId"
      style="box-shadow: #222222 0 5px 6px -2px"
      :style="{ '--va-card-outlined-border': '1px solid var(--va-background-element)' }"
      outlined
    >
      <VaCardContent class="flex flex-col h-full">
        <div style="display: flex; justify-content: space-between">
          <div class="text-[var(--va-secondary)]" style="font-size: large; display: flex; align-items: center">
            <h4 class="va-h6 text-center self-stretch overflow-hidden line-clamp-2 text-ellipsis">
              申请岗位: {{ resume.postName }}
            </h4>
          </div>
          <VaButton
            preset="secondary"
            size="large"
            icon="info"
            title="详细信息"
            color="primary"
            @click="$emit('view', resume)"
          />
        </div>
        <div class="flex flex-col items-center gap-4 grow">
          <h4 class="va-h4 text-center self-stretch overflow-hidden line-clamp-2 text-ellipsis">
            {{ resume.name }}
          </h4>
          <p>
            <span class="text-[var(--va-secondary)]" style="font-size: large">岗位匹配度: </span>
            <span style="font-weight: bold; font-size: large; color: orange">{{ resume.similarity }}%</span>
          </p>
          <ProjectStatusBadge :status="resume.status" />
        </div>
        <VaDivider class="my-6" />
        <div class="flex justify-between">
          <VaButton
            v-if="resume.status != '录用' && resume.status != '淘汰'"
            preset="secondary"
            color="danger"
            @click="$emit('delete', resume)"
          >
            淘汰
          </VaButton>
          <VaButton v-if="resume.status == '笔试中'" color="success" @click="$emit('interview', resume)">
            完成笔试/约面试
          </VaButton>
        </div>
      </VaCardContent>
    </VaCard>
  </VaInnerLoading>
  <div v-else class="p-4 flex justify-center items-center text-[var(--va-secondary)]">无面试</div>
</template>

<style lang="scss" scoped></style>
