<template>
  <div>
    <Header></Header>
    <div class="m-content">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="title" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>
        <el-form-item label="description" prop="htmlContent">
          <el-input v-model="ruleForm.htmlContent"></el-input>
        </el-form-item>
        <el-form-item label="category" prop="category">
          <template>
            <el-select v-model="ruleForm.typeId" placeholder="please select category">
              <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="content" prop="content">
          <mavon-editor v-model="ruleForm.markdownContent" language="en"></mavon-editor>
        </el-form-item>
        <el-form-item style="text-align: center;">
          <el-button type="primary" @click="submitForm('ruleForm')">Post</el-button>
          <el-button @click="resetForm('ruleForm')">Reset</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header";
export default {
  name: "PostsIndexEdit",
  components: { Header },
  data() {
    return {
      ruleForm: {
        id: '',
        authorId: '',
        title: '',
        typeId: '',
        markdownContent: '',
        htmlContent: '',
        category: 'ARTICLE'
      },
      rules: {
        title: [
          { required: true, message: 'please enter titile', trigger: 'blur' }
        ],
        htmlContent: [
        { required: true, message: 'please enter description', trigger: 'blur' }
        ],
        markdownContent: [
          { trequired: true, message: 'please enter content', trigger: 'blur' }
        ]
      },
      typeList: []
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {

          const _this = this
          this.ruleForm.authorId = this.$store.getters.getUser.id;
          this.$axios.post('/posts/index/edit', this.ruleForm).then(res => {
            console.log(res)
            _this.$alert('operate successful', 'notice', {
              confirmButtonText: 'OK',
              callback: action => {
                _this.$router.push("/posts/index")
              }
            });

          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getTypeList() {
      this.$axios.get("/article/types").then(res => {
        this.typeList = res.data.data
      })
    }
  },
  created() {
    this.getTypeList();
    const postId = this.$route.params.postId
    console.log(postId)
    const _this = this

    if (postId) {
      this.$axios.get('/posts/index/' + postId).then(res => {
        const post = res.data.data
        _this.ruleForm.id = post.id
        _this.ruleForm.title = post.title
        _this.ruleForm.htmlContent = post.htmlContent
        _this.ruleForm.markdownContent = post.markdownContent
        _this.ruleForm.typeId = post.typeId
      })
    }

  }
}
</script>