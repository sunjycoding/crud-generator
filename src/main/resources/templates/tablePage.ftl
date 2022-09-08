<template>
  <el-container>
    <el-main>
      <el-form :model="${tableNameFirstLetterLowercase}ConditionDTO" :inline="true">
        <el-form-item label="参数Key值">
          <el-input
            v-model="${tableNameFirstLetterLowercase}ConditionDTO.paramKey"
            placeholder="参数Key值"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="listTableData">查询</el-button>
        </el-form-item>
        <el-form-item class="condition-last-btn">
          <el-button type="success" @click="handleAddClick">新增</el-button>
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
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <span class="operate-btn-group">
              <el-button type="primary" @click="handleUpdateClick(scope.row)">
                修改
              </el-button>
              <el-button type="danger" @click="handleDeleteClick(scope.row)">
                删除
              </el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
        :current-page="currentPage"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalElements"
        :page-sizes="[10, 20, 50, 100]"
      >
      </el-pagination>
      <${tableNameFirstLetterUppercase}AddOrUpdate
        v-if="addOrUpdateVisible"
        v-model:selectedData="selectedData"
        v-model:addOrUpdateVisible="addOrUpdateVisible"
        @refreshTableDataList="listTableData"
      />
    </el-main>
  </el-container>
</template>

<script>
import ${tableNameFirstLetterUppercase}AddOrUpdate from "./components/${tableNameFirstLetterUppercase}AddOrUpdate"
import { ${tableNameFirstLetterLowercase}PageApi, ${tableNameFirstLetterLowercase}Api } from "@/api/${moduleName}Api"

export default {
  components: { ${tableNameFirstLetterUppercase}AddOrUpdate },
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      totalElements: 0,
      tableColumnList: [
        <#list tableColumnDTOList as tableColumn>
        {
          prop: "${tableColumn.columnName}",
          label: "${tableColumn.remarks}",
        },
        </#list>
      ],
      ${tableNameFirstLetterLowercase}ConditionDTO: {},
      tableDataListLoading: false,
      tableDataList: [],
      addOrUpdateVisible: false,
      selectedData: {},
    }
  },
  created() {
    this.listTableData()
  },
  methods: {
    listTableData() {
      this.tableDataListLoading = true
      let params = {
        page: this.currentPage,
        size: this.pageSize,
      }
      this.axios
        .get(${tableNameFirstLetterLowercase}PageApi, {
          params: params,
        })
        .then((res) => {
          let response = res.data.data
          this.tableDataList = response.content
          this.totalElements = response.totalElements
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
    handleSizeChange(val) {
      this.pageSize = val
      this.listTableData()
    },
    handlePageChange(val) {
      this.currentPage = val
      this.listTableData()
    },
    handleAddClick() {
      this.addOrUpdateVisible = true
    },
    handleUpdateClick(row) {
      //复制一个对象, 防止原数据被修改
      this.selectedData = JSON.parse(JSON.stringify(row))
      this.addOrUpdateVisible = true
    },
    handleDeleteClick(row) {
      let id = row.id
      this.$confirm(`确定进行删除操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.axios
            .delete(${tableNameFirstLetterLowercase}Api + "/" + id)
            .then((res) => {
              if (res.data.code === 0) {
                this.$message({
                  message: "操作成功",
                  type: "success",
                })
              }
            })
            .then(() => {
              this.listTableData()
            })
        })
        .catch(() => {})
    },
  },
}
</script>

<style scoped>
</style>