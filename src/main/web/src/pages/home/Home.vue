<template>
  <div v-show="firstPageOpacityText >= 0" class="flex flex-col absolute text-center w-full top-36" ref="firstPageText">
    <div class="lg:text-6xl text-3xl font-extrabold mt-10">Artificial
      FOOD
    </div>
    <div class="lg:text-7xl text-5xl font-extrabold">CHECK
      CALORIES FAST
    </div>
  </div>
  <div v-show="secondPageOpacityText > 0" class="flex flex-col text-center w-full absolute top-64"
       ref="secondPageText">
    <div class="lg:text-4xl text-3xl font-extrabold mt-10">
      Tracking calories has never been easier
    </div>
    <div class="lg:text-4xl text-3xl font-extrabold">60+ foods
      that the AI can identify
    </div>
  </div>
  <canvas class="h-screen w-full relative" ref="canvas"></canvas>
</template>

<script setup>
import {onMounted, onUnmounted, ref} from 'vue'
import {checkIfMobile} from "../../utils/Functions";

const canvas = ref(null);
const mouse = ref({x: null, y: null})
const firstPageText = ref(null);
const secondPageText = ref(null);
const currentPage = ref(1);
const firstPageOpacityText = ref(1);
const secondPageOpacityText = ref(0);
let xDown = null;
let yDown = null;
const scrollType = ref("steady");

function getMousePosition(event) {
  mouse.value.x = event.clientX
  mouse.value.y = event.clientY
}


function getTouches(event) {
  return event.touches || event.originalEvent.touches;
}

function handleTouchStart(event) {
  const firstTouch = getTouches(event)[0];
  xDown = firstTouch.clientX;
  yDown = firstTouch.clientY;
}

function handleTouchMove(event) {
  if (!xDown || !yDown) {
    return;
  }
  let xUp = event.touches[0].clientX;
  let yUp = event.touches[0].clientY;

  let xDiff = xDown - xUp;
  let yDiff = yDown - yUp;

  if (Math.abs(xDiff) < Math.abs(yDiff)) {
    if (yDiff > 0) {
      scrollType.value = "down";
    } else scrollType.value = "up";
  }

  xDown = null;
  yDown = null;
}

function checkScroll(event) {
  if (event.wheelDelta >= 0 && scrollType.value === "steady" && currentPage.value === 2) {
    scrollType.value = "up"
    currentPage.value = 1
  }
  if (event.wheelDelta <= 0 && scrollType.value === "steady" && currentPage.value === 1) {
    scrollType.value = "down"
    currentPage.value = 2
  }
}

onMounted(() => {
  const leftSideVeggiesImage = new Image();
  leftSideVeggiesImage.src = 'home_page_images/leftSideVeggies.png'

  const rightSideVeggiesImage = new Image();
  rightSideVeggiesImage.src = 'home_page_images//rightSideVeggies.png'

  const cerealBowlImage = new Image();
  cerealBowlImage.src = 'home_page_images//cerealBowl.png';

  const fishBowlImage = new Image();
  fishBowlImage.src = 'home_page_images//fishBowl.png';

  const fruitBowlImage = new Image();
  fruitBowlImage.src = 'home_page_images//fruitBowl.png';

  const veggiesBowlImage = new Image();
  veggiesBowlImage.src = 'home_page_images//veggiesBowl.png';


  let leftSideVeggiesObject = {x: 0, y: 0, height: 0, width: 0};
  let rightSideVeggiesObject = {x: 0, y: 0, height: 0, width: 0};
  let cerealBowlObject = {x: 0, y: 0, height: 0, width: 0};
  let fruitBowlObject = {x: 0, y: 0, height: 0, width: 0};
  let fishBowlObject = {x: 0, y: 0, height: 0, width: 0};
  let veggiesBowlObject = {x: 0, y: 0, height: 0, width: 0};

  let mouseMiddleImageMovement = {x: 0, y: 0};
  let mouseRightImageMovement = {x: 0, y: 0};
  let mouseLeftImageMovement = {x: 0, y: 0};

  let numberOfCallsForTransition = 0;
  let secondPageImageTransition = 0;

  let topPosition = 0;
  let bottomPosition = 0;
  const mediumScreenWidth = 768;

  let firstPageTranslateTextY = 0;
  let secondPageTranslateTextY = 0;


  let cssWidth = null;
  let cssHeight = null;
  let dpr = null;
  let pxWidth = null;
  let pxHeight = null;
  let context = null;
  let animation = null;

  function firstPageScrollDownTextTransform() {
    firstPageTranslateTextY--;
    firstPageOpacityText.value -= 0.02;
    firstPageText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageText.value.style.opacity = firstPageOpacityText.value;
  }

  function secondPageScrollDownTextTransform() {
    secondPageOpacityText.value += 0.02;
    secondPageTranslateTextY--;
    secondPageText.value.style.transform = "translateY(" + secondPageTranslateTextY + "px)";
    secondPageText.value.style.opacity = secondPageOpacityText.value;
  }

  function firstPageScrollUpTextTransform() {
    firstPageTranslateTextY++;
    firstPageOpacityText.value += 0.02;
    firstPageText.value.style.transform = "translateY(" + firstPageTranslateTextY + "px)";
    firstPageText.value.style.opacity = firstPageOpacityText.value;
  }

  function secondPageScrollUpTextTransform() {
    secondPageOpacityText.value -= 0.02
    secondPageTranslateTextY++;
    secondPageText.value.style.transform = "translateY(" + secondPageTranslateTextY + "px)";
    secondPageText.value.style.opacity = secondPageOpacityText.value;
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

    fishBowlObject = {x: -cssWidth - 100, y: (cssHeight + 100) / 2, width: cssWidth / 4 + 100, height: cssWidth / 4};
    fruitBowlObject = {x: -cssWidth - 100, y: (cssHeight + 100) / 2, width: cssWidth / 4 + 100, height: cssWidth / 4};
    veggiesBowlObject = {x: -cssWidth - 100, y: (cssHeight + 100) / 2, width: cssWidth / 4 + 100, height: cssWidth / 4};
    leftSideVeggiesObject = {x: -cssWidth / 4, y: cssHeight / 3, width: cssWidth * 1.5 / 2, height: cssHeight};
    rightSideVeggiesObject = {x: cssWidth / 2, y: cssHeight / 2.5, width: cssWidth * 1.5 / 2, height: cssHeight};

    if (cssWidth > mediumScreenWidth) {
      bottomPosition = cssHeight - cssWidth / 9
      topPosition = -200;
      cerealBowlObject = {
        x: cssWidth / 2 - cssWidth / 6.3,
        y: cssHeight - cssWidth / 9,
        width: cssWidth / 3,
        height: cssWidth / 3 - cssWidth / 10
      };
    } else {
      cerealBowlObject = {
        x: cssWidth / 2 - cssWidth / 4,
        y: cssHeight - cssWidth / 4,
        width: cssWidth / 2,
        height: cssWidth / 2 - cssWidth / 10
      };
      topPosition = -100;
      bottomPosition = cssHeight - cssWidth / 4
    }
    numberOfCallsForTransition = Math.abs(Math.floor((100 - cerealBowlObject.y) / 10));
    secondPageImageTransition = Math.abs(Math.abs(fishBowlObject.x) - Math.abs(cssWidth) / 2) / numberOfCallsForTransition;
  }

  redraw();
  const resizeObserver = new ResizeObserver(() => redraw())
  resizeObserver.observe(canvas.value)

  function animate() {
    if (animation) {
      window.cancelAnimationFrame(animation)
    }
    animation = requestAnimationFrame(animate)
    if (checkIfMobile) {
      mouseMiddleImageMovement = {x: (mouse.value.x * 4) / 250, y: (mouse.value.y * 4) / 250};
      mouseRightImageMovement = {x: -(mouse.value.x * 3) / 250, y: (mouse.value.y * 2) / 250};
      mouseLeftImageMovement = {x: +(mouse.value.x * 3) / 250, y: (mouse.value.y * 2) / 250};
    }

    context.clearRect(0, 0, cssWidth, cssHeight);
    if (cssWidth > mediumScreenWidth) {
      context.drawImage(leftSideVeggiesImage, leftSideVeggiesObject.x + mouseLeftImageMovement.x, leftSideVeggiesObject.y + mouseLeftImageMovement.y, leftSideVeggiesObject.width, leftSideVeggiesObject.height);
      context.drawImage(rightSideVeggiesImage, rightSideVeggiesObject.x + mouseLeftImageMovement.x, rightSideVeggiesObject.y + mouseLeftImageMovement.y, rightSideVeggiesObject.width, rightSideVeggiesObject.height);
    }
    context.drawImage(cerealBowlImage, cerealBowlObject.x - mouseMiddleImageMovement.x, cerealBowlObject.y + mouseMiddleImageMovement.y, cerealBowlObject.width, cerealBowlObject.height);
    context.drawImage(fruitBowlImage, fruitBowlObject.x + mouseMiddleImageMovement.x, fruitBowlObject.y + mouseMiddleImageMovement.y, fruitBowlObject.width, fruitBowlObject.height)

    context.drawImage(fishBowlImage, fishBowlObject.x + mouseLeftImageMovement.x, fishBowlObject.y + mouseLeftImageMovement.y, cssWidth / 4 + 100, cssWidth / 4)
    context.drawImage(veggiesBowlImage, veggiesBowlObject.x + mouseRightImageMovement.x, veggiesBowlObject.y + mouseRightImageMovement.y, veggiesBowlObject.width, veggiesBowlObject.height)

    if (scrollType.value === "down" && cerealBowlObject.y >= topPosition) {

      if (firstPageOpacityText.value > 0) {
        firstPageScrollDownTextTransform();
        return;
      }

      if (cssWidth > mediumScreenWidth) {
        rightSideVeggiesObject.x += 10;
        leftSideVeggiesObject.x -= 10;
      }

      cerealBowlObject.y -= 10;
      fishBowlObject.x += secondPageImageTransition
      fruitBowlObject.x += 2 * secondPageImageTransition - secondPageImageTransition / 2;
      veggiesBowlObject.x += 2 * secondPageImageTransition;

      secondPageScrollDownTextTransform();
    }

    if (scrollType.value === "up" && cerealBowlObject.y !== bottomPosition) {
      if (secondPageOpacityText.value > 0) {
        secondPageScrollUpTextTransform();
        return;
      }

      if (cssWidth > mediumScreenWidth) {
        rightSideVeggiesObject.x -= 10;
        leftSideVeggiesObject.x += 10;
      }

      cerealBowlObject.y += 10;
      fishBowlObject.x -= secondPageImageTransition
      fruitBowlObject.x -= 2 * secondPageImageTransition - secondPageImageTransition / 2;
      veggiesBowlObject.x -= 2 * secondPageImageTransition;

      firstPageScrollUpTextTransform();

    }

    if (cerealBowlObject.y <= topPosition || cerealBowlObject.y === bottomPosition) {
      scrollType.value = "steady";
    }

  }

  animate();

  window.addEventListener('mousewheel', checkScroll);
  window.addEventListener('touchstart', handleTouchStart, false);
  window.addEventListener('touchmove', handleTouchMove, false);
  addEventListener("mousemove", getMousePosition);
  onUnmounted(() => {
    removeEventListener("mousemove", getMousePosition);
    removeEventListener("mousewheel", checkScroll);
    removeEventListener('touchstart', handleTouchStart);
    removeEventListener('touchmove', handleTouchMove);
    resizeObserver.disconnect()
  })
})


</script>
