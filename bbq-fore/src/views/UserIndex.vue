<template>

  <div class="mcontaner">
  <Header></Header>

    <div>
      <el-card>
        <el-descriptions class="margin-top" title="Info" :column="2" border>
          <template slot="extra">
            <el-button type="primary" v-if="$route.params.userId==$store.getters.getUser.id" size="small">edit</el-button>
          </template>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-picture-outline"></i>
              avatar
            </template>
            <img class="img" :src="$store.getters.getUser.avatar" alt="" />
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              username
            </template>
            {{ $store.getters.getUser.username }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-custom"></i>
              role
            </template>
            <el-tag size="small">{{ $store.getters.getUser.role }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-odometer"></i>
              signature
            </template>
            {{ $store.getters.getUser.signature }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-male"></i>
              <i class="el-icon-female"></i>
              sex
            </template>
            <el-tag size="small">{{ $store.getters.getUser.sex }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-message"></i>
              Email
            </template>
            {{ $store.getters.getUser.email }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-date"></i>
              create date
            </template>
            {{ $store.getters.getUser.createTime }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
export default {
  name: "UserIndex",
  components: {Header}
}
</script>

<style scoped>
.img {
  width: 80px;
  height: 80px;
}

</style>