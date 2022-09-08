<template>
  <el-dialog
    v-model="addOrUpdateVisible"
    :title="selectedData.id ? '修改' : '新增'"
    width="30%"
    show-close
    center
    :before-close="beforeDialogClose"
    destroy-on-close
  >
    <el-form
      ref="dataForm"
      :rules="dataRule"
      :model="selectedData"
      label-width="auto"
    >
      <#list tableColumnDTOList as tableColumn>
      <el-form-item label="${tableColumn.remarks}" prop="${tableColumn.columnName}">
        <el-input placeholder="${tableColumn.remarks}" v-model="selectedData.${tableColumn.columnName}" />
      </el-form-item>
      </#list>
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
import { ${tableNameFirstLetterLowercase}Api } from "@/api/${moduleName}Api"

export default {
  emits: [
    "refreshTableDataList",
    "update:addOrUpdateVisible",
    "update:selectedData",
  ],
  props: {
    addOrUpdateVisible: Boolean,
    selectedData: Object,
  },
  data() {
    return {
      okButtonLoading: false,
      dataRule: {
        <#list tableColumnDTOList as tableColumn>
        ${tableColumn.columnName}: [
          { required: true, message: "${tableColumn.remarks}不能为空", trigger: "blur" },
        ],
        </#list>
      },
    }
  },
  created() {},
  methods: {
    beforeDialogClose() {
      this.$emit("update:addOrUpdateVisible", false)
      this.$emit("update:selectedData", {})
    },
    onClose() {
      this.beforeDialogClose()
      this.$emit("refreshTableDataList")
    },
    handleCancelClick() {
      this.beforeDialogClose()
    },
    handleOkClick() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.okButtonLoading = true
          if (this.selectedData.id) {
            this.update()
          } else {
            this.save()
          }
        }
      })
    },
    update() {
      this.axios.put(${tableNameFirstLetterLowercase}Api, this.selectedData).then((res) => {
        if (res.data.code === 0) {
          this.$message({
            message: "操作成功",
            type: "success",
          })
        }
        this.onClose()
        setTimeout(() => {
          this.okButtonLoading = false
        }, 200)
      })
    },
    save() {
      this.axios.post(${tableNameFirstLetterLowercase}Api, this.selectedData).then((res) => {
        if (res.data.code === 0) {
          this.$message({
            message: "操作成功",
            type: "success",
          })
        }
        this.onClose()
        setTimeout(() => {
          this.okButtonLoading = false
        }, 200)
      })
    },
  },
}
</script>

<style scoped>
</style>
