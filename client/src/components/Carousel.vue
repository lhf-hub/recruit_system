<template>
  <VaCarousel autoscroll autoscroll-interval="4000" indicators="infinite" style="object-fit: cover; height: 45vh" v-model="value" :items="items" :indicators="false" />
</template>

<script>
export default {
  data() {
    return {
      value: 0,
      items: [
        '/home2.jpg',
        '/home3.png',
        '/home1.png',
      ],
    }
  },
}
</script>
