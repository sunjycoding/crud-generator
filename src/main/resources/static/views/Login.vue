<template>
  <el-form
      ref="loginForm"
      :model="mysqlInfoDTO"
      :rules="loginFormRules"
      label-width="auto"
  >
    <el-form-item label="MySQL IP" prop="ip">
      <el-input placeholder="MySQL IP" v-model="mysqlInfoDTO.ip"/>
    </el-form-item>
    <el-form-item label="MySQL Port" prop="port">
      <el-input placeholder="MySQL Port" v-model="mysqlInfoDTO.port"/>
    </el-form-item>
    <el-form-item label="MySQL DB Name" prop="dbName">
      <el-input placeholder="MySQL DB Name" v-model="mysqlInfoDTO.dbName"/>
    </el-form-item>
    <el-form-item label="MySQL User" prop="user">
      <el-input placeholder="MySQL User" v-model="mysqlInfoDTO.user"/>
    </el-form-item>
    <el-form-item label="MySQL Password" prop="password">
      <el-input placeholder="MySQL Password" type="password" v-model="mysqlInfoDTO.password"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleLoginClick" :loading="loginButtonLoading">
        Login
      </el-button>
    </el-form-item>
  </el-form>
</template>
<script>

export default {
  data() {
    return {
      loginFormRules: {},
      mysqlInfoDTO: {
        ip: "172.16.22.136",
        port: "3306",
        dbName: "db_wisdom_control_platform",
        user: "root",
        password: "d9fv-B8=y54R0",
      },
      loginButtonLoading: false
    }
  },
  created() {
  },

  methods: {
    handleLoginClick() {
      this.$refs["loginForm"].validate((valid) => {
        if (valid) {
          this.loginButtonLoading = true
          axios
              .post(loginApi, this.mysqlInfoDTO)
              .then((response) => {
                if (response && response.data.code === 0) {
                  localStorage.setItem("connected", "true")
                  this.$router.push("/table")
                  this.$message.success("Connected Succcessfully");
                }
                this.loginButtonLoading = false
              })
              .catch(() => {
                this.loginButtonLoading = false
              })
        }
      })
    }
  },
}
</script>

<style scoped>
</style>
