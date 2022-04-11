<template>
  <div class="min-h-screen py-12 px-8">
    <div class="flex flex-row gap-x-8 flex-wrap items-center justify-center text-center">
      <div @click="previousWeek">Back</div>
      <div @click="changeDay(day)" class="cursor-pointer text-3xl font-lg"
           :class="day.active ? 'text-gray-800 dark:text-white' : 'text-gray-400 dark:text-gray-400'"
           v-for="day in WEEK_DAY" :key="day.name">
        {{ day.name }}
      </div>
      <div @click="nextWeek">Next</div>
    </div>
    <div class="flex flex-row flex-wrap gap-20 mt-12 items-center justify-center">
      <template v-if="historyByWeek">
        <template v-for="food in historyByWeek[key]" :key="food.id">
          <FoodCard :food="food"/>
        </template>
      </template>
    </div>
  </div>
</template>

<script setup>
import FoodCard from "./FoodCard.vue"
import {BACKEND_URL} from "../../Constants";
import axios from "axios";
import {onBeforeMount, ref} from 'vue'
import {useUserStore} from "../../store/userStore";

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

onBeforeMount(() => {
  axios.get(BACKEND_URL + 'history/getHistory/' + user.getEmail, {
    params: {
      startingDate: currentWeekMonday.value.getDate() + '/' + (currentWeekMonday.value.getMonth() + 1) + '/' + currentWeekMonday.value.getFullYear(),
      endingDate: currentWeekSunday.value.getDate() + '/' + (currentWeekSunday.value.getMonth() + 1) + '/' + currentWeekSunday.value.getFullYear(),
    },
    headers: {
      Authorization: "Bearer " + user.getToken
    }
  }).then(res => {
    historyByWeek.value = res.data
  }).catch(err => console.log(err.response))
})

function previousWeek() {
  currentWeekMonday.value.setDate(currentWeekMonday.value.getDate() - 7)
  currentWeekSunday.value.setDate(currentWeekSunday.value.getDate() - 7)
}

function nextWeek() {
  currentWeekMonday.value.setDate(currentWeekMonday.value.getDate() + 7)
  currentWeekSunday.value.setDate(currentWeekSunday.value.getDate() + 7)
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
