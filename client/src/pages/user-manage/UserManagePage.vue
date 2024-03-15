<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { User } from './types'
import bus from 'vue3-eventbus'
import { auth, user } from '../../netConfig'

onMounted(() => {
  getUsers()
})

const columns = [
  { key: 'userId', sortable: true, lable: '用户ID' },
  { key: 'name', sortable: true, lable: '姓名' },
  { key: 'phoneNumber', sortable: true, lable: '性别' },
  { key: 'password', sortable: true, lable: '电话号码' },
  { key: 'actions', lable: '操作' },
]

const users = ref<User[]>([])

const getUsers = async () => {
  try {
    // users.value = []
    const res = await user.query({
      name: '',
    })
    if (res.data.code === 200) {
      users.value = res.data.data
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
  } catch (e) {
    bus.emit('alert', { type: 'error', content: '网络错误' })
  }
}

const doShowEditUserModal = ref(false)
const doShowDeleteConfirm = ref(false)
const doShowNewUserModal = ref(false)

const editedItem = ref<User>({
  userId: 0,
  password: '',
  name: '',
  phoneNumber: 0,
})

const deletedItem = ref<User>({
  userId: 0,
  password: '',
  name: '',
  phoneNumber: 0,
})

const newItem = ref<User>({
  userId: 0,
  password: '',
  name: '',
  phoneNumber: 0,
})

const openModalToEdit = (user: User) => {
  editedItem.value = user
  doShowEditUserModal.value = true
}

const openModalToAdd = () => {
  newItem.value = {
    userId: 0,
    password: '',
    name: '',
    phoneNumber: 0,
  }
  doShowNewUserModal.value = true
}

const openConfirmModal = (user: User) => {
  deletedItem.value = user
  doShowDeleteConfirm.value = true
}
const deleteUser = async () => {
  try {
    const res = await user.delete({
      userId: deletedItem.value.userId,
    })
    if (res.data.code === 200) {
      bus.emit('alert', { type: 'success', content: '删除成功' })
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
  } catch (e) {
    bus.emit('alert', { type: 'error', content: '网络错误' })
  }
  await getUsers()
}
const addUser = async () => {
  doShowNewUserModal.value = false
  try {
    const res = await auth.signup({
      userId: newItem.value.userId,
      password: newItem.value.password,
    })
    if (res.data.code === 200) {
      bus.emit('alert', { type: 'success', content: '添加成功' })
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
  } catch (e) {
    bus.emit('alert', { type: 'error', content: '网络错误' })
  }
  await getUsers()
}

const updateUser = async () => {
  doShowEditUserModal.value = false

  try {
    const res = await auth.update({
      userId: editedItem.value.userId,
      password: editedItem.value.password,
    })
    if (res.data.code === 200) {
      bus.emit('alert', { type: 'success', content: '修改成功' })
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
  } catch (e) {
    bus.emit('alert', { type: 'error', content: '网络错误' })
  }
  await getUsers()
}

const resetEditedItem = () => {
  editedItem.value = {
    userId: 0,
    password: '',
    name: '',
    phoneNumber: 0,
  }
}
const resetNewItem = () => {
  newItem.value = {
    userId: 0,
    password: '',
    name: '',
    phoneNumber: 0,
  }
}
</script>

<template>
  <VaCard>
    <VaCardContent>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <VaCardTitle text-color="primary" class="page-title">用户管理</VaCardTitle>
        <VaButton preset="flat" style="height: 30px" @click="openModalToAdd">添加用户</VaButton>
      </div>
      <VaDataTable class="table-crud" :items="users" :columns="columns" striped>
        <template #cell(actions)="{ row }">
          <VaButton preset="plain" icon="edit" @click="openModalToEdit(row.rowData)" />
          <VaButton preset="plain" icon="delete" class="ml-3" @click="openConfirmModal(row.rowData)" />
        </template>
      </VaDataTable>

      <VaModal
        v-model="doShowEditUserModal"
        class="modal-crud"
        size="auto"
        ok-text="保存"
        cancel-text="取消"
        @ok="updateUser"
        @cancel="resetEditedItem"
      >
        <span style="font-size: x-large; font-weight: bold">修改信息</span>
        <VaInput v-model="editedItem.userId" class="my-6" label="用户ID" readonly />
        <VaInput v-model="editedItem.name" class="my-6" label="姓名" readonly />
        <VaInput v-model="editedItem.phoneNumber" class="my-6" label="电话号码" readonly />
        <VaInput v-model="editedItem.password" class="my-6" type="password" label="密码" />
      </VaModal>

      <VaModal
        v-model="doShowNewUserModal"
        class="modal-crud"
        size="auto"
        ok-text="保存"
        cancel-text="取消"
        @ok="addUser"
        @cancel="resetNewItem"
      >
        <span style="font-size: x-large; font-weight: bold">添加用户</span>
        <VaInput v-model="newItem.userId" class="my-6" type="number" label="用户ID" />
        <VaInput v-model="newItem.password" class="my-6" type="password" label="密码" />
      </VaModal>

      <VaModal
        style="font-size: larger"
        v-model="doShowDeleteConfirm"
        size="small"
        message="确定删除吗？"
        ok-text="删除"
        cancel-text="取消"
        @ok="deleteUser"
      />
    </VaCardContent>
  </VaCard>
</template>

<style scoped>
.page-title {
  font-size: 30px;
  text-shadow: 0 0 2px grey;
}
</style>
