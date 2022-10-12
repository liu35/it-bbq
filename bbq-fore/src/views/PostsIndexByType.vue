<template>
    <div class="mcontaner">
      <Header></Header>
  
      <div class="block">
        <el-timeline>
  
          <el-timeline-item :timestamp="post.createTime" placement="top" v-for="post in posts">
            <el-card>
              <el-tag size="mini" type="success" v-show="post.top==1">top</el-tag>
              <el-tag size="mini" type="success" v-show="post.marrow==1">hot</el-tag>
              <el-tag size="mini" type="success" v-show="post.official==1">official</el-tag>
              <h2 style="text-align: center">
                <router-link :to="{name: 'PostsDetail', params: {postId: post.id}}">
                  {{post.title}}
                </router-link>
              </h2>
              <div style="text-align: center">
                <span class="el-icon-user" >{{post.authorName}}</span>
                <div style="text-align: right"><el-tag size="mini">{{post.type}}</el-tag></div>
              </div>
               <p>{{post.htmlContent}}</p >
              <div style="text-align:right">
              <p class="el-icon-thumb">{{post.approvals}}</p >&emsp;&emsp;&emsp;
              <p class="el-icon-view">{{post.views}}</p >
            </div>
            </el-card>
          </el-timeline-item>
  
        </el-timeline>
  
        <el-pagination class="mpage"
                       background
                       layout="prev, pager, next"
                       :current-page="currentPage"
                       :page-size="pageSize"
                       :total="total"
                       @current-change=page>
        </el-pagination>
  
      </div>
  
    </div>
  </template>
  
  <script>
    import Header from "../components/Header";
  
    export default {
      name: "PostIndexByType",
      components: {Header},
      data() {
        return {
          posts: {},
          currentPage: 1,
          total: 0,
          pageSize: 10
        }
      },
      methods: {
        page(currentPage) {
          const _this = this
  
          const typeId = _this.$route.params.typeId;
  
          _this.$axios.get("/posts/index/list?pageNum=" + currentPage + "&typeId=" +  typeId).then(res => {
            console.log(res)
            _this.posts = res.data.data.records
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