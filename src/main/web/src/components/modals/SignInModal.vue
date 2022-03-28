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
<!--      <hr v-show="check" class="dark_style">-->
<!--      <hr v-show="!check" class="light_style">-->
      <!--      <div-->
      <!--          class="mt-4 border border-2 px-2 py-4 dark:text-white text-black grid grid-flow-col cursor-pointer rounded-md">-->
      <!--        <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px"-->
      <!--             width="24" height="24"-->
      <!--             viewBox="0 0 48 48"-->
      <!--             style=" fill:#000000;">-->
      <!--          <path fill="#FFC107"-->
      <!--                d="M43.611,20.083H42V20H24v8h11.303c-1.649,4.657-6.08,8-11.303,8c-6.627,0-12-5.373-12-12c0-6.627,5.373-12,12-12c3.059,0,5.842,1.154,7.961,3.039l5.657-5.657C34.046,6.053,29.268,4,24,4C12.955,4,4,12.955,4,24c0,11.045,8.955,20,20,20c11.045,0,20-8.955,20-20C44,22.659,43.862,21.35,43.611,20.083z"></path>-->
      <!--          <path fill="#FF3D00"-->
      <!--                d="M6.306,14.691l6.571,4.819C14.655,15.108,18.961,12,24,12c3.059,0,5.842,1.154,7.961,3.039l5.657-5.657C34.046,6.053,29.268,4,24,4C16.318,4,9.656,8.337,6.306,14.691z"></path>-->
      <!--          <path fill="#4CAF50"-->
      <!--                d="M24,44c5.166,0,9.86-1.977,13.409-5.192l-6.19-5.238C29.211,35.091,26.715,36,24,36c-5.202,0-9.619-3.317-11.283-7.946l-6.522,5.025C9.505,39.556,16.227,44,24,44z"></path>-->
      <!--          <path fill="#1976D2"-->
      <!--                d="M43.611,20.083H42V20H24v8h11.303c-0.792,2.237-2.231,4.166-4.087,5.571c0.001-0.001,0.002-0.001,0.003-0.002l6.19,5.238C36.971,39.205,44,34,44,24C44,22.659,43.862,21.35,43.611,20.083z"></path>-->
      <!--        </svg>-->
      <!--        Continue with google-->
      <!--      </div>-->
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

const URL = 'http://localhost:8080/login'

const check = !localStorage.getItem('theme') ? true : (localStorage.theme === 'dark')
const emit = defineEmits(['closeSignInModal'])
const loginForm = ref({email: '', password: ''})
const userStore = useUserStore()
let errorMessage = ref('')

function login() {
  userStore.login(loginForm, URL).then(() => {
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

<style scoped>
hr.dark_style {
  border: none;
  border-top: 3px ridge #e3dcdc;
  color: #9d9797;
  overflow: visible;
  text-align: center;
  height: 5px;
}

hr.dark_style:after {
  background: #171717;
  color: white;
  content: 'Or';
  padding: 0 4px;
  position: relative;
  top: -13px;
}

hr.light_style {
  border: none;
  border-top: 3px ridge #e3dcdc;
  color: #9d9797;
  overflow: visible;
  text-align: center;
  height: 5px;
}

hr.light_style:after {
  background: #ffffff;
  color: #9d9797;
  content: ' Or ';
  padding: 0 4px;
  position: relative;
  top: -13px;
}
</style>