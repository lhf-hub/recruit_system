export type Resume = {
  userId: number
  name: string
  idNumber: string
  place: string
  phoneNumber: number
  age: number
  education: string
  major: string
  academy: string
  birthdate: string
  politicsStatus: string
  otherInfo: string
}

export type ApplicationInfo = {
  applicationId: number
  postId: number
  userId: number
  applicationTime: string
  status: string
  similarity: number
  writtenTime: string
  writtenScore: number
  interviewTime: string
}

export type Post = {
  postId: number
  postName: string
  department: string
  ageRequirement: number
  educationRequirement: string
  majorRequirement: string
}

// 整合简历信息和申请信息
export type ResumeInfo = Resume & ApplicationInfo & Post
