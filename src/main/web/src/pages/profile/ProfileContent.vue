<template>
  <div class="bg-gray-200 dark:bg-neutral-800 min-h-screen ">
    <div class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8 overflow-auto">
      <ProfileInformation/>
      <ProfileDelete />
    </div>
  </div>
</template>

<script setup>
import ProfileInformation from "./ProfileInformation.vue";
import ProfileDelete from "./ProfileDisable.vue";
</script>