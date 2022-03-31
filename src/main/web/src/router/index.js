import {createRouter, createWebHistory} from 'vue-router'
import {useUserStore} from "../store/userStore";

const About = () => import('../pages/About.vue')
const CheckCalories = () => import('../pages/check_calories/CheckCalories.vue')
const CheckHistory = () => import('../pages/CheckHistory.vue')
const Register = () => import('../pages/authentication/Register.vue')
const ConfirmEmail = () => import('../pages/authentication/ConfirmEmail.vue')
const RegistrationSuccess = () => import('../pages/authentication/RegisterSuccess.vue')
const ProfileContent = () => import('../pages/profile/ProfileContent.vue')

const routes = [
    {
        path: '/',
        name: 'About',
        component: About,
        meta: {requireUserAuth: false, requireUserNoAuth: false}
    },
    {
        path: '/Calories',
        name: 'Check Calories',
        component: CheckCalories,
        meta: {requireUserAuth: false, requireUserNoAuth: false}
    },
    {
        path: '/History',
        name: 'History',
        component: CheckHistory,
        meta: {requireUserAuth: true, requireUserNoAuth: false}
    },
    {
        path: '/register',
        name: 'Register',
        component: Register,
        meta: {requireUserAuth: false, requireUserNoAuth: true}
    },
    {
        path: '/register/confirm',
        name: 'Confirm Email',
        component: ConfirmEmail,
        meta: {requireUserAuth: false, requireUserNoAuth: true}
    },
    {
        path: '/register/success',
        name: 'Registration Success',
        component: RegistrationSuccess,
        meta: {requireUserAuth: false, requireUserNoAuth: true}
    },
    {
        path: '/profile',
        name: 'Profile',
        component: ProfileContent,
        meta: {requireUserAuth: true, requireUserNoAuth: false}
    },
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.meta.requireUserAuth && !useUserStore().getIsUserAuth) {
        next({name: 'About'})
    } else if (to.meta.requireUserNoAuth && useUserStore().getIsUserAuth) {
        next({name: 'About'})
    } else next()
})

export default router