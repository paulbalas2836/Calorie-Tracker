<template>
  <div class="md:grid md:grid-cols-3 md:gap-6 mt-8">
    <div class="md:col-span-1">
      <div class="px-4 sm:px-0">
        <h3 class="text-lg font-medium leading-6 dark:text-white text-neutral-900">Disable Account</h3>
        <p class="text-sm dark:text-white text-neutral-900">You can disable your account any time you want</p>
      </div>
    </div>
    <div class="mt-5 md:mt-0 md:col-span-2">
      <div class="sm:overflow-hidden shadow-sm sm:rounded-md">
        <div class="dark:bg-neutral-900 px-4 py-5 space-y-6 sm:p-6 bg-white">
            <div class="flex flex-col">
              <p class="dark:text-white text-neutral-900">You won't be able to access your account, unless you enable it
                back </p>
              <Button class="dark:bg-red-500 hover:dark:bg-red-400 bg-red-600 hover:bg-red-500 w-24 " @click="disableAccount">Disable</Button>
              <ErrorMessage>{{ disableError }}</ErrorMessage>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Button from "../../components/basic/Button.vue"
import constants from "../../utils/FrozenConstants.js"
import ErrorMessage from "../../components/basic/ErrorMessage.vue"
import {useUserStore} from "../../store/userStore.js";
import {ref} from "vue";
import axios from "axios";

const useUser = useUserStore();
const disableError = ref(null);

function disableAccount() {
  const config = {
    headers: {
      Authorization: "Bearer " + useUser.getToken
    }
  };
  axios.patch(constants.API + '/user/disableAppUser/' + useUser.getEmail, config).then(() => {
    useUser.logout();
  }).catch(() => {
    disableError.value = "Oops, something went wrong!";
  });
}
</script>
