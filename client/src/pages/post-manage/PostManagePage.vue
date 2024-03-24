<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { Post } from './types'
import PostTable from './widgets/PostsTable.vue'
import bus from 'vue3-eventbus'
import { useForm } from 'vuestic-ui'
import { job } from '../../netConfig'

const posts = ref<Post[]>([])
const getPosts = (async () => {
  const res = await job.query({ postName: '' })
  if (res.data.code === 200) {
    postDb.value = res.data.data
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
})
onMounted(async () => {
  await getPosts()
})

const defaultItem: Post = {
  postId: 0,
  postName: '',
  minSalary: 0,
  maxSalary: 0,
  department: '',
  ageRequirement: 0,
  educationRequirement: '',
  majorRequirement: '',
  postState: '',
  category: '',
  startTime: new Date(Date.now()).toISOString(),
  endTime: new Date(Date.now()).toISOString(),
  postSynopsis: '',
  numberRequirement: 0,
}

const postDb = ref<Post[]>([
])
const showDeletePostModal = async (postId: number) => {
  const res = await job.delete({ postId: postId })
  if (res.data.code === 200) {
    getPosts()
    bus.emit('alert', { type: 'success', content: '删除成功' })
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}
const showEditPostModal = async (post: Post) => {
  const res = await job.update(post)
  if (res.data.code === 200) {
    getPosts()
    bus.emit('alert', { type: 'success', content: '修改成功' })
  }else if(res.data.code === 501){
    bus.emit('alert', { type: 'error', content: res.data.msg })
  } 
  else {
    bus.emit('alert', { type: 'error', content: '岗位更新错误,请检查格式' })
  }
}

const showSomePost = async (postName: string) => {
  const res = await job.query( { postName: postName })
  if (res.data.code === 200) {
    console.log('查找成功')
  } else {
    console.log('服务器错误')
  }
}

const isModalVisible = ref(false)
const createdItem = ref({
  postId: 0,
  postName: '',
  minSalary: 0,
  maxSalary: 0,
  department: '',
  ageRequirement: 0,
  educationRequirement: '',
  majorRequirement: '',
  postState: '',
  category: '',
  startTime: new Date(Date.now()).toISOString(),
  endTime: new Date(Date.now()).toISOString(),
  postSynopsis: '',
  numberRequirement: 0,
})

const addPost = async () => {
  isModalVisible.value = false
  const res = await job.insert(createdItem.value)
  if (res.data.code === 200) {
    getPosts()
    bus.emit('alert', { type: 'success', content: '新增成功' })
  }else if(res.data.code === 501){
    bus.emit('alert', { type: 'error', content: res.data.msg })
  } 
   else {
    bus.emit('alert', { type: 'error', content: '岗位添加错误,请检查格式' })
  }
}

const postState = computed(() => {
  return new Date(createdItem.value.startTime) > new Date(Date.now())
    ? '未开始'
    : new Date(Date.now()) > new Date(createdItem.value.endTime)
      ? '已结束'
      : '招聘中'
})

watch(postState, (newV) => {
  createdItem.value.postState = newV
})

const selectMode = ref(false)
const selectedPosts = ref<Post[]>([])
const multipleDelete = (mode: boolean, selectPosts: Post[]) => {
  selectMode.value = mode
  selectedPosts.value = selectPosts
}
const deletePosts = async () => {
  while (selectedPosts.value.length > 0) {
    const res = await job.delete({ postId: selectedPosts.value[0].postId })
    if (res.data.code === 200) {
      selectedPosts.value.shift()
      bus.emit('alert', { type: 'success', content: '删除成功' })
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
  }
  getPosts()
}

const { isValid, isLoading, validate } = useForm('myForm')
</script>

<template>
  <VaCard>
    <div style="display: flex; justify-content: space-between">
      <VaCardTitle
        text-color="primary"
        style="font-size: 30px; text-shadow: 0 0 2px grey; text-align: center; padding: 10px"
      >
        岗位管理
      </VaCardTitle>
      <div>
        <VaButton
          v-if="selectMode"
          preset="secondary"
          color="danger"
          border-color="danger"
          style="margin: 10px"
          @click="deletePosts"
        >
          批量删除
        </VaButton>

        <VaButton
          preset="secondary"
          border-color="primary"
          style="margin: 10px"
          @click="(isModalVisible = true) && (createdItem = { ...defaultItem })"
          >新增岗位</VaButton
        >
      </div>
    </div>
    <div class="page-content">
      <PostTable
        :posts="postDb"
        @deletePost="showDeletePostModal"
        @editPost="showEditPostModal"
        @findPost="showSomePost"
        @selectMode="multipleDelete"
      />
    </div>
  </VaCard>

  <VaModal v-model="isModalVisible" title="新增招聘岗位" hide-default-actions>
    <VaForm ref="myForm">
      <VaProgressBar v-show="isLoading" indeterminate />
      <VaInput v-model="createdItem.postName" label="岗位名称" :rules="[(v) => !!v || '岗位名称不能为空']" />
      <VaInput
        v-model="createdItem.minSalary"
        label="最低薪资(元)"
        type="number"
        :rules="[(v) => !!v || '最低薪资不能为空']"
      />
      <VaInput
        v-model="createdItem.maxSalary"
        label="最高薪资(元)"
        type="number"
        :rules="[(v) => !!v || '最高薪资不能为空']"
      />
      <VaInput v-model="createdItem.department" label="部门" :rules="[(v) => !!v || '部门不能为空']" />
      <VaInput
        v-model="createdItem.ageRequirement"
        label="年龄需求(下限)"
        type="number"
        :rules="[(v) => !!v || '年龄需求不能为空']"
      />
      <VaSelect
        v-model="createdItem.educationRequirement"
        label="学历需求"
        :options="['大专', '本科', '硕士', '博士']"
        :rules="[(v) => !!v || '学历需求不能为空']"
      />
      <VaInput v-model="createdItem.majorRequirement" label="专业需求" :rules="[(v) => !!v || '专业需求不能为空']" />
      <VaInput v-model="createdItem.postState" label="招聘状态" disabled placeholder="您还未开始招聘" />
      <VaSelect
        v-model="createdItem.category"
        label="类别"
        :options="['科研类', '图书馆管理类', '教学类', '实验室管理类']"
        :rules="[(v) => !!v || '类别不能为空']"
      />
      <VaDateInput v-model="createdItem.startTime" label="开始时间" :rules="[(v) => !!v || '开始时间不能为空']" />
      <VaDateInput
        v-model="createdItem.endTime"
        stateful
        :rules="[(v) => new Date(v) > new Date(createdItem.startTime) || '结束时间必须大于开始时间']"
        label="结束时间"
      />
      <VaInput v-model="createdItem.postSynopsis" label="简介" :rules="[(v) => !!v || '简介不能为空']" />
      <VaInput
        v-model="createdItem.numberRequirement"
        label="招聘人数"
        type="number"
        :rules="[(v) => !!v || '招聘人数不能为空']"
      />
    </VaForm>
    <template #footer>
      <VaButton preset="secondary" border-color="primary" @click="isModalVisible = false">取消</VaButton>
      <VaButton
        style="margin-left: 30px"
        :loading="isLoading"
        preset="primary"
        border-color="primary"
        @click="validate() && addPost()"
        >确定</VaButton
      >
    </template>
  </VaModal>
</template>
