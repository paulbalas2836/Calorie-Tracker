<template>
  <div v-for="macro in macroNutrients" :key="macro.label" class="self-center mt-4 md:mt-0 text-center relative">
    <div class="text-xl  text-gray-700 dark:text-white">{{ macro.label }}</div>
    <div v-if="macro.label !== 'Calories'" class="text-lg  text-gray-700 dark:text-white">{{ macro.amount }}g</div>
    <div v-else class="text-lg  text-gray-700 dark:text-white">{{ macro.amount }}</div>
    <div v-if="macro.hasOwnProperty('percentage')" class="text-lg font-medium text-gray-700 dark:text-white"
         :style="macro.percentageColor">{{ macro.percentage }}%
    </div>
  </div>
</template>

<script setup>

defineProps({
  macroNutrients: {
    type: Object
  }
})
</script>