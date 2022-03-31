<template>
<div class="dark:text-white text-black sm:text-lg mb-1 text-sm">
  {{label}}
</div>
</template>

<script setup>
defineProps({
  label: {
    type: String,
    default: 'label'
  }
})
</script>
