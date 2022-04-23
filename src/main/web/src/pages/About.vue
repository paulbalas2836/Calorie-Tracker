<template>
  <div class="overflow-hidden flex justify-center">
<!--    <div class="bg-light-home h-[36rem] w-9/12 absolute z-0"> </div>-->
    <div class="absolute z-10 text-7xl font-extrabold dark:text-white" ref="titleText">Arti FOOD</div>
    <div class="absolute z-10 text-9xl font-extrabold mt-20 dark:text-white" ref="bigText">GET CALORIES FAST</div>
    <div class="absolute z-10 text-9xl font-extrabold mt-20 dark:text-white" ref="normalText">GET CALORIES FAST</div>
    <img src="/FishImage.png" alt="" ref="fishImage" class="absolute z-10">
    <img src="/AvogadoImage.png" alt="" class="absolute z-10">
    <img src="/BrocoliImage.png" alt="" class="absolute z-10">
    <img src="/BananaImage.png" alt="" class="absolute z-10">
<!--      <canvas ref="canvas" class="h-screen w-full absolute z-10"></canvas>-->
  </div>
</template>

<script setup>
import {darkMode} from "../SealConstants";
import {onMounted, ref, watch} from 'vue'

const fishImage = ref(null);
const titleText = ref(null);
const bigText = ref(null);
const normalText = ref(null);

addEventListener("mousemove", parallax);

function parallax(e){
  let x = (e.clientX * 5)/250;
  let y = (e.clientY*4)/250;
  fishImage.value.style.transform = "translateX(" + x + "px) translateY(" + y + "px)";
  titleText.value.style.transform = "translateX(" + (e.clientX * 2)/250 + "px) translateY(" + e.clientY/250 + "px)";
  bigText.value.style.transform = "translateX(" + (e.clientX * 2)/250 + "px) translateY(" + e.clientY/250 + "px)";
  normalText.value.style.transform = "translateX(" + (e.clientX * 2)/250 + "px) translateY(" + e.clientY/250 + "px)";

}

</script>
