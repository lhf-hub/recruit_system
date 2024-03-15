package com.example.school.entity;

import lombok.Data;

/*应聘消息*/
@Data
public class Information {
    private int informId;//应聘消息id（自动增长无需展示）
    private int userId;//用户id（外键）
    private int postId;//岗位Id(外键)
    private String informTitle;//消息标题
    private String informContent;//消息内容
    private String informTime;//消息发布时间
    private int informState;//消息状态
}
