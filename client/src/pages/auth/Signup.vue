<template>
  <VaForm ref="form" @submit.prevent="submit">
    <h1 class="font-semibold text-4xl mb-4">注册</h1>
    <p class="text-base mb-4 leading-5">
      已有帐号? 
      <RouterLink :to="{ name: 'login' }" class="font-semibold text-primary">登录</RouterLink>
    </p>
    <VaInput
      v-model="formData.userId"
      class="mb-4"
      label="id"
      :rules="[
        // 不许输入自然底数，不许输入负数，长度需大于4位，不超过九位
        (v) => {
          if (v === 'e' || v === 'E') {
            return '禁止输入非数字'
          }
          if (v === '-') {
            return '禁止输入负数'
          }
          if (v.length < 4) {
            return 'ID长度须大于3位'
          }
          if (v.length > 8) {
            return 'ID长度须小于9位'
          }
          return true
        },
      ]"
      type="number"
    />
    <VaValue v-slot="isPasswordVisible" :default-value="false">
      <VaInput
        ref="password1"
        v-model="formData.password"
        :rules="passwordRules"
        :type="isPasswordVisible.value ? 'text' : 'password'"
        class="mb-4"
        label="密码"
        messages="密码应为 8+ 个字符：字母、数字和特殊字符。"
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
      <VaInput
        ref="password2"
        v-model="formData.repeatPassword"
        :rules="[
          (v) => !!v || '重复密码字段为必填项',
          (v) => v === formData.password || '密码不一致',
        ]"
        :type="isPasswordVisible.value ? 'text' : 'password'"
        class="mb-4"
        label="确认密码"
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
      <VaButton class="w-full" @click="submit">注册</VaButton>
    </div>
  </VaForm>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useForm, useToast } from 'vuestic-ui'
import axios from 'axios'

const { validate } = useForm('form')
const { push } = useRouter()

const formData = reactive({
  userId: '',
  password: '',
  repeatPassword: '',
})

import bus from 'vue3-eventbus'
const submit = async () => {
  if (validate()) {
    try {
      const res = await axios.post('/UserAccount/signup', { userId: formData.userId, password: formData.password })
      if (res.data.code === 200) {
        bus.emit('alert', { type: 'success', content: '注册成功，请登陆' })
        push({ name: 'login' })
      } else {
        bus.emit('alert', { type: 'error', content: res.data.msg })
      }
    } catch {
      bus.emit('alert', { type: 'error', content: '网络错误' })
    }
  }
}

const passwordRules: ((v: string) => boolean | string)[] = [
  (v) => !!v || '密码字段为必填项',
  (v) => (v && v.length >= 8) || '密码长度必须至少为 8 个字符',
  (v) => (v && /[A-Za-z]/.test(v)) || '密码必须至少包含一个字母',
  (v) => (v && /\d/.test(v)) || '密码必须至少包含一个数字',
  (v) => (v && /[!@#$%^&*(),.?":{}|<>]/.test(v)) || '密码必须至少包含一个特殊字符',
]
</script>
