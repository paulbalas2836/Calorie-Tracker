<template>
  <div v-show="scrollUpOpacityText >= 0" class="flex justify-center">
    <div class="absolute z-10 lg:text-6xl text-4xl font-extrabold dark:text-white mt-10" ref="titleText">Artificial
      FOOD
    </div>
    <div class="absolute z-10 lg:text-7xl text-5xl font-extrabold mt-28 dark:text-white" ref="subtitleText">CHECK
      CALORIES FAST
    </div>
  </div>
  <canvas class="min-h-screen w-full absolute " ref="canvas"></canvas>
</template>

<script setup>
import {darkMode} from "../SealConstants";
import {onMounted, onUnmounted, ref, watch} from 'vue'
import Navbar from "../components/navbar/Navbar.vue"

const canvas = ref(null);
const savedContext = ref(null);
const mouse = ref({x: null, y: null})
const titleText = ref(null);
const subtitleText = ref(null);
const scrollUpOpacityText = ref(1);

function getMousePosition(event) {
  mouse.value.x = event.clientX
  mouse.value.y = event.clientY
}


onMounted(() => {
  let leftImageXPosition = 0;
  let leftImageYPosition = 100;

  let rightImageXPosition = 0;
  let rightImageYPosition = 100;

  let middleImageXPosition = 0;
  let middleImageYPosition = 100;

  let scrollUpTranslateTextY = 0;

  let scrollDownTranslateTextY = 0;
  let scrollDownOpacityText = 0;

  let cssWidth = null;
  let cssHeight = null;
  let dpr = null;
  let pxWidth = null;
  let pxHeight = null;
  let context = null;
  let animation = null;
  let scrollType = "steady";
  const leftImage = new Image();
  const rightImage = new Image();
  const middleImage = new Image();

  function redraw() {
    dpr = window.devicePixelRatio
    cssWidth = canvas.value.clientWidth
    cssHeight = canvas.value.clientHeight
    pxWidth = Math.round(dpr * cssWidth)
    pxHeight = Math.round(dpr * cssHeight)
    canvas.value.width = pxWidth
    canvas.value.height = pxHeight
    context = canvas.value.getContext("2d")
    context.scale(dpr, dpr)
    savedContext.value = context

    leftImage.src = '/leftFoodsImage.png';
    leftImage.onload = function () {
      context.drawImage(leftImage, leftImageXPosition, leftImageYPosition, cssWidth, cssHeight);
    }

    rightImage.src = '/rightFoodsImage.png';
    rightImage.onload = function () {
      context.drawImage(rightImage, rightImageXPosition, rightImageYPosition, cssWidth, cssHeight);
    }

    middleImage.src = '/CerealImage.png';
    middleImage.onload = function () {
      context.drawImage(middleImage, middleImageXPosition, middleImageYPosition, cssWidth, cssHeight);
    }
  }

  redraw();
  // const resizeObserver = new ResizeObserver(() => redraw())
  // resizeObserver.observe(canvas.value)

  function checkScroll(event) {
    if (event.wheelDelta >= 0 && scrollType === "steady")
      scrollType = "up"
    if (event.wheelDelta <= 0 && scrollType === "steady")
      scrollType = "down"
  }


  function parallax() {
    if (animation) {
      window.cancelAnimationFrame(animation)
    }
    animation = requestAnimationFrame(parallax)
    let bowlImageX = (mouse.value.x * 4) / 250;
    let bowlImageY = (mouse.value.y * 4) / 250;

    let rightImageX = (mouse.value.x * 2) / 250;
    let rightImageY = (mouse.value.y * 2) / 250;


    context.clearRect(0, 0, cssWidth, cssHeight);
    context.drawImage(leftImage, leftImageXPosition, leftImageYPosition + rightImageY, cssWidth, cssHeight);
    context.drawImage(rightImage, rightImageXPosition + rightImageX, rightImageYPosition + rightImageY, cssWidth, cssHeight);
    context.drawImage(middleImage, middleImageXPosition + bowlImageX, middleImageYPosition + bowlImageY, cssWidth, cssHeight);

    if (scrollType === "down" && middleImageYPosition !== -600) {
      middleImageYPosition -= 10;
      rightImageXPosition += 10;
      leftImageXPosition -= 10;
      scrollUpTranslateTextY--;
      scrollUpOpacityText.value -= 0.02;
      titleText.value.style.transform = "translateY(" + scrollUpTranslateTextY + "px)";
      titleText.value.style.opacity = scrollUpOpacityText.value;
      subtitleText.value.style.transform = "translateY(" + scrollUpTranslateTextY + "px)";
      subtitleText.value.style.opacity = scrollUpOpacityText.value;
    }

    if (scrollType === "up" && middleImageYPosition !== 50) {
      middleImageYPosition += 10;
      rightImageXPosition -= 10;
      leftImageXPosition += 10;
      scrollUpTranslateTextY++;
      scrollUpOpacityText.value += 0.02;
      titleText.value.style.transform = "translateY(" + scrollUpTranslateTextY + "px)";
      titleText.value.style.opacity = scrollUpOpacityText.value;
      subtitleText.value.style.transform = "translateY(" + scrollUpTranslateTextY + "px)";
      subtitleText.value.style.opacity = scrollUpOpacityText.value;
    }

    if (middleImageYPosition === -600 || middleImageYPosition === 50) {
      scrollType = "steady";
    }

    //
    // rightFoodsImage.value.style.transform = "translateX(" + rightImageX + "px) translateY(" + rightImageY + "px)";
    // leftFoodsImage.value.style.transform = "translateY(" + bowlImageY + "px)";

    // titleText.value.style.transform = "translateX(" + (e.clientX * 2) / 250 + "px) translateY(" + e.clientY / 250 + "px)";
    // bigText.value.style.transform = "translateX(" + (e.clientX * 2) / 250 + "px) translateY(" + e.clientY / 250 + "px)";
    // normalText.value.style.transform = "translateX(" + (e.clientX * 2) / 250 + "px) translateY(" + e.clientY / 250 + "px)";

  }

  parallax()

  window.addEventListener('mousewheel', checkScroll);
  addEventListener("mousemove", getMousePosition);
  onUnmounted(() => {
    removeEventListener("mousemove", getMousePosition);
    removeEventListener("mousewheel", checkScroll);
    // resizeObserver.disconnect()
  })
})


</script>
