<template>
  <div v-show="firstPageOpacityText >= 0" class="flex flex-col absolute text-center w-full">
    <div class="lg:text-6xl text-4xl font-extrabold dark:text-white mt-10" ref="firstPageTitleText">Artificial
      FOOD
    </div>
    <div class="lg:text-7xl text-5xl font-extrabold dark:text-white" ref="firstPageSubtitleText">CHECK
      CALORIES FAST
    </div>
  </div>
  <div v-show="secondPageOpacityText > 0" class="flex flex-col text-center w-full absolute z-10 bottom-28">
    <div class="lg:text-6xl text-4xl font-extrabold dark:text-white mt-10" ref="secondPageTitleText">Over 100 foods
    </div>
    <div class="lg:text-7xl text-5xl font-extrabold dark:text-white" ref="secondPageSubtitleText">Upload image and see
      the
      results
    </div>
  </div>
  <canvas class="h-screen w-full relative" ref="canvas"></canvas>
</template>

<script setup>
import {onMounted, onUnmounted, ref} from 'vue'

const canvas = ref(null);
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
  let leftSideVeggiesImageXPosition = 0;
  let leftSideVeggiesImageYPosition = 0;

  let rightSideVeggiesImageXPosition = 0;
  let rightSideVeggiesImageYPosition = 0;

  let cerealBowlImageXPosition = 0;
  let cerealBowlImageYPosition = 0;
  let cerealBowlImageHeight = 0;
  let cerealBowlImageWidth = 0;

  let firstPageTranslateTextY = 0;
  let secondPageTranslateTextY = 0;

  let fishBowlImageXPosition = 0;
  let fishBowlImageYPosition = 0;

  let fruitBowlImageXPosition = 0;
  let fruitBowlImageYPosition = 0;

  let veggiesBowlImageXPosition = 0;
  let veggiesBowlImageYPosition = 0;

  let cssWidth = null;
  let cssHeight = null;
  let dpr = null;
  let pxWidth = null;
  let pxHeight = null;
  let context = null;
  let animation = null;
  let scrollType = "steady";
  let secondPageImageTransition = null;
  const leftSideVeggiesImage = new Image();
  const rightSideVeggiesImage = new Image();
  const cerealBowlImage = new Image();
  const fishBowlImage = new Image();
  const fruitBowlImage = new Image();
  const veggiesBowlImage = new Image();

  function scrollDownTextTransform() {
    firstPageTranslateTextY--;
    firstPageOpacityText.value -= 0.02;
    firstPageTitleText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageTitleText.value.style.opacity = firstPageOpacityText.value;
    firstPageSubtitleText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageSubtitleText.value.style.opacity = firstPageOpacityText.value;

    secondPageOpacityText.value += 0.02;
    secondPageTranslateTextY--;
    secondPageTitleText.value.style.transform = "translateY(" + secondPageTranslateTextY + "px)";
    secondPageTitleText.value.style.opacity = secondPageOpacityText.value;
    secondPageSubtitleText.value.style.transform = "translateY(" + secondPageTranslateTextY + "px)";
    secondPageSubtitleText.value.style.opacity = secondPageOpacityText.value;
  }

  function scrollUpTextTransform() {
    firstPageTranslateTextY++;
    firstPageOpacityText.value += 0.02;
    firstPageTitleText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageTitleText.value.style.opacity = firstPageOpacityText.value;
    firstPageSubtitleText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageSubtitleText.value.style.opacity = firstPageOpacityText.value;


    secondPageOpacityText.value -= 0.02
    secondPageTranslateTextY++;
    secondPageTitleText.value.style.transform = "translateY(" + secondPageTranslateTextY + "px)";
    secondPageTitleText.value.style.opacity = secondPageOpacityText.value;
    secondPageSubtitleText.value.style.transform = "translateY(" + secondPageTranslateTextY + "px)";
    secondPageSubtitleText.value.style.opacity = secondPageOpacityText.value;
  }

  function redraw() {
    dpr = window.devicePixelRatio;
    cssWidth = canvas.value.clientWidth;
    cssHeight = canvas.value.clientHeight;
    pxWidth = Math.round(dpr * cssWidth);
    pxHeight = Math.round(dpr * cssHeight);
    canvas.value.width = pxWidth;
    canvas.value.height = pxHeight;
    context = canvas.value.getContext("2d");
    context.scale(dpr, dpr);

    fishBowlImageXPosition = -canvas.value.width - 100;
    fishBowlImageYPosition = (canvas.value.height + 100) / 2;

    fruitBowlImageXPosition = -canvas.value.width - 100;
    fruitBowlImageYPosition = (canvas.value.height + 100) / 2;

    veggiesBowlImageXPosition = -canvas.value.width - 100;
    veggiesBowlImageYPosition = (canvas.value.height + 100) / 2;

    leftSideVeggiesImageXPosition = -cssWidth/4;
    leftSideVeggiesImageYPosition =  cssHeight/3;

    rightSideVeggiesImageXPosition = cssWidth/2;
    rightSideVeggiesImageYPosition =  cssHeight/2.5;

    if (cssWidth > 1000) {
      cerealBowlImageHeight = cssWidth / 4;
      cerealBowlImageWidth = cssWidth / 4;
      cerealBowlImageXPosition = cssWidth / 2 - cssWidth / 7.8;
      cerealBowlImageYPosition = cssHeight - cssWidth / 9;
    } else {
      cerealBowlImageHeight = cssWidth / 2;
      cerealBowlImageWidth = cssWidth /2;
      cerealBowlImageXPosition = cssWidth / 2 - cssWidth / 4;
      cerealBowlImageYPosition = cssHeight - cssWidth / 4;
    }

    leftSideVeggiesImage.src = '/leftSideVeggiesImage.png'
    leftSideVeggiesImage.onload = function () {
      context.drawImage(leftSideVeggiesImage, leftSideVeggiesImageXPosition, leftSideVeggiesImageYPosition, cssWidth * 1.5 / 2, cssHeight);
    }

    rightSideVeggiesImage.src = '/rightSideVeggiesImage.png'
    rightSideVeggiesImage.onload = function () {
      context.drawImage(rightSideVeggiesImage, rightSideVeggiesImageXPosition, rightSideVeggiesImageYPosition, cssWidth * 1.5 / 2, cssHeight);
    }

    cerealBowlImage.src = '/cerealBowlImage.png';
    cerealBowlImage.onload = function () {
      context.drawImage(cerealBowlImage, cerealBowlImageXPosition, cerealBowlImageYPosition, cerealBowlImageWidth, cerealBowlImageHeight);

    }

    fishBowlImage.src = '/fishBowlImage.png';
    fishBowlImage.onload = function () {
      context.drawImage(fishBowlImage, fishBowlImageXPosition, fishBowlImageYPosition, 400, 300)
    }
    fruitBowlImage.src = '/fruitBowlImage.png';
    fruitBowlImage.onload = function () {
      context.drawImage(fruitBowlImage, fruitBowlImageXPosition, fruitBowlImageYPosition, 400, 300)
    }
    veggiesBowlImage.src = '/veggiesBowlImage.png';
    veggiesBowlImage.onload = function () {
      context.drawImage(veggiesBowlImage, veggiesBowlImageXPosition, veggiesBowlImageYPosition, 400, 300)
    }
  }

  redraw();
  const resizeObserver = new ResizeObserver(() => redraw())
  resizeObserver.observe(canvas.value)

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

    let cerealBowlImageMoveX = 0;
    let cerealBowlImageMoveY = 0;
    let topPosition = -100;
    let bottomPosition = cssHeight - cssWidth / 4

    if (cssWidth > 1000) {
      cerealBowlImageMoveX = (mouse.value.x * 4) / 250;
      cerealBowlImageMoveY = (mouse.value.y * 4) / 250;
      bottomPosition = cssHeight - cssWidth / 9
      topPosition = -200;
    }
    let rightImageX = (mouse.value.x * 2) / 250;
    let rightImageY = (mouse.value.y * 2) / 250;


    context.clearRect(0, 0, cssWidth, cssHeight);
    context.drawImage(leftSideVeggiesImage, leftSideVeggiesImageXPosition, leftSideVeggiesImageYPosition + rightImageY, cssWidth * 1.5 / 2, cssHeight);
    context.drawImage(rightSideVeggiesImage, rightSideVeggiesImageXPosition + rightImageX, rightSideVeggiesImageYPosition + rightImageY, cssWidth * 1.5 / 2, cssHeight);
    context.drawImage(cerealBowlImage, cerealBowlImageXPosition + cerealBowlImageMoveX, cerealBowlImageYPosition + cerealBowlImageMoveY, cerealBowlImageWidth, cerealBowlImageHeight);
    context.drawImage(fishBowlImage, fishBowlImageXPosition, fishBowlImageYPosition, 400, 300)
    context.drawImage(fruitBowlImage, fruitBowlImageXPosition, fruitBowlImageYPosition, 400, 300)
    context.drawImage(veggiesBowlImage, veggiesBowlImageXPosition, veggiesBowlImageYPosition, 400, 300)
    if (scrollType === "down" && cerealBowlImageYPosition >= topPosition) {
      cerealBowlImageYPosition -= 10;
      rightSideVeggiesImageXPosition += 10;
      leftSideVeggiesImageXPosition -= 10;
      fishBowlImageXPosition += secondPageImageTransition
      fruitBowlImageXPosition += 2 * secondPageImageTransition - 1;
      veggiesBowlImageXPosition += 3 * secondPageImageTransition - 2;
      scrollDownTextTransform();
    }
    if (scrollType === "up" && cerealBowlImageYPosition !== bottomPosition) {
      cerealBowlImageYPosition += 10;
      rightSideVeggiesImageXPosition -= 10;
      leftSideVeggiesImageXPosition += 10;
      fishBowlImageXPosition -= secondPageImageTransition
      fruitBowlImageXPosition -= 2 * secondPageImageTransition - 1;
      veggiesBowlImageXPosition -= 3 * secondPageImageTransition - 2;

      scrollUpTextTransform();
    }
    if (cerealBowlImageYPosition <= topPosition || cerealBowlImageYPosition === bottomPosition) {
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
    resizeObserver.disconnect()
  })
})


</script>
