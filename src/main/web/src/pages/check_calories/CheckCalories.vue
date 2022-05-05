<template>
  <div class="dark:bg-gray-800 bg-gray-200">
    <div class="min-h-screen max-w-7xl mx-auto px-4 py-10">
      <div class="lg:grid lg:grid-cols-3 gap-6">
        <div
            class="dark:bg-neutral-900 min-h-fit px-3 py-5 bg-white shadow-2xl rounded-md grid grid-rows-4 justify-center items-center">
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
          <div class="dark:bg-neutral-900 bg-white shadow-2xl px-4 py-5 rounded-md flex flex-col lg:mt-0 mt-4">
            <div v-show="label !== null"
                 class="flex justify-center text-center  text-xl dark:text-white text-gray-700">
              {{ label }}: {{ quantity }}g
            </div>
            <div class="flex md:flex-row flex-col mt-4 justify-center items-center md:gap-x-12 lg:gap-x-9">
              <Vue3ChartJs :height=180 :width=180 :id="macroNutrientChart.id"
                           :type="macroNutrientChart.type"
                           ref="chartRef"
                           :data="macroNutrientChart.data"
                           :options="macroNutrientChart.options"></Vue3ChartJs>
              <MacroNutrients :macroNutrients="macroNutrients"/>
            </div>
          </div>
          <div class="dark:bg-neutral-900 bg-white shadow-2xl px-6 py-8 rounded-md flex flex-col mt-4 gap-8">
            <MicroNutrients :microNutrients="microNutrients"></MicroNutrients>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import * as tf from '@tensorflow/tfjs';
import Button from "../../components/basic/Button.vue";
import MicroNutrients from "./MicroNutrients.vue";
import {onMounted, ref} from "vue"
import Input from "../../components/basic/Input.vue"
import {useUserStore} from "../../store/userStore";
import ErrorMessage from "../../components/basic/ErrorMessage.vue"
import axios from "axios";
import constants from "../../utils/FrozenConstants.js"
import {L2} from "../../utils/L2.js"
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'
import MacroNutrients from './MacroNutrients.vue'
import {microNutrients, macroNutrientChart, macroNutrients} from '../../utils/SealConstants'
import {initMicroNutrients, initMacroNutrient} from '../../utils/ReusableFunctions.js'

const user = useUserStore();
const chartRef = ref(null);
const image = ref(null);
const saveToHistory = new FormData();
const isImageUploaded = ref(false);
const imageError = ref(null);
const imageTensor = ref(null);
const saveHistoryDto = ref({weight: null, label: '', email: user.getEmail});
const label = ref(null);
const quantity = ref(null);
let model = null;

function imageValidator(value) {
  if (!(value.type.split('/')[0] === 'image'))
    return "File must be an image";

  if (value.size > 52428800)
    return "File is too big";

  return true
}

function onFileSelected(event) {
  if (event.target.files.length === 0)
    return;

  const file = event.target.files[0];
  if (!imageValidator(file) === true) {
    imageError.value = imageValidator(file);
    return;
  }


  saveToHistory.append("image", file);
  imageError.value = null;
  image.value = URL.createObjectURL(file);
  isImageUploaded.value = true;

  //Resize image for a faster prediction
  const img = new Image();
  img.src = image.value;
  img.onload = () => {
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');
    imageTensor.value = ctx.getImageData(0, 0, 64, 64);
  }
}


onMounted(async () => {
  initMacroNutrient(macroNutrientChart, chartRef, 500, 8, 35, 35, 20, 20, 45, 45);
  initMicroNutrients(0, 0, 0, 0, 0);
  tf.serialization.registerClass(L2)
  model = await tf.loadLayersModel("http://127.0.0.1:8081/model.json")
})

function getLabel(prediction) {
  const max = Math.max.apply(null, prediction);
  const index = prediction.indexOf(max);

  return constants.PRODUCT_MAP.get(index);
}

function getCalories() {
  if (!isImageUploaded.value) {
    imageError.value = "You need to upload an image!";
    return;
  }

  const normalizedData = tf.browser.fromPixels(imageTensor.value).toFloat().div(tf.scalar(255));
  const prediction = model.predict(normalizedData.expandDims()).dataSync();
  label.value = getLabel(prediction);

  saveHistoryDto.value.label = label.value;
  saveToHistory.append("saveHistoryDto", new Blob([JSON.stringify({
    "weight": saveHistoryDto.value.weight,
    "label": saveHistoryDto.value.label,
    "email": saveHistoryDto.value.email
  })], {
    type: "application/json"
  }));

  axios.post(constants.BACKEND_URL + 'history/prediction', saveToHistory,
      {
        headers: {
          "Content-Type": undefined
        }
      })
      .then(res => {
        const data = res.data;
        initMacroNutrient(macroNutrientChart,chartRef,data.calories, data.fiber, data.protein, data.fat, data.carbs);
        initMicroNutrients(data.potassium, data.sodium, data.calcium, data.cholesterol, data.iron);

        quantity.value = data.quantity;
        saveToHistory.delete("saveHistoryDto");
      }).catch(err => console.log(err.response.data));
}
</script>
