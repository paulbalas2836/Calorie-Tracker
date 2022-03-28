import {defineStore} from 'pinia'
import axios from "axios"

export const useUserStore = defineStore('user', {
    state: () => {
        return {
            user: sessionStorage.getItem("user") || null,
            token: sessionStorage.getItem("token") || null,
        }
    },


    getters: {
        getUser: (state) => state.user,
        getToken: (state) => state.token
    },

    actions: {
        login(user, url) {
            return new Promise((resolve, reject) => {
                axios.post(url, {email: user.value.email, password: user.value.password})
                    .then(response => {
                        this.user = response.data.appUserDto
                        this.token = response.data.jwt
                        sessionStorage.setItem("user", this.user)
                        sessionStorage.setItem("token", this.token)
                        resolve(response)
                    }).catch(error => {
                    reject(error.response)
                })
            })
        },
        logout(){
            return new Promise((resolve => {
                sessionStorage.removeItem("user")
                sessionStorage.removeItem("token")
                location.reload()
                resolve()
            }))
        },
    },

})
