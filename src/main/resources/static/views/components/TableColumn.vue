<template>
  <el-dialog
      v-model="tableColumnVisible"
      :title="selectedData.tableName"
      width="30%"
      show-close
      center
      :before-close="beforeDialogClose"
      destroy-on-close
  >
    <el-table stripe :data="tableDataList" v-loading="tableDataListLoading">
      <el-table-column
          v-for="item in tableColumnList"
          :key="item"
          :prop="item.prop"
          :label="item.label"
          :formatter="item.formatter"
          show-overflow-tooltip
      >
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script>

export default {
  emits: [
    "refreshTableDataList",
    "update:tableColumnVisible",
    "update:selectedData",
  ],
  props: {
    tableColumnVisible: Boolean,
    selectedData: Object,
  },
  data() {
    return {
      tableColumnList: [
        {
          prop: "columnName",
          label: "列名",
        },
        {
          prop: "typeName",
          label: "Mysql类型",
        },
        {
          prop: "javaTypeName",
          label: "Java类型",
        },
        {
          prop: "columnSize",
          label: "大小",
        },
        {
          prop: "remarks",
          label: "备注",
        },
      ],
      tableDataListLoading: false,
      tableDataList: [],
    };
  },
  created() {
    this.listTableData()
  },
  methods: {
    listTableData() {
      if (!this.selectedData.tableName) {
        return
      }
      this.tableDataListLoading = true
      let params = {
        tableName: this.selectedData.tableName
      }
      axios
          .get(tableColumnApi, {
            params: params,
          })
          .then((response) => {
            this.tableDataList = response.data.data
            setTimeout(() => {
              this.tableDataListLoading = false
            }, 200)
          })
          .catch(() => {
            setTimeout(() => {
              this.tableDataListLoading = false
            }, 200)
          })
    },
    beforeDialogClose() {
      this.$emit("update:tableColumnVisible", false);
      this.$emit("update:selectedData", {});
    },
    onClose() {
      this.beforeDialogClose();
      this.$emit("refreshTableDataList");
    },
  },
};
</script>

<style scoped>
</style>
