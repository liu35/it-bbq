<template>
  <div class="m-content">
    <h2 style="text-align: center;">the BBQ admin</h2>

    <div class="block">
      <span v-show="hasLogin">
        <el-button type="danger" @click="logout" size="small">Home</el-button>
      </span>
      <br><br>

      <el-avatar :size="60" :src="user.avatar"></el-avatar>
      <div>{{ user.username }}</div>
    </div>

    <div class="maction">
      <el-menu default-active="" class="el-menu-demo" mode="horizontal" background-color="#635f5e" text-color="#FFFFFF">
        <el-menu-item index="1">
          <span>
            <el-link href="/admin/posts/index">POST MANAGEMENT</el-link>
          </span>
        </el-menu-item>


        <el-menu-item index="2">
          <span>
            <el-link type="success" href="/admin/user/index">
              USER MANAGEMENT
            </el-link>
          </span>
        </el-menu-item>

        <el-menu-item index="3">
          <span>
            <el-link type="success" href="/admin/login/log">
              LOGIN LOG
            </el-link>
          </span>
        </el-menu-item>

        <el-menu-item index="4">
          <span>
            <el-link type="success" href="/admin/category">
              CATEGORY MANAGEMENT
            </el-link>
          </span>
        </el-menu-item>

        <div style="margin-left: 800px;margin-top: 12px;">
          <el-input size="small" style="width: 200px;"  @keydown.enter.native="search()" v-model="searchData"></el-input>
          <el-button slot="append" icon="el-icon-search" @click="search()" size="small" style="margin-left: 10px"></el-button>
        </div>


      </el-menu>

</div>
  </div>
</template>

<script>
export default {
  name: "AdminHeader",
  data() {
    return {
      user: {
        username: 'please login',
        avatar: 'https://bpic.588ku.com/element_pic/20/06/30/38e8d734e32f4b7db560f0fd9c5b1893.jpg!/fw/253/quality/90/unsharp/true/compress/true'
      },
      hasLogin: false,
      isAdmin: false,
      activeIndex: '1',
      typeList: [],
      searchData: ''
    };
  },
  methods: {
    logout() {
      const _this = this
      _this.$router.push({ name: 'PostsIndex' }).catch(() => {})
      _this.$router.go(0)
    },
    getTypeList() {
      this.$axios.get("/article/types").then(res => {
        this.typeList = res.data.data
      })
    },
  },
  created() {
    this.getTypeList();
    if (this.$store.getters.getUser.username) {
      this.user.username = this.$store.getters.getUser.username
      this.hasLogin = true
      if (this.$store.getters.getUser.role == "ADMIN") {
        this.isAdmin = true
      }

    }

  },
  watch: {
    $route: {
      handler: function(val, oldVal){
        if (val != oldVal) {
          this.$router.go(0)
        }
      },
      deep: true
    }
  }
}
</script>

<style scoped>
.m-content {
  margin: 0 auto;
  text-align: left;
}

.maction {
  margin: 10px 0;
  text-align: center;
}

.block {
  text-align: right;
}
</style>