<script setup lang="ts">
import { Post } from '../types'
import { computed, ref } from 'vue'
import { defineExpose } from 'vue'
import { useForm, useModal } from 'vuestic-ui'

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
  startTime: '',
  endTime: '',
  postSynopsis: '',
  numberRequirement: 0,
}

const isModalVisible = ref(false) // 是否显示编辑模态框
const columns = [
  { key: 'postId', label: 'ID', width: 50 },
  { key: 'postName', label: '岗位名称' },
  { key: 'minSalary', label: '最低薪资' },
  { key: 'maxSalary', label: '最高薪资' },
  { key: 'department', label: '部门' },
  { key: 'ageRequirement', label: '年龄需求' },
  { key: 'educationRequirement', label: '学历需求' },
  { key: 'majorRequirement', label: '专业需求' },
  { key: 'postState', label: '招聘状态' },
  { key: 'category', label: '类别' },
  // { key: 'startTime', label: '开始时间' },
  // { key: 'endTime', label: '结束时间' },
  // { key: 'postSynopsis', label: '简介' },
  { key: 'numberRequirement', label: '招聘人数' },
  { key: 'actions', lable: '更多操作', width: 150 },
]
const props = defineProps({
  posts: {
    type: Array as () => Post[],
    required: true,
  },
})

const emit = defineEmits<{
  (event: 'delete-post', postId: number): void
  (event: 'edit-post', post: Post): void
  (event: 'add-post', post: Post): void
  (event: 'find-post', postName: string): void
  (event: 'select-mode', mode: boolean, selectPosts: Post[]): void
}>()

const editedItem = ref<Post>(defaultItem)
const createdItem = ref({ ...defaultItem })

const resetEditedItem = () => {
  editedItem.value = { ...defaultItem }
}

const resetCreatedItem = (item) => {
  createdItem.value = { ...defaultItem }
}
const { confirm } = useModal()
const deleteItemById = async (item: Post) => {
  const agreed = await confirm({
    title: '删除该岗位招聘信息',
    message: `您确定要删除 ${item.postName}?`,
    okText: '删除',
    cancelText: '取消',
    size: 'small',
    maxWidth: '380px',
  })
  if (agreed) {
    emit('delete-post', item.postId)
  }
}

const addNewItem = (item) => {
  item.value = [...item.value, { ...createdItem.value }]
  resetCreatedItem(item)
}

const editItem = async () => {
  isModalVisible.value = false
  // 保存编辑
  const agree = await confirm({
    title: '保存编辑',
    message: `您确定要提交本次编辑?`,
    okText: '确定',
    cancelText: '取消',
    size: 'small',
    maxWidth: '380px',
  })
  if (agree) {
    emit('edit-post', editedItem.value as Post)
  }
}

const openModalToEditItemById = (item: Post) => {
  isModalVisible.value = true
  editedItem.value = { ...item }
}
//
// defineExpose({
//   columns,
//   editedItem,
//   createdItem,
//   isNewData,
//   resetEditedItem,
//   resetCreatedItem,
//   deleteItemById,
//   addNewItem,
//   editItem,
//   openModalToEditItemById,
// })

const searchContent = ref('')
const query = () => {
  emit('find-post', searchContent.value)
}

const currentSelectedItems = ref<Post[]>([])
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
      :items="posts"
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
          icon="edit"
          @click="openModalToEditItemById(row.rowData as Post)"
        />
        <VaButton
          v-if="currentSelectedItems.length <= 0"
          style="margin-top: 8px"
          preset="plain"
          icon="delete"
          class="ml-3"
          @click="deleteItemById(row.rowData as Post)"
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
        <!--       只放简介，带标签指示-->
        <template v-if="row.rowData">
          <div class="p-4">
            <span>简介: </span>
            <p disabled>{{ row.rowData.postSynopsis }}</p>
            <br />
            <p>招聘开始时间: {{ row.rowData.startTime }}</p>
            <br />
            <p>招聘结束时间: {{ row.rowData.endTime }}</p>
          </div>
        </template>
      </template>
    </VaDataTable>
  </div>
  <!--  <VaModal-->
  <!--    class="modal-crud"-->
  <!--    :model-value="!!editedItem"-->
  <!--    title="Edit item"-->
  <!--    size="small"-->
  <!--    @ok="editItem"-->
  <!--    @cancel="resetEditedItem"-->
  <!--  >-->
  <!--    <VaInput-->
  <!--      v-for="key in Object.keys(editedItem ?? {})"-->
  <!--      :key="key"-->
  <!--      v-model="editedItem[key]"-->
  <!--      class="my-6"-->
  <!--      :label="key"-->
  <!--    />-->
  <!--  </VaModal>-->
  <VaModal v-model="isModalVisible" class="modal-crud" title="更新岗位招聘信息" size="small" hide-default-actions>
    <VaForm ref="myForm">
      <VaInput v-model="editedItem.postName" label="岗位名称" :rules="[(v) => !!v || '岗位名称不能为空']" />
      <VaInput
        v-model="editedItem.minSalary"
        label="最低薪资(元)"
        type="number"
        :rules="[(v) => !!v || '最低薪资不能为空']"
      />
      <VaInput
        v-model="editedItem.maxSalary"
        label="最高薪资(元)"
        type="number"
        :rules="[(v) => !!v || '最高薪资不能为空']"
      />
      <VaInput v-model="editedItem.department" label="部门" :rules="[(v) => !!v || '部门不能为空']" />
      <VaInput
        v-model="editedItem.ageRequirement"
        label="年龄需求(下限)"
        type="number"
        :rules="[(v) => !!v || '年龄需求不能为空']"
      />
      <VaSelect
        v-model="editedItem.educationRequirement"
        label="学历需求"
        :options="['大专', '本科', '硕士', '博士']"
        :rules="[(v) => !!v || '学历需求不能为空']"
      />
      <VaInput v-model="editedItem.majorRequirement" label="专业需求" :rules="[(v) => !!v || '专业需求不能为空']" />
      <VaInput v-model="editedItem.postState" label="招聘状态" disabled placeholder="您还未开始招聘" />
      <VaInput v-model="editedItem.category" label="类别" :rules="[(v) => !!v || '类别不能为空']" />
      <VaDateInput v-model="editedItem.startTime" label="开始时间" :rules="[(v) => !!v || '开始时间不能为空']" />
      <VaDateInput
        v-model="editedItem.endTime"
        stateful
        :rules="[(v) => new Date(v) > new Date(editedItem.startTime) || '结束时间必须大于开始时间']"
        label="结束时间"
      />
      <VaInput v-model="editedItem.postSynopsis" label="简介" :rules="[(v) => !!v || '简介不能为空']" />
      <VaInput
        v-model="editedItem.numberRequirement"
        label="招聘人数"
        type="number"
        :rules="[(v) => !!v || '招聘人数不能为空']"
      />
    </VaForm>
    <template #footer>
      <VaButton preset="secondary" border-color="primary" @click="isModalVisible = false">取消</VaButton>
      <VaButton style="margin-left: 30px" preset="primary" border-color="primary" @click="validate() && editItem()"
        >确定</VaButton
      >
    </template>
  </VaModal>
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
