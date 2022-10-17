<template>

  <div class="mcontaner">
  <Header></Header>

    <div>
      <el-card>
        <el-descriptions class="margin-top" title="user info" :column="2" border>
          <template slot="extra">
            <el-button type="primary" v-if="$route.params.userId==$store.getters.getUser.id" size="small" @click="edit">edit</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-picture-outline"></i>
              avatar
            </template>
            <el-avatar :size="60" :src="this.avatar"></el-avatar>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              username
            </template>
            {{ this.username }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-custom"></i>
              role
            </template>
            <el-tag size="small">{{ this.role }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-odometer"></i>
              signature
            </template>
            {{ this.signature }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-male"></i>
              <i class="el-icon-female"></i>
              sex
            </template>
            <el-tag size="small">{{ this.sex }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-message"></i>
              Email
            </template>
            {{ this.email }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              create date
            </template>
            {{ this.createTime}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              update date
            </template>
            {{ this.updateTime }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>
    <user-edit-dia ref="dia"></user-edit-dia>
  </div>
</template>

<script>
import UserEditDia from "@/views/UserEditDia";
import Header from "@/components/Header";
export default {
  name: "UserIndex",
  components: {UserEditDia, Header},
  data(){
    return{
      id: this.$route.params.userId,
      avatar: "",
      username: "",
      role: "",
      signature:"",
      sex: "",
      email: "",
      createTime: "",
      updateTime: "",
    }
  },
  methods: {
    edit() {
      this.$refs.dia.open();
    }
  },
  created() {
    this.$axios.get("/user/" + this.id).then(res => {
      this.avatar = res.data.data.avatar;
      this.username = res.data.data.username;
      this.role = res.data.data.role;
      this.signature = res.data.data.signature;
      this.sex = res.data.data.sex;
      this.email = res.data.data.email;
      this.createTime = res.data.data.createTime;
      this.updateTime = res.data.data.updateTime;
    })
  }
}
</script>

<style scoped>
</style>