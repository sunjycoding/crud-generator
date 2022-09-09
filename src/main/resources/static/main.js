const app = Vue.createApp()

let prefix = "/generator"
axios.defaults.baseURL = prefix
axios.defaults.timeout = 200 * 1000
axios.defaults.headers["Content-Type"] = "application/json;charset=UTF-8"

// Add a request interceptor
axios.interceptors.request.use(
    (config) => {
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

// Add a response interceptor
axios.interceptors.response.use(
    (response) => {
        if (response.config.responseType === 'blob') {
            return response
        }
        if (response.data.code !== 0) {
            ElementPlus.ElMessage.error(response.data.msg)
            //数据库连接失败
            if (response.data.code === 1) {
                localStorage.removeItem("currentDbUrl")
            }
        }
        return response
    },
    (error) => {
        console.log("response error")
        console.log(error)
        return Promise.reject(error)
    }
)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(router)
app.use(ElementPlus)
app.mount('#app')
