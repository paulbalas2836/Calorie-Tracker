<template>
  <ModalBase>
    <template #header>
      <h2 class="mt-4 text-2xl text-dark dark:text-white">Sign in to your account</h2>
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
      <ErrorMessage>{{ errorMessage }}</ErrorMessage>
    </template>

    <template #footer>
      <Button @click="login"
              class="mb-4 dark:bg-fuchsia-300 dark:hover:bg-fuchsia-200 bg-emerald-500 hover:bg-emerald-400 dark:text-black text-white">
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
import constants from "../../FrozenConstants.js";

const emit = defineEmits(['closeSignInModal'])
const loginForm = ref({email: '', password: ''})
const userStore = useUserStore()
let errorMessage = ref('')

function login() {
  userStore.login(loginForm, constants.BACKEND_URL + "login").then(() => {
    emit("closeSignInModal")
  }).catch(err => {
    err.data.errors.forEach(err => {
      if (err.includes('enable')) {
        errorMessage.value = err
      } else
        errorMessage.value = "Email and password don't match"
    })


  })
}


</script>
