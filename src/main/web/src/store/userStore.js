import {defineStore} from 'pinia'
import axios from "axios"

export const useUserStore = defineStore('user', {
    state: () => {
        return {
            name: sessionStorage.getItem("name") || null,
            email: sessionStorage.getItem("email") || null,
            image: sessionStorage.getItem("image") || null,
            token: sessionStorage.getItem("token") || null,
            provider: sessionStorage.getItem("provider") || null,
        }
    },


    getters: {
        getName: (state) => state.name,
        getEmail: (state) => state.email,
        getImage: (state) => state.image,
        getToken: (state) => state.token,
        getProvider: (state) => state.provider,
        getIsUserAuth: state => { return state.email != null }
    },

    actions: {
        login(user, url) {
            return new Promise((resolve, reject) => {
                axios.post(url, {email: user.value.email, password: user.value.password})
                    .then(response => {
                        this.name = response.data.appUserDto.name
                        this.email = response.data.appUserDto.email
                        this.image= response.data.appUserDto.image
                        this.token = response.data.jwt
                        this.provider = response.data.appUserDto.provider
                        sessionStorage.setItem("name", this.name)
                        sessionStorage.setItem("email", this.email)
                        sessionStorage.setItem("image", this.image)
                        sessionStorage.setItem("token", this.token)
                        sessionStorage.setItem("provider", this.provider)
                        resolve(response)
                    }).catch(error => {
                    reject(error.response)
                })
            })
        },
        logout(){
            return new Promise((resolve => {
                sessionStorage.removeItem("image")
                sessionStorage.removeItem("email")
                sessionStorage.removeItem("name")
                sessionStorage.removeItem("token")
                sessionStorage.removeItem("provider")
                location.reload()
                resolve()
            }))
        },
    },

})
