<template>
  <div class="m-content">
    <h2 style="text-align: center;">Welcome to the BBQ forum</h2>

    <div class="block">
      <div style="text-align: left" v-show="isAdmin">
        <el-button type="warning" @click="admin" size="medium">Admin</el-button>
      </div>

      <span v-show="!hasLogin">
        <el-button type="primary" @click="login" size="small">Login</el-button>
      </span>

      <span v-show="hasLogin">
        <el-button type="danger" @click="logout" size="small">LogOut</el-button>
      </span>
      <br><br>

      <el-avatar :size="60" :src="user.avatar"></el-avatar>
      <div>{{ user.username }}</div>
    </div>

    <div class="maction">


      <el-menu default-active="activeIndex" class="el-menu-demo" mode="horizontal" background-color="#635f5e" text-color="#FFFFFF">
        <el-menu-item index="1">
          <span>
            <el-link href="/posts/index">All Posts</el-link>
          </span>
        </el-menu-item>

        <el-submenu index="2">
          
         <template slot="title">CATEGORY</template>
          <el-menu-item index="2-index" v-for="(item, index) in typeList" :key="index" @click="getPostByType(item.id)">
              {{item.name}}
          </el-menu-item>
        </el-submenu>


        <el-menu-item index="3">
          <span>
            <el-link type="success" href="/posts/index/add">
              CREATE POST
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
  name: "Header",
  data() {
    return {
      user: {
        username: 'please login',
        avatar: 'https://img2.baidu.com/it/u=2306449352,310199388&fm=253&fmt=auto&app=138&f=PNG?w=500&h=343'
      },
      hasLogin: false,
      isAdmin: false,
      activeIndex: '1',
      typeList: [],
      searchData: ''
    };
  },
  methods: {
    login() {
      this.$router.push({ name: 'Login' })
    },
    logout() {
      const _this = this
      _this.$axios.get("/logout").then(res => {
        _this.$store.commit("REMOVE_INFO")
        _this.$router.push({ name: 'PostsIndex' }).catch(() => {})
        _this.$router.go(0)

      })
    },
    admin(){
      this.$router.push({ name: 'Admin' })
    },
    getTypeList() {
      this.$axios.get("/article/types").then(res => {
        this.typeList = res.data.data
      })
    },
    getPostByType(typeId) {
       this.$router.push({name : 'PostIndexByType', params : {typeId : typeId}})
    },
    search() {
       this.$router.push({name : 'PostIndexSearch', params : {search: this.searchData}})
    }
  },
  created() {

    this.getTypeList();
    if (this.$store.getters.getUser.username) {
      this.user.username = this.$store.getters.getUser.username
      this.user.avatar = this.$store.getters.getUser.avatar
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
  max-width: 1200px;
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