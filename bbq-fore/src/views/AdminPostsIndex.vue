<template>
  <div class="mcontaner">
    <AdminHeader> </AdminHeader>
    <div class="block">
      <el-table
          :data="posts"
          stripe
          style="width: 100%">
        <el-table-column
            prop="id"
            label="id"
            width="50">
        </el-table-column>
        <el-table-column
            prop="authorName"
            label="author"
            width="100">
        </el-table-column>
        <el-table-column
            prop="type"
            label="category"
            width="150">
        </el-table-column>
        <el-table-column label="post" width="160">
          <template slot-scope="scope">
            <router-link :to="{name: 'PostsDetail', params: {postId: scope.row.id}}">
              {{scope.row.title}}
            </router-link>
          </template>
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="createTime">
        </el-table-column>
        <el-table-column
            prop="updateTime"
            label="updateTime">
        </el-table-column>
        <el-table-column label="operator" width="130">
          <template slot-scope="scope" >
            <span>
            <el-button v-show="scope.row.top==0" size="mini" type="primary" plain @click = "setTop(scope.row.id)">set top </el-button>
            <el-button v-show="scope.row.top!=0"size="mini" type="primary" plain @click = "deleteTop(scope.row.id)">delete top</el-button>
            </span>
            <span>
            <el-button v-show="scope.row.marrow==0" size="mini" type="primary" plain @click = "setMarrow(scope.row.id)">set hot</el-button>
            <el-button v-show="scope.row.marrow!=0"size="mini" type="primary" plain @click = "deleteMarrow(scope.row.id)">delete hot</el-button>
            </span>
            <span>
            <el-button v-show="scope.row.official==0" size="mini" type="primary" plain @click = "setOfficial(scope.row.id)">set official</el-button>
            <el-button v-show="scope.row.official!=0" size="mini" type="primary" plain @click = "deleteOfficial(scope.row.id)">delete official</el-button>
            </span>
            <span>
            <el-button v-show="scope.row.auditState!='PASS'" size="mini" type="primary" plain @click = "setSeen(scope.row.id)">set seen</el-button>
            <el-button v-show="scope.row.auditState=='PASS'" size="mini" type="primary" plain @click = "setUnseen(scope.row.id)">set unseen</el-button>
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
  name: "AdminPostsIndex",
  components: {AdminHeader},
  data() {
    return {
      posts: [],
      currentPage: 1,
      total: 0,
      pageSize: 10
    }
  },
  methods: {
    page(currentPage) {
      const _this = this

      _this.$axios.get("/posts/admin/index/list?pageNum=" + currentPage).then(res => {
        console.log(res)
        _this.posts = res.data.data.records
        _this.currentPage = res.data.data.current
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size

      })
    },
    setTop(postId) {
      const _this = this
      _this.$axios.get("/posts/admin/top/set?postId="+postId).then(res => {
        MessageBox.alert("set top success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage);
      })
    },
    setMarrow(postId) {
      const _this = this
      _this.$axios.get("/posts/admin/marrow/set?postId="+postId).then(res => {
        MessageBox.alert("set hot success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage);
      })
    },
    setOfficial(postId) {
      const _this = this
      _this.$axios.get("/posts/admin/official/set?postId="+postId).then(res => {
        MessageBox.alert("set official success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage);
      })
    },
    setSeen(postId) {
      const _this = this
      _this.$axios.get("/posts/admin/posts/seen?postId="+postId).then(res => {
        MessageBox.alert("set seen success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage);
      })
    },
    deleteTop(postId) {
      const _this = this
      _this.$axios.get("/posts/admin/top/delete?postId="+postId).then(res => {
        MessageBox.alert("delete top success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage);
      })
    },
    deleteMarrow(postId) {
      const _this = this
      _this.$axios.get("/posts/admin/marrow/delete?postId="+postId).then(res => {
        MessageBox.alert("delete hot success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage);
      })
    },
    deleteOfficial(postId) {
      const _this = this
      _this.$axios.get("/posts/admin/official/delete?postId="+postId).then(res => {
        MessageBox.alert("delete official success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage);
      })
    },
    setUnseen(postId) {
      const _this = this
      _this.$axios.get("/posts/admin/posts/unseen?postId="+postId).then(res => {
        MessageBox.alert("set unseen success", 'success', {
          confirmButtonText: 'ok',
        });
        _this.page(_this.currentPage);
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