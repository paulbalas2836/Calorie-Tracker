<template>
  <div class="md:grid md:grid-cols-3 md:gap-6 mt-8">
    <div class="md:col-span-1">
      <div class="px-4 sm:px-0">
        <h3 class="text-lg font-medium leading-6 dark:text-white text-neutral-900">Profile</h3>
        <p class="text-sm dark:text-white text-neutral-900">This is your personal information, be careful with who you
          share it</p>
      </div>
    </div>
    <div class="mt-5 md:mt-0 md:col-span-2">
      <div class="sm:overflow-hidden shadow-sm sm:rounded-md">
        <div class="dark:bg-neutral-900 px-4 py-5 space-y-6 sm:p-6 bg-white">
          <div class="grid sm:grid-cols-3 gap-6 mb-12">
            <div class="sm:col-span-2 mb-12 sm:mb-0">
              <form @submit.prevent="submitChangePassword()" class="flex flex-col max-w-sm">
                <SuccessMessage>{{ successMessage }}</SuccessMessage>
                <div class="mt-4">
                  <Label for="change_current_password" label="Current Password"/>
                  <Input placeholder="Current Password" id="change_current_password"
                         v-model="currentPassword" type="password"/>
                  <ErrorMessage>{{ currentPasswordError }}</ErrorMessage>
                </div>

                <div class="mt-4">
                  <Label for="change_new_password" label="New Password"/>
                  <Input placeholder="New Password" id="change_new_password" type="password"
                         v-model="newPassword"/>
                  <ErrorMessage>{{ newPasswordError }}</ErrorMessage>
                </div>

                <div class="mt-4">
                  <Label for="change_confirm_new_password" label="Confirm New Password"/>
                  <Input placeholder="Confirm New Password" id="change_confirm_new_password"
                         v-model="confirmNewPassword" type="password"/>
                  <ErrorMessage>{{ confirmNewPasswordError }}</ErrorMessage>
                </div>
                <Button type="submit" :disabled="isSubmitting" class="mt-8 sm:self-end self-center w-24">Change</Button>
              </form>
            </div>
            <div class="my-auto">
              <Label for="photo" label="Photo" class="sm:ml-1 ml-2 hidden sm:flex"/>
              <div class="flex flex-col sm:flex-row items-center">
                <Label for="photo" label="Photo" class="sm:hidden"/>
                <span class="sr-only">Open user menu</span>
                <img :src="useUser.getImage" class="rounded-full w-14 h-14 " alt=""/>
                <Button class="h-10 w-24 self-center sm:ml-8 mt-8 sm:mt-0">Choose</Button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Label from "../../components/basic/Label.vue";
import {useUserStore} from "../../store/userStore";
import Button from "../../components/basic/Button.vue";
import Input from "../../components/basic/Input.vue";
import {useForm, useField} from 'vee-validate';
import ErrorMessage from "../../components/basic/ErrorMessage.vue"
import axios from "axios"
import SuccessMessage from "../../components/basic/SuccessMessage.vue";
import {ref} from 'vue'
import {BACKEND_URL} from "../../Constants.js";

const {handleSubmit, isSubmitting, setFieldError} = useForm();
const useUser = useUserStore()

const {
  value: currentPassword,
  errorMessage: currentPasswordError
} = useField('currentPassword', passwordValidator, {initialValue: ''})
const {
  value: newPassword,
  errorMessage: newPasswordError
} = useField('newPassword', newPasswordValidator, {initialValue: ''})
const {
  value: confirmNewPassword,
  errorMessage: confirmNewPasswordError
} = useField('confirmNewPassword', confirmNewPasswordValidator, {initialValue: ''})
const successMessage = ref('')

function passwordValidator(value) {
  if (!value || (value.trim().length === 0))
    return "A password is required!"

  if (value.length < 8)
    return "The password requires at least 8 characters!"

  return true
}

function newPasswordValidator(value) {
  if (!value || (value.trim().length === 0))
    return "A password is required!"

  if (value === currentPassword.value)
    return "Your new password should be different from the current one!"

  if (value.length < 8)
    return "The password requires at least 8 characters!"

  return true
}

function confirmNewPasswordValidator(value) {
  if (!value || (value.trim().length === 0))
    return "Confirm your password!"

  if (!(value === newPassword.value))
    return "The passwords don't match!"

  return true

}

const submitChangePassword = handleSubmit(values => {
  successMessage.value = ''
  axios.put(BACKEND_URL + "user/changePassword/" + useUser.getEmail, {
    currentPassword: values.currentPassword,
    newPassword: values.newPassword,
    confirmNewPassword: values.confirmNewPassword,
  }, {
    headers: {
      Authorization: "Bearer " + useUser.getToken
    }
  }).then(() => successMessage.value = "Password has successfully been changed!")
      .catch(error => {
        if (error.response)
          if (error.response.data.includes('Current'))
            setFieldError('currentPassword', error.response.data)
      })
})

</script>
