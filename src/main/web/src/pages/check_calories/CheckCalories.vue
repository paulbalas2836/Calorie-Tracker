<template>
  <div class="dark:bg-neutral-800 bg-gray-200">
    <div class="min-h-screen max-w-7xl mx-auto px-4 py-10">
      <div class="lg:grid lg:grid-cols-3 gap-6">
        <div
            class="dark:bg-neutral-900 min-h-fit px-3 py-5 bg-white shadow-2xl rounded-md grid grid-rows-4 justify-center items-center">
          <div class="row-span-3 w-max h-max justify-self-center">
            <img :src="image" alt="" height="240" width="240" v-show="isImageUploaded" class="relative"/>
            <div class="flex flex-col justify-center mt-8 items-center">
              <video v-show="isCameraOpen" v-if="useCheckIfMobile()" class="mb-4" ref="videoRef" height="240" width="320" autoplay/>
              <label
                  class="relative cursor-pointer border border-transparent dark:text-gray-900 text-white rounded-md py-2 px-4 bg-light-mode-green hover:bg-light-mode-hover-green dark:hover:bg-dark-mode-hover-green dark:bg-dark-mode-green text-sm font-medium shadow-md">
                <span>{{ isImageUploaded === false ? "Upload a file" : "Upload another file" }}</span>
                <input id="file_upload" type="file" class="sr-only" @change="onFileSelected"
                       accept=".jpg, .jpeg, .png"/>
              </label>
              <Button class="mt-2 mb-4" @click="toggleCamera()" v-if="useCheckIfMobile()">{{
                  isCameraOpen ? "Close camera" : "Open camera"
                }}
              </Button>
              <Button class="mt-2 mb-4" @click="takePhoto()" v-if="useCheckIfMobile() && isCameraOpen">Take a photo</Button>
              <ErrorMessage class="mt-2">{{ imageError }}</ErrorMessage>
            </div>
          </div>
          <div class="flex gap-2">
            <Input placeholder="Weight in grams" v-model="saveHistoryDto.weight"/>
            <Button class="self-end" @click="getCalories">Calculate</Button>
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
import {microNutrients, macroNutrientChart, macroNutrients} from '../../utils/ReactiveConstants'
import {useInitMicroNutrients, useInitMacroNutrient, useCheckIfMobile} from '../../utils/Composable.js'

const user = useUserStore();
const chartRef = ref(null);
const videoRef = ref(null);

const image = ref(null);
const saveToHistory = new FormData();
const imageError = ref(null);
const imageTensor = ref(null);
const saveHistoryDto = ref({weight: null, label: '', email: user.getEmail});
const label = ref(null);
const quantity = ref(null);
const isCameraOpen = ref(false);
const isImageUploaded = ref(false);
let model = null;


function imageValidator(value) {
  if (!(value.type.split('/')[0] === 'image'))
    return "File must be an image";

  if (value.size > 52428800)
    return "File is too big";

  return true
}

function onFileSelected(event) {
  if (isCameraOpen.value) {
    stopCameraStream();
  }

  if (event.target.files.length === 0)
    return;

  const file = event.target.files[0];
  if (!imageValidator(file) === true) {
    imageError.value = imageValidator(file);
    return;
  }

  if(saveToHistory.get("image") != null){
    saveToHistory.delete("image");
  }
  saveToHistory.append("image", file);
  imageError.value = null;
  image.value = URL.createObjectURL(file);
  isImageUploaded.value = true;
  getTensorOfImage();
}

function getTensorOfImage() {
  const img = new Image();
  img.src = image.value;
  img.onload = () => {
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0, 64, 64);
    imageTensor.value = ctx.getImageData(0, 0, 64, 64);
  }
}

function toggleCamera() {
  isImageUploaded.value = false
  if (isCameraOpen.value) {
    stopCameraStream();
    return;
  }

  createCameraElement();

}

function createCameraElement() {
  isCameraOpen.value = true;
  const constraints = (window.constraints = {
    audio: false,
    video: true
  });

  navigator.mediaDevices.getUserMedia(constraints).then(stream => {
    videoRef.value.srcObject = stream;
  }).catch(() => {
    imageError.value = "The browser doesn't support or there is some error."
  })
}

function stopCameraStream() {
  isCameraOpen.value = false;
  let tracks = videoRef.value.srcObject.getTracks();
  tracks.forEach(track => {
    track.stop();
  })
}

function takePhoto() {
  saveToHistory.delete("image");

  isImageUploaded.value = true;
  const canvas = document.createElement('canvas');
  const ctx = canvas.getContext('2d');
  ctx.drawImage(videoRef.value, 0, 0, canvas.width, canvas.height);
  canvas.toBlob(function (blob) {
    saveToHistory.append("image", blob);
  }, "image/jpeg")
  image.value = canvas.toDataURL("image/jpeg");
  getTensorOfImage();
  stopCameraStream();
}

onMounted(async () => {
  useInitMacroNutrient(macroNutrientChart, chartRef, 500, 8, 35, 20, 45, 20, 45, 45);
  useInitMicroNutrients(0, 0, 0, 0, 0);
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
  axios.post(constants.API + '/history/prediction', saveToHistory,
      {
        headers: {
          "Content-Type": undefined
        }
      })
      .then(res => {
        const data = res.data;
        useInitMacroNutrient(macroNutrientChart, chartRef, data.calories, data.fiber, data.protein, data.fat, data.carbs);
        useInitMicroNutrients(data.potassium, data.sodium, data.calcium, data.cholesterol, data.iron);

        quantity.value = data.quantity;
        saveToHistory.delete("saveHistoryDto");
      }).catch(err => console.log(err.response));
}
</script>
