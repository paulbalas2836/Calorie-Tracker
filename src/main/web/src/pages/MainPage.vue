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
import gsap from "gsap";

const canvas = ref(null)
const savedContext =ref(null)
const cssWidth = ref(null)
const cssHeight = ref(null)
const dpr = ref(null)
const pxWidth = ref(null)
const pxHeight = ref(null)

const PERSPECTIVE = ref(null)
const PROJECTION_CENTER_X = ref(null)
const PROJECTION_CENTER_Y = ref(null)

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
    PERSPECTIVE.value = canvas.value.width * 0.8
    PROJECTION_CENTER_X.value = canvas.value.width/2
    PROJECTION_CENTER_Y.value = canvas.value.height/2

    const DOT_RADIUS = 10; // Radius of the dots
    let GLOBE_RADIUS = canvas.value.width / 3; // Radius of the globe based on the canvas width
    class Dot {
      constructor() {
        this.theta = Math.random() * 2 * Math.PI; // Random value between [0, 2Pi]
        this.phi = Math.acos((Math.random() * 2) - 1); // Random value between [0, Pi]
        this.x = (Math.random() - 0.5) * canvas.value.width;
        this.y = (Math.random() - 0.5) * canvas.value.height;
        this.z = Math.random() * canvas.value.width;
        this.radius = 10;

        this.xProjected = 0;
        this.yProjected = 0;
        this.scaleProjected = 0;

        gsap.to(this, {duration :Math.random() * 10 + 15,
          z: canvas.value.width,
          repeat: -1,
          theta: this.theta + Math.PI *2,
          ease: "power0.easeNone",
        });
      }

      project() {
        this.x = GLOBE_RADIUS * Math.sin(this.phi) * Math.cos(this.theta);
        this.y = GLOBE_RADIUS * Math.cos(this.phi);
        this.z = GLOBE_RADIUS * Math.sin(this.phi) * Math.sin(this.theta) + GLOBE_RADIUS;

        this.scaleProjected = PERSPECTIVE.value / (PERSPECTIVE.value + this.z);
        this.xProjected = (this.x * this.scaleProjected) + PROJECTION_CENTER_X.value;
        this.yProjected = (this.y * this.scaleProjected) + PROJECTION_CENTER_Y.value;
      }
      draw() {
        this.project();
        context.globalAlpha = Math.abs(1 - this.z / canvas.value.width);
        context.beginPath();
        context.arc(this.xProjected, this.yProjected, DOT_RADIUS * this.scaleProjected, 0, Math.PI * 2);
        // context.fillRect(this.xProjected - this.radius, this.yProjected - this.radius, this.radius * 2 * this.scaleProjected, this.radius * 2 * this.scaleProjected);
        context.fill()
      }
    }
    for (let i = 0; i < 800; i++) {
      dots.push(new Dot());
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
