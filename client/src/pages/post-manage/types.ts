import { Resume } from '../resume-manage/types'
import internal from 'node:stream'

// export type UserRole = 'admin' | 'user' | 'owner'

export type Post = {
  postId: number
  postName: string
  minSalary: number
  maxSalary: number
  department: string
  ageRequirement: number
  educationRequirement: string
  majorRequirement: string
  postState: string
  category: string
  startTime: string
  endTime: string
  postSynopsis: string
  numberRequirement: number
}
