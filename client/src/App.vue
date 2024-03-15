<template>
  <RouterView />
  <Alert :type="alertType" :value="alertShow">{{ alertContent }}</Alert>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import Alert from './components/alert/Alert.vue'
import bus from 'vue3-eventbus'
interface AlertType {
  type: string
  content: string
}

const alertShow = ref(false)
const alertType = ref('success')
const alertContent = ref('')

bus.on('alert', (data: AlertType) => {
  alertType.value = data.type
  alertContent.value = data.content
  alertShow.value = true
  setTimeout(() => {
    alertShow.value = false
  }, 600)
})

document.addEventListener(
  'mousewheel',
  function (e) {
    e = e || window.event
    if ((e.wheelDelta && event.ctrlKey) || e.detail) {
      event.preventDefault()
    }
  },
  { capture: false, passive: false },
)
document.addEventListener(
  'keydown',
  function (event) {
    if (
      (event.ctrlKey === true || event.metaKey === true) &&
      (event.keyCode === 61 ||
        event.keyCode === 107 ||
        event.keyCode === 173 ||
        event.keyCode === 109 ||
        event.keyCode === 187 ||
        event.keyCode === 189)
    ) {
      event.preventDefault()
    }
  },
  false,
)
</script>

<style lang="scss">
@import 'scss/main.scss';

#app {
  font-family: 'Inter', Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

body {
  margin: 0;
  min-width: 20rem;
}
</style>
