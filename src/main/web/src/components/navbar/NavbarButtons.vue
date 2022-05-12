<template>
  <button
      class="border rounded-md py-2 sm:px-4 px-2 sm:w-24 w-20 hover:text-gray-600 hover:border-gray-600 border-gray-900 text-gray-900 font-signika-negative"
      :class="{'dark:border-white dark:text-white dark:hover:bg-inherit dark:hover:text-gray-300 dark:hover:border-gray-300': !homePage, 'text-lg border-2' : homePage}">
    <slot>
    </slot>
  </button>
</template>

<script setup>
defineProps({
  homePage: {
    type: Boolean,
    default: false,
  }
})
</script>