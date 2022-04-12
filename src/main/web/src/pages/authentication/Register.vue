<template>
  <Card>
    <template #header>
      <div class="text-3xl mb-2">Sign Up</div>
      <div class="mb-4">Want to sign up fill this form!</div>
      <hr>
    </template>
    <template #main>
      <form @submit.prevent="submitRegister()">
        <div>
          <Label label="Name" for="registration_name"></Label>
          <Input placeholder="Name" name="name" id="registration_name" v-model="name"/>
          <ErrorMessage>{{ nameError }}</ErrorMessage>
        </div>


        <div class="mt-4">
          <Label label="Email Address" for="registration_email"></Label>
          <Input placeholder="email@example.domain" name="email" id="registration_email" v-model="email"/>
          <ErrorMessage>{{ emailError }}</ErrorMessage>
        </div>

        <div class="mt-4">
          <Label label="Password" for="registration_password"></Label>
          <Input placeholder="Password" type="password" name="password" id="registration_password" v-model="password"/>
          <ErrorMessage>{{ passwordError }}</ErrorMessage>
        </div>

        <div class="mt-4">
          <Label label="Confirm Password" for="registration_confirm_password"></Label>
          <Input placeholder="Confirm Password" type="password" name="confirm_password"
                 id="registration_confirm_password"
                 v-model="confirmPassword"/>
          <ErrorMessage>{{ confirmPasswordError }}</ErrorMessage>
        </div>

        <div class="flex items-center justify-end mt-8">
          <Button type="submit" :disabled="isSubmitting">
            Register
          </Button>
        </div>
      </form>
    </template>

  </Card>
</template>

<script setup>
import Card from "../../components/basic/AuthenticationCard.vue"
import Input from "../../components/basic/Input.vue"
import Label from "../../components/basic/Label.vue"
import Button from "../../components/basic/Button.vue";
import ErrorMessage from "../../components/basic/ErrorMessage.vue";
import {useForm, useField} from 'vee-validate';
import {useRouter} from 'vue-router'
import axios from 'axios'
import constants from "../../Constants.js";

const {handleSubmit, isSubmitting, setFieldError} = useForm();
const {value: email, errorMessage: emailError} = useField('email', emailValidator, {initialValue: ''})
const {value: name, errorMessage: nameError} = useField('name', nameValidator, {initialValue: ''})
const {value: password, errorMessage: passwordError} = useField('password', passwordValidator, {initialValue: ''})
const {
  value: confirmPassword,
  errorMessage: confirmPasswordError
} = useField('confirm_password', confirmPasswordValidator, {initialValue: ''})
const specialChars = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
const validEmailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
const router = useRouter()

function emailValidator(value) {
  if (!value || (value.trim().length === 0))
    return "An email address is required!"

  if (!validEmailRegex.test(value))
    return "This field must be a valid email";

  return true
}

function nameValidator(value) {
  if (!value || (value.trim().length === 0))
    return "A name is required!"

  if (specialChars.test(value))
    return "The name should not contain special characters"
  return true
}

function passwordValidator(value) {
  if (!value || (value.trim().length === 0))
    return "A password is required!"

  if (value.length < 8)
    return "The password requires at least 8 characters!"

  return true
}

function confirmPasswordValidator(value) {
  if (!value || (value.trim().length === 0))
    return "Confirm your password!"

  if (!(value === password.value))
    return "The passwords don't match!"

  return true
}

const submitRegister = handleSubmit(values => {
  axios.post(constants.BACKEND_URL + "register", {
    name: values.name,
    email: values.email,
    password: values.password,
    matchingPassword: values.confirm_password
  }).then(() =>
      router.push('/register/success')).catch(error => {
    if (error.response) {
      if (typeof error.response.data === 'string') {
        if (error.response.data.includes('email')) setFieldError('email', error.response.data)
        return;
      }
      error.response.data.errors.forEach(err => {
        if (err.includes('name')) {
          setFieldError('name', err)
        }
        if (err.includes('email')) {
          setFieldError('email', err)
        }
        if (err.includes('password')) {
          setFieldError('password', err)
        }
        if (err.includes('confirm')) {
          setFieldError('confirm_password', err)
        }
      })
    }
  })
})
</script>
