<template>
  <div class="header">
    <div class="logo-wrapper">
      <el-image src="images/logo.png" style="width: 100%;" fit="fill"></el-image>
    </div>
    <div class="title">CRUD代码生成平台</div>
  </div>
</template>

<script>

export default {
  data() {
    return {};
  },
  created() {
  },
  methods: {},
};
</script>

<style scoped>
.header {
  height: 100%;
  display: flex;
}

.logo-wrapper {
  width: 240px;
  cursor: pointer;
}

.header .title {
  font-size: 2em;
  margin: auto 150px;
  border-left: 1px solid #eee;
}
</style>