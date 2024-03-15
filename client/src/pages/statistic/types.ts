export type Statistic = {
  resumeNum: number // 简历数
  postNum: number // 岗位数
  preRecruitsNum: number // 预招人数
  trialNum: number // 在初审中的简历数
  writtenNum: number // 在笔试中的简历数
  interviewNum: number // 在面试中的简历数
  hiredNum: number // 已录用的简历数
  outNum: number // 已淘汰的简历数
  specialistNum: number // 专科简历数
  undergraduateNum: number // 本科简历数
  masterNum: number // 硕士简历数
  doctorNum: number // 博士简历数
  nameNum: Array<{
    postND: string // 岗位名称或所处部门
    resumeNum: number // 对应简历数
  }>
  departNum: Array<{
    postND: string // 岗位名称或所处部门
    resumeNum: number // 对应简历数
  }>
}

export type Statistic_two = {
  resumeNum: number // 简历数
  postNum: number // 岗位数
  preRecruitsNum: number // 预招人数
  trialNum: number // 在初审中的简历数
  writtenNum: number // 在笔试中的简历数
  interviewNum: number // 在面试中的简历数
  hiredNum: number // 已录用的简历数
  outNum: number // 已淘汰的简历数
  specialistNum: number // 专科简历数
  undergraduateNum: number // 本科简历数
  masterNum: number // 硕士简历数
  doctorNum: number // 博士简历数
}
