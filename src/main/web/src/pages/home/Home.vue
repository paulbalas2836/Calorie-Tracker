<template>
<!--  <div v-show="currentPage === 1" class="bottom-10 flex flex-col w-full z-10 absolute justify-center items-center">-->
<!--    <div class="lg:text-xl text-lg font-bold dark:text-white">Scroll Down</div>-->
<!--    <div class="">&#8595;</div>-->
<!--  </div>-->
<!--  <div v-show="currentPage === 2" class="top-28 flex flex-col w-full z-10 absolute justify-center items-center">-->
<!--    <div class="lg:text-xl text-lg font-bold dark:text-white">Scroll Up</div>-->
<!--    <div class="">&#8595;</div>-->
<!--  </div>-->

  <div v-show="firstPageOpacityText >= 0" class="flex flex-col absolute text-center w-full top-36" ref="firstPageText">
    <div class="lg:text-6xl text-3xl font-extrabold dark:text-white mt-10">Artificial
      FOOD
    </div>
    <div class="lg:text-7xl text-5xl font-extrabold dark:text-white">CHECK
      CALORIES FAST
    </div>
  </div>
  <div v-show="secondPageOpacityText > 0" class="flex flex-col text-center w-full absolute top-52"
       ref="secondPageText">
    <div class="lg:text-4xl text-3xl font-extrabold dark:text-white mt-10">Over 60 foods
      that the AI can identify
    </div>
    <div class="lg:text-4xl text-3xl font-extrabold dark:text-white">Upload image and see
      the results
    </div>
  </div>
  <canvas class="h-screen w-full relative" ref="canvas"></canvas>
</template>

<script setup>
import {onMounted, onUnmounted, ref} from 'vue'

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

function getTouches(event){
  return event.touches || event.originalEvent.touches;
}

function handleTouchStart(event){
  const firstTouch = getTouches(event)[0];
  xDown = firstTouch.clientX;
  yDown = firstTouch.clientY;
}

function handleTouchMove(event){
  if(!xDown || !yDown){
    return;
  }
  let xUp = event.touches[0].clientX;
  let yUp = event.touches[0].clientY;

  let xDiff = xDown - xUp;
  let yDiff = yDown - yUp;

  if(Math.abs( xDiff ) < Math.abs( yDiff )){
    if( yDiff > 0){
      scrollType.value = "down";
    }else scrollType.value = "up";
  }

  xDown = null;
  yDown = null;
}

function checkIfMobile() {
  let check = false;
  (function (a) {
    if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4))) window.location = b
  })(navigator.userAgent || navigator.vendor || window.opera);
  return check;
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
  leftSideVeggiesImage.src = '/leftSideVeggiesImage.png'

  const rightSideVeggiesImage = new Image();
  rightSideVeggiesImage.src = '/rightSideVeggiesImage.png'

  const cerealBowlImage = new Image();
  cerealBowlImage.src = '/cerealBowlImage.png';

  const fishBowlImage = new Image();
  fishBowlImage.src = '/fishBowlImage.png';

  const fruitBowlImage = new Image();
  fruitBowlImage.src = '/fruitBowlImage.png';

  const veggiesBowlImage = new Image();
  veggiesBowlImage.src = '/veggiesBowlImage.png';


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
