<template>
  <div class="min-h-screen py-12 px-8">
    <div class="flex items-center justify-center text-center mb-8">
      <div class="flex flex-row flex-wrap gap-x-1 items-center">
        <ChevronLeftIcon @click="previousDay" class="h-[52px] dark:bg-neutral-900 cursor-pointer dark:fill-white"/>
        <div @click="openCalendarModal = true"
             class="text-xl dark:text-white px-3 py-3 text-gray-800 font-medium dark:bg-neutral-900 cursor-pointer">
          {{ getSelectedDate() }}
        </div>
        <ChevronRightIcon @click="nextDay"
                          class="h-[52px] py-2 px-2 dark:bg-neutral-900 cursor-pointer dark:fill-white"/>
      </div>
    </div>
    <div class="flex flex-row flex-wrap gap-20 mt-12 items-center justify-center">
      <template v-if="historyByDay">
        <template v-for="food in historyByDay.products" :key="food.id">
          <transition enter-active-class="transition ease-out duration-300"
                      enter-from-class="transform opacity-0 scale-95"
                      enter-to-class="transform opacity-100 scale-100"
                      leave-active-class="transition ease-in duration-75"
                      leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
            <FoodCard :food="food"/>
          </transition>
        </template>
      </template>
    </div>
    <div class="dark:bg-neutral-900 bg-white shadows-md px-6 py-8 rounded-md flex flex-col mt-4 gap-8">
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
    <CalendarModal v-if="openCalendarModal" @selectNewDate="getNewDate" @closeModal="openCalendarModal = false"
                   :selectedDate="selectedDate"/>
  </div>
</template>

<script setup>
import FoodCard from "./FoodCard.vue"
import constants from "../../FrozenConstants";
import axios from "axios";
import {onBeforeMount, ref, watch} from 'vue'
import {useUserStore} from "../../store/userStore";
import MicroNutrients from "../check_calories/MicroNutrients.vue";
import Vue3ChartJs from '@j-t-mcc/vue3-chartjs'
import {microNutrients, macroNutrientChart, macroNutrients} from '../../SealConstants'
import MacroNutrients from '../check_calories/MacroNutrients.vue'
import {ChevronLeftIcon, ChevronRightIcon} from '@heroicons/vue/solid'
import CalendarModal from '../../components/modals/CalendarModal.vue'

const user = useUserStore()

const selectedDate = ref(new Date())
const historyByDay = ref(null)
const chartRef = ref(null)
const openCalendarModal = ref(false)

function getSelectedDate() {
  return selectedDate.value.getDate() + '/' + (selectedDate.value.getMonth() + 1) + '/' + selectedDate.value.getFullYear()
}


async function getHistoryByWeek() {
  await axios.get(constants.BACKEND_URL + 'history/getHistory/' + user.getEmail, {
    params: {
      selectedDate: getSelectedDate(),
    },
    headers: {
      Authorization: "Bearer " + user.getToken
    }
  }).then(res => {
    historyByDay.value = res.data
    microNutrients.value.calcium.amount = res.data.dailyNutrients.calcium
    microNutrients.value.potassium.amount = res.data.dailyNutrients.potassium
    microNutrients.value.sodium.amount = res.data.dailyNutrients.sodium
    microNutrients.value.cholesterol.amount = res.data.dailyNutrients.cholesterol
    microNutrients.value.iron.amount = res.data.dailyNutrients.iron

    macroNutrients.value.proteins.amount = res.data.dailyNutrients.protein
    macroNutrients.value.carbs.amount = res.data.dailyNutrients.carbs
    macroNutrients.value.fats.amount = res.data.dailyNutrients.fat
    macroNutrients.value.fiber.amount = res.data.dailyNutrients.fiber
    macroNutrients.value.calories.amount = res.data.dailyNutrients.calories

    macroNutrients.value.proteins.percentage = Math.round((res.data.dailyNutrients.protein * 4 * 100) / res.data.dailyNutrients.calories)
    macroNutrients.value.fats.percentage = Math.round((res.data.dailyNutrients.fat * 9 * 100) / res.data.dailyNutrients.calories)
    macroNutrients.value.carbs.percentage = 100 - macroNutrients.value.proteins.percentage - macroNutrients.value.fats.percentage

    macroNutrientChart.data.datasets = [
      {
        backgroundColor: [
          '#FF9933',
          '#00CC66',
          '#0080FF',
        ],
        data: [res.data.dailyNutrients.protein, res.data.dailyNutrients.fat, res.data.dailyNutrients.carbs]
      }
    ]

    chartRef.value.update()
    console.log(res.data)
  }).catch(err => console.log(err.response))
}

onBeforeMount(() => {
  getHistoryByWeek()
})

function previousDay() {
  selectedDate.value.setDate(selectedDate.value.getDate() - 1)
  getHistoryByWeek()
}

function nextDay() {
  selectedDate.value.setDate(selectedDate.value.getDate() + 1)
  getHistoryByWeek()
}

function getNewDate(newDate) {
  selectedDate.value = newDate
  openCalendarModal.value = false
  getHistoryByWeek()
}

</script>
