<template>
<div>
<el-dialog
    title="Correction of Personal Information"
    :visible.sync="dialogVisible"
    width="60%"
    :before-close="handleClose">
  <el-form :model="form" :rules="rules" ref="form" label-width="150px">
    <div class="updateinfo">
      <div class="left">
        <el-form-item label="avatar" prop="avatar">
          <img style="width:150px;height:110px" :src="form.avatar"></img>
        </el-form-item>
        <el-form-item label="upload new avatar" prop="newAvatar">
          <el-upload class="avatar-uploader" action="http://localhost:8888/upload" :show-file-list="false"
                     :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
            <img v-if="form.newAvatar" :src="form.newAvatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="username" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>

        <el-form-item label="Gender" prop="sex">
          <el-radio v-model="form.sex" label="Male">Male</el-radio>
          <el-radio v-model="form.sex" label="Female">Female</el-radio>
          <el-radio v-model="form.sex" label="No">Prefer not to Say</el-radio>
        </el-form-item>

      </div>
      <div class="right">
        <el-form-item label="user id" prop="id">
          <el-input v-model="form.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="email" prop="email">
          <el-input v-model="form.email" disabled></el-input>
        </el-form-item>
        <el-form-item label="signature" prop="signature">
          <el-input v-model="form.signature"></el-input>
        </el-form-item>
        <el-form-item label="password" prop="password">
          <el-input type="password" v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="new password" prop="newPassword">
          <el-input type="password" v-model="form.newPassword"></el-input>
        </el-form-item>
        <el-form-item label="confirm password" prop="cnewPassword">
          <el-input type="password" v-model="form.cnewPassword"></el-input>
        </el-form-item>
      </div>
    </div>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">cancel</el-button>
    <el-button type="primary" @click="submit">submit</el-button>
  </span>
</el-dialog>
</div>
</template>

<script>

export default {
  name: "UserEditDia",
  data() {
    var validatorPassword = (rule, value, callback) => {
      if (value != this.form.newPassword) {
        callback(new Error('The two entered passwords are different. Please enter them again'))
      }else {
        callback()
      }
    }
    return {
      dialogVisible: false,
      form: {
        newAvatar:"",
        avatar: "",
        password: "",
        username: "",
        email: "",
        sex: "",
        id: "",
        signature: "",
        newPassword:"",
        cnewPassword:""
      },
      rules: {
        username: [
          { required: true, message: 'Please enter user name', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'change' }
        ],
        cnewPassword: [
          { required: false, },
          { validator: validatorPassword,  trigger: 'blur' }
        ],
      },
    };
  },
  mounted() {
    this.load();
  },
  methods: {
    load(){
      this.form.avatar = this.$store.getters.getUser.avatar;
      this.form.username = this.$store.getters.getUser.username;
      this.form.signature = this.$store.getters.getUser.signature;
      this.form.id = this.$store.getters.getUser.id;
      this.form.sex = this.$store.getters.getUser.sex;
      this.form.email = this.$store.getters.getUser.email;

    },
    open() {
      this.dialogVisible = true;

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
    },
    handleAvatarSuccess(res, file) {
      console.log(res)
      this.form.newAvatar = res.data;
    },
    submit() {
      updateUser(this.form)
          .then((res) => {
            console.log(res);
            this.dialogVisible = false;
            this.$emit("flesh");
          })
          .catch((err) => {
            console.log(err);
          });
    },
    handleClose() {
      this.dialogVisible = false;
      this.$emit("flesh");
    },
  },
};
</script>

<style scoped>
.updateinfo {
  height: 350px;
  overflow: auto;
}
.left {
  /* width: 330px; */
  float: left;
}
.right {
  overflow: hidden;
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
