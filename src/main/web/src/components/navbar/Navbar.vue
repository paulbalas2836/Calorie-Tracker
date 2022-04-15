<template>
  <div class="fixed w-full dark:bg-neutral-900 bg-blue-500">
    <div class="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
      <div class="relative flex items-center justify-between h-16">
        <div class="absolute inset-y-0 left-0 flex items-center sm:hidden">
          <MenuButton @click="mobileToggleNavbarPages"></MenuButton>
        </div>
        <div class="flex-1 flex items-center justify-center sm:items-stretch sm:justify-start">
          <div class="hidden sm:block sm:ml-6">
            <div class="flex space-x-4">
              <template v-for="page in navbarPages" :key="page.name">
                <router-link :to=page.route><a href="#"
                                               class="dark:hover:bg-fuchsia-200 dark:hover:text-black hover:bg-emerald-400 dark:text-white text-white px-3 py-2 rounded-md text-sm font-medium"
                                               :class="{'dark:bg-fuchsia-300 bg-emerald-500 dark:text-black': page.isActive }"
                                               aria-current="page">{{ page.name }}</a></router-link>
              </template>

            </div>
          </div>
        </div>
        <div class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
          <DarkModeToggle class="mr-4" :darkMode="darkMode" @click="toggleDarkLightMode"></DarkModeToggle>
          <div v-if="userStore.getIsUserAuth">
            <dropdown>
              <template #toggle>
                <button
                    class="bg-gray-800 flex text-sm rounded-full focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-blue-500 dark:focus:ring-offset-gray-800 focus:ring-white">
                  <span class="sr-only">Open user menu</span>
                  <img class="h-8 w-8 rounded-full"
                       :src="userStore.getImage"
                       alt="">
                </button>
              </template>
              <dropdown-content class="origin-top-right absolute">
                <router-link :to="userNavigation.profile.href" v-if="userStore.getProvider === 'LOCAL'">
                  <dropdown-items>
                    {{ userNavigation.profile.name }}
                  </dropdown-items>
                </router-link>
                <dropdown-items @click="logout">
                  {{ userNavigation.logout.name }}
                </dropdown-items>
              </dropdown-content>
            </dropdown>
          </div>
          <div v-else class="flex">
            <div class="mr-4">
              <div id="g_id_onload"
                   data-client_id="1078733011731-q159frn9qatl2h8iiqt11jkhh8o68nmq.apps.googleusercontent.com"
                   data-context="signin"
                   data-ux_mode="popup"
                   data-login_uri="http://localhost:8080/login/google"
                   data-callback=connectWithGoogle
                   data-auto_prompt="false">
              </div>

              <div class="g_id_signin"
                   data-type="icon"
                   data-shape="circle"
                   data-theme="outline"
                   data-text="signin_with"
                   data-size="large">
              </div>
            </div>
            <NavbarButtons class="sm:mr-4 mr-2 dark:hover:bg-fuchsia-200 " @click="openSignInModal">Sign in
            </NavbarButtons>
            <router-link to="/register">
              <NavbarButtons class="dark:hover:bg-fuchsia-200">Register</NavbarButtons>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="sm:hidden" id="mobile-menu" v-show="mobileNavbarPages">
    <div class="px-2 pt-2 pb-3 space-y-1 dark:bg-neutral-900 bg-blue-500">
      <template v-for="page in navbarPages" :key="page.name">
        <router-link :to=page.route><a href="#"
                                       class="text-white block px-3 py-2 rounded-md text-base font-medium"
                                       :class="{'dark:bg-fuchsia-300 bg-emerald-500 dark:text-black': page.isActive }"
                                       aria-current="page">{{ page.name }}</a></router-link>
      </template>
    </div>
  </div>
  <Transition enter-active-class="transition ease-out duration-400"
              enter-from-class="transform opacity-0 scale-0"
              enter-to-class="transform opacity-100 scale-100 "
              leave-active-class="transition ease-in duration-300"
              leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-100">
    <SignInModal v-if="signInModal" @closeModal="closeSignInModal" @closeSignInModal='closeSignInModal'/>
  </Transition>
</template>

<script setup>
import MenuButton from "./MenuButton.vue"
import NavbarButtons from "./NavbarButtons.vue"
import SignInModal from "../modals/SignInModal.vue";
import DarkModeToggle from "./DarkModeToggle.vue";
import {watch, ref} from 'vue'
import {useRoute} from 'vue-router'
import {useUserStore} from '../../store/userStore'
import Dropdown from "../dorpdown/Dropdown.vue"
import DropdownContent from "../dorpdown/DropdownContent.vue"
import DropdownItems from "../dorpdown/DropdownItems.vue"

const userNavigation = {
  profile: {name: 'Your Profile', href: 'Profile'},
  logout: {name: 'Sign out', href: '#'},
}
const navbarPages = ref([{name: "About", route: '/', isActive: false},
  {name: "Check Calories", route: "/Calories", isActive: false},
  {name: "History", route: "/History", isActive: false}])
const currentRouteName = useRoute();
const signInModal = ref(false)
const mobileNavbarPages = ref(false)
const darkMode = ref(!localStorage.getItem('theme') ? true : (localStorage.theme === 'dark'))
const userStore = useUserStore()

watch(currentRouteName, () => {
  navbarPages.value.forEach(el => {
    el.isActive = el.name === currentRouteName.name;
  })
})

function openSignInModal() {
  signInModal.value = true
}

function closeSignInModal() {
  signInModal.value = false
}

function logout() {
  userStore.logout()
}

function mobileToggleNavbarPages() {
  mobileNavbarPages.value = !mobileNavbarPages.value
}


function toggleDarkLightMode() {
  darkMode.value = !darkMode.value
  if (darkMode.value) {
    document.documentElement.classList.add('dark')
    localStorage.theme = 'dark'
    return
  }
  localStorage.theme = 'light'
  document.documentElement.classList.remove('dark')
}
</script>
