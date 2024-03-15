<template>
  <div style="width: 100vw; min-height: 100vh">
    <div class="container">
      <div class="box A flex">
        <Carousel class="fit" />
      </div>

      <div id="inform" class="box B flex">
        <InformCard class="fit" :data="informs" @showInformDetail="showInformDetails" />
      </div>
    </div>
    <div
      id="post"
      style="width: 800px; margin: 20px auto auto; display: flex; align-items: center; justify-content: center"
    >
      <VaInput
        v-model="searchContent"
        placeholder="搜索"
        preset="solid"
        clearable
        style="opacity: 0.7; font-weight: bold"
      />
      <VaButton preset="solid" style="width: 90px; height: 38px" @click="search">搜索</VaButton>
    </div>
    <div class="scroll-view">
      <template v-for="post in posts" :key="post.postId">
        <PostCard :data="post" @click="showPostDetails(post)"></PostCard>
      </template>
    </div>
    <VaModal v-if="store.isLogin" v-model="showPostDetail" hide-default-actions>
      <VaCard style="padding: 20px">
        <VaCardTitle style="display: flex; justify-content: space-between">
          <div style="display: flex; justify-content: space-between; align-items: center">
            <span style="color: #222222; font-size: 20px">{{ selectPost.postName }}</span>
            <span
              v-if="selectPost.postState === '招聘中'"
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
            selectPost.minSalary / 1000 + '-' + selectPost.maxSalary / 1000 + 'k'
          }}</span>
        </VaCardTitle>
        <VaCardContent>
          <span class="tag">{{ selectPost.educationRequirement }}</span>
          <span class="tag">{{ selectPost.ageRequirement + '岁以上' }}</span>
          <span class="tag">{{ '招聘' + selectPost.numberRequirement + '人' }}</span>
        </VaCardContent>
        <VaCardTitle style="display: flex; justify-content: space-between"> </VaCardTitle>
        <VaCardContent>
          类别：<span style="color: #666666; font-size: 15px">{{ selectPost.category }}</span>
        </VaCardContent>
        <VaCardContent>
          公司部门：<span style="color: #666666; font-size: 15px">{{ selectPost.department }}</span>
        </VaCardContent>
        <VaCardContent>
          专业要求：<span style="color: #666666; font-size: 15px">{{ selectPost.majorRequirement }}</span>
        </VaCardContent>
        <VaCardContent>
          岗位介绍：<span style="color: #666666; font-size: 15px">{{ selectPost.postSynopsis }}</span>
        </VaCardContent>
        <VaCardContent style="display: flex; justify-content: space-between">
          <span style="color: #666666; font-size: 10px">{{ selectPost.startTime + '至' + selectPost.endTime }}</span>
          <VaButton @click="deliver(selectPost)">投递简历</VaButton>
        </VaCardContent>
      </VaCard>
    </VaModal>
    <VaModal
      v-else
      v-model="showPostDetail"
      style="font-size: 15px"
      message="游客查看具体信息请登录"
      ok-text="确定"
      cancel-text="取消"
      size="auto"
      @ok="toLogin"
    />
    <VaModal
      v-model="ensureDeliver"
      style="font-size: 15px"
      message="确认投递？"
      ok-text="确定"
      cancel-text="取消"
      size="auto"
      @ok="addApplication"
    />
    <VaModal v-model="showInformDetail" close-button hide-default-actions>
      <VaCard
        style="
          padding: 50px;
          height: 70vh;
          overflow: auto;
          display: flex;
          flex-direction: column;
          justify-content: space-between;
        "
      >
        <VaCardTitle style="display: flex; justify-content: center; margin-bottom: 30px">
          <span style="color: #222222; font-size: 40px">{{ selectInform.newsTitle }}</span>
        </VaCardTitle>
        <VaCardContent>
          <p style="color: #666666; font-size: 20px">{{ selectInform.newsContent }}</p>
        </VaCardContent>
        <div>
          <VaCardContent style="display: flex; justify-content: space-between">
            <span></span>
            <span style="color: #666666; font-size: 15px">{{ selectInform.department }}</span>
          </VaCardContent>
          <VaCardContent style="display: flex; justify-content: space-between">
            <span></span>
            <span style="color: #666666; font-size: 15px">{{ selectInform.time }}</span>
          </VaCardContent>
        </div>
      </VaCard>
    </VaModal>
  </div>
</template>

<script lang="ts" setup>
import Carousel from '../../components/Carousel.vue'
import InformCard from './cards/InformCard.vue'
import PostCard from './cards/PostCard.vue'
import { onMounted, ref } from 'vue'
import router from '../../router'

import { useUserStore } from '../../stores/user-store'
import axios from 'axios'

const store = useUserStore()

interface PostData {
  postId: number
  postName: string
  department: string
  educationRequirement: string
  majorRequirement: string
  minSalary: number
  maxSalary: number
  endTime: string
  postState: string
  category: string
  ageRequirement: number
  numberRequirement: number
  postSynopsis: string
  startTime: string
}
interface InformData {
  newsId: number
  newsTitle: string
  time: string
  newsContent: string
  department: string
}

onMounted(async() => {
  await getPosts();
})

const posts = ref<PostData[]>([])

const informs = ref<InformData[]>([])

import bus from 'vue3-eventbus'
import { application, job, news } from '../../netConfig'
import { get } from 'node:http'

onMounted(() => {
  getInforms()
  getPosts()
})
const getInforms = async () => {
  const res = await axios.post('/News/find', { newsTitle: '' })
  if (res.data.code == 200) {
    informs.value = res.data.data
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}
const getPosts = async () => {
  const res = await job.query({ postName: '' })
  if (res.data.code == 200) {
    posts.value = res.data.data
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}

const showPostDetail = ref(false)
const ensureDeliver = ref(false)
const showInformDetail = ref(false)
const searchContent = ref('')
const selectPost = ref<PostData>({
  postId: 0,
  postName: '',
  department: '',
  educationRequirement: '',
  majorRequirement: '',
  minSalary: 0,
  maxSalary: 0,
  endTime: '',
  postState: '',
  category: '',
  ageRequirement: 0,
  numberRequirement: 0,
  postSynopsis: '',
  startTime: '',
})
const selectInform = ref<InformData>({
  newsId: 0,
  newsTitle: '',
  time: '',
  newsContent: '',
  department: '',
})

const toLogin = () => {
  showPostDetail.value = false
  router.push('/auth')
}

const search = async () => {
  const res = await job.query({
    postName: searchContent.value,
  })
  if (res.data.code == 200) {
    posts.value = res.data.data
    bus.emit('alert', { type: 'success', content: '查询成功' })
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}

const showPostDetails = (post: PostData) => {
  selectPost.value = post
  showPostDetail.value = true
}

const showInformDetails = (inform: InformData) => {
  selectInform.value = inform
  showInformDetail.value = true
}

const deliver = (post: PostData) => {
  ensureDeliver.value = true
}
const addApplication = async () => {
  const res = await application.insert({
    postId: selectPost.value.postId,
    userId: store.userId,
  })
  if (res.data.code == 200) {
    bus.emit('alert', { type: 'success', content: '投递成功' })
  } else if(res.data.code == 500 ){
    bus.emit('alert', { type: 'error', content: '请先完成简历填写' })
  } else if(res.data.code == 505 ){
    bus.emit('alert', { type: 'error', content: res.data.msg })
  }else if(res.data.code == 502 ){
    bus.emit('alert', { type: 'error', content: res.data.msg })
  }else if(res.data.code == 501 ){
    bus.emit('alert', { type: 'error', content: res.data.msg })
  }else if(res.data.code == 503 ){
    bus.emit('alert', { type: 'error', content: res.data.msg })
  }
  else {
    bus.emit('alert', { type: 'error', content: '服务器问题' })
  }
  showPostDetail.value = false
  await getPosts()
}
</script>

<style lang="scss" scoped>
p {
  text-indent: 2em;
  line-height: 1.5;
}

.container {
  display: grid;
  grid-gap: 10px;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr;
}

.box {
  border-radius: 5px;
  padding: 20px;
  font-size: 150%;
}

.A {
  grid-column: 1 / 4;
  grid-row: 1;
}

.B {
  grid-column: 4 / 6;
  grid-row: 1;
}

.fit {
  object-fit: cover;
  width: 100%;
  height: 100%;
}

.flex {
  display: flex;
  justify-content: center;
  align-items: center;
}

.scroll-view {
  display: grid;
  grid-template-columns: repeat(auto-fill, 400px);
  grid-auto-rows: 280px;
  gap: 20px;
  justify-content: center;
  justify-items: center;
  align-items: center;
}
</style>
