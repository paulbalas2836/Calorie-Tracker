<template>
  <div
      class="border border-transparent shadow-xl cursor-pointer px-2 py-2 dark:bg-dark-mode-green dark:hover:bg-dark-mode-hover-green rounded-md hover:bg-light-mode-hover-green bg-light-mode-green">
    <span class="sr-only">Toggle Dark Mode</span>
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