<template>
  <el-dialog
      v-model="frontendGenerateInfoDialogVisible"
      title="前端代码生成"
      width="30%"
      show-close
      center
      :before-close="beforeDialogClose"
      destroy-on-close
  >
    <el-form
        ref="dataForm"
        :rules="dataRule"
        :model="requiredInfoDTO"
        label-width="auto"
    >
      <el-form-item label="模块名" prop="moduleName">
        <el-input placeholder="模块名" v-model="requiredInfoDTO.moduleName"/>
      </el-form-item>
      <el-form-item label="CRUD请求基本路径（包含模块名）" prop="routerPath">
        <el-input placeholder="CRUD请求基本路径（包含模块名）" v-model="requiredInfoDTO.routerPath"/>
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
    "update:frontendGenerateInfoDialogVisible",
    "update:selectedData",
  ],
  props: {
    frontendGenerateInfoDialogVisible: Boolean,
    selectedData: Object,
  },
  data() {
    return {
      okButtonLoading: false,
      dataRule: {},
      requiredInfoDTO: {
        moduleName: "",
        routerPath: "",
      }
    }
  },
  created() {
    this.setDefault()
  },
  methods: {
    setDefault() {
      //模块名, 默认为去掉tb_后默认取第一个下划线之前的单词
      let tableName = this.selectedData.tableName
      let dropTbTableName = tableName.replace("tb_", "")
      let endIndex = dropTbTableName.indexOf("_")
      let firstWord = dropTbTableName.substring(0, endIndex)
      this.requiredInfoDTO.moduleName = firstWord
      //路由, 默认为模块后的单词, 并将下划线替换成中划线
      let suffix = dropTbTableName.substring(endIndex + 1, dropTbTableName.length)
      this.requiredInfoDTO.routerPath = firstWord + "/" + suffix.replaceAll("_", "-")
    },
    beforeDialogClose() {
      this.$emit("update:frontendGenerateInfoDialogVisible", false)
      this.$emit("update:selectedData", {})
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
          let requestUri = tableGenerateFrontendApi + "?tableName=" + this.selectedData.tableName
          axios({
            method: "POST",
            url: requestUri,
            data: this.requiredInfoDTO,
            responseType: "blob",
          })
              .then((response) => {
                this.okButtonLoading = false
                let link = document.createElement("a")
                link.href = window.URL.createObjectURL(new Blob([response.data]))
                link.download = 'crud-frontend-' + this.selectedData.tableName + ".zip"
                link.click()
                this.onClose()
              })
              .catch(() => {
                this.okButtonLoading = false
              })
        }
      })
    },
  },
}
</script>

<style scoped>
</style>
