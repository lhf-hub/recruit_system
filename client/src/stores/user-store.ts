import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => {
    return {
      userId: 99987875499,
      password: '',
      isLogin: false,
      role: '',
    }
  },

  actions: {
    login() {
      this.isLogin = true
    },
    logout() {
      this.isLogin = false
    },
    setRole(role: string) {
      this.role = role
    },
    setUserId(userId: string) {
      this.userId = userId
    },
    setPassword(password: string) {
      this.password = password
    },
  },
})
