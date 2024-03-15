<script setup lang="ts">
import { News } from '../types'
import { computed, ref } from 'vue'
import { useForm, useModal } from 'vuestic-ui'

const defaultItem: News = {
  newsId: 99999999,
  newsTitle: '',
  newsContent: '',
  time: new Date(Date.now()).toISOString(),
  department: '',
}

const isModalVisible = ref(false) // 是否显示编辑模态框
const columns = [
  // { key: 'newsId', label: 'ID', width: 50 },
  { key: 'newsTitle', label: '公告标题' },
  // { key: 'newsContent', label: '新闻内容' },
  { key: 'time', label: '发布时间' },
  { key: 'department', label: '发布部门' },
  { key: 'actions', lable: '更多操作', width: 150 },
]
const props = defineProps({
  newss: {
    type: Array as () => News[],
    required: true,
  },
})

const emit = defineEmits<{
  (event: 'delete-news', newsId: number): void
  (event: 'edit-news', news: News): void
  (event: 'add-news', news: News): void
  (event: 'find-news', newsName: string): void
  (event: 'select-mode', mode: boolean, selectNewss: News[]): void
}>()

const createdItem = ref({ ...defaultItem })


const resetCreatedItem = (item) => {
  createdItem.value = { ...defaultItem }
}
const { confirm } = useModal()
const deleteItemById = async (item: News) => {
  const agreed = await confirm({
    title: '删除该公告信息',
    message: `您确定要删除 ${item.newsTitle}?`,
    okText: '删除',
    cancelText: '取消',
    size: 'small',
    maxWidth: '380px',
  })
  if (agreed) {
    emit('delete-news', item.newsId)
  }
}

const addNewItem = (item) => {
  item.value = [...item.value, { ...createdItem.value }]
  resetCreatedItem(item)
}


const searchContent = ref('')
const query = () => {
  emit('find-news', searchContent.value)
}

const currentSelectedItems = ref<News[]>([])
const selectedItemsEmitted = (selectedItems) => {
  currentSelectedItems.value = selectedItems.currentSelectedItems
  emit('select-mode', selectedItems.currentSelectedItems.length > 0, selectedItems.currentSelectedItems)
}

const { isValid, validate } = useForm('myForm')
</script>

<template>
  <VaInput v-model="searchContent" style="margin: 0 2px 2px 0" placeholder="输入岗位名称模糊查询">
    <template #appendInner>
      <VaButton icon="search " gradient round @click="query" />
    </template>
  </VaInput>
  <div class="grid gap-10">
    <VaDataTable
      class="custom-table"
      :items="newss"
      :columns="columns"
      striped
      hoverable
      height="100%"
      sticky-header
      :scroll-bottom-margin="20"
      selectable
      @selectionChange="selectedItemsEmitted"
    >
      <template #cell(actions)="{ row, isExpanded }">
        <VaButton
          v-if="currentSelectedItems.length <= 0"
          style="margin-top: 8px"
          preset="plain"
          icon="delete"
          class="ml-3"
          @click="deleteItemById(row.rowData as News)"
        />
        <VaButton
          :icon="isExpanded ? 'va-arrow-up' : 'va-arrow-down'"
          preset="secondary"
          @click="row.toggleRowDetails()"
        >
          {{ isExpanded ? '' : '' }}
        </VaButton>
      </template>
      <template #expandableRow="row">
        <template v-if="row.rowData">
          <div class="p-4">
            <h2>公告内容: </h2>
            <p disabled>{{ row.rowData.newsContent }}</p>
          </div>
        </template>
      </template>
    </VaDataTable>
  </div>
</template>

<style lang="scss" scoped>
.va-data-table {
  ::v-deep(.va-data-table__table-tr) {
    border-bottom: 1px solid var(--va-background-border);
  }
}
.va-table-responsive {
  overflow: auto;
}
::v-deep(.custom-table) {
  --va-data-table-thead-background: linear-gradient(0deg, var(--va-primary), var(--va-info));
  --va-data-table-tfoot-background: linear-gradient(0deg, var(--va-info), var(--va-primary));
  --va-data-table-max-height: 400px;
  --va-data-table-thead-color: var(--va-text-inverted);
  --va-data-table-tfoot-color: var(--va-text-inverted);
}

.p-4 {
  padding: 20px;
  background-color: #e3f1fc;
}
</style>
