<template>
  <div class="min-h-screen py-12 px-8">
    <div class="flex flex-row gap-x-8 flex-wrap items-center justify-center text-center">
      <div @click="changeDay(day)" class="cursor-pointer text-3xl font-lg"
           :class="day.active ? 'text-gray-800 dark:text-white' : 'text-gray-400 dark:text-gray-400'"
           v-for="day in WEEK_DAY" :key="day.name">
        {{ day.name }}
      </div>
    </div>
    <div class="flex flex-row gap-20 mt-12">
      <FoodCard/>
      <FoodCard/>
      <FoodCard/>
      <FoodCard/>
    </div>
  </div>
</template>

<script setup>
import FoodCard from "../../components/basic/FoodCard.vue"
import {BACKEND_URL} from "../../Constants";
import axios from "axios";
import {onBeforeMount, ref} from 'vue'
import {useUserStore} from "../../store/userStore";

const user = useUserStore()

const WEEK_DAY = ref([
  {name: "Monday", active: true},
  {name: "Tuesday", active: false},
  {name: "Wednesday", active: false},
  {name: "Thursday", active: false},
  {name: "Friday", active: false}
])
Object.seal(WEEK_DAY)

onBeforeMount(() => {
  axios.get(BACKEND_URL + 'history/getHistory/' + user.getEmail, {
    headers: {
      Authorization: "Bearer " + user.getToken
    }
  }).then(res => console.log(res)).catch(err => console.log(err))
})

const changeDay = (day) => {
  day.active = true
  WEEK_DAY.value.forEach((el) => {
    if (el.name !== day.name) {
      el.active = false
    }
  })
}
</script>
