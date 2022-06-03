<template>
  <div class="flex flex-wrap -mb-1 mt-4">
    <button type="button" :disabled="isInFirstPage" @click="firstPage"
            class=" mr-2 mb-1 px-4 py-3 text-sm leading-4 border-2 rounded"
            :class="{'shadow-md hover:dark:bg-gray-800 hover:bg-gray-100 cursor-pointer' : !isInFirstPage}">
      <ChevronDoubleLeftIcon class="h-4 w-4 dark:fill-white"/>
    </button>
    <button type="button" :disabled="isInFirstPage" @click="previousPage"
            class=" mr-2 mb-1 px-4 py-3 text-sm  leading-4 border-2 rounded"
            :class="{'shadow-md hover:dark:bg-gray-800 hover:bg-gray-100 cursor-pointer' : !isInFirstPage}">
      <ChevronLeftIcon class="h-4 w-4 dark:fill-white"/>
    </button>
    <template v-for="page in pages" :key="page.name">
      <button type="button" @click="selectedPage(page.name - 1)" :disabled="page.isDisabled"
              class="mr-2 mb-1 px-4 py-3 text-sm leading-4 border-2 rounded dark:text-white"
              :class="{'shadow-md hover:dark:bg-gray-800 hover:bg-gray-100 cursor-pointer' : !page.isDisabled }">
        {{ page.name }}
      </button>
    </template>
    <button type="button" :disabled="isInLastPage" @click="nextPage"
            class=" mr-2 mb-1 px-4 py-3 text-sm leading-4 border-2 rounded"
            :class="{'shadow-md hover:dark:bg-gray-800 hover:bg-gray-100 cursor-pointer' : !isInLastPage}">
      <ChevronRightIcon class="h-4 w-4 dark:fill-white"/>
    </button>
    <button type="button" :disabled="isInLastPage" @click="lastPage"
            class=" mr-2 mb-1  px-4 py-3 text-sm  leading-4 border-2 rounded"
            :class="{'shadow-md hover:dark:bg-gray-800 hover:bg-gray-100 cursor-pointer' : !isInLastPage}">
      <ChevronDoubleRightIcon class="h-4 w-4 dark:fill-white"/>
    </button>
  </div>
</template>


<script setup>
import {ChevronLeftIcon, ChevronRightIcon, ChevronDoubleLeftIcon, ChevronDoubleRightIcon} from "@heroicons/vue/solid"
import {computed} from "vue";

const emit = defineEmits(['pageChange'])

const props = defineProps({
  maxVisibleButtons: {
    type: Number,
    required: false,
    default: 3,
  },
  totalPages: {
    type: Number,
    required: true,
  },
  currentPage: {
    type: Number,
    required: true,
  },
});

const startPage = computed(() => {
  if (props.currentPage === 0) return 1;

  if (props.currentPage === props.totalPages - 1) return props.totalPages - props.maxVisibleButtons + 1;

  return props.currentPage;
});

const pages = computed(() => {
  const range = [];
  for (let i = startPage.value; i <= Math.min(startPage.value + props.maxVisibleButtons - 1, props.totalPages); i++) {
    if (i === 0 && Math.min(startPage.value + props.maxVisibleButtons - 1, props.totalPages) === 2)
      continue;
    range.push({name: i, isDisabled: i === props.currentPage + 1});
  }
  return range;
});

const isInFirstPage = computed(() => {
  return props.currentPage === 0;
});

const isInLastPage = computed(() => {
  return props.currentPage === props.totalPages - 1;
});

function firstPage() {
  emit("pageChange", 0);
}

function previousPage() {
  emit("pageChange", props.currentPage - 1);
}

function selectedPage(page) {
  emit("pageChange", page);
}

function nextPage() {
  emit("pageChange", props.currentPage + 1);
}

function lastPage() {
  emit("pageChange", props.totalPages - 1);
}
</script>