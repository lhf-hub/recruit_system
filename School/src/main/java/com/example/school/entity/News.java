package com.example.school.entity;

import lombok.Data;
/*系统公告*/
@Data
public class News {
    private int newsId;//通知id（该属性自动增长无需展示）
    private String newsTitle;//通知标题
    private String newsContent;//通知内容
    private String time;//通知发布时间
    private String department;//通知部门
}
