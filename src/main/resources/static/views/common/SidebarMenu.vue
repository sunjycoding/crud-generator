<template>
  <el-menu
      default-active="/table"
      @open="handleOpen"
      @close="handleClose"
      :unique-opened="true"
      router
      text-color="#ffffff"
  >
    <el-menu-item index="/table">
      <span>数据库表信息</span>
    </el-menu-item>
    <el-menu-item index="/connection">
      <span>连接信息</span>
    </el-menu-item>
    <el-menu-item index="/log">
      <span>日志</span>
    </el-menu-item>
    <el-menu-item index="/monitor">
      <span>监控</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  components: {},
  data() {
    return {};
  },
  created() {
  },
  methods: {
    handleOpen() {
    },
    handleClose() {
    },
  },
};
</script>

<style scoped>
</style>