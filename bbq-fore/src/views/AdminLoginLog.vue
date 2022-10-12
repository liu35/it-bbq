<template>
  <div class="mcontaner">
    <AdminHeader> </AdminHeader>
    <div class="block">
      <el-table
          :data="loginLogs"
          stripe
          style="width: 100%">
        <el-table-column
            prop="id"
            label="id"
            width="50">
        </el-table-column>
        <el-table-column
            prop="userId"
            label="user id"
            width="100">
        </el-table-column>
        <el-table-column
            prop="userName"
            label="user name">
        </el-table-column>
        <el-table-column
            prop="userRole"
            label="user role">
        </el-table-column>
        <el-table-column
            prop="loginTime"
            label="login time">
        </el-table-column>
      </el-table>

      <el-pagination class="mpage" background layout="prev, pager, next" :current-page="currentPage"
                     :page-size="pageSize" :total="total" @current-change=page>
      </el-pagination>

    </div>
  </div>


</template>

<script>
import AdminHeader from "@/components/AdminHeader";
import {MessageBox} from "element-ui";
export default {
  name: "AdminLoginLog",
  components: {AdminHeader},
  data() {
    return {
      loginLogs: [],
      currentPage: 1,
      total: 0,
      pageSize: 10
    }
  },
  methods: {
    page(currentPage) {
      const _this = this

      _this.$axios.get("/user/admin/loginLog?pageNum=" + currentPage).then(res => {
        console.log(res)
        _this.loginLogs = res.data.data.records
        _this.currentPage = res.data.data.current
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size

      })
    },
    setAdmin(userId) {
      const _this = this
      _this.$axios.get("/user/admin/role/set?userId="+userId+'&role=ADMIN').then(res => {
        MessageBox.alert("set admin success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage)
      })
    },
    setUser(userId) {
      const _this = this
      _this.$axios.get("/user/admin/role/set?userId="+userId+'&role=USER').then(res => {
        MessageBox.alert("set user success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage)
      })
    },
    setDisabled(userId) {
      const _this = this
      _this.$axios.get("/user/admin/disable?userId="+userId).then(res => {
        MessageBox.alert("set the user disabled success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage)
      })
    },
    setEnabled(userId) {
      const _this = this
      _this.$axios.get("/user/admin/enable?userId="+userId).then(res => {
        MessageBox.alert("set the user enabled success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage)
      })
    },
  },
  created() {
    this.page(1)
  }
}
</script>

<style scoped>
.mpage {
  margin: 0 auto;
  text-align: center;
}
</style>