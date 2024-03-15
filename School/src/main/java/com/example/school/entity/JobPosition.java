package com.example.school.entity;

import lombok.Data;

import java.sql.Date;
/*岗位实体类*/
@Data
public class JobPosition {
    private int postId;//岗位id（自增长）
    private String postName;//岗位名称
    private int minSalary;//最低资薪
    private int maxSalary;//最高资薪
    private String department;//岗位部门
    private int ageRequirement;//年龄需求
    private String educationRequirement;//学历需求
    private String majorRequirement;//专业需求
    private String postState;//当前岗位应聘状态（应聘中，已结束）
    private String category;//岗位类别
    private Date startTime;//开始时间
    private String postSynopsis;//岗位简介
    private Date endTime;//结束时间（可选)
    private int numberRequirement;//招聘人数（可选）
}
