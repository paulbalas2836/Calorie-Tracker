<template>
  <div class="min-h-full">
    <navbar></navbar>
    <main>
      <router-view></router-view>
    </main>
    <Footer />
  </div>
</template>


<script setup>
import Navbar from "../components/navbar/Navbar.vue"
import Footer from "../components/basic/Footer.vue"
import { ref } from 'vue'

</script>
