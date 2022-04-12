<template>
  <div
      class="sm:px-4 sm:py-4 px-2 py-2 border border-transparent items-center rounded-md sm:w-16 sm:h-16 w-14 h-14 pointer cursor-pointer"
      @click="openMenu">
    <MenuIcon v-show="!menuActive" class="fill-white"></MenuIcon>
    <XIcon v-show="menuActive" class="fill-white"></XIcon>
  </div>
</template>

<script setup>
import {MenuIcon, XIcon} from '@heroicons/vue/solid'
import {ref} from "vue"

const menuActive = ref(false)

function openMenu() {
  menuActive.value = !menuActive.value

}
</script>

