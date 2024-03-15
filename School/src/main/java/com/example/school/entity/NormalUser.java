package com.example.school.entity;

import lombok.Data;

import java.sql.Date;
/*简历实体类*/
@Data
public class NormalUser {
    private int userId;//用户名（与账号的user_id一致，前端在写简历时不需要展示）
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
