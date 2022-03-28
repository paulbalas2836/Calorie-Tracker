import {createRouter, createWebHistory} from 'vue-router'

const About = () => import('../pages/About.vue')
const CheckCalories = () => import('../pages/CheckCalories.vue')
const CheckHistory = () => import('../pages/CheckHistory.vue')
const Register = () => import('../pages/authentication/Register.vue')
const ConfirmEmail = () => import('../pages/authentication/ConfirmEmail.vue')
const RegistrationSuccess = () => import('../pages/authentication/RegisterSuccess.vue')
const ProfileContent = () => import('../pages/profile/ProfileContent.vue')

const routes = [
    {
        path: '/',
        name: 'About',
        component: About
    },
    {
        path: '/Calories',
        name: 'Check Calories',
        component: CheckCalories
    },
    {
        path: '/History',
        name: 'History',
        component: CheckHistory
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/register/confirm',
        name: 'Confirm Email',
        component: ConfirmEmail
    },
    {
        path: '/register/success',
        name: 'Registration Success',
        component: RegistrationSuccess
    },
    {
        path: '/profile',
        name: 'Profile',
        component: ProfileContent
    },
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router