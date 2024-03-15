package com.example.school.entity;

import lombok.Data;

import java.sql.Date;
/*应聘流程信息实体类*/
@Data
public class ApplicationInfo {
    private int applicationId;//应聘流程信息id（该属性自动增长前端无需展示）
    private int postId;//岗位id（外键）
    private int userId;//用户id（外键）
    private String applicationTime;//应聘提交时间
    private String status;//应聘所处状态（初审中，笔试中，面试中，录用，淘汰）
    private int similarity;//岗位相似度（算法给出）
    private String writtenTime;//笔试时间
    private int writtenScore;//笔试成绩
    private String interviewTime;//面试时间
}
