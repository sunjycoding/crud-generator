const loadOptions = {
    moduleCache: {
        vue: Vue
    },
    async getFile(url) {
        const res = await fetch(url);
        if (!res.ok)
            throw Object.assign(new Error(res.statusText + ' ' + url), {res});
        return {
            getContentData: asBinary => asBinary ? res.arrayBuffer() : res.text(),
        }
    },
    addStyle(textContent) {
        const style = Object.assign(document.createElement('style'), {textContent});
        const ref = document.head.getElementsByTagName('style')[0] || null;
        document.head.insertBefore(style, ref);
    },
}
let {loadModule} = window['vue3-sfc-loader'];

let NotFound = Vue.defineAsyncComponent(() => loadModule('./views/common/Notfound.vue', loadOptions))
let Login = Vue.defineAsyncComponent(() => loadModule('./views/Login.vue', loadOptions))
let Layout = Vue.defineAsyncComponent(() => loadModule('./views/Layout.vue', loadOptions))
let Table = Vue.defineAsyncComponent(() => loadModule('./views/Table.vue', loadOptions))

let routes = [
    {
        path: "/:pathMatch(.*)*",
        name: "404",
        component: Layout,
        children: [
            {
                path: "/:pathMatch(.*)*",
                name: "404",
                component: NotFound,
            },
        ],
    },
    {
        path: "/login",
        name: "Login",
        component: Login,
    },
    {path: '/', component: Layout},
    {
        path: '/table',
        component: Layout,
        children: [
            {
                path: "/table",
                name: "数据库表",
                component: Table,
            },
        ],
    },
]

const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes,
})


router.beforeEach((to, from, next) => {
    next()
})
