<template>
  <div>
    <Vue3ChartJs :height=200 :width=200 :id="macroNutrientChart.id"
                 :type="macroNutrientChart.type"
                 ref="chartRef"
                 :data="macroNutrientChart.data"
                 :options="macroNutrientChart.options"
    ></Vue3ChartJs>
    <MicroNutrient :microNutrients="microNutrients"/>
  </div>
</template>

<script setup>
import MicroNutrient from "../check_calories/MicroNutrients.vue"
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'
import {ref} from "vue";

const props = defineProps({
  dailyNutrients: Object
})


const microNutrients = ref({
  calcium: {
    label: "Calcium",
    amount: props.dailyNutrients.calcium,
    recommendedDailyIntake: 1000,
    unitOfMeasurement: "mg"
  },
  potassium: {
    label: "Potassium",
    amount: props.dailyNutrients.potassium,
    recommendedDailyIntake: 3500,
    unitOfMeasurement: "mg"
  },
  sodium: {
    label: "Sodium",
    amount: props.dailyNutrients.sodium,
    recommendedDailyIntake: 2300,
    unitOfMeasurement: "mg"
  },
  cholesterol: {
    label: "Cholesterol",
    amount: props.dailyNutrients.cholesterol,
    recommendedDailyIntake: 300,
    unitOfMeasurement: "mg"
  },
  iron: {
    label: "Iron",
    amount: props.dailyNutrients.iron,
    recommendedDailyIntake: 19.3,
    unitOfMeasurement: "mg"
  }
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
