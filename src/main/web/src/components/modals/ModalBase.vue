<template>
  <div class="fixed z-20 inset-0 overflow-y-auto" aria-labelledby="modal-title" role="dialog" aria-modal="true">
    <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
      <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"
           @click="closeModal"></div>
      <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>
        <div
            class="relative inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg w-full">
          <div class="dark:bg-neutral-900 bg-white">
            <header class="flow-col justify-center text-center text-lg">
              <slot name="header"></slot>
            </header>
            <main class="px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
              <slot></slot>
            </main>
            <footer class="flex flex-col px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
              <slot name="footer"></slot>
            </footer>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup>
import {XIcon} from "@heroicons/vue/solid";
const emit = defineEmits(['closeModal'])

function closeModal() {
  emit('closeModal')
}
</script>
