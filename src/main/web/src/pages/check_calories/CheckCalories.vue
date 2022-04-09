<template>
  <div class="dark:bg-gray-800 bg-gray-200">
    <div class="min-h-screen max-w-7xl mx-auto px-4 py-10">
      <div class="lg:grid lg:grid-cols-3 gap-6">
        <div
            class="dark:bg-neutral-900 min-h-fit px-3 py-5 bg-white shadows-md rounded-md grid grid-rows-4 justify-center items-center">
          <div class="row-span-3 w-max h-max justify-self-center">
            <img :src="image" alt="" height="240" width="240" v-show="isImageUploaded" class="relative"/>
            <div class="flex flex-col justify-center mt-8 items-center">
              <label
                  class="relative cursor-pointer border border-transparent dark:text-gray-900 text-white rounded-md py-2 px-4 bg-emerald-400 hover:bg-emerald-300 dark:hover:bg-fuchsia-200 dark:bg-fuchsia-300 text-sm font-medium shadow-md">
                <span>Upload a file</span>
                <input id="file_upload" type="file" class="sr-only" @change="onFileSelected"
                       accept=".jpg, .jpeg, .png"/>
              </label>
              <ErrorMessage class="mt-2">{{ imageError }}</ErrorMessage>
            </div>
          </div>
          <div class="flex gap-2">
            <Input placeholder="Weight in grams" v-model="saveHistoryDto.weight"/>
            <Button class="self-end" @click="getCalories">Get Calories</Button>
          </div>
        </div>
        <div class="md:col-span-2 sm:mt-0 mt-4">
          <div class="dark:bg-neutral-900 bg-white shadows-md px-4 py-5 rounded-md flex flex-col lg:mt-0 mt-4">
            <div v-show="label !== null" class="flex justify-center text-center font-md text-xl dark:text-white text-gray-700">
              {{ label }}: {{ quantity }}g
            </div>
            <div class="flex md:flex-row flex-col mt-4 justify-center items-center md:gap-x-12 lg:gap-x-9">
              <Vue3ChartJs :height=180 :width=180 :id="macroNutrientChart.id"
                           :type="macroNutrientChart.type"
                           ref="chartRef"
                           :data="macroNutrientChart.data"
                           :options="macroNutrientChart.options"></Vue3ChartJs>
              <div v-for="macro in macroNutrients" :key="macro.label" class="self-center mt-4 md:mt-0 text-center relative">
                <div class="text-xl font-md text-gray-700 dark:text-white">{{ macro.label }}</div>
                <div v-if="macro.label !== 'Calories'" class="text-lg font-md text-gray-700 dark:text-white">{{
                    macro.amount
                  }}g
                </div>
                <div v-else class="text-lg font-md text-gray-700 dark:text-white">{{ macro.amount }}</div>
                <div v-if="macro.hasOwnProperty('percentage')" class="text-lg font-md text-gray-700 dark:text-white"
                     :style="macro.percentageColor">
                  {{ macro.percentage }}%
                </div>
              </div>
            </div>
          </div>
          <MicroNutrients :microNutrients="microNutrients"></MicroNutrients>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import * as tf from '@tensorflow/tfjs';
import Button from "../../components/basic/Button.vue";
import MicroNutrients from "./MicroNutrients.vue";
import {ref} from "vue"
import Input from "../../components/basic/Input.vue"
import {useUserStore} from "../../store/userStore";
import ErrorMessage from "../../components/basic/ErrorMessage.vue"
import axios from "axios";
import * as constants from "../../Constants.js"
import {L2} from "../../L2.js"
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'
const user = useUserStore()

const macroNutrients = ref({
  calories: {label: "Calories", amount: 500},
  fiber: {label: "Fiber", amount: 36},
  proteins: {label: "Proteins", amount: 35, percentage: 35, percentageColor: 'color:#FF9933;'},
  fats: {label: "Fats", amount: 20, percentage: 25, percentageColor: 'color:#00CC66;'},
  carbs: {label: "Carbs", amount: 45, percentage: 45, percentageColor: 'color:#0080FF;'},
})

const microNutrients = ref({
  calcium: {
    label: "Calcium",
    amount: 2.5,
    recommendedDailyIntake: 1000,
    unitOfMeasurement: "mg"
  },
  potassium: {
    label: "Potassium",
    amount: 1500,
    recommendedDailyIntake: 3500,
    unitOfMeasurement: "mg"
  },
  sodium: {
    label: "Sodium",
    amount: 500,
    recommendedDailyIntake: 2300,
    unitOfMeasurement: "mg"
  },
  cholesterol: {
    label: "Cholesterol",
    amount: 50,
    recommendedDailyIntake: 300,
    unitOfMeasurement: "mg"
  },
  iron: {
    label: "Iron",
    amount: 5,
    recommendedDailyIntake: 19.3,
    unitOfMeasurement: "mg"
  }
})
Object.seal(microNutrients)
Object.seal(macroNutrients)

const macroNutrientChart = {
  id: 'doughnut',
  type: 'doughnut',
  data: {
    labels: [macroNutrients.value.proteins.label, macroNutrients.value.fats.label, macroNutrients.value.carbs.label],
    datasets: [
      {
        backgroundColor: [
          '#FF9933',
          '#00CC66',
          '#0080FF',
        ],
        data: [macroNutrients.value.proteins.amount, macroNutrients.value.fats.amount, macroNutrients.value.carbs.amount]
      }
    ]
  },
  options: {
    responsive: false,
  }
}
const chartRef = ref(null)
const image = ref(null)
const saveToHistory = new FormData()
const isImageUploaded = ref(false)
const imageError = ref(null)
const imageTensor = ref(null)
const saveHistoryDto = ref({weight: null, label: null, email: null})
const label = ref(null)
const quantity = ref(null)

function imageValidator(value) {
  if (!(value.type.split('/')[0] === 'image'))
    return "File must be an image"

  if (value.size > 52428800)
    return "File is too big"

  return true
}

function onFileSelected(event) {
  if (event.target.files.length === 0)
    return

  const file = event.target.files[0];
  if (!imageValidator(file) === true) {
    imageError.value = imageValidator(file)
    return;
  }


  saveToHistory.append("image", file)
  imageError.value = null
  isImageUploaded.value = true
  image.value = URL.createObjectURL(file)

  const img = new Image()
  img.src = image.value
  img.onload = () => {
    const canvas = document.createElement('canvas')
    const ctx = canvas.getContext('2d')
    ctx.drawImage(img, 0, 0, 64, 64)
    imageTensor.value = ctx.getImageData(0, 0, 64, 64)
  }
}

function getLabel(prediction) {
  let labelByIndex = -1
  let value = -1
  for (let i = 0; i < prediction.length; i++) {
    if (prediction[i] > value) {
      value = prediction[i]
      labelByIndex = i
    }
  }
  return constants.productMap.get(labelByIndex)
}

async function getCalories() {
  tf.serialization.registerClass(L2)
  const normalizedData = tf.browser.fromPixels(imageTensor.value).toFloat().div(tf.scalar(255))
  const model = await tf.loadLayersModel(constants.TENSORFLOW_MODEL);
  const prediction = model.predict(normalizedData.expandDims()).dataSync()

  label.value = getLabel(prediction)
  saveHistoryDto.value.email = user.getEmail;
  saveHistoryDto.value.label = label.value;
  saveToHistory.append("saveHistoryDto", new Blob([JSON.stringify({
    "weight": saveHistoryDto.value.weight,
    "label": saveHistoryDto.value.label,
    "email": saveHistoryDto.value.email
        })], {
        type: "application/json"
        }))
  axios.post(constants.BACKEND_URL + 'history/prediction', saveToHistory,
      {
        headers: {
          "Content-Type": undefined
        }
      }).then(res => {
    console.log(res.data)
    macroNutrients.value.calories.amount = res.data.calories
    macroNutrients.value.carbs.amount = res.data.carbs
    macroNutrients.value.fiber.amount = res.data.fiber
    macroNutrients.value.fats.amount = res.data.fat
    macroNutrients.value.proteins.amount = res.data.protein
    macroNutrients.value.proteins.percentage = Math.round((res.data.protein*4*100)/res.data.calories)
    macroNutrients.value.fats.percentage = Math.round((res.data.fat*9*100)/res.data.calories)
    macroNutrients.value.carbs.percentage = 100- macroNutrients.value.proteins.percentage - macroNutrients.value.fats.percentage

    microNutrients.value.calcium.amount = res.data.calcium
    microNutrients.value.iron.amount = res.data.iron
    microNutrients.value.cholesterol.amount = res.data.cholesterol
    microNutrients.value.sodium.amount = res.data.sodium
    microNutrients.value.potassium.amount = res.data.potassium

    macroNutrientChart.data.datasets = [
      {
        backgroundColor: [
          '#FF9933',
          '#00CC66',
          '#0080FF',
        ],
        data: [res.data.protein, res.data.fat,  res.data.carbs]
      }
    ]

    chartRef.value.update()
    quantity.value = res.data.quantity
    saveToHistory.delete("saveHistoryDto")
  }).catch(err => console.log(err.response.data))
}
</script>
