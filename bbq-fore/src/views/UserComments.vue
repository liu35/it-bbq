<template>
  <div class="mcontaner">
    <Header></Header>

    <div class="block">
      <el-timeline>
        <el-timeline-item :timestamp="comment.createTime" placement="top" v-for="comment in comments">
          <el-card>
            <h3 style="text-align: center">
                {{comment.content}}
            </h3>
            <div style="text-align:right">
              <p class="el-icon-s-comment">{{comment.replyNum}}</p >
            </div>
            <div style="text-align: center">
              <router-link :to="{name: 'PostsDetail', params: {postId: comment.posts.id}}">
                {{comment.posts.title}}
              </router-link>
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
  name: "UserComments",
  components: { Header },
  data(){
    return{
      id: this.$route.params.userId,
      comments:{},
      currentPage: 1,
      total: 0,
      pageSize: 10
    }

  },
  methods: {
    page(currentPage) {
      const _this = this

      _this.$axios.get("/user/comments/list?userId="+ this.id +"&pageNum=" + currentPage).then(res => {
        console.log(res)
        _this.comments = res.data.data.records
        _this.currentPage = res.data.data.current
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size

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