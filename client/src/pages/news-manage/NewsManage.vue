<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { News } from './types'
import NewsTable from './widgets/NewsTable.vue'
import bus from 'vue3-eventbus'
import { useForm } from 'vuestic-ui'
import { news } from '../../netConfig'

const newss = ref<News[]>([])
const getNewss = (async () => {
  const res = await news.query({ newsTitle: '' })
  if (res.data.code === 200) {
    newss.value = res.data.data
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
})
onMounted(async () => {
  await getNewss()
})

const defaultItem: News = {
  newsId: 99999999,
  newsTitle: '',
  newsContent: '',
  time: new Date(Date.now()).toISOString(),
  department: '',
}
const showDeleteNewsModal = async (newsId: number) => {
  const res = await news.delete({ newsId: newsId })
  if (res.data.code === 200) {
    getNewss()
    bus.emit('alert', { type: 'success', content: '删除成功' })
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}

const showSomeNews = async (newsTitle: string) => {
  const res = await news.query( { newsTitle: newsTitle })
  if (res.data.code === 200) {
    // console.log('查找成功')
  } else {
    console.log('服务器错误')
  }
}
const isModalVisible = ref(false)
const createdItem = ref({ ...defaultItem })

const addNews = async () => {
  isModalVisible.value = false
  const res = await news.insert(createdItem.value)
  if (res.data.code === 200) {
    getNewss()
    bus.emit('alert', { type: 'success', content: '新增成功' })
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}
const selectMode = ref(false)
const selectedNewss = ref<News[]>([])
const multipleDelete = (mode: boolean, selectNewss: News[]) => {
  selectMode.value = mode
  selectedNewss.value = selectNewss
}
const deleteNewss = async () => {
  while (selectedNewss.value.length > 0) {
    const res = await news.delete({ newsId: selectedNewss.value[0].newsId })
    if (res.data.code === 200) {
      selectedNewss.value.shift()
      bus.emit('alert', { type: 'success', content: '删除成功' })
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
  }
  getNewss()
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
        通知公告管理
      </VaCardTitle>
      <div>
        <VaButton
          v-if="selectMode"
          preset="secondary"
          color="danger"
          border-color="danger"
          style="margin: 10px"
          @click="deleteNewss"
        >
          批量删除
        </VaButton>

        <VaButton
          preset="secondary"
          border-color="primary"
          style="margin: 10px"
          @click="(isModalVisible = true) && (createdItem = { ...defaultItem })"
        >发布公告</VaButton
        >
      </div>
    </div>
    <div class="page-content">
      <NewsTable
        :newss="newss"
        @deleteNews="showDeleteNewsModal"
        @findNews="showSomeNews"
        @selectMode="multipleDelete"
      />
    </div>
  </VaCard>

  <VaModal v-model="isModalVisible" title="发布公告" hide-default-actions>
    <VaForm ref="myForm">
      <VaProgressBar v-show="isLoading" indeterminate />
      <VaInput v-model="createdItem.newsTitle" label="公告标题" :rules="[(v) => !!v || '公告标题不能为空']" />
      <VaInput v-model="createdItem.department" label="部门" :rules="[(v) => !!v || '部门不能为空']" />
      <VaInput v-model="createdItem.newsContent" label="公告内容" :rules="[(v) => !!v || '公告内容不能为空']" />
    </VaForm>
    <template #footer>
      <VaButton preset="secondary" border-color="primary" @click="isModalVisible = false">取消</VaButton>
      <VaButton
        style="margin-left: 30px"
        :loading="isLoading"
        preset="primary"
        border-color="primary"
        @click="validate() && addNews()"
      >确定</VaButton
      >
    </template>
  </VaModal>
</template>

<style scoped lang="scss">

</style>
