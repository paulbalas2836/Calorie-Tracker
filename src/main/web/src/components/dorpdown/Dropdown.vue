<template>
  <div @click="toggle" v-closable="onClose">
    <slot name="toggle">
      <button>Toggle</button>
    </slot>
    <slot/>
  </div>
</template>

<script setup>
import {ref, provide} from "vue";

const sharedState = ref({active: false})

provide('sharedState', sharedState.value)

function toggle() {
  sharedState.value.active = !sharedState.value.active;
}

function onClose() {
  sharedState.value.active = false;
}

</script>
