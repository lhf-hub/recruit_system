<script setup lang="ts">
import { PropType, computed, ref, watch } from 'vue'
import { useForm } from 'vuestic-ui'
import { Post } from '../types'
import PostAvatar from './PostAvatar.vue'
import { validators } from '../../../services/utils'

const props = defineProps({
  post: {
    type: Object as PropType<Post | null>,
    default: null,
  },
  saveButtonLabel: {
    type: String,
    default: 'Save',
  },
})

const defaultNewPost = ref<Post>({
  // projects: [],
  postId: 0,
  postName: '',
  department: '',
  ageRequirement: 20,
  educationRequirement: '',
  majorRequirement: '',
  minSalary: 0,
  maxSalary: 0,
  postState: '',
  postSynopsis: '',
  category: '',
  endTime: new Date(),
  startTime: new Date(),
  numberRequirement: 0,
})
const educationRequirementOptions = ref([
  { text: '大专', value: '大专' },
  { text: '本科', value: '本科' },
  { text: '硕士', value: '硕士' },
  { text: '博士', value: '博士' },
  { text: '不限', value: '不限' },
])

const newPost = ref<Post>({ ...defaultNewPost })

const isFormHasUnsavedChanges = computed(() => {
  return Object.keys(newPost.value).some((key) => {
    if (key === 'avatar' || key === 'resumes') {
      return false
    }

    return newPost.value[key as keyof Post] !== (props.post ?? defaultNewPost)?.[key as keyof Post]
  })
})

defineExpose({
  isFormHasUnsavedChanges,
})

watch(
  () => props.post,
  () => {
    if (!props.post) {
      return
    }

    newPost.value = {
      ...props.post,
      avatar: props.post.avatar || '',
    }
  },
  { immediate: true },
)

const avatar = ref<File>()

const makeAvatarBlobUrl = (avatar: File) => {
  return URL.createObjectURL(avatar)
}

watch(avatar, (newAvatar) => {
  newPost.value.avatar = newAvatar ? makeAvatarBlobUrl(newAvatar) : ''
})

const form = useForm('add-user-form')

const emit = defineEmits(['close', 'save'])

const onSave = () => {
  if (form.validate()) {
    emit('save', newPost.value)
  }
}

// const roleSelectOptions: { text: Capitalize<UserRole>; value: UserRole }[] = [
//   { text: 'Admin', value: 'admin' },
//   { text: 'User', value: 'user' },
//   { text: 'Owner', value: 'owner' },
// ]

// const { projects } = useProjects({ pagination: ref({ page: 1, perPage: 9999, total: 10 }) })
</script>

<template>
  <VaForm v-slot="{ isValid }" ref="add-post-form" class="flex-col justify-start items-start gap-4 inline-flex w-full">
    <VaFileUpload
      v-model="avatar"
      type="single"
      hide-file-list
      class="self-stretch justify-start items-center gap-4 inline-flex"
    >
      <PostAvatar :post="newPost" size="large" />
      <VaButton preset="primary" size="small">Add image</VaButton>
      <VaButton
        v-if="avatar"
        preset="primary"
        color="danger"
        size="small"
        icon="delete"
        class="z-10"
        @click.stop="avatar = undefined"
      />
    </VaFileUpload>
    <div class="self-stretch flex-col justify-start items-start gap-4 flex">
      <div class="flex gap-4 flex-col sm:flex-row w-full">
        <VaInput
          v-model="newPost.postName"
          label="岗位名称"
          class="w-full sm:w-1/2"
          :rules="[validators.required]"
          name="postName"
        />
        <VaInput
          v-model="newPost.department"
          label="部门"
          class="w-full sm:w-1/2"
          :rules="[validators.required]"
          name="department"
        />
      </div>
      <div class="flex gap-4 flex-col sm:flex-row w-full">
        <VaInput
          v-model="newPost.ageRequirement"
          label="年龄要求"
          class="w-full sm:w-1/2"
          :rules="[validators.required, validators.email]"
          name="ageRequirement"
        />
        <VaSelect
          v-model="newPost.educationRequirement"
          label="学历要求"
          class="w-full sm:w-1/2"
          :options="educationRequirementOptions"
          :rules="[validators.required]"
          name="educationRequirement"
          text-by="text"
          track-by="value"
          multiple
          :max-visible-options="2"
        />
      </div>

      <div class="flex gap-4 w-full">
        <VaInput
          v-model="newPost.majorRequirement"
          label="专业要求"
          class="w-full"
          :rules="[validators.required]"
          name="majorRequirement"
        />
      </div>
      <div class="flex gap-4 flex-col sm:flex-row w-full">
        <VaInput
          v-model="newPost.minSalary"
          label="最低薪资"
          class="w-full sm:w-1/2"
          :rules="[validators.required]"
          name="minSalary"
        />
        <VaInput
          v-model="newPost.maxSalary"
          label="最高薪资"
          class="w-full sm:w-1/2"
          :rules="[validators.required]"
          name="maxSalary"
        />
      </div>

      <div class="flex gap-4 flex-col sm:flex-row w-full">
        <VaInput
          v-model="newPost.numberRequirement"
          label="招聘人数"
          class="w-full sm:w-1/2"
          :rules="[validators.required]"
          name="numberRequirement"
        />
        <VaInput
          v-model="newPost.postState"
          label="岗位状态"
          class="w-full sm:w-1/2"
          :rules="[validators.required]"
          name="postState"
        />
      </div>

      <VaTextarea v-model="newPost.postSynopsis" label="岗位简介" class="w-full" name="postSynopsis" />
      <div class="flex gap-2 flex-col-reverse items-stretch justify-end w-full sm:flex-row sm:items-center">
        <VaButton preset="secondary" color="secondary" @click="$emit('close')">Cancel</VaButton>
        <VaButton :disabled="!isValid" @click="onSave">{{ saveButtonLabel }}</VaButton>
      </div>
    </div>
  </VaForm>
</template>
