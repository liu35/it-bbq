import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import PostsIndex from '../views/PostsIndex.vue'
import PostsIndexEdit from '../views/PostsIndexEdit.vue'
import PostsDetail from '../views/PostsDetail.vue'
import Register from '../views/Register.vue'
import PostIndexByType from '../views/PostsIndexByType.vue'
import PostIndexSearch from '../views/PostsIndexSearch.vue'
import Admin from "../views/Admin.vue";
import AdminPostsIndex from "@/views/AdminPostsIndex";
import AdminUserIndex from "@/views/AdminUserIndex";
import AdminLoginLog  from "@/views/AdminLoginLog";
import UserIndex  from "@/views/UserIndex";
import PostComments from "@/views/PostComments";
import UserFansList from "@/views/UserFansList";
import UserFollowList from "@/views/UserFollowList";
import UserComments from "@/views/UserComments";
import UserApprovalPosts from "@/views/UserApprovalPosts";
import AdminCategory from "@/views/AdminCategory";

Vue.use(VueRouter)

const routes = [
  {
    // index
    path: '/',
    name: 'Index',
    redirect: {name: "PostsIndex"}
  },
  {
    // postList
    path: '/posts/index',
    name: 'PostsIndex',
    component: PostsIndex
  },
  {
    // user register
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    // login
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    // index post add
    path: '/posts/index/add',
    name: 'PostsIndexAdd',
    component: PostsIndexEdit,
    meta: {
      requireAuth: true
    }
  },
  {
    // index post detail
    path: '/posts/index/:postId',
    name: 'PostsDetail',
    component: PostsDetail
  },
  {
    // index post edit
    path: '/posts/index/:postId/edit',
    name: 'PostsIndexEdit',
    component: PostsIndexEdit,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/postsIndexSearch/:search',
    name: 'PostIndexSearch',
    component: PostIndexSearch
  },
  {
    path: '/postsIndexType/:typeId',
    name: 'PostIndexByType',
    component: PostIndexByType
  },
  {
    // admin
    path: '/admin',
    name: 'Admin',
    component: Admin
  },
  {
    // admin post list
    path: '/admin/posts/index',
    name: 'AdminPostsIndex',
    component: AdminPostsIndex
  },
  {
    // admin user list
    path: '/admin/user/index',
    name: 'AdminUserIndex',
    component: AdminUserIndex
  },
  {
    // admin login log
    path: '/admin/login/log',
    name: 'AdminLoginLog',
    component: AdminLoginLog
  },
  {
    // user index
    path: '/user/index/:userId',
    name: 'UserIndex',
    component: UserIndex
  },
  {
    // post comments
    path: '/post/comments/:postId',
    name: 'PostComments',
    component: PostComments
  },
  {
    // user fans list
    path: '/user/fans/list',
    name: 'UserFansList',
    component: UserFansList
  },
  {
    path: '/user/follow/list',
    name: 'UserFollowList',
    component: UserFollowList
  },
  {
    path: '/user/comments/:userId',
    name: 'UserComments',
    component: UserComments
  },
  {
    path: '/user/approval/:userId',
    name: 'UserApprovalPosts',
    component: UserApprovalPosts
  },
  {
    path: '/admin/category',
    name: 'AdminCategory',
    component: AdminCategory
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
