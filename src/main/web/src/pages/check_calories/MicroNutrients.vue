<template>
  <div class="dark:bg-neutral-900 bg-white shadows-md px-6 py-8 rounded-md flex flex-col mt-4 gap-8">
    <template v-for="micro in microNutrients" :key="micro.label">
      <div>
        <div class="flex flex-row">
          <div class="text-md dark:text-white text-gray-800">{{ micro.label }}</div>
          <div class="text-md dark:text-white text-gray-800 ml-4">{{ micro.amount }}
            {{ micro.unitOfMeasurement }}
          </div>
          <div class="text-md dark:text-white text-gray-800 ml-auto">
            {{ micro.recommendedDailyIntake }} {{ micro.unitOfMeasurement }}
          </div>
        </div>
        <div class="h-1 w-full dark:bg-white bg-gray-300">
          <div class="bg-blue-500 h-1" :style="width(micro.recommendedDailyIntake,micro.amount)"></div>
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>

function width(all, current) {
  return {
    'width': ((current * 100) / all - 100 > 0 ? 100 : (current * 100) / all) + '%',
    'transition-duration': '2s'
  }
}

defineProps({
  microNutrients: {
    type: Object,
  }
})
</script>
