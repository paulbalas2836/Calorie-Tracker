<template>
  <button class="border rounded-md py-2 sm:px-4 px-2 sm:w-24 text-white hover:bg-gray-600 hover:bg-white hover:text-gray-500 hover:border-gray-500 border-gray-900 text-gray-900 dark:border-transparent dark:text-white dark:hover:bg-inherit dark:hover:text-gray-400 dark:hover:border-gray-400" >
    <slot>
    </slot>
  </button>
</template>

<script setup>
defineProps({
  noIntersecting: {type: Boolean, default: false}
})
</script>