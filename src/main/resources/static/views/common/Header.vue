<template>
  <div class="header">
    <div class="logo-wrapper">
      <el-image src="images/logo.png" style="width: 100%;" fit="fill"></el-image>
    </div>
    <div class="title">CRUD代码生成平台</div>
    <div class="right-box">
      <div class="btn-group-wrapper">
        <el-tooltip
            effect="dark"
            content="全局设置"
            placement="left"
        >
          <el-button size="large" text @click="handleGlobalSettingClick">
            <el-icon :size="30">
              <Setting/>
            </el-icon>
          </el-button>
        </el-tooltip>
        <el-tooltip
            effect="dark"
            content="切换数据库"
            placement="right"
        >
          <el-button size="large" text @click="handleSwitchDatabaseClick">
            <el-icon :size="30">
              <Switch/>
            </el-icon>
          </el-button>
        </el-tooltip>
      </div>
    </div>
    <GlobalSettingDialog
        v-if="globalSettingDialogVisible"
        v-model:globalSettingDialogVisible="globalSettingDialogVisible"
    />
  </div>
</template>

<script>
import GlobalSettingDialog from "./GlobalSettingDialog.vue"

export default {
  components: {GlobalSettingDialog},
  data() {
    return {globalSettingDialogVisible: false};
  },
  created() {
  },
  methods: {
    handleGlobalSettingClick() {
      this.globalSettingDialogVisible = true
    },

    handleSwitchDatabaseClick() {
      this.$router.push("/login")
    }
  },
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
}

.right-box {
  margin: auto;
  float: right;
}

.btn-group-wrapper {
  margin-left: 500px;
}
</style>