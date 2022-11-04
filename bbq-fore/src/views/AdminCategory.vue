<template>
  <div class="mcontaner">
    <AdminHeader> </AdminHeader>
    <div style="text-align: center">
        <el-input v-model="name" size="10px" style="width: 200px"></el-input>
      <el-button type="primary" @click="addType"  style="margin-left: 20px">add category
      </el-button>
    </div>

    <br>
    <div style="text-align: center">
      <el-table
          :data="types"
          stripe
          style="width: 50%;margin: auto">
        <el-table-column
            prop="id"
            label="id"
            width="50">
        </el-table-column>
        <el-table-column
            prop="name"
            label="category"
            width="100">
        </el-table-column>
        <el-table-column
            prop="refCount"
            label="number of articles"
            width="100">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="createTime">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import AdminHeader from "@/components/AdminHeader";
export default {
  name: "AdminCategory",
  components: {AdminHeader},
  data() {
    return {
      types: [],
      name:'',
    }
  },
  methods:{
    addType(){
      if (this.name == ''){
        this.$alert("category is not null!")
      }else {
        this.$axios.get("/article/admin/type/add?typeName=" + this.name).then(
            res=>{
              if (res.data.code == 200) {
                this.$alert('add category successful', 'notice', {
                  confirmButtonText: 'OK',
                  callback: action => {
                    this.$router.go(0)
                  }
                })
              }
            }
        )
      }
    }
  },
  created() {
    this.$axios.get("/article/types").then(res=>{
      console.log(res);
      this.types = res.data.data;
    })
  }
}
</script>

<style scoped>

</style>