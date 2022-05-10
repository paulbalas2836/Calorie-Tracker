<template>
  <div class="min-h-screen py-12 px-8">
    <p class="flex text-center justify-center text-2xl mb-2 font-md">Select Date</p>
    <div class="flex items-center justify-center text-center mb-8">
      <div class="flex flex-row flex-wrap gap-x-1 items-center shadow-2xl">
        <ChevronLeftIcon @click="previousDay" class="h-[52px] bg-white dark:bg-neutral-900 cursor-pointer dark:fill-white"/>
        <div @click="openCalendarModal = true"
             class="text-xl dark:text-white px-3 py-3 text-gray-800 font-medium bg-white dark:bg-neutral-900 cursor-pointer">
          {{ getSelectedDate() }}
        </div>
        <ChevronRightIcon @click="nextDay"
                          class="h-[52px] py-2 px-2 bg-white dark:bg-neutral-900 cursor-pointer dark:fill-white"/>
      </div>
    </div>
    <div class="flex flex-row flex-wrap gap-20 mt-12 items-center justify-center">
      <template v-if="historyByDay">
        <TransitionGroup enter-active-class="transition ease-out duration-700"
                         enter-from-class="transform opacity-0 scale-95"
                         enter-to-class="transform opacity-100 scale-100"
                         leave-active-class="transition ease-in duration-700"
                         leave-from-class="transform opacity-100 scale-100"
                         leave-to-class="transform opacity-0 scale-95">
          <template v-for="food in historyByDay.products" :key="food">
            <FoodCard :food="food"/>
          </template>
        </TransitionGroup>
      </template>
    </div>
    <div class="dark:bg-neutral-900 bg-white shadow-2xl px-6 py-8 rounded-md flex flex-col mt-8 gap-8 ">
      <div class="flex md:flex-row flex-col mt-4 justify-center items-center md:gap-x-12 lg:gap-x-9">
        <Vue3ChartJs :height=200 :width=200 :id="macroNutrientChart.id"
                     :type="macroNutrientChart.type"
                     ref="chartRef"
                     :data="macroNutrientChart.data"
                     :options="macroNutrientChart.options"
        ></Vue3ChartJs>
        <MacroNutrients :macroNutrients="macroNutrients"/>
      </div>
      <MicroNutrients :microNutrients="microNutrients"></MicroNutrients>
    </div>
    <Transition enter-active-class="transition ease-out duration-400"
                enter-from-class="transform opacity-0 scale-50"
                enter-to-class="transform opacity-100 scale-100 "
                leave-active-class="transition ease-in duration-300"
                leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-100">
      <CalendarModal v-if="openCalendarModal" @selectNewDate="getNewDate" @closeModal="openCalendarModal = false"
                     :selectedDate="selectedDate"/>
    </Transition>
  </div>
</template>

<script setup>
import FoodCard from "./FoodCard.vue"
import constants from "../../utils/FrozenConstants";
import axios from "axios";
import {onBeforeMount, ref} from 'vue'
import {useUserStore} from "../../store/userStore";
import MicroNutrients from "../check_calories/MicroNutrients.vue";
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'
import MacroNutrients from '../check_calories/MacroNutrients.vue'
import {ChevronLeftIcon, ChevronRightIcon} from '@heroicons/vue/solid'
import CalendarModal from '../../components/modals/CalendarModal.vue'
import {initMicroNutrients, initMacroNutrient} from "../../utils/Functions";
import {microNutrients, macroNutrientChart, macroNutrients} from '../../utils/SealConstants'

const user = useUserStore()

const selectedDate = ref(new Date())
const historyByDay = ref(null)
const chartRef = ref(null)
let openCalendarModal = ref(false)

function getSelectedDate() {
  return selectedDate.value.getDate() + '/' + (selectedDate.value.getMonth() + 1) + '/' + selectedDate.value.getFullYear()
}


async function getHistoryByDay() {
  await axios.get(constants.API + '/history/getHistory/' + user.getEmail, {
    params: {
      selectedDate: getSelectedDate(),
    },
    headers: {
      Authorization: "Bearer " + user.getToken
    }
  }).then(res => {
    historyByDay.value = res.data
    const nutrients = res.data.dailyNutrients;
    initMicroNutrients(nutrients.potassium, nutrients.sodium, nutrients.calcium, nutrients.cholesterol, nutrients.iron);
    initMacroNutrient(macroNutrientChart, chartRef, nutrients.calories, nutrients.fiber, nutrients.protein, nutrients.fat, nutrients.carbs);
  }).catch(err => console.log(err.response))
}

onBeforeMount(async () => {
  await getHistoryByDay();
})

function previousDay() {
  selectedDate.value.setDate(selectedDate.value.getDate() - 1)
  getHistoryByDay()
}

function nextDay() {
  selectedDate.value.setDate(selectedDate.value.getDate() + 1)
  getHistoryByDay()
}

function getNewDate(newDate) {
  selectedDate.value = newDate
  openCalendarModal.value = false
  getHistoryByDay()
}

</script>
