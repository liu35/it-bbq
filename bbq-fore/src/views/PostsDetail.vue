<template>
  <div>
    <Header></Header>
    <div>

      <p style="font-size: 30px; font-weight:700;text-align: center">{{ post.title }}
      </p >

      <p style="font-size: 15px;text-align: right; "> <el-tag>{{ post.type }}</el-tag>
      </p >
      <el-tag size="mini" type="success" v-show="post.top==1">top</el-tag>
      <el-tag size="mini" type="success" v-show="post.marrow==1">marrow</el-tag>
      <el-tag size="mini" type="success" v-show="post.official==1">official</el-tag>
      <p>
        <el-button icon="el-icon-edit" v-if="ownBlog" @click="toEdit()" type="primary">edit</el-button>
        <el-button icon="el-icon-edit" v-if="ownBlog" @click="toDelete()" type="primary">delete</el-button>
      </p >
    </div>
    <div style="text-align: center; margin-left: 20px;" @click="approvals()">
      <span class="el-icon-user">{{post.authorName}}
        <el-button @click="follow(post.authorId)" v-if="!ownBlog && hasLogin" round size="small">Follow author</el-button>
        <el-button @click="unfollow(post.authorId)" v-if="!ownBlog && hasLogin" round size="small">UnFollow author</el-button>
      </span>
    </div>
    <div class="mblog">
      <el-divider></el-divider>
      <div class="markdown-body" v-html="post.markdownContent"></div>
    </div>

    <div style="text-align: right; margin-right: 20px;" @click="approvals()">
      <span class="el-icon-thumb" />
</div>
</div>
</template>

<script>
import { link } from 'fs';
import 'github-markdown-css'
import { colon } from 'markdown-it/lib/common/entities';
import Header from "../components/Header";

export default {
  name: "PostsDetail",
  components: { Header },
  data() {
    return {
      post: {
        id: "",
        title: "",
        markdownContent: "",
        authorName: '',
        authorId: '',
        type: "",
      },
      ownBlog: false,
      isClick:false,
      hasLogin: false
    }
  },
  methods: {
    toEdit() {
      this.$router.push({ name: 'PostsIndexEdit', params: { postId: this.post.id } })
    },
    toDelete() {
      this.$axios.delete('/posts/index/delete', {params: {postId: this.post.id}}).then(res => {
        if (res.data.code == 200) {
          console.log(res)
            this.$alert('delete successful', 'notice', {
              confirmButtonText: 'OK',
              callback: action => {
                this.$router.push({name : 'PostsIndex'})
              }
            })
        }else {
          _this.$alert('delete failed')
        }
        console.log(res)
      })
    },
    approvals() {
      if (this.isClick) {
        this.$axios.get('/posts/index/approval?postId=' + this.post.id + "&click=" + 1).then(res => {
          this.isClick = false;
        })
      }else {
        this.$axios.get('/posts/index/approval?postId=' + this.post.id + "&click=" + 0).then(res => {
          this.isClick = true;
        })
      }
    },
    follow(authorId) {
      this.$axios.get('/follow/add?authorId=' + authorId, {
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        if (res.data.code == 200) {
          this.$alert('follow successful', 'notice', {
              confirmButtonText: 'OK',
              callback: action => {
                this.$router.go(0)
              }
            })
        }
      })
    },
    unfollow(authorId) {
      this.$axios.delete('/follow/delete', {
        params: {authorId : authorId},
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        if (res.data.code == 200) {
          this.$alert('unfollow successful', 'notice', {
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
    const postId = this.$route.params.postId
    console.log(postId)
    const _this = this
    this.$axios.get('/posts/index/' + postId).then(res => {
      const post = res.data.data
      _this.post.id = post.id
      _this.post.title = post.title
      _this.post.approvals = post.approvals
      _this.post.authorName = post.authorName
      _this.post.authorId = post.authorId
      _this.post.isFollow = post.isFollow
      _this.post.type = post.type
      _this.post.top = post.top
      _this.post.marrow = post.marrow
      _this.post.official = post.official

      var MardownIt = require("markdown-it")
      var md = new MardownIt()

      var result = md.render(post.markdownContent)
      _this.post.markdownContent = result
      _this.ownBlog = (post.authorId === _this.$store.getters.getUser.id)

    })
    this.$axios.get('/posts/index/views?postId=' +  postId)
    if (this.$store.getters.getUser.id) {
      this.hasLogin = true
    }
  }
}
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  min-height: 700px;
  padding: 20px 15px;
}

.edit {
  margin-left: 700px;
}
</style>