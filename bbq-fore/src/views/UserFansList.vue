<template>
  <div class="mcontaner">
    <Header></Header>
    <h1 style="text-align: center">your fans</h1>
    <div class="block">
      <el-table
          :data="follows"
          stripe
          style="width: 100%">
        <el-table-column label="id" width="50">
          <template slot-scope="scope">
            {{(scope.$index + 1)}}
          </template>
        </el-table-column>
        <el-table-column label="username" width="160">
          <template slot-scope="scope">
            <router-link :to="{name: 'UserIndex', params: {userId: scope.row.followerInfo.id}}">
              {{scope.row.followerInfo.username}}
            </router-link>
          </template>
        </el-table-column>
        <el-table-column label="avatar">
          <template slot-scope="scope">
            <el-avatar :size="60" :src="scope.row.followerInfo.avatar"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column
            prop="followerInfo.sex"
            label="sex">
        </el-table-column>
        <el-table-column
            prop="followerInfo.signature"
            label="signature">
        </el-table-column>
        <el-table-column
            prop="followerInfo.role"
            label="role">
        </el-table-column>
        <el-table-column label="operator" width="130">
          <template slot-scope="scope" >
            <span>
            <el-button v-show="scope.row.isDelete == 0" size="mini" type="primary" plain @click = "follow(scope.row.followerInfo.id)">follow</el-button>
            <el-button v-show="scope.row.isDelete == 1"size="mini" type="primary" plain @click = "unfollow(scope.row.followerInfo.id)">followed</el-button>
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
import Header from "@/components/Header";
export default {
  name: "UserFansList",
  components: {Header},
  data() {
    return {
      follows: [],
      currentPage: 1,
      total: 0,
      pageSize: 10,
      bool: [],
    }
  },
  methods: {
    page(currentPage) {
      const _this = this

      _this.$axios.get("/user/follower/list?userId="+this.$store.getters.getUser.id+"&pageNum=" + currentPage)
    .then(res => {

        _this.follows = res.data.data.records
         console.log(_this.follows)
        _this.currentPage = res.data.data.current
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size

      })
    },



    follow(followed){
      this.$axios.get('/follow/add?authorId=' + followed + '&followerId=' + this.$store.getters.getUser.id, {
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
        params: {authorId: authorId,
          followerId: this.$store.getters.getUser.id
        },

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