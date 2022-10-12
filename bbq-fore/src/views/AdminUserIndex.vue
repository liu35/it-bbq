<template>
  <div class="mcontaner">
    <AdminHeader> </AdminHeader>
    <div class="block">
      <el-table
          :data="users"
          stripe
          style="width: 100%">
        <el-table-column
            prop="id"
            label="id"
            width="50">
        </el-table-column>
        <el-table-column
            prop="email"
            label="email">
        </el-table-column>
        <el-table-column
            prop="username"
            label="username">
        </el-table-column>
        <el-table-column label="avatar">
          <template slot-scope="scope">
            <el-avatar :size="60" :src="scope.row.avatar"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column
            prop="sex"
            label="sex">
        </el-table-column>
        <el-table-column
            prop="signature"
            label="signature">
        </el-table-column>
        <el-table-column
            prop="role"
            label="role">
        </el-table-column>
        <el-table-column label="operator" width="130">
          <template slot-scope="scope" >
            <span>
            <el-button v-show="scope.row.role!='ADMIN'" size="mini" type="primary" plain @click = "setAdmin(scope.row.id)">set admin </el-button>
            <el-button v-show="scope.row.role=='ADMIN'"size="mini" type="primary" plain @click = "setUser(scope.row.id)">set user</el-button>
            </span>
            <span>
            <el-button v-show="scope.row.state=='ENABLED'" size="mini" type="primary" plain @click = "setDisabled(scope.row.id)">set disabled</el-button>
            <el-button v-show="scope.row.state!='ENABLED'"size="mini" type="primary" plain @click = "setEnabled(scope.row.id)">set enabled</el-button>
            </span>
          </template>
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
  name: "AdminUserIndex",
  components: {AdminHeader},
  data() {
    return {
      users: [],
      currentPage: 1,
      total: 0,
      pageSize: 10
    }
  },
  methods: {
    page(currentPage) {
      const _this = this

      _this.$axios.get("/user/admin/all?pageNum=" + currentPage).then(res => {
        console.log(res)
        _this.users = res.data.data.records
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