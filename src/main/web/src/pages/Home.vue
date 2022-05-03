<template>
  <div v-show="firstPageOpacityText >= 0" class="flex flex-col absolute text-center w-full">
    <div class="lg:text-6xl text-4xl font-extrabold dark:text-white mt-10" ref="firstPageTitleText">Artificial
      FOOD
    </div>
    <div class="lg:text-7xl text-5xl font-extrabold dark:text-white" ref="firstPageSubtitleText">CHECK
      CALORIES FAST
    </div>
  </div>
  <div v-show="currentPage === 1" class="flex flex-col w-full bottom-28 z-10 absolute justify-center items-center">
    <div class="lg:text-xl text-lg font-bold dark:text-white">Scroll Down</div>
    <div class="">&#8595;</div>
  </div>
  <div v-show="currentPage === 2"
       class="flex flex-col w-full top-28 z-10 absolute text-center lg:text-xl text-lg font-bold dark:text-white">Scroll
    Up &#8593
  </div>
  <div v-show="secondPageOpacityText > 0" class="flex flex-col text-center w-full absolute z-10 bottom-56">
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
  let copyCerealBowlImageYPosition = 0;

  let firstPageTranslateTextY = 0;
  let secondPageTranslateTextY = 0;

  let fishBowlImageXPosition = 0;
  let fishBowlImageYPosition = 0;
  let startingPointFishBowlImageXPosition = 0;

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
  let scrollType = "steady"
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

    fishBowlImageXPosition = -cssWidth - 100;
    fishBowlImageYPosition = (cssHeight + 100) / 2;
    startingPointFishBowlImageXPosition = fishBowlImageXPosition;

    fruitBowlImageXPosition = -cssWidth - 100;
    fruitBowlImageYPosition = (cssHeight + 100) / 2;

    veggiesBowlImageXPosition = -cssWidth - 100;
    veggiesBowlImageYPosition = (cssHeight + 100) / 2;

    leftSideVeggiesImageXPosition = -cssWidth / 4;
    leftSideVeggiesImageYPosition = cssHeight / 3;

    rightSideVeggiesImageXPosition = cssWidth / 2;
    rightSideVeggiesImageYPosition = cssHeight / 2.5;

    if (cssWidth > 768) {
      cerealBowlImageHeight = cssWidth / 3 - cssWidth / 10;
      cerealBowlImageWidth = cssWidth / 3;
      cerealBowlImageXPosition = cssWidth / 2 - cssWidth / 6.3;
      cerealBowlImageYPosition = cssHeight - cssWidth / 9;

      leftSideVeggiesImage.src = '/leftSideVeggiesImage.png'
      leftSideVeggiesImage.onload = function () {
        context.drawImage(leftSideVeggiesImage, leftSideVeggiesImageXPosition, leftSideVeggiesImageYPosition, cssWidth * 1.5 / 2, cssHeight);
      }

      rightSideVeggiesImage.src = '/rightSideVeggiesImage.png'
      rightSideVeggiesImage.onload = function () {
        context.drawImage(rightSideVeggiesImage, rightSideVeggiesImageXPosition, rightSideVeggiesImageYPosition, cssWidth * 1.5 / 2, cssHeight);
      }
    } else {
      cerealBowlImageHeight = cssWidth / 2 - cssWidth / 10;
      cerealBowlImageWidth = cssWidth / 2;
      cerealBowlImageXPosition = cssWidth / 2 - cssWidth / 4;
      cerealBowlImageYPosition = cssHeight - cssWidth / 4;
    }
    copyCerealBowlImageYPosition = cerealBowlImageYPosition;


    cerealBowlImage.src = '/cerealBowlImage.png';
    cerealBowlImage.onload = function () {
      context.drawImage(cerealBowlImage, cerealBowlImageXPosition, cerealBowlImageYPosition, cerealBowlImageWidth, cerealBowlImageHeight);

    }
    fishBowlImage.src = '/fishBowlImage.png';
    fishBowlImage.onload = function () {
      context.drawImage(fishBowlImage, fishBowlImageXPosition, fishBowlImageYPosition, cssWidth / 4 + 100, cssWidth / 4)
    }
    fruitBowlImage.src = '/fruitBowlImage.png';
    fruitBowlImage.onload = function () {
      context.drawImage(fruitBowlImage, fruitBowlImageXPosition, fruitBowlImageYPosition, cssWidth / 4 + 100, cssWidth / 4)
    }
    veggiesBowlImage.src = '/veggiesBowlImage.png';
    veggiesBowlImage.onload = function () {
      context.drawImage(veggiesBowlImage, veggiesBowlImageXPosition, veggiesBowlImageYPosition, cssWidth / 4 + 100, cssWidth / 4)
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

    let mouseMiddleImageMoveX = 0;
    let mouseMiddleImageMoveY = 0;
    let mouseRightImageMoveX = 0;
    let mouseRightImageMoveY = 0;
    let mouseLeftImageMoveX = 0;
    let mouseLeftImageMoveY = 0;

    let topPosition = -100;
    let bottomPosition = cssHeight - cssWidth / 4
    let numberOfCallsForTransition = Math.abs(Math.floor((Math.abs(topPosition) - copyCerealBowlImageYPosition) / 10));
    let secondPageImageTransition = Math.abs(Math.abs(startingPointFishBowlImageXPosition) - Math.abs(cssWidth) / 2) / numberOfCallsForTransition
    if (cssWidth > 768) {
      mouseMiddleImageMoveX = (mouse.value.x * 4) / 250;
      mouseMiddleImageMoveY = (mouse.value.y * 4) / 250;

      mouseRightImageMoveX = -(mouse.value.x * 3) / 250;
      mouseRightImageMoveY = (mouse.value.y * 2) / 250;

      mouseLeftImageMoveX = +(mouse.value.x * 3) / 250;
      mouseLeftImageMoveY = (mouse.value.y * 2) / 250;
      bottomPosition = cssHeight - cssWidth / 9
      topPosition = -200;
    }
    let rightImageX = (mouse.value.x * 2) / 250;
    let rightImageY = (mouse.value.y * 2) / 250;

    context.clearRect(0, 0, cssWidth, cssHeight);
    if (cssWidth > 768) {
      context.drawImage(leftSideVeggiesImage, leftSideVeggiesImageXPosition + rightImageX, leftSideVeggiesImageYPosition + rightImageY, cssWidth * 1.5 / 2, cssHeight);
      context.drawImage(rightSideVeggiesImage, rightSideVeggiesImageXPosition + rightImageX, rightSideVeggiesImageYPosition + rightImageY, cssWidth * 1.5 / 2, cssHeight);
    }
    context.drawImage(cerealBowlImage, cerealBowlImageXPosition - mouseMiddleImageMoveX, cerealBowlImageYPosition + mouseMiddleImageMoveY, cerealBowlImageWidth, cerealBowlImageHeight);
    context.drawImage(fruitBowlImage, fruitBowlImageXPosition + mouseMiddleImageMoveX, fruitBowlImageYPosition + mouseMiddleImageMoveY, cssWidth / 4 + 100, cssWidth / 4)

    context.drawImage(fishBowlImage, fishBowlImageXPosition + mouseLeftImageMoveX, fishBowlImageYPosition + mouseLeftImageMoveY, cssWidth / 4 + 100, cssWidth / 4)
    context.drawImage(veggiesBowlImage, veggiesBowlImageXPosition + mouseRightImageMoveX, veggiesBowlImageYPosition + mouseRightImageMoveY, cssWidth / 4 + 100, cssWidth / 4)

    if (scrollType === "down" && cerealBowlImageYPosition >= topPosition) {
      cerealBowlImageYPosition -= 10;
      if (cssWidth > 768) {
        rightSideVeggiesImageXPosition += 10;
        leftSideVeggiesImageXPosition -= 10;
      }
      fishBowlImageXPosition += secondPageImageTransition
      fruitBowlImageXPosition += 2 * secondPageImageTransition - secondPageImageTransition / 2;
      veggiesBowlImageXPosition += 2 * secondPageImageTransition;
      scrollDownTextTransform();
    }
    if (scrollType === "up" && cerealBowlImageYPosition !== bottomPosition) {
      cerealBowlImageYPosition += 10;
      if (cssWidth > 768) {
        rightSideVeggiesImageXPosition -= 10;
        leftSideVeggiesImageXPosition += 10;
      }
      fishBowlImageXPosition -= secondPageImageTransition
      fruitBowlImageXPosition -= 2 * secondPageImageTransition - secondPageImageTransition / 2;
      veggiesBowlImageXPosition -= 2 * secondPageImageTransition;

      scrollUpTextTransform();
    }
    if (cerealBowlImageYPosition <= topPosition || cerealBowlImageYPosition === bottomPosition) {
      scrollType = "steady";
    }

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
