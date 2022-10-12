<template>
  <div>

    <el-container>
      <el-header>
        <img class="mlogo" src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.51yuansu.com%2Fbackgd%2Fcover%2F00%2F25%2F00%2F5bab24287e38b.jpg%21%2Ffw%2F780%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue&refer=http%3A%2F%2Fpic.51yuansu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666444217&t=207dfc694d94b62ff5821c79f3cf64e1" alt="">
      </el-header>
      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="email" prop="email">
            <el-input v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="password" prop="password">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')" round>Login</el-button>
            <el-button @click="toRegister()" round>Register</el-button>
            <el-button @click="toPosts()" round>Home</el-button>
          </el-form-item>
        </el-form>

      </el-main>
    </el-container>

  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        ruleForm: {
          email: '',
          password: ''
        },
        rules: {
          email: [
            { required: true, message: 'Please enter a user email', trigger: 'blur' }
          ],
          password: [
            { required: true, message: 'Please enter your password', trigger: 'change' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const _this = this
            this.$axios.post('/login', this.ruleForm).then(res => {

              console.log(res.data)
              const jwt = res.headers['authorization']
              const userInfo = res.data.data

              
              _this.$store.commit("SET_TOKEN", jwt)
              _this.$store.commit("SET_USERINFO", userInfo)

              console.log(_this.$store.getters.getUser)

              _this.$router.push("/posts/index")
            })

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      toRegister() {
        this.$router.push("/register")
      },
      toPosts() {
        this.$router.push("/posts/index")
      }
    }
  }
</script>

<style scoped>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    /*background-color: #E9EEF3;*/
    color: #333;
    text-align: center;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

  .mlogo {
    height: 60%;
    margin-top: 10px;
  }

  .demo-ruleForm {
    max-width: 500px;
    margin: 0 auto;
  }

</style>