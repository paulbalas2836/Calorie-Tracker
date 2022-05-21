<template>
  <div class="w-full border border-transparent"
       :class="{'absolute z-10 bg-inherit' : homePage, 'bg-white dark:bg-neutral-900':!homePage}">
    <div class="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
      <div class="relative flex items-center justify-between h-16">
        <div class="absolute inset-y-0 left-0 flex items-center sm:hidden">
          <MenuButton @click="mobileToggleNavbarPages" :showMobileNavbarPages="showMobileNavbarPages"></MenuButton>
        </div>
        <div class="flex-1 flex items-center justify-center sm:items-stretch sm:justify-start">
          <div class="hidden sm:block sm:ml-6">
            <div class="flex space-x-4">
              <template v-for="page in navbarPages" :key="page.name">
                <router-link :to=page.route><a href="#"
                                               class=" dark:hover:bg-dark-mode-hover-green dark:hover:text-gray-900 hover:bg-light-mode-hover-green hover:text-white px-3 py-2 rounded-md text-lg font-signika-negative"
                                               :class="{ 'dark:bg-dark-mode-green bg-light-mode-green dark:text-black shadow-lg text-white': page.isActive, 'dark:text-white' : !homePage}"
                                               aria-current="page">{{ page.name }}</a></router-link>
              </template>

            </div>
          </div>
        </div>
        <div class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
          <DarkModeToggle v-if="!homePage" class="mr-4" :darkMode="darkMode"
                          @click="toggleDarkLightMode"/>
          <div v-if="userStore.isUserAuth">
            <dropdown>
              <template #toggle>
                <button
                    class="flex text-sm rounded-full focus:outline-none focus:ring-2 focus:ring-offset-2  focus:ring-light-mode-green "
                    :class="{'dark:focus:dark-mode-green dark:focus:ring-offset-neutral-900': !homePage}">
                  <span class="sr-only">Open user menu</span>
                  <img class="h-12 w-12 rounded-full"
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
            <div class="sm:mr-4 mr-2">
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
            <NavbarButtons class="sm:mr-4 mr-2" :homePage="homePage" @click="openSignInModal">Sign in
            </NavbarButtons>
            <router-link to="/register">
              <NavbarButtons :homePage="homePage" >Register</NavbarButtons>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="sm:hidden absolute z-30 w-full h-full bg-gray-900/60" id="mobile-menu " v-show="showMobileNavbarPages">
    <div class="px-2 pt-2 pb-3 space-y-1 dark:bg-neutral-900 bg-white h-full w-1/2 flex flex-col justify-start ">
      <MenuButton @click="mobileToggleNavbarPages" :showMobileNavbarPages="showMobileNavbarPages" v-if="homePage"></MenuButton>
      <template v-for="page in navbarPages" :key="page.name">
        <router-link :to=page.route><a href="#" @click="closeMobileNavbar()"
                                       class=" block px-3 py-2 rounded-md text-base font-medium dark:text-white text-black"
                                       :class="{'dark:bg-dark-mode-green bg-light-mode-green dark:text-black text-white': page.isActive }"
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
import {darkMode} from "../../utils/ReactiveConstants";

const userNavigation = {
  profile: {name: 'Your Profile', href: 'Profile'},
  logout: {name: 'Sign out', href: '#'},
}
const homePage = ref(false)
const navbarPages = ref([{name: "Home", route: '/', isActive: false},
  {name: "Check Calories", route: "/Calories", isActive: false},
  {name: "History", route: "/History", isActive: false}])
const currentRouteName = useRoute();
const signInModal = ref(false)
const showMobileNavbarPages = ref(false)
const userStore = useUserStore()

function openSignInModal() {
  signInModal.value = true
}

function closeSignInModal() {
  signInModal.value = false
}

function closeMobileNavbar(){
  showMobileNavbarPages.value = false;
}

function logout() {
  userStore.logout()
}

function mobileToggleNavbarPages() {
  showMobileNavbarPages.value = !showMobileNavbarPages.value
}

watch(showMobileNavbarPages, () => {
  showMobileNavbarPages.value === true ? document.body.style.overflow = "hidden" : document.body.style.overflow = "auto"
})

watch(currentRouteName, () => {
  navbarPages.value.forEach(el => {
    el.isActive = el.name === currentRouteName.name;
    homePage.value = currentRouteName.name === 'Home';
  })
})

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
