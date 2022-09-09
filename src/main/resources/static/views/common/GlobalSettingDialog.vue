<template>
  <el-dialog
      v-model="globalSettingDialogVisible"
      title="全局设置"
      width="30%"
      show-close
      center
      :before-close="beforeDialogClose"
      destroy-on-close
  >
    <el-form
        ref="dataForm"
        :rules="dataRule"
        :model="globalSetting"
        label-width="auto"
    >
      <el-form-item label="作者" prop="author">
        <el-input placeholder="作者" v-model="globalSetting.author"/>
      </el-form-item>
      <el-form-item label="basePackage" prop="author">
        <el-input placeholder="basePackage" v-model="globalSetting.basePackage"/>
      </el-form-item>
    </el-form>

    <template #footer>
      <span>
        <el-button type="info" @click="handleCancelClick">取消</el-button>
        <el-button
            type="primary"
            @click="handleOkClick"
            :loading="okButtonLoading"
        >
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>

export default {
  emits: [
    "update:globalSettingDialogVisible",
  ],
  props: {
    globalSettingDialogVisible: Boolean,
  },
  data() {
    return {
      okButtonLoading: false,
      dataRule: {},
      globalSetting: {
        author: localStorage.getItem("author") ? localStorage.getItem("basePackage") : "",
        basePackage: localStorage.getItem("basePackage") ? localStorage.getItem("basePackage") : ""
      }
    }
  },
  created() {
  },
  methods: {
    beforeDialogClose() {
      this.$emit("update:globalSettingDialogVisible", false)
    },
    onClose() {
      this.beforeDialogClose()
    },
    handleCancelClick() {
      this.beforeDialogClose()
    },
    handleOkClick() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.okButtonLoading = true
          localStorage.setItem("author", this.globalSetting.author)
          localStorage.setItem("basePackage", this.globalSetting.basePackage)
          this.okButtonLoading = false
          this.onClose()
          this.$message({
            message: "操作成功",
            type: "success",
          })
        }
      })
    },
  },
}
</script>

<style scoped>
</style>
