import {createRouter, createWebHistory} from 'vue-router'
import {useUserStore} from "../store/userStore";

const Home = () => import('../pages/home/Home.vue')
const CheckCalories = () => import('../pages/check_calories/CheckCalories.vue')
const CheckHistory = () => import('../pages/check_history/CheckHistory.vue')
const Register = () => import('../pages/authentication/Register.vue')
const ConfirmEmail = () => import('../pages/authentication/ConfirmEmail.vue')
const RegistrationSuccess = () => import('../pages/authentication/RegisterSuccess.vue')
const ProfileContent = () => import('../pages/profile/ProfileContent.vue')
const ViewFood = () => import('../pages/foods/ViewFood.vue')

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        meta: {requireUserAuth: false, roles: ['USER', 'ADMIN']}
    },
    {
        path: '/Calories',
        name: 'Check Calories',
        component: CheckCalories,
        meta: {requireUserAuth: false, roles: ['USER', 'ADMIN']}
    },
    {
        path: '/History',
        name: 'History',
        component: CheckHistory,
        meta: {requireUserAuth: true, roles: ['USER', 'ADMIN']}
    },
    {
        path: '/register',
        name: 'Register',
        component: Register,
        meta: {requireUserAuth: false, roles: ['USER', 'ADMIN']}
    },
    {
        path: '/register/confirm',
        name: 'Confirm Email',
        component: ConfirmEmail,
        meta: {requireUserAuth: false, roles: ['USER', 'ADMIN']}
    },
    {
        path: '/register/success',
        name: 'Registration Success',
        component: RegistrationSuccess,
        meta: {requireUserAuth: false, roles: ['USER', 'ADMIN']}
    },
    {
        path: '/profile',
        name: 'Profile',
        component: ProfileContent,
        meta: {requireUserAuth: true, roles: ['USER', 'ADMIN']}
    },
    {
        path: '/viewFood',
        name: 'View Food',
        component: ViewFood,
        meta: {requireUserAuth: true, roles: ['ADMIN']}
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.meta.requireUserAuth) {
        if (to.meta?.roles?.includes(useUserStore().getRole))
            next();
    } else next();
})

export default router