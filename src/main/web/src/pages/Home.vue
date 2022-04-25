<template>
  <div class="overflow-hidden flex justify-center">
    <div class="absolute z-10 lg:text-6xl text-4xl font-extrabold dark:text-white mt-10" ref="titleText">Artificial FOOD</div>
    <div class="absolute z-10 lg:text-7xl text-5xl font-extrabold mt-28 dark:text-white" ref="bigText">CHECK CALORIES FAST</div>
    <canvas class="min-h-screen w-full absolute z-10" ref="canvas" ></canvas>
<!--    <img src="/CerealImage.png" alt="" ref="cerealImage" class="absolute z-10">-->
<!--    <img src="/leftFoodsImage.png" alt="" ref="leftFoodsImage" class="absolute z-10">-->
<!--    <img src="/rightFoodsImage.png" alt="" ref="rightFoodsImage" class="absolute z-10">-->
  </div>
</template>

<script setup>
import {darkMode} from "../SealConstants";
import {onMounted, onUnmounted, ref, watch} from 'vue'

const canvas = ref(null);
const savedContext= ref(null);
const titleText = ref(null);
const bigText = ref(null);
const normalText = ref(null);



onMounted(()=>{
  let cssWidth = null;
  let cssHeight = null;
  let dpr = null;
  let pxWidth = null;
  let pxHeight = null;
  const leftImage = new Image()

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
    savedContext.value = context

    leftImage.src = '/leftFoodsImage.png'
    leftImage.onload = function () {
      context.drawImage(leftImage, 0, 50, cssWidth, cssHeight);
    }
  }
  new ResizeObserver(() => redraw()).observe(canvas.value)

  function parallax(e) {
    let bowlImageX = (e.clientX * 3) / 250;
    let bowlImageY = (e.clientY * 2) / 250;

    let rightImageX = (e.clientX * 2) / 250;
    let rightImageY = (e.clientY * 3) / 250;


    
    rightFoodsImage.value.style.transform = "translateX(" + rightImageX + "px) translateY(" + rightImageY + "px)";
    leftFoodsImage.value.style.transform = "translateY(" + bowlImageY + "px)";

    // titleText.value.style.transform = "translateX(" + (e.clientX * 2) / 250 + "px) translateY(" + e.clientY / 250 + "px)";
    // bigText.value.style.transform = "translateX(" + (e.clientX * 2) / 250 + "px) translateY(" + e.clientY / 250 + "px)";
    // normalText.value.style.transform = "translateX(" + (e.clientX * 2) / 250 + "px) translateY(" + e.clientY / 250 + "px)";

  }

  addEventListener("mousemove", parallax);

  onUnmounted(() => {
    removeEventListener("mousemove", parallax);
  })
})



</script>
