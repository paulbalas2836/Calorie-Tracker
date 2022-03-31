<template>
  <div class="dark:bg-gray-800 bg-gray-200">
    <div class="min-h-screen max-w-7xl mx-auto px-4 py-10">
      <div class="md:grid md:grid-cols-3 gap-6">
        <div
            class="dark:bg-neutral-900 h-96 px-3 py-5 bg-white shadows-md rounded-md grid grid-rows-4 justify-center items-center">
          <div class="row-span-3 w-max h-max justify-self-center">
            <img :src="user.getImage" alt="" height="240" width="240" v-if="uploadImage != null" class="relative"/>
            <Button v-if="uploadImage == null">Upload image</Button>
          </div>
          <div class="flex gap-2">
            <Input placeholder="Weight in grams"/>
            <Button class="self-end">Get Calories</Button>
          </div>
        </div>
        <div class="md:col-span-2 sm:mt-0 mt-4">
          <MacroNutrients :macroNutrients="macroNutrients"></MacroNutrients>
          <MicroNutrients :microNutrients="microNutrients"></MicroNutrients>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Button from "../../components/basic/Button.vue";
import MacroNutrients from "./MacroNutrients.vue"
import MicroNutrients from "./MicroNutrients.vue";
import {ref} from "vue"
import Input from "../../components/basic/Input.vue"
import {useUserStore} from "../../store/userStore";

const macroNutrients = ref({
  calories: {label: "Calories", amount: 500},
  fiber: {label: "Fiber", amount: 36},
  proteins: {label: "Proteins", amount: 35, percentage: 35},
  fats: {label: "Fats", amount: 20, percentage: 25},
  carbs: {label: "Carbs", amount: 45, percentage: 45},
})

const microNutrients = ref({
  calcium: {
    label: "Calcium",
    amount: 2.5,
    recommendedDailyIntake: 1000,
    unitOfMeasurement: "mg"
  },
  cholesterol: {
    label: "Cholesterol",
    amount: 50,
    recommendedDailyIntake: 300,
    unitOfMeasurement: "mg"
  },
  potassium:{
    label: "Potassium",
    amount:1500,
    recommendedDailyIntake:3500,
    unitOfMeasurement: "mg"
  },
  sodium:{
    label: "Sodium",
    amount: 500,
    recommendedDailyIntake: 2300,
    unitOfMeasurement: "mg"
  },
  iron: {
    label: "Iron",
    amount: 5,
    recommendedDailyIntake: 19.3,
    unitOfMeasurement: "mg"
  }
})

const user = useUserStore()
const uploadImage = ref(null)

</script>
