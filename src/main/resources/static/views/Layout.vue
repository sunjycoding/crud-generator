<template>
  <el-container>
    <el-header class="header-wrapper">
      <Header/>
    </el-header>
    <el-container>
      <el-aside class="aside-wrapper">
        <SidebarMenu/>
      </el-aside>
      <el-container>
        <el-main class="main-wrapper">
          <div class="content-wrapper">
            <router-view></router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import Header from "./common/Header.vue";
import SidebarMenu from "./common/SidebarMenu.vue";

export default {
  components: {Header, SidebarMenu},
  data() {
    return {};
  },
  created() {
  },
  methods: {},
};
</script>

<style scoped>
.header-wrapper {
  background: #ffffff;
  height: 90px;
  display: block !important;
  border-bottom: 1px solid #eaecef;
}

.aside-wrapper {
  width: 240px;
}

.main-wrapper {
  background: #e9f1ff;
  height: 100%;
  width: 100%;
  padding: 5px 20px;
}

</style>