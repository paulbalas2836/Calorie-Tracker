import {defineStore} from 'pinia'
import axios from "axios"

export const useUserStore = defineStore('user', {
    state: () => {
        return {
            user: JSON.parse(sessionStorage.getItem("user")) || null,
            token: sessionStorage.getItem("token") || null,
        }
    },


    getters: {
        getName: (state) => state.user?.name,
        getEmail: (state) => state.user?.email,
        getImage: (state) => state.user?.image,
        getToken: (state) => state.token,
        getProvider: (state) => state.user?.provider,
        isUserAuth: state => state.user?.email != null,
    },

    actions: {
       async changeProfileImage(url, profileImageData) {
           try {
              const profileImage =  await axios.patch(url, profileImageData, {
                   headers: {
                       Authorization: "Bearer " + this.getToken
                   }
               });
               const currentUser = JSON.parse(sessionStorage.getItem("user"));
               currentUser.image = profileImage.data;
               sessionStorage.setItem("user", JSON.stringify(currentUser));
               this.user = currentUser;
           }catch(err) {
                throw new Error(err.response);
           }

       },
        login(user, url) {
            return new Promise((resolve, reject) => {
                axios.post(url, {email: user.value.email, password: user.value.password})
                    .then(response => {
                        sessionStorage.setItem("user", JSON.stringify(response.data.appUserDto));
                        sessionStorage.setItem("token", response.data.jwt);
                        this.user = response.data.appUserDto;
                        this.token = response.data.jwt;
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
                    sessionStorage.setItem("user", JSON.stringify(data.appUserDto));
                    sessionStorage.setItem("token", data.jwt);
                    this.user = data.appUserDto;
                    this.token = data.token;
                    resolve(data);
                }).catch(err => {
                    reject(err)
                })
            })
        },
        logout() {
            return new Promise((resolve => {
                sessionStorage.removeItem("user");
                sessionStorage.removeItem("token");
                location.reload();
                resolve();
            }))
        },
    },

})
