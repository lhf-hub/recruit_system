<template>
  <VaForm ref="form" @submit.prevent="submit">
    <h1 class="font-semibold text-4xl mb-4">登录</h1>
    <p class="text-base mb-4 leading-5">
      新用户？
      <RouterLink :to="{ name: 'signup' }" class="font-semibold text-primary">注册</RouterLink>
    </p>
    <VaInput v-model="formData.userId" :rules="[validators.required]" class="mb-4" label="ID" />
    <VaValue v-slot="isPasswordVisible" :default-value="false">
      <VaInput
        v-model="formData.password"
        :rules="[validators.required]"
        :type="isPasswordVisible.value ? 'text' : 'password'"
        class="mb-4"
        label="密码"
        @clickAppendInner.stop="isPasswordVisible.value = !isPasswordVisible.value"
      >
        <template #appendInner>
          <VaIcon
            :name="isPasswordVisible.value ? 'mso-visibility_off' : 'mso-visibility'"
            class="cursor-pointer"
            color="secondary"
          />
        </template>
      </VaInput>
    </VaValue>

    <div class="flex justify-center mt-4">
      <VaButton class="w-full" @click="submit">登录</VaButton>
    </div>
  </VaForm>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useForm } from 'vuestic-ui'
import { validators } from '../../services/utils'
import { useUserStore } from '../../stores/user-store'
import bus from 'vue3-eventbus'
import { auth } from '../../netConfig'

const { validate } = useForm('form')
const { push } = useRouter()

const formData = reactive({
  userId: '',
  password: '',
})

const store = useUserStore()


const submit = async () => {
  if (validate()) {
    try {
      const res = await auth.login(formData)
      if (res.data.code === 200 && res.data.data[0].role === 'user') {
        store.login()
        store.role = res.data.data[0].role
        store.setUserId(formData.userId)
        store.setPassword(formData.password)
        bus.emit('alert', { type: 'success', content: '登录成功' })
        push({ name: 'front' })
      } else if (res.data.code === 200 && res.data.data[0].role === 'admin') {
        store.login()
        store.role = res.data.data[0].role
        store.setUserId(formData.userId)
        store.setPassword(formData.password)
        bus.emit('alert', { type: 'success', content: '登录成功' })
        push({ name: 'admin' })
      } else if (res.data.code === 200 && res.data.data[0].role === 'super-admin') {
        store.login()
        store.role = res.data.data[0].role
        store.setUserId(formData.userId)
        store.setPassword(formData.password)
        bus.emit('alert', { type: 'success', content: '登录成功' })
        push({ name: 'super-admin' })
      } else {
        bus.emit('alert', { type: 'error', content: res.data.msg })
      }
    } catch {
      bus.emit('alert', { type: 'error', content: '网络错误' })
    }
  }
}
</script>
