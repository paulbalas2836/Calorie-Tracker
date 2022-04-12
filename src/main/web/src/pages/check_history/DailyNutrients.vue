<template>
  <div>
    <Vue3ChartJs :height=180 :width=180 :id="macroNutrientChart.id"
                 :type="macroNutrientChart.type"
                 ref="chartRef"
                 :data="macroNutrientChart.data"
                 :options="macroNutrientChart.options"
   ></Vue3ChartJs>
  </div>
</template>

<script setup>
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'
const props = defineProps({
  dailyNutrients: Object
})

const macroNutrientChart = {
  id: 'doughnut',
  type: 'doughnut',
  data: {
    labels: ["Protein", "Fat", "Carbs"],
    datasets: [
      {
        backgroundColor: [
          '#FF9933',
          '#00CC66',
          '#0080FF',
        ],
        data: [props.dailyNutrients.protein, props.dailyNutrients.fat, props.dailyNutrients.carbs]
      }
    ]
  },
  options: {
    responsive: false,
  }
}
</script>
