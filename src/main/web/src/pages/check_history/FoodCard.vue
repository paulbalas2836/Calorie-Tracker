<template>
  <div @mouseover="hover = true" @mouseleave="hover = false"
       class="h-52 w-52 sm:h-72 sm:w-72 border border-transparent cursor-pointer rounded-md items-center block ">
    <img :src="HISTORY_IMAGES+food.path" alt="" class="rounded-md absolute sm:h-72 sm:w-72 h-52 w-52"
         :class="hover ? 'opacity-50' : 'opacity-100'"/>
    <div v-show="hover" class="h-52 w-52 sm:h-72 sm:w-72 rounded-md relative flex flex-col text-center py-4 px-4 bg-emerald-400/70">
      <div class="text-gray-800 fond-lg text-2xl">{{ food.name }}: {{ food.quantity }}g</div>
      <div class="flex flex-row justify-evenly mt-8 ">
        <div class="flex flex-col">
          <div>Calories</div>
          <div>{{ food.calories }}</div>
        </div>
        <div class="flex flex-col">
          <div>Fiber</div>
          <div>{{ food.fiber }}g</div>
        </div>
      </div>
      <div class="flex flex-row mt-4 justify-evenly">
        <div class="flex flex-col">
          <div>Proteins</div>
          <div>{{ food.protein }}g</div>
        </div>
        <div class="flex flex-col">
          <div>Carbs</div>
          <div>{{ food.carbs }}g</div>
        </div>
        <div class="flex flex-col">
          <div>Fats</div>
          <div>{{ food.fat }}g</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {useUserStore} from "../../store/userStore";
import {ref} from "vue"
import {HISTORY_IMAGES} from "../../Constants";

defineProps({
  food: Object
})
const user = useUserStore()
let hover = ref(false)
</script>