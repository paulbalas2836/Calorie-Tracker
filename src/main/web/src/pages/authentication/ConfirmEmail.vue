<template>
  <Card>
    <template #main>
      <div class="flex flex-col sm:justify-center items-center">
        <div class="text-center dark:text-white text-xl font-medium">Your email address has been verified with
          success!
        </div>
        <BadgeCheckIcon class="dark:fill-dark-mode-green w-24 fill-dark-mode-green"></BadgeCheckIcon>
      </div>
    </template>
  </Card>
</template>

<script setup>
import Card from "../../components/basic/AuthenticationCard.vue"
import {BadgeCheckIcon} from "@heroicons/vue/solid"
</script>
