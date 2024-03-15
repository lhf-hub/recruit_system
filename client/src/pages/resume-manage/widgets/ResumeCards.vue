<script setup lang="ts">
import { PropType , onMounted} from 'vue'
import { ResumeInfo } from '../types'
import ProjectStatusBadge from '../components/StatusBadge.vue'

const props= defineProps({
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
  (event: 'written', resume: ResumeInfo): void // 笔试
  (event: 'interview', resume: ResumeInfo): void // 面试
  (event: 'hire', resume: ResumeInfo): void // 录用
}>()
onMounted(() => {
  console.log('resumes', props.resumes)
})
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
            匹配度:<span style="color: orange; font-weight: bold; font-size: x-large">{{ resume.similarity }}%</span>
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
            <span class="text-[var(--va-secondary)]">应聘岗位: </span>
            <span>{{ resume.postName }}</span>
          </p>
          <p>
            <span class="text-[var(--va-secondary)]">学历: </span>
            <span>{{ resume.education }}</span>
          </p>
          <p>
            <span class="text-[var(--va-secondary)]">专业 </span>
            <span>{{ resume.major }}</span>
          </p>
          <p>
            <span class="text-[var(--va-secondary)]">毕业院校: </span>
            <span>{{ resume.academy }}</span>
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

          <VaButton v-if="resume.status == '初审中'" color="success" @click="$emit('written', resume)">
            约笔试
          </VaButton>
          <VaButton v-if="resume.status == '笔试中'" color="success" @click="$emit('interview', resume)">
            约面试
          </VaButton>
          <VaButton v-if="resume.status == '面试中'" color="success" @click="$emit('hire', resume)"> 录用 </VaButton>
        </div>
      </VaCardContent>
    </VaCard>
  </VaInnerLoading>
  <div v-else class="p-4 flex justify-center items-center text-[var(--va-secondary)]">无简历</div>
</template>

<style lang="scss" scoped></style>
