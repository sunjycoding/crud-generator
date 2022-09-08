<template>
  <div class="max-height">
    <el-container>
      <el-header class="header-wrapper">
        <Header/>
      </el-header>
      <div class="max-height">
        <el-container>
          <el-aside class="aside-wrapper">
            <SidebarMenu/>
          </el-aside>
          <el-container>
            <el-main class="main-wrapper">
              <el-card class="box-card">
                <router-view></router-view>
              </el-card>
            </el-main>
            <el-footer>
              <Footer/>
            </el-footer>
          </el-container>
        </el-container>
      </div>
    </el-container>
  </div>
</template>

<script>
import Header from "./common/Header.vue";
import SidebarMenu from "./common/SidebarMenu.vue";
import Footer from "./common/Footer.vue";

export default {
  components: {Header, SidebarMenu, Footer},
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
  padding: 15px 20px;
}

.box-card {
  height: 100%;
  overflow-y: auto;
}

</style>