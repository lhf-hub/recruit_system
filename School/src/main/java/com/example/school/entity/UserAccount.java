package com.example.school.entity;

import lombok.Data;

/*账号管理实体类*/
@Data
public class UserAccount {
    private int userId;//用户名
    private String password;//密码
    private String role;//权限（该属性前端不需要展示）
}
