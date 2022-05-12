<template>
  <ModalBase>
    <template #header>
      <div class="flex flex-row justify-between dark:bg-dark-mode-green bg-light-mode-green px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
        <ChevronLeftIcon @click="previousMonth" class="h-12 dark:fill-neutral-900 fill-white cursor-pointer"/>
        <div>
          <div class="uppercase text-xl text-shadow font-semibold dark:text-neutral-900 text-white">
            {{ getCurrentMonth }}
          </div>
          <p class="text-sm font-medium mt-4 text-shadow dark:text-neutral-900 text-white">{{ getCurrentDate }}</p>
        </div>
        <ChevronRightIcon @click="nextMonth" class="h-12 dark:fill-neutral-900 fill-white cursor-pointer"/>
      </div>
    </template>
    <template #default>
      <div class="flex flex-row justify-between px-2 py-2 text-center">
        <template v-for="dayName in constants.DAYS" :key="dayName.id">
          <div class="dark:text-white w-16">{{ dayName }}</div>
        </template>
      </div>
      <div class="flex flex-wrap text-center items-center px-2 py-2">
        <template v-for="days in daysOfTheMonth">
          <div class="dark:text-white sm:w-16 sm:py-2 basis-[14.2857143%] sm:px-2 cursor-pointer" @click="selectNewDate(days)"
               :style="days.dayClass">{{ days.day }}
          </div>
        </template>
      </div>
    </template>
  </ModalBase>
</template>

<script setup>
import ModalBase from './ModalBase.vue'
import {computed, ref, watch} from 'vue'
import {ChevronLeftIcon, ChevronRightIcon} from '@heroicons/vue/solid'
import constants from '../../utils/FrozenConstants.js'
import {darkMode} from "../../utils/ReactiveConstants";

const props = defineProps({
  selectedDate: Date
});

const emit = defineEmits(['selectNewDate']);

const currentDate = ref(props.selectedDate);
const currentDateFormatted = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth(), currentDate.value.getDate());
const daysOfTheMonth = ref([]);

const getLastDayOfMonth = computed(() => {
  return new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, 0).getDate();
})

const getLastDayOfPreviousMonth = computed(() => {
  return new Date(currentDate.value.getFullYear(), currentDate.value.getMonth(), 0).getDate();
})

const getCurrentMonth = computed(() => {
  return constants.MONTHS[currentDate.value.getMonth()];
})

const getCurrentDate = computed(() => {
  return currentDateFormatted.toDateString();
})

function getAllDaysOfTheMonth() {
  const currentDayBackgroundColor = darkMode.value ? "background-color: rgb(76 206 134);" : "background-color: rgb(0 167 46);";
  const firstDayIndex = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth(), 1).getDay();
  const lastDayIndex = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, 0).getDay();
  const precedingDaysOfThisMonth = 6 - lastDayIndex;
  daysOfTheMonth.value = [];
  for (let f = firstDayIndex; f > 0; f--) {
    daysOfTheMonth.value.push({
      day: getLastDayOfPreviousMonth.value - f + 1,
      month: currentDate.value.getMonth(),
      year: currentDate.value.getFullYear(),
      whichMonth: "previous",
      dayClass: "opacity: 0.5;"
    });
  }

  for (let i = 1; i <= getLastDayOfMonth.value; i++) {
    if (i === currentDate.value.getDate() && currentDateFormatted.getMonth() === currentDate.value.getMonth()) {
      daysOfTheMonth.value.push({
        day: i,
        month: currentDate.value.getMonth(),
        year: currentDate.value.getFullYear(),
        whichMonth: "current",
        dayClass: currentDayBackgroundColor
      });
      continue;
    }

    daysOfTheMonth.value.push({
      day: i,
      month: currentDate.value.getMonth(),
      year: currentDate.value.getFullYear(),
      whichMonth: "current",
      dayClass: ""
    });
  }

  for (let l = 1; l <= precedingDaysOfThisMonth; l++) {
    daysOfTheMonth.value.push({
      day: l,
      month: currentDate.value.getMonth(),
      year: currentDate.value.getFullYear(),
      whichMonth: "next",
      dayClass: "opacity: 0.5;"
    });
  }
}

getAllDaysOfTheMonth();

watch(currentDate, () => {
  getAllDaysOfTheMonth();
})

function nextMonth() {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, currentDate.value.getDate());
}

function previousMonth() {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() - 1, currentDate.value.getDate());
}

function selectNewDate(day) {
  const newDate = day.whichMonth === "current" ? new Date(day.year, day.month, day.day) : day.whichMonth === "next" ? new Date(day.year, day.month + 1, day.day) : new Date(day.year, day.month - 1, day.day);

  emit("selectNewDate", newDate);

}
</script>
