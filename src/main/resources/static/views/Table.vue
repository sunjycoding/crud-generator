<template>
  <el-container>
    <el-main>
      <el-form :model="tableConditionDTO" :inline="true">
        <el-form-item label="表名">
          <el-input
              v-model="tableConditionDTO.tableName"
              placeholder="表名"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="listTableData">查询</el-button>
          <span class="count-hint">共有{{ tableDataList.length }}张表</span>
        </el-form-item>
      </el-form>
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
        <el-table-column label="操作" header-align="center" align="center">
          <template #default="scope">
            <span class="operate-btn-group">
              <el-button type="primary" @click="handleTableColumnClick(scope.row)">
                详情
              </el-button>
              <el-button type="success" @click="handleBackendClick(scope.row)">
                后端代码生成
              </el-button>
              <el-button type="danger" @click="handleFrontendClick(scope.row)">
                前端代码生成
              </el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>
      <TableColumn
          v-if="tableColumnVisible"
          v-model:selectedData="selectedData"
          v-model:tableColumnVisible="tableColumnVisible"
          @refreshTableDataList="listTableData"
      />
      <BackendGenerateInfoDialog
          v-if="backendGenerateInfoDialogVisible"
          v-model:selectedData="selectedData"
          v-model:backendGenerateInfoDialogVisible="backendGenerateInfoDialogVisible"
      />
      <FrontendGenerateInfoDialog
          v-if="frontendGenerateInfoDialogVisible"
          v-model:selectedData="selectedData"
          v-model:frontendGenerateInfoDialogVisible="frontendGenerateInfoDialogVisible"
      />
    </el-main>
  </el-container>
</template>

<script>
import TableColumn from "./components/TableColumn.vue"
import BackendGenerateInfoDialog from "./components/BackendGenerateInfoDialog.vue"
import FrontendGenerateInfoDialog from "./components/FrontendGenerateInfoDialog.vue"

export default {
  components: {TableColumn, BackendGenerateInfoDialog, FrontendGenerateInfoDialog},
  data() {
    return {
      tableColumnList: [
        {
          prop: "tableName",
          label: "表名",
        },
        {
          prop: "remarks",
          label: "备注",
        },
      ],
      tableDataListLoading: false,
      tableDataList: [],
      tableConditionDTO: {},
      selectedData: {},
      tableColumnVisible: false,
      backendGenerateInfoDialogVisible: false,
      frontendGenerateInfoDialogVisible: false
    }
  },
  created() {
    this.listTableData()
  },
  methods: {
    listTableData() {
      this.tableDataListLoading = true
      let params = {}
      let tableName = this.tableConditionDTO["tableName"]
      if (tableName) {
        params["tableName"] = tableName
      }
      axios
          .get(tableApi, {
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

    handleTableColumnClick(row) {
      this.selectedData = JSON.parse(JSON.stringify(row))
      this.tableColumnVisible = true
    },
    handleBackendClick(row) {
      this.selectedData = JSON.parse(JSON.stringify(row))
      this.backendGenerateInfoDialogVisible = true
    },
    handleFrontendClick(row) {
      this.selectedData = JSON.parse(JSON.stringify(row))
      this.frontendGenerateInfoDialogVisible = true
    },

  },
}
</script>

<style scoped>
.count-hint {
  margin-left: 200px;
  color: red;
  font-weight: bold;
}
</style>