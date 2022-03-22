<template>
  <Card>
    <template #header>
      <div class="text-3xl mb-2">Sign Up</div>
      <div class="mb-4">Want to sign up fill this form!</div>
      <hr>
    </template>
    <template #main>
      <div>
        <Label label="Name" for="registration_name"></Label>
        <Input placeholder="Name" id="registration_name" v-model="form.name"/>
      </div>


      <div class="mt-4">
        <Label label="Email Address" for="registration_email"></Label>
        <Input placeholder="email@example.domain" id="registration_email" v-model="form.email"/>
      </div>

      <div class="mt-4">
        <Label label="Password" for="registration_password"></Label>
        <Input placeholder="Password" type="password" id="registration_password" v-model="form.password"/>
      </div>

      <div class="mt-4">
        <Label label="Confirm Password" for="registration_confirm_password"></Label>
        <Input placeholder="Confirm Password" type="password" id="registration_confirm_password"
               v-model="form.confirmPassword"/>
      </div>
    </template>

    <template #footer>
      <div class="flex items-center justify-end">
        <Button @click.enter="submitRegister"
                class="dark:bg-fuchsia-300 dark:hover:bg-fuchsia-200 bg-emerald-500 hover:bg-emerald-400 dark:text-black text-white">
          Register
        </Button>
      </div>
    </template>
  </Card>
</template>

<script setup>
import Card from "../../components/basic/AuthenticationCard.vue"
import Input from "../../components/basic/Input.vue"
import Label from "../../components/basic/Label.vue"
import Button from "../../components/basic/Button.vue";
import axios from 'axios'
import {ref} from 'vue'

const form = ref({name: '', email: '', password: '', confirmPassword: ''})
const url = 'http://localhost:8080/register'

function submitRegister() {
  axios.post(url, {
    name: form.value.name,
    email: form.value.email,
    password: form.value.password,
    matchingPassword: form.value.confirmPassword
  }).then(res => console.log(res)).catch(err => console.log(err))
}
</script>
