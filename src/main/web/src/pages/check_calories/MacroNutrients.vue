<template>
  <div class="dark:bg-neutral-900 bg-white shadows-md px-4 py-5 rounded-md flex flex-col lg:mt-0 mt-4">
    <div class="flex md:flex-row flex-col mt-4 justify-center items-center md:gap-x-12 lg:gap-x-9">
      <Vue3ChartJs :height=180 :width=180 :id="macroNutrientChart.id"
                   :type="macroNutrientChart.type"
                   :data="macroNutrientChart.data"
                   :options="macroNutrientChart.options"></Vue3ChartJs>
      <div class="self-center mt-4 md:mt-0 text-center relative ">
        <div class="text-xl font-md text-gray-700 dark:text-white">{{
            macroNutrients.calories.label
          }}
        </div>
        <div class="text-xl font-md text-gray-700 dark:text-white">{{ macroNutrients.calories.amount }}</div>
      </div>

      <div class="self-center mt-4 md:mt-0 text-center relative">
        <div class="text-xl font-md text-gray-700 dark:text-white">{{ macroNutrients.fiber.label }}</div>
        <div class="text-lg font-md text-gray-700 dark:text-white">{{ macroNutrients.fiber.amount }}g</div>
      </div>

      <div class="self-center mt-4 md:mt-0 text-center relative">
        <div class="text-xl font-md text-gray-700 dark:text-white">{{
            macroNutrients.proteins.label
          }}
        </div>
        <div class="text-lg font-md text-gray-700 dark:text-white">{{
            macroNutrients.proteins.amount
          }}g
        </div>
        <div class="text-lg font-md text-gray-700 dark:text-white" style="color:#FF9933;">
          {{ macroNutrients.proteins.percentage }}%
        </div>
      </div>

      <div class="self-center mt-4 md:mt-0 text-center relative">
        <div class="text-xl font-md text-gray-700 dark:text-white">{{ macroNutrients.fats.label }}</div>
        <div class="text-lg font-md text-gray-700 dark:text-white">{{ macroNutrients.fats.amount }}g</div>
        <div class="text-lg font-md text-gray-700 dark:text-white" style="color:#00CC66;">
          {{ macroNutrients.fats.percentage }}%
        </div>
      </div>

      <div class="self-center mt-4 md:mt-0 text-center relative">
        <div class="text-xl font-md text-gray-700 dark:text-white">{{ macroNutrients.carbs.label }}</div>
        <div class="text-lg font-md text-gray-700 dark:text-white">{{ macroNutrients.carbs.amount }}g</div>
        <div class="text-lg font-md text-gray-700 dark:text-white" style="color:#0080FF;">
          {{ macroNutrients.carbs.percentage }}%
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'

const props = defineProps({
  macroNutrients: {
    type: Object,
  }
})
const macroNutrientChart = {
  id: 'doughnut',
  type: 'doughnut',
  data: {
    labels: [props.macroNutrients.proteins.label, props.macroNutrients.fats.label, props.macroNutrients.carbs.label],
    datasets: [
      {
        backgroundColor: [
          '#FF9933',
          '#00CC66',
          '#0080FF',
        ],
        data: [props.macroNutrients.proteins.amount, props.macroNutrients.fats.amount, props.macroNutrients.carbs.amount]
      }
    ]
  },
  options: {
    responsive: false,
  }
}
</script>
