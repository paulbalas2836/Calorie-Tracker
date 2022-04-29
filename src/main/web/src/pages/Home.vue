<template>
  <div v-show="firstPageOpacityText >= 0" class="flex flex-col absolute z-10 text-center w-full">
    <div class="lg:text-6xl text-4xl font-extrabold dark:text-white mt-10" ref="firstPageTitleText">Artificial
      FOOD
    </div>
    <div class="lg:text-7xl text-5xl font-extrabold dark:text-white" ref="firstPageSubtitleText">CHECK
      CALORIES FAST
    </div>
  </div>
  <div v-show="secondPageOpacityText > 0" class="flex flex-col text-center w-full absolute z-10 bottom-52">
    <div class="lg:text-6xl text-4xl font-extrabold dark:text-white mt-10" ref="secondPageTitleText">Over 100 foods
    </div>
    <div class="lg:text-7xl text-5xl font-extrabold dark:text-white" ref="secondPageSubtitleText">Upload image and see the
      results
    </div>
  </div>
  <canvas class="min-h-screen w-full relative" ref="canvas"></canvas>
</template>

<script setup>
import {darkMode} from "../SealConstants";
import {onMounted, onUnmounted, ref, watch} from 'vue'
import Navbar from "../components/navbar/Navbar.vue"

const canvas = ref(null);
const savedContext = ref(null);
const mouse = ref({x: null, y: null})
const firstPageTitleText = ref(null);
const firstPageSubtitleText = ref(null);
const currentPage = ref(1);
const secondPageTitleText = ref(null);
const secondPageSubtitleText = ref(null);
const firstPageOpacityText = ref(1);
const secondPageOpacityText = ref(0);

function getMousePosition(event) {
  mouse.value.x = event.clientX
  mouse.value.y = event.clientY
}


onMounted(() => {
  let leftImageXPosition = -400;
  let leftImageYPosition = 200;

  let rightImageXPosition = 750;
  let rightImageYPosition = 200;

  let middleImageXPosition = 0;
  let middleImageYPosition = 100;

  let firstPageTranslateTextY = 0;
  let secondPageTranslateTextY = 0;

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

  function scrollDownTextTransform(){
    firstPageTranslateTextY--;
    firstPageOpacityText.value -= 0.02;
    firstPageTitleText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageTitleText.value.style.opacity = firstPageOpacityText.value;
    firstPageSubtitleText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageSubtitleText.value.style.opacity = firstPageOpacityText.value;

    secondPageOpacityText.value += 0.02;
    secondPageTranslateTextY ++;
    secondPageTitleText.value.style.transform = "translateY("+secondPageTranslateTextY + "px)";
    secondPageTitleText.value.style.opacity = secondPageOpacityText.value;
    secondPageSubtitleText.value.style.transform = "translateY("+secondPageTranslateTextY + "px)";
    secondPageSubtitleText.value.style.opacity = secondPageOpacityText.value;
  }

  function scrollUpTextTransform(){
    firstPageTranslateTextY++;
    firstPageOpacityText.value += 0.02;
    firstPageTitleText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageTitleText.value.style.opacity = firstPageOpacityText.value;
    firstPageSubtitleText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageSubtitleText.value.style.opacity = firstPageOpacityText.value;


    secondPageOpacityText.value -= 0.02
    secondPageTranslateTextY --;
    secondPageTitleText.value.style.transform = "translateY("+secondPageTranslateTextY + "px)";
    secondPageTitleText.value.style.opacity = secondPageOpacityText.value;
    secondPageSubtitleText.value.style.transform = "translateY("+secondPageTranslateTextY + "px)";
    secondPageSubtitleText.value.style.opacity = secondPageOpacityText.value;
  }

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
      context.drawImage(leftImage, leftImageXPosition, leftImageYPosition, cssWidth*1.5/2, cssHeight);
      // console.log(this.width, this.height, cssWidth/2, cssHeight/2);
    }

    rightImage.src = '/rightFoodsImage.png';
    rightImage.onload = function () {
      context.drawImage(rightImage, rightImageXPosition, rightImageYPosition, cssWidth*1.5/2, cssHeight);
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
    if (event.wheelDelta >= 0 && scrollType === "steady" && currentPage.value === 2) {
      scrollType = "up"
      currentPage.value = 1
    }
    if (event.wheelDelta <= 0 && scrollType === "steady" && currentPage.value === 1) {
      scrollType = "down"
      currentPage.value = 2
    }
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
    context.drawImage(leftImage, leftImageXPosition, leftImageYPosition + rightImageY, cssWidth*1.5/2, cssHeight);
    context.drawImage(rightImage, rightImageXPosition + rightImageX, rightImageYPosition + rightImageY, cssWidth*1.5/2, cssHeight);
    context.drawImage(middleImage, middleImageXPosition + bowlImageX, middleImageYPosition + bowlImageY, cssWidth, cssHeight);
    if (scrollType === "down" && middleImageYPosition !== -600) {
      middleImageYPosition -= 10;
      rightImageXPosition += 10;
      leftImageXPosition -= 10;

      scrollDownTextTransform();
    }

    if (scrollType === "up" && middleImageYPosition !== 100) {
      middleImageYPosition += 10;
      rightImageXPosition -= 10;
      leftImageXPosition += 10;

      scrollUpTextTransform();
    }

    if (middleImageYPosition === -600 || middleImageYPosition === 100) {
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
