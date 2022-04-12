<template>
  <div class="min-h-screen py-12 px-8">
    <div class="flex flex-row flex-wrap gap-x-1 items-center justify-center text-center mb-8">
      <ChevronLeftIcon @click="previousWeek"
                       class="h-8 w-10 dark:fill-gray-400 cursor-pointer hover:fill-gray-800 fill-gray-400 dark:hover:fill-white self-end"/>
      <div @click="changeDay(day)"
           class="cursor-pointer text-3xl font-medium mr-2 ml-2 dark:hover:text-gray-200 hover:text-gray-600"
           :class="day.active ? 'text-gray-800 dark:text-white' : 'text-gray-400 dark:text-gray-400'"
           v-for="day in WEEK_DAY" :key="day.name">
        {{ day.name }}
      </div>
      <ChevronRightIcon @click="nextWeek"
                        class="h-8 w-10 dark:fill-gray-400 cursor-pointer hover:fill-gray-800 fill-gray-400 dark:hover:fill-white self-end"/>
    </div>
    <div class="flex justify-center font-medium text-center text-xl dark:text-white text-gray-800 ">
      {{ getCurrentWeekMonday() }} - {{ getCurrentWeekSunday() }}
    </div>
    <div class="flex flex-row flex-wrap gap-20 mt-12 items-center justify-center">
      <template v-if="historyByWeek">
        <template v-for="food in historyByWeek.products[key]" :key="food.id">
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
    <div class="w-full h-full flex flex-row border border-transparent dark:bg-neutral-900 bg-gray-400 h-40 mt-8 rounded-md">
      <DailyNutrients v-if="historyByWeek" :dailyNutrients="historyByWeek.dailyNutrients[key]"/>
    </div>
  </div>
</template>

<script setup>
import FoodCard from "./FoodCard.vue"
import constants from "../../Constants";
import axios from "axios";
import {onBeforeMount, ref} from 'vue'
import {useUserStore} from "../../store/userStore";
import DailyNutrients from "./DailyNutrients.vue"
import {ChevronLeftIcon, ChevronRightIcon} from '@heroicons/vue/solid'

const user = useUserStore()

const WEEK_DAY = ref([
  {name: "Monday", active: true, index: 0},
  {name: "Tuesday", active: false, index: 1},
  {name: "Wednesday", active: false, index: 2},
  {name: "Thursday", active: false, index: 3},
  {name: "Friday", active: false, index: 4},
  {name: "Saturday", active: false, index: 5},
  {name: "Sunday", active: false, index: 6}
])
Object.seal(WEEK_DAY)
const currentWeekMonday = ref(new Date(new Date().setDate(new Date().getDate() - new Date().getDay() + 1)))
const currentWeekSunday = ref(new Date(new Date().setDate(new Date().getDate() - new Date().getDay() + 7)))

const key = ref(0)
const historyByWeek = ref(null)

function getCurrentWeekMonday() {
  return currentWeekMonday.value.getDate() + '/' + (currentWeekMonday.value.getMonth() + 1) + '/' + currentWeekMonday.value.getFullYear()
}

function getCurrentWeekSunday() {
  return currentWeekSunday.value.getDate() + '/' + (currentWeekSunday.value.getMonth() + 1) + '/' + currentWeekSunday.value.getFullYear()
}

function getHistoryByWeek() {
  axios.get(constants.BACKEND_URL + 'history/getHistory/' + user.getEmail, {
    params: {
      startingDate: getCurrentWeekMonday(),
      endingDate: getCurrentWeekSunday(),
    },
    headers: {
      Authorization: "Bearer " + user.getToken
    }
  }).then(res => {
    historyByWeek.value = res.data
    console.log(res.data)
  }).catch(err => console.log(err.response))
}

onBeforeMount(async () => {
  await getHistoryByWeek()
})

function previousWeek() {
  currentWeekMonday.value.setDate(currentWeekMonday.value.getDate() - 7)
  currentWeekSunday.value.setDate(currentWeekSunday.value.getDate() - 7)
  getHistoryByWeek()
}

function nextWeek() {
  currentWeekMonday.value.setDate(currentWeekMonday.value.getDate() + 7)
  currentWeekSunday.value.setDate(currentWeekSunday.value.getDate() + 7)
  getHistoryByWeek()
}

function changeDay(day) {
  day.active = true
  key.value = day.index
  WEEK_DAY.value.forEach((el) => {
    if (el.name !== day.name) {
      el.active = false
    }
  })


}
</script>
