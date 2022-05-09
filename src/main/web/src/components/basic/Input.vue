<template>
  <div>
    <input :name="name" :type="type"
           class="block w-full bg-white border border-gray-300 rounded-md py-2 pl-3 pr-3 text-sm placeholder-gray-500 focus:outline-none focus:text-gray-900 focus:placeholder-gray-400 focus:ring-1 focus:ring-blue-500 dark:focus:ring-blue-400 sm:text-sm"
           :placeholder="placeholder"
           :value="modelValue"
           @input="$emit('update:modelValue',$event.target.value)"
           ref="input"/>
  </div>
</template>

<script setup>
defineProps({
  name: {
    type: String,
    default: 'name'
  },
  modelValue: {
    type: String,
    default: ''
  },
  placeholder: {
    type: String,
    default: 'Placeholder'
  },
  type: {
    type: String,
    default: 'text'
  }
})
defineEmits(['update:modelValue'])

</script>