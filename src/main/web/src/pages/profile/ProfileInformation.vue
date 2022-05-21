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
                <img
                    :src="isImageUploaded ? profileImage : useUser.getImage"
                    class="rounded-full w-14 h-14 " alt=""/>
                <label
                    class="sm:ml-4 mt-8 sm:mt-0 cursor-pointer border border-transparent dark:text-gray-900 text-white rounded-md py-2 px-4 bg-light-mode-green hover:bg-light-mode-hover-green dark:hover:bg-dark-mode-hover-green dark:bg-dark-mode-green text-sm font-medium shadow-md">
                  <span> Change picture </span>
                  <Input id="file_upload" type="file" class="sr-only" @change="onFileSelected"
                         accept=".jpg, .jpeg, .png"/>
                </label>
              </div>
              <ErrorMessage>{{ profileImageError }}</ErrorMessage>
              <Button v-show="isImageUploaded" class="mt-4" @click="changeProfileImage">Save</Button>
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
import constants from "../../utils/FrozenConstants.js";

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

const profileImageError = ref(null);
const isImageUploaded = ref(false);
const profileImage = ref(null);
const profileImageData = new FormData();
const successMessage = ref('');

function passwordValidator(value) {
  if (!value || (value.trim().length === 0))
    return "A password is required!"

  if (value.length < 8)
    return "The password requires at least 8 characters!"

  return true
}

function profileImageValidator(value) {
  if (!(value.type.split('/')[0] === 'image'))
    return "File must be an image";

  if (value.size > 52428800)
    return "File is too big";

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

function onFileSelected(event) {
  if (event.target.files.length === 0)
    return;

  const file = event.target.files[0];
  if (profileImageValidator(file) !== true) {
    profileImageError.value = profileImageValidator(file);
    isImageUploaded.value = false;
    profileImage.value = null;
    if (profileImageData.get("image"))
      profileImageData.delete("image")
    return;
  }

  profileImage.value = URL.createObjectURL(file);
  profileImageData.append("image", file);
  profileImageError.value = null;
  isImageUploaded.value = true;
}

function changeProfileImage() {
  const url = constants.API + "/user/changeProfileImage/" + useUser.getEmail;
  useUser.changeProfileImage(url, profileImageData).catch((err)=>{
    profileImageError.value = err;
  });
}

const submitChangePassword = handleSubmit(values => {
  successMessage.value = ''
  axios.put(constants.API + "/user/changePassword/" + useUser.getEmail, {
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
