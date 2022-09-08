const app = Vue.createApp()

let prefix = "/generator"
axios.defaults.baseURL = prefix;
axios.defaults.timeout = 200 * 1000;
axios.defaults.headers["Content-Type"] = "application/json;charset=UTF-8"

app.use(router)
app.use(ElementPlus)
app.mount('#app')
