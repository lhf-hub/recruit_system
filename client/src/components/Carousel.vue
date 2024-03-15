<template>
  <VaCarousel style="object-fit: cover; height: 45vh" v-model="value" :items="items" :indicators="false" />
</template>

<script>
export default {
  data() {
    return {
      value: 0,
      items: [
        '/home1.png',
        '/home2.jpg',
        '/home3.png',
      ],
    }
  },
}
</script>
