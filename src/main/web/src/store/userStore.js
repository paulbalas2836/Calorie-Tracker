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
        isUserAuth: state => state.email != null,
    },

    actions: {
        login(user, url) {
            return new Promise((resolve, reject) => {
                axios.post(url, {email: user.value.email, password: user.value.password})
                    .then(response => {
                        const {name, email, image, provider} = response.data.appUserDto;
                        sessionStorage.setItem("name", name);
                        sessionStorage.setItem("email", email);
                        sessionStorage.setItem("image", image);
                        sessionStorage.setItem("provider", provider);

                        sessionStorage.setItem("token", response.data.jwt);
                        location.reload();
                        resolve(response);
                    }).catch(error => {
                    reject(error.response);
                })
            })
        },
        loginWithGoogle(tokens) {
            return new Promise((resolve, reject) => {
                fetch(URL, {
                    method: 'POST',
                    mode: 'cors',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: tokens.credential,
                }).then(response => response.json()).then(data => {
                    sessionStorage.setItem("name", data.appUserDto.name);
                    sessionStorage.setItem("email", data.appUserDto.email);
                    sessionStorage.setItem("image", data.appUserDto.image);
                    sessionStorage.setItem("token", data.jwt);
                    sessionStorage.setItem("provider", data.appUserDto.provider);
                    resolve(data);
                    location.reload();
                }).catch(err => {
                    reject(err)
                })
            })
        },
        logout() {
            return new Promise((resolve => {
                sessionStorage.removeItem("image");
                sessionStorage.removeItem("email");
                sessionStorage.removeItem("name");
                sessionStorage.removeItem("token");
                sessionStorage.removeItem("provider");
                location.reload();
                resolve();
            }))
        },
    },

})
