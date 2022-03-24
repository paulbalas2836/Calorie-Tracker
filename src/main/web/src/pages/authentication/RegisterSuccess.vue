<template>
  <Card>
    <template #header>
      <div class="dark:text-white font-medium text-center text-3xl">One more step...</div>
    </template>
    <template #main>
      <div class="dark:text-white font-medium text-center text-xl">An email has been sent to verify your account</div>
    </template>
  </Card>
</template>

<script setup>
import Card from "../../components/basic/AuthenticationCard.vue"
</script>