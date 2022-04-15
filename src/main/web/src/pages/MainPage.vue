<template>
  <div class="min-h-full">
    <canvas id="canvas" width="1000" height="1000"/>
    <navbar ></navbar>
    <main>
      <router-view></router-view>
    </main>
    <Footer/>
  </div>
</template>


<script setup>
import Proton from "proton-engine";
import Navbar from "../components/navbar/Navbar.vue"
import Footer from "../components/basic/Footer.vue"
</script>
