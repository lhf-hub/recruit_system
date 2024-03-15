package com.example.school.combineEntity;

import lombok.Data;

import java.sql.Date;
/*应聘相关的所有信息*/
@Data
public class CV {
    private int applicationId;//应聘流程信息id（该属性自动增长前端无需展示）
    private String applicationTime;//应聘提交时间
    private String status;//应聘所处状态（初审中，笔试中，面试中，录用，淘汰）
    private int similarity;//岗位相似度（算法给出）
    private String writtenTime;//笔试时间
    private int writtenScore;//笔试成绩
    private String interviewTime;//面试时间

    private int postId;//岗位id（外键）
    private String postName;//岗位名称
    private String department;//岗位部门
    private int ageRequirement;//年龄需求
    private String educationRequirement;//学历需求
    private String majorRequirement;//专业需求

    private int userId;//用户id（外键）
    private String name;//姓名
    private String sex;//性别
    private String idNumber;//证件号
    private String place;//出生地
    private String phoneNumber;//电话号码
    private int age;//年龄
    private String education;//学历
    private String major;//专业
    private String academy;//毕业学校
    private Date birthdate;//生日
    private String politicsStatus;//政治面貌
    private String otherInfo;//其他信息
}
