<template>
  <div class="box">
    <VaLayout>
      <template #top>
        <VaNavbar style="position: fixed; top: 0; font-size: 20px; background-color: rgba(0, 0, 0, 0.3)">
          <template #left>
            <VaNavbarItem>
              <VuesticLogo :height="50" />
            </VaNavbarItem>
          </template>

          <template #right>
            <VaNavbarItem class="va-navbar-item">
              <a href="#"> 首页 </a>
            </VaNavbarItem>
            <VaNavbarItem class="va-navbar-item">
              <a href="#inform"> 通知公告 </a>
            </VaNavbarItem>
            <VaNavbarItem class="va-navbar-item">
              <a href="#post"> 岗位信息 </a>
            </VaNavbarItem>
            <VaNavbarItem class="va-navbar-item">
              <a href="#footer"> 学校简介 </a>
            </VaNavbarItem>
            <VaNavbarItem v-if="!store.isLogin" style="margin-left: 30px; margin-right: 30px" class="va-navbar-item">
              <VaIcon size="36px" name="person" @click="signIn" />
            </VaNavbarItem>
            <VaNavbarItem v-else style="margin-left: 30px; margin-right: 30px" class="va-navbar-item">
              <VaAvatar size="36px" @click="showProfiles" />
            </VaNavbarItem>
          </template>
        </VaNavbar>
      </template>

      <template #content>
        <div
          style="
            margin-top: 100px;
            padding: 10px;
            overflow: auto;
            display: flex;
            align-items: center;
            justify-content: center;
          "
        >
          <RouterView />
        </div>
      </template>
    </VaLayout>
    <!-- footer 部分 -->
    <div id="footer" class="footer">
      <p>版权所有 &copy; CQJTU 2024 重庆交通大学</p>
      <p>学校地址：重庆市南岸区学府大道66号</p>
      <p>联系我们: jdxz@cqjtu.edu.cn</p>
      <p>电话: 023-62652439</p>
      <div>
        <a
          href="https://www.cqjtu.edu.cn"
          target="_blank"
          style="text-decoration: underline; color: white; margin-right: 10px"
          >官网</a
        >
        <a
          href="https://www.weibo.com/u/2673433235"
          target="_blank"
          style="text-decoration: underline; color: white; margin-right: 10px"
          >新浪微博</a
        >

        <a
          href="https://www.douyin.com/user/MS4wLjABAAAAC-7wQhiNmMj0hDoEQTTpKBftgBCpw71vxTSrrFYNHT7Ih_BVIkez0zQXbtuav2W2"
          target="_blank"
          style="text-decoration: underline; color: white"
          >抖音</a
        >
      </div>
    </div>
    <!--  个人中心  -->
    <VaModal v-if="store.isLogin" v-model="showProfile" hide-default-actions close-button>
      <VaCard style="padding: 20px">
        <VaCardTitle style="font-size: 24px">个人中心</VaCardTitle>
        <VaCardContent style="display: flex; justify-content: space-between">
          <VaAvatar size="100px" />
          <div>
            <VaIcon style="margin-left: 20px" size="large" name="email" title="信箱" @click="showMailboxes"></VaIcon>
            <VaIcon
              style="margin-left: 20px"
              size="large"
              name="description"
              title="简历"
              @click="showResumes"
            ></VaIcon>
            <VaIcon
              style="margin-left: 20px"
              size="large"
              name="logout"
              title="退出登录"
              @click="store.logout"
            ></VaIcon>
          </div>
        </VaCardContent>
        <VaCardContent>
          <VaInput v-model="store.userId" label="用户名" readonly />
          <VaValue v-slot="isPasswordVisible" :default-value="false">
            <VaForm ref="myForm">
              <VaInput
                v-model="store.password"
                label="密码"
                :type="isPasswordVisible.value ? 'text' : 'password'"
                :rules="[(v) => !!v || '密码不能为空']"
                @clickAppendInner="isPasswordVisible.value = !isPasswordVisible.value"
              >
                <template #appendInner>
                  <VaIcon
                    :name="isPasswordVisible.value ? 'visibility_off' : 'visibility'"
                    size="small"
                    color="primary"
                  />
                </template>
                <template #append>
                  <VaButton @click="validate() && updatePassword()">修改</VaButton>
                </template>
              </VaInput>
            </VaForm>
          </VaValue>
        </VaCardContent>
      </VaCard>
    </VaModal>
    <!--  简历管理  -->
    <VaModal v-if="store.isLogin" v-model="showResume" hide-default-actions close-button>
      <VaCard style="padding: 20px">
        <VaCardTitle style="font-size: 24px">个人简历</VaCardTitle>
        <VaCardContent>
          <VaForm ref="myForm">
            <VaInput v-model="resumeData.name" label="姓名" />
            <VaInput
              v-model="resumeData.idNumber"
              label="身份证号"
              :rules="[(v) => /^\d{17}(\d|X)$/.test(v) || '身份证号必须是18位，最后一位可以是数字或X']"
            />
            <VaInput v-model="resumeData.place" label="籍贯" />
            <VaInput v-model="resumeData.phoneNumber" type="number" label="电话号码" />
            <VaInput v-model="resumeData.age" type="number" label="年龄" readonly />
            <VaSelect v-model="resumeData.education" label="学历" :options="['大专', '本科', '硕士', '博士']" />
            <VaInput v-model="resumeData.major" label="专业" />
            <VaInput v-model="resumeData.academy" label="毕业院校" />
            <VaDateInput v-model="resumeData.birthdate" label="出生日期" :rules="[calculateAge]" />
            <VaSelect
              v-model="resumeData.politicsStatus"
              label="政治面貌"
              :options="['中共党员', '共青团员', '群众']"
            />
            <VaInput v-model="resumeData.otherInfo" label="其他信息" />
          </VaForm>
        </VaCardContent>
        <VaCardActions style="display: flex; justify-content: center; gap: 60px">
          <VaButton color="info" @click="showResume = false">取消</VaButton>

          <VaButton color="success" @click="validate() && submit()">保存</VaButton>
        </VaCardActions>
      </VaCard>
    </VaModal>
    <!--  邮件管理  -->
    <VaModal v-if="store.isLogin" v-model="showMailbox" fullscreen hide-default-actions close-button>
      <h1 class="text-center" style="font-size: xx-large; margin: 20px; color: #333">消息列表</h1>
      <VaList class="text-center">
        <h2 v-if="messages.length == 0" style="color: #666">无数据</h2>

        <VaListItem
          v-for="message in messages"
          :key="message.informId"
          style="margin-bottom: 5px; background-color: #f0f0f0; text-align: start"
          @click="goToDetail(message)"
        >
          <VaContent style="padding: 30px; position: relative; width: 100%" class="message-item">
            <div class="row">
              <div class="col-10">
                <h4 class="headline" style="color: #333">{{ message.informTitle }}</h4>
                <VaContent style="padding: 20px; color: #666; font-size: large">{{ message.informContent }}</VaContent>
              </div>
              <div class="col-2" style="display: flex; align-items: center; justify-content: center">
                <div
                  v-if="message.informState == 0"
                  style="
                    position: absolute;
                    width: 12px;
                    height: 12px;
                    border: none;
                    border-radius: 8px;
                    background-color: #ff0000;
                    right: 10px;
                    top: 10px;
                  "
                ></div>
              </div>
            </div>
            <p style="color: #999; position: absolute; bottom: 5px; right: 30px">发送时间：{{ message.informTime }}</p>
          </VaContent>
        </VaListItem>
      </VaList>
    </VaModal>
    <!--  邮件详细信息  -->
    <VaModal v-if="store.isLogin" v-model="showMailDetail" hide-default-actions>
      <h1 class="text-center" style="font-size: xx-large; margin: 20px; color: #333">消息详情</h1>
      <VaCard style="padding: 20px">
        <VaCardTitle style="font-size: 24px">{{ selectMessage.informTitle }}</VaCardTitle>
        <VaCardContent>
          <VaContent style="padding: 20px; color: #666; font-size: large">{{ messages[0].informContent }}</VaContent>
        </VaCardContent>
        <VaCardActions style="display: flex; justify-content: center; gap: 60px">
          <VaButton color="info" @click="showMailDetail = false">返回</VaButton>
        </VaCardActions>
      </VaCard>
    </VaModal>
  </div>
</template>

<script setup lang="ts">
import VuesticLogo from '../components/VuesticLogo.vue'
import { useUserStore } from '../stores/user-store'
import router from '../router'
import { ref } from 'vue'
import axios from 'axios'
import { useForm, useToast } from 'vuestic-ui'

import bus from 'vue3-eventbus'
import { auth, message, resume } from '../netConfig'

const { isValid, validate } = useForm('myForm')

const { notify } = useToast()

const calculateAge = (value: Date) => {
  if (value) {
    const birthdate = new Date(value)
    const today = new Date()
    const age = today.getFullYear() - birthdate.getFullYear()
    resumeData.value.age = age
  }
  return true
}
interface Message {
  informId: number
  userId: number
  informContent: string
  informTime: string
  informTitle: string
  informState: number
}
interface ResumeData {
  userId: number
  name: string
  idNumber: string
  place: string
  phoneNumber: number
  age: number
  education: string
  major: string
  academy: string
  birthdate: Date
  politicsStatus: string
  otherInfo: string
}

const store = useUserStore()
const resumeData = ref<ResumeData>({
  userId: store.userId,
  name: '',
  idNumber: '',
  place: '',
  phoneNumber: 0,
  age: 0,
  education: '',
  major: '',
  academy: '',
  birthdate: new Date(),
  politicsStatus: '',
  otherInfo: '',
})

const getMyResume = async () => {
  const res = await resume.query({ userId: store.userId })
  try{
    if (res.data.code === 200) {
      resumeData.value = res.data.data[0]
    } else {
      bus.emit('alert', { type: 'error', content: '服务器错误' })
    }
  }catch {
    bus.emit('alert', { type: 'error', content: '网络错误' })
  }
}

const messages = ref<Message[]>([])
const selectMessage = ref<Message>({
  informId: 0,
  userId: 0,
  informContent: '',
  informTime: '',
  informTitle: '',
  informState: 0,
})




const getMessages = async () => {
  const res = await message.query({ userId: store.userId, informTitle: '' })
  if (res.data.code == 200) {
    messages.value = res.data.data
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}

const signIn = () => {
  router.push('/auth')
}
const showProfile = ref(false)
const showResume = ref(false)
const showMailbox = ref(false)
const showMailDetail = ref(false)

const showProfiles = () => {
  showProfile.value = true
}
const showResumes = () => {
  getMyResume()
  showResume.value = true
}
const showMailboxes = () => {
  getMessages()
  showMailbox.value = true
}

const goToDetail = async (msg: Message) => {
  selectMessage.value = msg
  showMailDetail.value = true
  selectMessage.value.informState = 1
  const res = await message.update({ informId: msg.informId, informState: 1 })
  if (res.data.code == 200) {
    getMessages()
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}

const submit = async () => {
  const res = await resume.update(resumeData.value)
  if (res.data.code === 200) {
    showResume.value = false
    bus.emit('alert', { type: 'success', content: '保存成功' })
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}

const updatePassword = async () => {
  const res = await auth.update({ userId: store.userId, password: store.password })
  if (res.data.code === 200) {
    bus.emit('alert', { type: 'success', content: '修改成功' })
  } else {
    bus.emit('alert', { type: 'error', content: '服务器错误' })
  }
}
</script>

<style scoped>
.logo {
  font-weight: 600;
  font-size: 1.5rem;
}

.va-navbar-item {
  font-weight: bold;
  margin-left: 20px;
  transition: all 0.15s linear;
}

.va-navbar-item:hover {
  color: white;
  cursor: pointer;
  border-radius: 5px;
  background-color: rgba(241, 156, 213, 0.1);
  transition: all 0.15s linear;
}

.router-link-active {
  color: white;
  cursor: pointer;
  border-radius: 5px;
}

.box {
  background-image: url('/bg.png');
  background-size: cover;
  background-attachment: fixed;
  background-repeat: no-repeat;
}

.box::before {
  position: absolute;
  content: '';
  width: 100%;
  height: 100%;
  backdrop-filter: blur(5px);
}

.footer {
  width: 100%;
  margin: 0;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  text-align: center;
  padding: 20px;
}
.message-item {
  transition: background-color 0.1s ease;
}

.message-item:hover {
  cursor: pointer;
  background-color: #e0e0e0;
}

.message-item:active {
  background-color: #d0d0d0;
}
</style>
