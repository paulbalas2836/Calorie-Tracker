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
            <Input placeholder="Weight in grams" v-model="weight"/>
            <Button class="self-end" @click="getCalories">Get Calories</Button>
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
import * as tf from '@tensorflow/tfjs';
import Button from "../../components/basic/Button.vue";
import MacroNutrients from "./MacroNutrients.vue"
import MicroNutrients from "./MicroNutrients.vue";
import {ref} from "vue"
import Input from "../../components/basic/Input.vue"
import {useUserStore} from "../../store/userStore";
import ErrorMessage from "../../components/basic/ErrorMessage.vue"
import { toRaw } from 'vue'
import axios from "axios";

const user = useUserStore()
const URL_PATH = 'http://localhost/8080/food/'
const TENSORFLOW_MODEL = "http://127.0.0.1:8081/model.json"

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
  iron: {
    label: "Iron",
    amount: 5,
    recommendedDailyIntake: 19.3,
    unitOfMeasurement: "mg"
  }
})
const image = ref(null)
const saveToHistory = new FormData()
const isImageUploaded = ref(false)
const imageError = ref(null)
const weight = ref(null)
const imageTensor = ref(null)

function imageValidator(value) {
  if (!(value.type.split('/')[0] === 'image'))
    return "File must be an image"

  if (value.size > 52428800)
    return "File is too big"

  return true
}

function onFileSelected(event) {
  const file = event.target.files[0];

  if (event.target.files.length === 0)
    return

  if (!imageValidator(file) === true) {
    imageError.value = imageValidator(file)
    return;
  }


  saveToHistory.append("image", file)
  imageError.value = null
  isImageUploaded.value = true
  image.value = URL.createObjectURL(file)

  const img = new Image()
  img.src = URL.createObjectURL(file)
  img.onload = () => {
    const canvas = document.createElement('canvas')
    const ctx = canvas.getContext('2d')
    ctx.drawImage(img, 0, 0, img.width, img.height)
    imageTensor.value = ctx.getImageData(0,0,img.width,img.height)
    // ctx.canvas.toBlob((blob) => {
    //   showImage.value = URL.createObjectURL(new File([blob], fileName, file));
    // })
  }
}

class L2 {

  static className = 'L2';

  constructor(config) {
    return tf.regularizers.l1l2(config)
  }
}

tf.serialization.registerClass(L2);

async function getCalories() {
  console.log(imageTensor.value)
  saveToHistory.append("weight", weight.value)
  saveToHistory.append("email", user.getEmail)
  saveToHistory.append("label", "banana")
  const model = await tf.loadLayersModel('http://127.0.0.1:8081/model.json');
  const prediction = model.predict(tf.browser.fromPixels(imageTensor.value).resizeNearestNeighbor([64, 64]).cast('float32').expandDims()).data()
  console.log(prediction)
  // axios.post('http://localhost:8080/food/prediction', prediction,).then(res => {
  //   console.log(res)
  // }).catch(err => console.log(err.response))
}
</script>
