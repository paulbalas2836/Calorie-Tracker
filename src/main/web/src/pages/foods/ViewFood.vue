<template>
  <div class="px-8 py-10 ">
    <div class="p-6 w-full h-fit bg-white dark:bg-neutral-900 rounded shadow-lg">
      <Suspense>
        <template #default>
          <FoodTable/>
        </template>
        <template #fallback>
          Loading ...
        </template>
      </Suspense>
    </div>
  </div>
</template>

<script setup>
import FoodTable from "./FoodTable.vue";
</script>