<template>
  <div class="bg-banner">
    <div class="login-box">
      <el-card>
        <div class="title">数据库连接</div>
        <el-form
            ref="loginForm"
            :model="mysqlInfoDTO"
            :rules="loginFormRules"
            label-width="auto"
            @keyup.enter="handleLoginClick"
        >
          <el-form-item label="MySQL IP" prop="ip">
            <el-input placeholder="MySQL IP" v-model="mysqlInfoDTO.ip"/>
          </el-form-item>
          <el-form-item label="MySQL 端口" prop="port">
            <el-input placeholder="MySQL 端口" v-model="mysqlInfoDTO.port"/>
          </el-form-item>
          <el-form-item label="MySQL 数据库名" prop="dbName">
            <el-input placeholder="MySQL 数据库名" v-model="mysqlInfoDTO.dbName"/>
          </el-form-item>
          <el-form-item label="MySQL 用户" prop="user">
            <el-input placeholder="MySQL 用户" v-model="mysqlInfoDTO.user"/>
          </el-form-item>
          <el-form-item label="MySQL 密码" prop="password">
            <el-input placeholder="MySQL 密码" type="password" v-model="mysqlInfoDTO.password" show-password/>
          </el-form-item>
          <div class="justify-center">
            <el-button type="primary" @click="handleLoginClick" :loading="loginButtonLoading" style="width: 200px">
              连接
            </el-button>
          </div>
        </el-form>
      </el-card>
    </div>
  </div>
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
                  this.$router.push("/table")
                  this.$message.success("数据库连接成功");
                  let ip = this.mysqlInfoDTO.ip
                  let port = this.mysqlInfoDTO.port
                  let dbName = this.mysqlInfoDTO.dbName
                  let currentDbUrl = ip + ":" + port + "/" + dbName
                  localStorage.setItem("currentDbUrl", currentDbUrl);
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
.bg-banner {
  width: 100%;
  height: 100%;
  color: #000000;
  margin: 0;
  padding: 0;
}

.login-box {
  display: flex;
  flex-direction: column;
  width: 450px;
  margin: 10% auto 0;
}

.title {
  text-align: center;
  font-size: 2em;
  font-weight: bold;
  margin: 40px auto;
}

.el-card {
  width: 450px;
}
</style>
