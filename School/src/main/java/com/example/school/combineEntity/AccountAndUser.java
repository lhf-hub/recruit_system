package com.example.school.combineEntity;

import lombok.Data;
/*账号与普通用户的集合类（普通用户管理功能）*/
@Data
public class AccountAndUser {
    private int userId;//用户名
    private String password;//密码
    private String name;//姓名
    private String sex;//性别
    private String phoneNumber;//电话号码
}
