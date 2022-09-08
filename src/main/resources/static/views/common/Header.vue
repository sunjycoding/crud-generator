<template>
  <div class="header">
    <div class="logo-wrapper">
      <el-image style="width: 100px; height: 100px"
                src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
                fit="fill"
      />
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
  cursor: pointer;
}

.header .title {
  font-size: 2em;
  margin: auto 300px;
}
</style>