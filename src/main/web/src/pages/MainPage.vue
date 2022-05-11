<template>
  <div class="min-h-full w-full">
    <div class="bg-home-page h-full w-full absolute z-0" v-show="homePage"></div>
    <navbar></navbar>
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>


<script setup>
import Navbar from "../components/navbar/Navbar.vue"
import {ref, watch} from "vue";
import {useRoute} from "vue-router";

const homePage = ref(false)

const currentRouteName = useRoute();
watch(currentRouteName, () => {
  homePage.value = currentRouteName.name === 'Home';
})

</script>
