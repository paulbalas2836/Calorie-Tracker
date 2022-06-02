<template>
  <div class="overflow-x-auto">
    <table class="table-auto w-full">
      <thead>
      <tr class="bg-light-mode-green dark:bg-dark-mode-green">
        <th v-for="(column, columnIndex) in Object.keys(items.length !== 0 ? items[0] : [])" :key="columnIndex"
            class="sm:px-4 sm:py-6 p-2 text-white dark:text-black">{{
            columnName(column)
          }}
        </th>
        <th class="sm:px-4 sm:py-6 p-2 text-white dark:text-black">
          Actions
        </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, itemIndex) in items" :key="itemIndex" class="border-x border-b">
        <td class="text-center sm:px-4 sm:py-6 p-2 dark:text-white"
            v-for="(itemData, itemDataIndex) in Object.values(item)" :key="itemDataIndex">
          {{ itemData }}
        </td>
        <td class="sm:px-4 sm:py-6 p-2 flex items-center justify-center">
          <Button class="mr-2" @click="updateRow(item)">
            Update
          </Button>
          <Button class="dark:bg-red-500 hover:dark:bg-red-400 bg-red-600 hover:bg-red-500" @click="deleteRow(item)">
            Delete
          </Button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import Button from "../basic/Button.vue"

defineProps({
  items: {
    type: Array,
    default: () => [],
  }
});
const emit = defineEmits(['updateRow', 'deleteRow']);

const columnName = function (column) {
  return column[0].toUpperCase() + column.slice(1);
}

function updateRow(item) {
  emit('updateRow', item);
}

function deleteRow(item) {
  emit('deleteRow', item.id);
}

</script>