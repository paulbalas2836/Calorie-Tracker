import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import vClosable from "./directives/close";
import axios from "axios";
import VueAxios from "vue-axios";
import { createPinia } from 'pinia'
import './index.css'

const app = createApp(App)
app.use(createPinia())
app.use(VueAxios, axios)
app.use(router)
app.directive('closable', vClosable)
app.mount('#app')