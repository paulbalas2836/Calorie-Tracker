<template>
  <div class="min-h-full w-full">
    <canvas class="min-h-screen w-full absolute z-0" ref="canvas"></canvas>
    <navbar></navbar>
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>


<script setup>
import Navbar from "../components/navbar/Navbar.vue"
import {onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";

const canvas = ref(null)
const homePage = ref(false)

const currentRouteName = useRoute();
watch(currentRouteName, () => {
  homePage.value = currentRouteName.name === 'Home';
})


onMounted(() => {
  let cssWidth = null;
  let cssHeight = null;
  let dpr = null;
  let pxWidth = null;
  let pxHeight = null;

  function redraw() {
    dpr = window.devicePixelRatio
    cssWidth = canvas.value.clientWidth
    cssHeight = canvas.value.clientHeight
    pxWidth = Math.round(dpr * cssWidth)
    pxHeight = Math.round(dpr * cssHeight)
    canvas.value.width = pxWidth
    canvas.value.height = pxHeight
    const context = canvas.value.getContext("2d")
    context.scale(dpr, dpr)
    const backgroundImage = new Image()
    backgroundImage.src = '/backgroundImage.png'
    backgroundImage.onload = function () {
      context.drawImage(backgroundImage, 0, 0, cssWidth, cssHeight);
    }
  }
  new ResizeObserver(() => redraw()).observe(canvas.value)
})


</script>
