<template>
  <div class="border border-transparent shadow-xl cursor-pointer px-2 py-2 dark:bg-fuchsia-300 dark:hover:bg-fuchsia-200 rounded-md hover:bg-emerald-400 bg-emerald-500">
    <span class="sr-only">View notifications</span>
    <MoonIcon v-show="darkMode === true" class="fill-dark h-4"></MoonIcon>
    <SunIcon v-show="darkMode === false" class="fill-white h-4"></SunIcon>
  </div>
</template>

<script setup>
import {MoonIcon, SunIcon} from "@heroicons/vue/solid"
defineProps({
  darkMode: {
    type: Boolean,
    default: true,
  }
})


</script>