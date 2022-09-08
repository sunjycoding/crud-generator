<template>
  <el-menu
      default-active="/table"
      @open="handleOpen"
      @close="handleClose"
      :unique-opened="true"
      router
  >
    <el-menu-item index="/table">
      <span>数据库表</span>
    </el-menu-item>
    <el-menu-item index="2" disabled>
      <span>日志</span>
    </el-menu-item>
    <el-menu-item index="3" disabled>
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