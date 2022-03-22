<template>
  <div class="min-h-full">
    <navbar></navbar>
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>


<script setup>
import Navbar from "../components/navbar/Navbar.vue"
import { ref } from 'vue'
</script>
