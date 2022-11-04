<template>
  <div class="mcontaner">
    <Header></Header>

    <div class="block">
      <el-timeline>
        <el-timeline-item placement="top" v-for="approval in approvals">
          <el-card>
            <h3 style="text-align: center">
              <router-link :to="{name: 'PostsDetail', params: {postId: approval.posts.id}}">
                {{approval.posts.title}}
              </router-link>
            </h3>
            <div style="text-align:right">
              <span class="el-icon-thumb"/>

              <el-button @click="unApprovals(approval.posts.id)" round size="small">unlike</el-button>
            </div>
            <div style="text-align: center">
              <p class="el-icon-s-comment">{{approval.posts.comments}}</p >&emsp;
              <p class="el-icon-thumb">{{approval.posts.approvals}}</p >&emsp;&emsp;&emsp;
              <p class="el-icon-view">{{approval.posts.views}}</p >
            </div>

          </el-card>
        </el-timeline-item>

      </el-timeline>

      <el-pagination class="mpage" background layout="prev, pager, next" :current-page="currentPage"
                     :page-size="pageSize" :total="total" @current-change=page>
      </el-pagination>

    </div>

  </div>
</template>

<script>
import Header from "../components/Header";
export default {
  name: "UserApprovalPosts",
  components: { Header },
  data(){
    return{
      id: this.$route.params.userId,
      approvals:{},
      currentPage: 1,
      total: 0,
      pageSize: 10
    }

  },
  methods: {
    page(currentPage) {
      const _this = this

      _this.$axios.get("/user/approval/posts?userId="+ this.id +"&pageNum=" + currentPage).then(res => {
        console.log(res)
        _this.approvals = res.data.data.records
        _this.currentPage = res.data.data.current
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size

      })
    },
    unApprovals(postId) {
      this.$axios.get('/posts/index/delete/approval?postId=' + postId+ '&userId=' + this.id).then(res => {
        if (res.data.code == 200) {
          this.$alert('delete approval successful', 'notice', {
            confirmButtonText: 'OK',
            callback: action => {
              this.$router.go(0)
            }
          })
        }
      })


    }
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