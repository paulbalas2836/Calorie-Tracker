<template>
  <ModalBase>
    <template #header>
      <h2 class="text-2xl text-dark dark:text-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">Sign in to your account</h2>
    </template>
    <template #default>
      <div class="mb-6">
        <Label label="Email address" for="login_email"/>
        <Input placeholder="email@gmail.com" name="login_email" v-model="loginForm.email"/>
      </div>

      <div>
        <Label label="Password" for="login_password"/>
        <Input placeholder="Password" type="password" v-model="loginForm.password" name="login_password"/>
      </div>

      <ErrorMessage v-for="error in errorMessage" class="mt-4 flex text-center">{{ error }}</ErrorMessage>
    </template>

    <template #footer>
      <Button @click="login"
              class="mb-4">
        Login
      </Button>
    </template>
  </ModalBase>
</template>

<script setup>
import ModalBase from "./ModalBase.vue"
import Input from "../basic/Input.vue"
import Label from "../basic/Label.vue"
import Button from "../basic/Button.vue"
import ErrorMessage from "../../components/basic/ErrorMessage.vue";
import {ref} from 'vue'
import {useUserStore} from '../../store/userStore'
import constants from "../../utils/FrozenConstants.js";

const emit = defineEmits(['closeSignInModal'])
const loginForm = ref({email: '', password: ''})
const userStore = useUserStore()
let errorMessage = ref([])

function login() {
  errorMessage.value = [];
  userStore.login(loginForm, constants.API + "/login").then(() => {
    emit("closeSignInModal")
  }).catch(err => {
    err.data.errors.forEach(err => {
      errorMessage.value.push(err)
    })
  })
}


</script>
