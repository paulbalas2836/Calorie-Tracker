<template>
  <div
      class="sm:px-4 sm:py-4 px-2 py-2 border border-transparent items-center rounded-md sm:w-16 sm:h-16 w-14 h-14 pointer cursor-pointer">
    <MenuIcon v-show="!showMobileNavbarPages" class="dark:fill-white fill-black"></MenuIcon>
    <XIcon v-show="showMobileNavbarPages" class="dark:fill-white fill-black"></XIcon>
  </div>
</template>

<script setup>
import {MenuIcon, XIcon} from '@heroicons/vue/solid'

 defineProps({
  showMobileNavbarPages: {
    type: Boolean,
    default: false,
  }
})
</script>

