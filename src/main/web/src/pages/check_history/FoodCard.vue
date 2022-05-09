<template>
  <div @mouseover="hover = true" @mouseleave="hover = false"
       class="h-52 w-52 sm:h-72 sm:w-72 border border-transparent cursor-pointer rounded-md items-center block ">
    <img :src="constants.HISTORY_IMAGES+'/'+food.path" alt="" class="rounded-md absolute sm:h-72 sm:w-72 h-52 w-52 border border-2"
         :class="hover ? 'opacity-50' : 'opacity-100'"/>
    <div v-show="hover"
         class="h-52 w-52 sm:h-72 sm:w-72 rounded-md relative flex flex-col text-center py-4 px-4 bg-light-mode-green/70 dark:bg-dark-mode-green/70">
      <Transition enter-active-class="transition ease-out duration-300"
                  enter-from-class="transform opacity-0 scale-95 translate-y-6"
                  enter-to-class="transform opacity-100 scale-100 translate-y-0"
                  leave-active-class="transition ease-in duration-75"
                  leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
        <div v-show="hover">
          <div class="text-gray-800 fond-2xl text-2xl">{{ food.name }}: {{ food.quantity }}g</div>
          <div class="flex flex-row justify-evenly mt-8 ">
            <div class="flex flex-col">
              <div class="text-lg">Calories</div>
              <div class="text-lg">{{ food.calories }}</div>
            </div>
            <div class="flex flex-col">
              <div class="text-lg">Fiber</div>
              <div class="text-lg">{{ food.fiber }}g</div>
            </div>
          </div>
          <div class="flex flex-row mt-4 justify-evenly">
            <div class="flex flex-col">
              <div class="text-lg">Proteins</div>
              <div class="text-lg">{{ food.protein }}g</div>
            </div>
            <div class="flex flex-col">
              <div class="text-lg">Carbs</div>
              <div class="text-lg">{{ food.carbs }}g</div>
            </div>
            <div class="flex flex-col">
              <div class="text-lg">Fats</div>
              <div class="text-lg">{{ food.fat }}g</div>
            </div>
          </div>
        </div>
      </Transition>
    </div>
  </div>
</template>

<script setup>
import {useUserStore} from "../../store/userStore";
import {ref} from "vue"
import constants from "../../utils/FrozenConstants";

defineProps({
  food: Object
})
const user = useUserStore()
let hover = ref(false)
</script>