<template>
  <div @mouseover="hover = true" @mouseleave="hover = false"
       class="w-[300px] h-[300px] border border-transparent cursor-pointer rounded-md items-center block ">
    <img src="/profile_images/1.jpg" alt="" width="300" height="300" class="rounded-md absolute"
         :class="hover ? 'opacity-50' : 'opacity-100'"/>
    <div v-show="hover" class="rounded-md w-[300px] h-[300px] relative flex flex-col text-center py-4 px-4 bg-emerald-400/70">
      <div class="text-gray-800 fond-lg text-2xl">Apple: 100g</div>
      <div class="flex flex-row mt-4">
          <div>Calories: 100</div>
          <div>Fiber: 15g</div>
      </div>
      <div class="flex flex-row">
        <div>Proteins: 30g</div>
        <div>Carb: 45g</div>
        <div>Fats: 20g</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {useUserStore} from "../../store/userStore";
import {ref} from "vue"

const user = useUserStore()
const hover = ref(false)
</script>