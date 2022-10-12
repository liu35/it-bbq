<template>
  <div>

    <el-container>
      <el-header>
        <img class="mlogo"
          src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.51yuansu.com%2Fbackgd%2Fcover%2F00%2F25%2F00%2F5bab24287e38b.jpg%21%2Ffw%2F780%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue&refer=http%3A%2F%2Fpic.51yuansu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666444217&t=207dfc694d94b62ff5821c79f3cf64e1"
          alt="">
        <span>Account Registration
          (slogan, example)
          Endless Content Awaits!
        </span>
      </el-header>
      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="Email" prop="email">
            <el-input v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="Password" prop="password">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>

          <el-form-item label="Confirm Password" prop="cpassword">
            <el-input type="password" v-model="ruleForm.cpassword"></el-input>
          </el-form-item>
          

          <el-form-item label="Gender" prop="sex">
            <el-radio v-model="ruleForm.sex" label="Male">Male</el-radio>
            <el-radio v-model="ruleForm.sex" label="Female">Female</el-radio>
            <el-radio v-model="ruleForm.sex" label="No">Prefer not to Say</el-radio>
          </el-form-item>

          <el-form-item label="Username" prop="username">
            <el-input type="username" v-model="ruleForm.username"></el-input>
          </el-form-item>

          <el-form-item label="Avatar" prop="avatar">
            <el-upload class="avatar-uploader" action="http://43.159.34.72:8888/upload" :show-file-list="false"
              :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="ruleForm.avatar" :src="ruleForm.avatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>


          <el-form-item style="text-align: center ;">
            <el-button type="primary" @click="submitForm('ruleForm')" round>Register</el-button>
            <el-button @click="toHome()" round>Home</el-button>
            <el-button @click="toLogin()" round>Login</el-button>
          </el-form-item>
        </el-form>

      </el-main>
    </el-container>

  </div>
</template>

<script>
export default {
  name: "Register",
  
  data() {
    var validatorPassword = (rule, value, callback) => {
      if (value != this.ruleForm.password) {
        callback(new Error('The two entered passwords are different. Please enter them again'))
      }else {
        callback()
      }
    }
    return {
      ruleForm: {
        username: '',
        password: '',
        sex: '',
        email: '',
        signature: '',
        cpassword:'',
        avatar: ''
      },
      rules: {
        username: [
          { required: true, message: 'Please enter user name', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'change' }
        ],
        cpassword: [
        { required: true, message: 'Please enter your password', trigger: 'blur' },
        { validator: validatorPassword,  trigger: 'blur' }
        ],
        email: [
          { required: true, message: 'Please enter your email', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this
          this.$axios.post('/user/register', this.ruleForm).then(res => {

            console.log(res)
            _this.$alert('Registration successful', 'notice', {
              confirmButtonText: 'OK',
              callback: action => {
                _this.$router.push("/login")
              }
            });
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    toLogin() {
      this.$router.push("/login")
    },
    toHome() {
      this.$router.push("/posts/index")
    },
    handleAvatarSuccess(res, file) {
      console.log(res)
      this.ruleForm.avatar = res.data;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('The uploaded profile picture can only be in JPG format!');
      }
      if (!isLt2M) {
        this.$message.error('The size of the uploaded profile picture cannot exceed 2MB!');
      }
      return isJPG && isLt2M;
    }
  }
}
</script>

<style scoped>
.el-header,
.el-footer {
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
  text-align: left;
  line-height: 160px;
}

body>.el-container {
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

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 90px;
    height: 90px;
    line-height: 90px;
    text-align: center;
  }
  .avatar {
    width: 90px;
    height: 90px;
    display: block;
  }
</style>