<template>
<div class="dark:text-white text-black text-lg mb-1">
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
