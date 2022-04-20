<template>
  <div class="min-h-full">
    <header>
      <navbar :intersecting="intersecting"></navbar>
      <canvas ref="canvas" class="w-full h-[30rem]"/>
    </header>
<!--    <canvas ref="canvas" ></canvas>-->
    <main>
      <router-view></router-view>
    </main>
    <Footer/>
  </div>
</template>


<script setup>
import Navbar from "../components/navbar/Navbar.vue"
import Footer from "../components/basic/Footer.vue"
import {onMounted, ref, watch} from "vue";
import {darkMode} from "../SealConstants";
import Particle from '../Particle.js'

const canvas = ref(null)
const savedContext = ref(null)
const cssWidth = ref(null)
const cssHeight = ref(null)
const dpr = ref(null)
const pxWidth = ref(null)
const pxHeight = ref(null)
const intersecting = ref(false)
const image = ref(null)
const mouse = ref({x: null, y: null, radius: 100})


watch(darkMode, () => {
  if (!darkMode.value) {
    canvas.value.style.background = "#fcba03"
  } else {
    canvas.value.style.background = "#34495E"
  }
})

onMounted(() => {
  let particlesArray = [];
  function redraw() {
    dpr.value = window.devicePixelRatio
    cssWidth.value = canvas.value.clientWidth
    cssHeight.value = canvas.value.clientHeight
    pxWidth.value = Math.round(dpr.value * cssWidth.value)
    pxHeight.value = Math.round(dpr.value * cssHeight.value)
    canvas.value.width = pxWidth.value
    canvas.value.height = pxHeight.value
    const context = canvas.value.getContext("2d")
    context.scale(dpr.value, dpr.value)
    savedContext.value = context

    const img = new Image()
    img.src = '/navigation.png'
    img.onload = function () {
      savedContext.value.drawImage(img, 0, 0, cssWidth.value, cssHeight.value);
    }
  }

  const navbarOptions = {};
  const navbarObserver = new IntersectionObserver(function (entries, navbarObserver) {
    entries.forEach(entry => {
      intersecting.value = !entry.isIntersecting;
    })
  }, navbarOptions);
  navbarObserver.observe(canvas.value);

  new ResizeObserver(() => redraw()).observe(canvas.value)

//
//   window.addEventListener('mousemove',
//       function (event) {
//         mouse.value.x = event.x + canvas.value.clientLeft / 2;
//         mouse.value.y = event.y + canvas.value.clientTop / 2;
//       })
//
//   function drawImage() {
//     let imageWidth = img.width;
//     let imageHeight = img.height;
//     const data = savedContext.value.getImageData(0, 0, imageWidth, imageHeight);
//     savedContext.value.clearRect(0, 0, canvas.value.width, canvas.value.height);
//
//     function init() {
//       particlesArray = [];
//       for (let y = 0, y2 = data.height; y < y2; y++) {
//         for (let x = 0, x2 = data.width; x< x2; x++) {
//           if(data.data[(y*4*data.width)+ (x*4)+3]>128){
//             let positionX = x;
//             let positionY = y;
//             let color = "rgb("  + data.data[(y*4*data.width) + (x*4)] + "," + data.data[(y*4*data.width)+(x*4) + 1]+ "," +data.data[(y*4*data.width)+(x*4) + 2] + ")";
//             particlesArray.push(new Particle(positionX * 4, positionY *4, 2, color, canvas.value.width,canvas.value.height, imageWidth, imageHeight));
//           }
//         }
//       }
//     }
//     function animate(){
//       requestAnimationFrame(animate);
//       savedContext.value.fillStyle = 'rgba(0,0,0,.05)';
//       savedContext.value.fillRect(0,0, canvas.value.width, canvas.value.height)
//
//       for(let i = 0; i < particlesArray.length; i++){
//         particlesArray[i].update(canvas.value.width,canvas.value.height,savedContext.value, mouse.value);
//       }
//     }
//     init();
//     animate();
//   }
//   const img = new Image()
//     img.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAcCAIAAAD9b0jDAAADnUlEQVRIDa3BfUicBRwH8O/vnnt5bnfe+V7zzpeztRA3ttOpp6MthwXj8g/JGNQGsaCwFwVXgzH2x4omURxlzSSEEThvDPtj1WoxdyM26tKtLTp93M2h4rvn9OT0vBfv+RUHUpLGzPt8iJmxcTU1NZeuX4FW4BQROvWB9B27CnY4HA7EETNjIyRJKraVLB1+CnkGbFHhL/dm6dowV+boOgbysrI9Hg8xMx4NM+v1+qA9G7Ysuj3FFSYsRunL3/mlAohKqAUoCQoFvf8zMTP+kyRJDocjNzfXZrM9d66BK0z4cZCMGhaIIjLPh1GYjoE50qnQNUzTwcbGRmJmrK++vt51qWPf9tRvpcXktDTP0UzcmqTLD/h4GfRquiDBpOfHdYjIeDCnvD7q9XotFgsxM9bX3t5+5vjrpXna3vGlW5X55J7gI4XQCJgNUe9DrspBjEGEvpmS31Td3d2II2bGakNDQy6Xi/zzF1taZkbHXnz7rbPnPq2w6L6+47coVHqVMmQx+rYZZtTEoWWSHlbmWOvq6mpra7GCmBkrOjs7z59tqf5D2qvWZggC4gKyXE0TBlFYmFh2pZqxQgb3R6N3I+Er6UbzgcrW1lasIGZGXFlZWfm9gZNJKQIIq32x5O/Lj7k9iz1pOVjLvCzb/dPtN34qKioCQMwMoLm5eeTU6ROGVKx2NRS8FgreX44mmYX+kVC2QnkhdatAhLU0zPn2fHC6oaGBmNnr9R7auasr00zYrD3TI11SHzGzKIr30806ImzaUCz60f5ycjqdn79y9JsMExJBBp4YHyS73f7ZnT6jQkAiMPC8b4zyMjJ7NAYFEoOBat847daIXRlmQmIwUDE1QjaN9rsMEyExZGD7xCBZVOqex3IIiREDlyiipCAazcrXECERFlh+Y3cB2e32Q7/etot6JMLN8FKw+WNyOp31h4/0b81DIlT6Rr9y/0LMnJSUdFKpfU1vxObIzNsWZvx+PzFzJBLRiaIrM7tQpcYmHPSNXp2eMhgMxMwAAoGAOSvre11qgUqN/6UjGNjb9YPVagVAzIy4WCxWXFz8wvDYm/pkbNAngbnkUycajx1DHDEz/sHtdr+8b/8ZY/qz4hY8Ak80/Ors1PmbN0pLS7GCmBn/0tvbW15e/kyM3zOmmQWVAmCA8Lcwsy+2/PTshDYlZXJyEqsRM2N9bW1tH77zrhAMepejJkG5U6UJsnw3Gn7Saq2qqmpqasJa/gR3z5SleYTThwAAAABJRU5ErkJggg=="
//     img.onload = function(){
//     savedContext.value.drawImage(img, 0,0);
//     drawImage()
//   }
})


</script>
