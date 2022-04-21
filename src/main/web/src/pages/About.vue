<template>
  <div class="overflow-hidden py-10 px-8 flex justify-center">
    <div class="bg-light-home h-[36rem] w-9/12 absolute z-0"> </div>
      <canvas ref="canvas" class="h-screen w-full absolute z-10"></canvas>
  </div>
</template>

<script setup>
import {darkMode} from "../SealConstants";
import {onMounted, ref, watch} from 'vue'


const canvas = ref(null)
onMounted(() => {
  let context = null;
  let dpr = window.devicePixelRatio;
  let cssWidth = canvas.value.clientWidth;
  let cssHeight = canvas.value.clientHeight;
  let pxWidth = Math.round(dpr * cssWidth);
  let pxHeight = Math.round(dpr * cssHeight);
  let fade = 0;
  let shouldAnimate = true;
  let animation = null;
  let handY = 1000;
  const robotImg = new Image();
  robotImg.src = '/hand.png'

  function redraw() {
    canvas.value.width = pxWidth;
    canvas.value.height = pxHeight;
    context = canvas.value.getContext("2d");
    context.scale(dpr, dpr);
  }

  redraw()

  function animate() {
    if (animation) {
      window.cancelAnimationFrame(animation)
    }
    animation = requestAnimationFrame(animate);

    if(shouldAnimate) {
      context.clearRect(0, 0, cssWidth, cssHeight);
      context.drawImage(robotImg, 0, handY, 600, 700);
      handY -= 10;
    }
    if(handY === 0){
      handY = 1000
      shouldAnimate = false
    }

  }

  watch(darkMode, () => {
    animate()
    shouldAnimate = true
  })
})
</script>
