<template>
  <el-dialog
      v-model="backendGenerateInfoDialogVisible"
      title="后端代码生成"
      width="30%"
      show-close
      center
      :before-close="beforeDialogClose"
      destroy-on-close
  >
    <div class="description">
      <p>
        <span style="font-weight: bold;color: #ff0000">说明: 例如Java类为com.company.module.ModuleExample.java</span>
        <br/>
        basePackage: com.company
        <br/>
        模块名: module
      </p>
    </div>
    <el-form
        ref="dataForm"
        :rules="dataRule"
        :model="requiredInfoDTO"
        label-width="auto"
    >
      <el-form-item label="作者" prop="author">
        <el-input placeholder="作者" v-model="requiredInfoDTO.author"/>
      </el-form-item>
      <el-form-item label="basePackage" prop="basePackage">
        <el-input placeholder="basePackage" v-model="requiredInfoDTO.basePackage"/>
      </el-form-item>
      <el-form-item label="模块名" prop="moduleName">
        <el-input placeholder="模块名" v-model="requiredInfoDTO.moduleName"/>
      </el-form-item>
      <el-form-item label="Controller路由路径" prop="routerPath">
        <el-input placeholder="Controller路由路径" v-model="requiredInfoDTO.routerPath"/>
      </el-form-item>
      <el-form-item label="Controller接口描述" prop="apiDescription">
        <el-input
            type="textarea"
            placeholder="Controller接口描述"
            v-model="requiredInfoDTO.apiDescription"
        />
      </el-form-item>
      <el-form-item label="DTO描述" prop="dtoDescription">
        <el-input
            type="textarea"
            placeholder="DTO描述"
            v-model="requiredInfoDTO.dtoDescription"
        />
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
    "update:backendGenerateInfoDialogVisible",
    "update:selectedData",
  ],
  props: {
    backendGenerateInfoDialogVisible: Boolean,
    selectedData: Object,
  },
  data() {
    return {
      okButtonLoading: false,
      dataRule: {
        author: [
          {required: true, message: "作者不能为空", trigger: "blur"},
        ],
        basePackage: [
          {required: true, message: "基础包路径不能为空", trigger: "blur"},
        ],
      },
      requiredInfoDTO: {
        author: "",
        basePackage: "",
        moduleName: "",
        routerPath: "",
        apiDescription: "",
        dtoDescription: ""
      }
    }
  },
  created() {
    this.setDefault()
  },
  methods: {
    setDefault() {
      //作者, basePackage, 取全局设置
      let author = localStorage.getItem("author")
      let basePackage = localStorage.getItem("basePackage")
      this.requiredInfoDTO.author = author ? author : ""
      this.requiredInfoDTO.basePackage = basePackage ? basePackage : ""
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
      this.$emit("update:backendGenerateInfoDialogVisible", false)
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
          let requestUri = tableGenerateBackendApi + "?tableName=" + this.selectedData.tableName
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
                link.download = 'crud-backend-' + this.selectedData.tableName + ".zip"
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
.description {
  margin: 0 auto 20px;
  text-align: center;
  width: 100%;
}
</style>
