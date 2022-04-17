<template>
  <div class="min-h-full">
<!--        <navbar ></navbar>-->
    <canvas ref="canvas" class="w-full h-80"></canvas>
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
import Dot from '../Dot.js'
import gsap from "gsap";

const canvas = ref(null)
const savedContext =ref(null)
const cssWidth = ref(null)
const cssHeight = ref(null)
const dpr = ref(null)
const pxWidth = ref(null)
const pxHeight = ref(null)


watch(darkMode, () => {
  if (!darkMode.value) {
    canvas.value.style.background = "#FFFFFF"
  } else {
    canvas.value.style.background = "#34495E"
  }
})


onMounted(() => {
  // const mouse = {x: null, y: null}
  // let particlesArray;
  // window.addEventListener('mousemove', function (event) {
  //   mouse.x = event.x;
  //   mouse.y = event.y
  // })

  const dots = [];

  function redraw() {
    dpr.value = window.devicePixelRatio
    cssWidth.value = canvas.value.clientWidth
    cssHeight.value = canvas.value.clientHeight
    pxWidth.value = Math.round(dpr.value*cssWidth.value)
    pxHeight.value = Math.round( dpr.value*cssHeight.value)
    canvas.value.width = pxWidth.value
    canvas.value.height = pxHeight.value
    const context = canvas.value.getContext("2d")
    context.scale(dpr.value, dpr.value)
    savedContext.value =  context
    const perspective = canvas.value.width * 0.8
    const projection_center_x = canvas.value.width/2
    const projection_center_y = canvas.value.height/2


    for (let i = 0; i < 800; i++) {
      dots.push(new Dot(cssWidth.value, cssHeight.value, perspective, projection_center_x, projection_center_y, context));
    }
    function render() {
      context.clearRect(0, 0, canvas.value.width, canvas.value.height);

      for (let i = 0; i < dots.length; i++) {
        dots[i].project();
      }

      // Sort dots array based on their projected size
      dots.sort((dot1, dot2) => {
        return dot1.sizeProjection - dot2.sizeProjection;
      });

      for (let i = 0; i < dots.length; i++) {
        dots[i].draw();
      }

      window.requestAnimationFrame(render);
    }
    render()
  }
   new ResizeObserver(() => redraw()).observe(canvas.value)


  // class Particle {
  //   constructor(x, y, directionX, directionY, size, color) {
  //     this.x = x;
  //     this.y = y;
  //     this.directionX = directionX;
  //     this.directionY = directionY;
  //     this.size = size;
  //     this.color = color;
  //   }
  //
  //   draw() {
  //     savedContext.value.beginPath()
  //     savedContext.value.arc(this.x, this.y, this.size, 0, Math.PI * 2, false);
  //     savedContext.value.fillStyle = '#8C5523'
  //     savedContext.value.fill();
  //   }
  //
  //   update() {
  //     if(this.x > canvas.value.width || this.x < 0){
  //       this.directionX  =- this.directionX;
  //     }
  //     if(this.y> canvas.value.height || this.y < 0){
  //       this.directionY = -this.directionY;
  //     }
  //
  //     this.x += this.directionX;
  //     this.y += this.directionY;
  //     this.draw();
  //   }
  //
  // }
  //
  // function init(){
  //   particlesArray = [];
  //   let numberOfParticles = 10
  //   for(let i = 0; i < numberOfParticles; i++){
  //     let size = (Math.random() * 5) + 1;
  //     let x = (Math.random()* ((canvas.value.width - size*2)- (size*2)) +size*2)
  //     let y = (Math.random()* ((canvas.value.height - size*2)- (size*2)) +size*2)
  //     let directionX = (Math.random()* 5) - 2.5
  //     let directionY = (Math.random()* 5) - 2.5
  //     let color = '#8C5523'
  //     particlesArray.push(new Particle(x,y,directionX,directionY,size, color))
  //   }
  // }
  //
  // function animate(){
  //   requestAnimationFrame(animate);
  //   savedContext.value.clearRect(0,0, canvas.value.width, canvas.value.height)
  //
  //   for(let i = 0; i < particlesArray.length; i++){
  //     particlesArray[i].update();
  //   }
  //   connect();
  // }
  //
  // function connect(){
  //   let opacityValue = 1;
  //   for(let a = 0; a< particlesArray.length; a++){
  //     for(let b = a; b<particlesArray.length; b++){
  //       let distance = ((particlesArray[a].x - particlesArray[b].x)*(particlesArray[a].x - particlesArray[b].x)) + ((particlesArray[a].y -particlesArray[b].y)* (particlesArray[a].y-particlesArray[b].y))
  //       if(distance < (canvas.value.width/7)*(canvas.value.height/7)){
  //         opacityValue = 1 -(distance/20000);
  //         savedContext.value.strokeStyle='rgba(140,85,31,'+opacityValue+')';
  //         savedContext.value.lineWidth = 1;
  //         savedContext.value.beginPath();
  //         savedContext.value.moveTo(particlesArray[a].x, particlesArray[a].y)
  //         savedContext.value.lineTo(particlesArray[b].x, particlesArray[b].y)
  //         savedContext.value.stroke();
  //       }
  //     }
  //   }
  // }
  // init();
  // animate();
})


</script>
