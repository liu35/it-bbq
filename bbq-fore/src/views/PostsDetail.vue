<template>
  <div>
    <Header></Header>
    <div>

      <p style="font-size: 30px; font-weight:700;text-align: center">{{ post.title }}
      </p>

      <p style="font-size: 15px;text-align: right; ">
        <el-tag>{{ post.type }}</el-tag>
      </p>
      <el-tag size="mini" type="success" v-show="post.top==1">top</el-tag>
      <el-tag size="mini" type="success" v-show="post.marrow==1">hot</el-tag>
      <el-tag size="mini" type="success" v-show="post.official==1">official</el-tag>
      <p>
        <el-button icon="el-icon-edit" v-if="ownBlog" @click="toEdit()" type="primary">edit</el-button>
        <el-button icon="el-icon-edit" v-if="ownBlog" @click="toDelete()" type="primary">delete</el-button>
      </p>
    </div>
    <div class="demo_input">
      <el-col :span="20">
        <el-input v-model="link" size="10px" disabled></el-input>
      </el-col>
      <el-button type="primary" @click="copyUrl" size="mini" style="margin-left: 20px">Copy the link to share
      </el-button>
    </div>
    <div style="text-align: center; margin-left: 20px;" @click="approvals()">
      <span class="el-icon-user">{{ post.authorName }}
        <el-button @click="follow(post.authorId)" v-if="!ownBlog && hasLogin" round
                   size="small">Follow author</el-button>
        <el-button @click="unfollow(post.authorId)" v-if="!ownBlog && hasLogin" round
                   size="small">UnFollow author</el-button>
      </span>
    </div>
    <div class="mblog">
      <el-divider></el-divider>
      <div class="markdown-body" v-html="post.markdownContent"></div>
    </div>

    <div style="text-align: right; margin-right: 20px;">
      <span class="el-icon-thumb"/>

      <el-button @click="approvals()" v-if="hasLogin" round size="small">Like</el-button>
    </div>

    <div class="write-reply">
      <i class="el-icon-edit"></i>
      <span class="add-comment">Add a new comment</span>
    </div>
    <transition name="fade">
      <div class="input-wrapper">
        <el-input class="gray-bg-input"
                  v-model="inputComment"
                  type="textarea"
                  :rows="3"
                  autofocus
                  placeholder="Write your comment">
        </el-input>
        <div class="btn-control">
          <el-button class="btn" type="success" round @click="commitComment">submit</el-button>
        </div>
      </div>
    </transition>

    <div class="container">
      <div class="comment" v-for="item in comments">
        <div class="info">
          <img class="avatar" :src="item.avatar" width="36" height="36"/>
          <div class="right">
            <div class="name">{{ item.username }}</div>
            <div class="date">{{ item.updateTime }}</div>
          </div>
        </div>
        <div class="content">{{ item.content }}</div>
        <div class="control">
          <span class="comment-reply" @click="showCommentInput(item,item.userId,item.username)">
          <i class="el-icon-s-comment"></i>
            <span>reply</span>
        </span>
        </div>
        <div class="reply">
          <div class="item" v-for="reply in item.commentReplyList">
            <div class="reply-content">
              <span class="from-name">{{ reply.username }}</span><span>: </span>
              <span class="to-name">@{{ reply.replyName }}</span>
              <span>{{ reply.content }}</span>
            </div>
            <div class="reply-bottom">
              <span>{{ reply.updateTime }}</span>
              <span class="reply-text" @click="showCommentInput(item, reply.userId,reply.username)">
              <i class="el-icon-s-comment"></i>
              <span>reply</span>
            </span>
            </div>
          </div>
          <div class="write-reply" v-if="item.commentReplyList.length > 0" @click="showCommentInput(item)">
            <i class="el-icon-edit"></i>
            <span class="add-comment">Add new reply</span>
          </div>
          <transition name="fade">
            <div class="input-wrapper" v-if="showItemId === item.id">
              <el-input class="gray-bg-input"
                        v-model="inputReply"
                        type="textarea"
                        :rows="3"
                        autofocus
                        placeholder="Write down your reply">
              </el-input>
              <div class="btn-control">
                <span class="cancel" @click="cancel">cancel</span>
                <el-button class="btn" type="success" round @click="commitReply">submit</el-button>
              </div>
            </div>
          </transition>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import {link} from 'fs';
import 'github-markdown-css'
import {colon} from 'markdown-it/lib/common/entities';
import Header from "../components/Header";
import Vue from 'vue';

export default {
  name: "PostsDetail",
  components: {Header},
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
      isClick: false,
      hasLogin: false,
      link: window.location.href,
      comments: {},
      inputComment: '',
      inputReply: '',
      showItemId: '',
      replyId: '',
      replyName: '',

    }
  },
  methods: {
    copyUrl() {
      const input = document.createElement("input");
      document.body.appendChild(input);
      input.setAttribute("value", this.link);
      input.select();
      if (document.execCommand("copy")) {
        document.execCommand("copy");
      }
      document.body.removeChild(input);
      this.$message({
        message: 'copy successful',
        type: 'success'
      });
    },
    toEdit() {
      this.$router.push({name: 'PostsIndexEdit', params: {postId: this.post.id}})
    },
    toDelete() {
      this.$axios.delete('/posts/index/delete', {params: {postId: this.post.id}}).then(res => {
        if (res.data.code == 200) {
          console.log(res)
          this.$alert('delete successful', 'notice', {
            confirmButtonText: 'OK',
            callback: action => {
              this.$router.push({name: 'PostsIndex'})
            }
          })
        } else {
          this.$alert('delete failed')
        }
        console.log(res)
      })
    },
    approvals() {
      if (this.isClick) {
        this.$axios.get('/posts/index/approval?postId=' + this.post.id + '&userId=' + this.$store.getters.getUser.id + "&click=" + 1).then(res => {
          this.isClick = false;
          this.$alert('approval successful')
        })
      } else {
        this.$axios.get('/posts/index/approval?postId=' + this.post.id + '&userId=' + this.$store.getters.getUser.id + "&click=" + 0).then(res => {
          this.isClick = true;
          this.$alert('approval successful')
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
        params: {authorId: authorId},
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
    },
    cancel() {
      this.showItemId = ''
    },
    showCommentInput(item, replyId, replyName) {
      if (replyId && replyName) {
        this.inputReply = ''
        this.replyId = replyId
        this.replyName = replyName
      } else {
        this.inputReply = ''
      }

      this.showItemId = item.id
    },
    commitComment() {
      if (this.inputComment && this.$store.getters.getUser.id) {
        this.$axios.post('/posts/comment/add', {
          postId: this.post.id,
          username: this.$store.getters.getUser.username,
          avatar: this.$store.getters.getUser.avatar,
          userId: this.$store.getters.getUser.id,
          content: this.inputComment
        }).then(res => {
          if (res.data.code == 200) {
            this.$alert('comment successful', 'notice', {
              confirmButtonText: 'OK',
              callback: action => {
                this.$router.go(0)
              }
            })
          }
        })
      } else {
        this.$alert('comment cannot be empty')
      }
    },
    commitReply() {
      if (this.inputReply && this.$store.getters.getUser.id && this.replyId && this.replyName) {
        this.$axios.post('/posts/reply/add', {
          commentId: this.showItemId,
          username: this.$store.getters.getUser.username,
          avatar: this.$store.getters.getUser.avatar,
          userId: this.$store.getters.getUser.id,
          content: this.inputReply,
          replyId: this.replyId,
          replyName: this.replyName
        }).then(res => {
          if (res.data.code == 200) {
            this.$alert('reply successful', 'notice', {
              confirmButtonText: 'OK',
              callback: action => {
                this.$router.go(0)
              }
            })
          }
        })
      } else {
        this.$alert('comment cannot be empty')
      }
    },
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
    this.$axios.get('/posts/index/views?postId=' + postId)
    if (this.$store.getters.getUser.id) {
      this.hasLogin = true
    }
    this.$axios.get('/posts/comment/list?postId=' + postId).then(res => {
      this.comments = res.data.data
    })
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


.container {
  padding: 0 10px;
  box-sizing: border-box;
}

.comment {
  display: flex;
  flex-direction: column;
  padding: 10px;
  border-bottom: 1px solid #F2F6FC;
}

.info {
  display: flex;
  align-items: center;
}

.avatar {
  border-radius: 50%;
}

.right {
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}

.name {
  font-size: 16px;
  color: #303133;
  margin-bottom: 5px;
  font-weight: 500;
}

.date {
  font-size: 12px;
  color: #909399;
}

.content {
  font-size: 16px;
  color: #303133;
  line-height: 20px;
  padding: 10px 0;
}

.control {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #909399;
}

.like {
  display: flex;
  align-items: center;
  margin-right: 20px;
  cursor: pointer;
}

.like.active, .like:hover {
  color: #409EFF;
}

.iconfont {
  font-size: 14px;
  margin-right: 5px;
}

.comment-reply {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.comment-reply:hover {
  color: #909399;
}

.iconfont {
  font-size: 16px;
  margin-right: 5px;
}

.reply {
  margin: 10px 0;
  border-left: 2px solid;
}

.item {
  margin: 0 10px;
  padding: 10px 0;
  border-bottom: 1px dashed #EBEEF5;
}

.reply-content {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #303133;
}

.from-name {
  color: #409EFF;
}

.to-name {
  color: #409EFF;
  margin-left: 5px;
  margin-right: 5px;
}

.reply-bottom {
  display: flex;
  align-items: center;
  margin-top: 6px;
  font-size: 12px;
  color: #909399;
}

.reply-text {
  display: flex;
  align-items: center;
  margin-left: 10px;
  cursor: pointer;
}

.reply-text:hover {
  color: #909399;
}

.icon-comment {
  margin-right: 5px;
}

.write-reply {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #909399;
  padding: 10px;
  cursor: pointer;
}

.write-reply:hover {
  color: #303133;
}

.el-icon-edit {
  margin-right: 5px;
}

.fade-enter-active, fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}

.input-wrapper {
  padding: 10px;
}

.gray-bg-input, .el-input__inner {
  /*background-color: #67C23A;*/
}

.btn-control {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-top: 10px;
}

.cancel {
  font-size: 16px;
  color: #606266;
  margin-right: 20px;
  cursor: pointer;
}

.cancel:hover {
  color: #909399;
}

.confirm {
  font-size: 16px;
}


</style>