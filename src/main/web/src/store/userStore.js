import {defineStore} from 'pinia'
import axios from "axios";

export const useUserStore = defineStore('user', {
    state: () => {
        return {
            user: null,
            token: null,
        }
    },
    getters: {
        getUser: (state) => state.user,
        getToken: (state) => state.token
    },

    actions: {
        login(user, url) {
            console.log(user)
            axios.post(url, {email: user.value.email, password: user.value.password}).then(res => {
                this.user = res.data.appUserDto
                this.token = res.data.jwt

            }).catch(err => console.log(err.response.data))
        }
    }

})
