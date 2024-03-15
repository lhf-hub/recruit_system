package com.example.school.combineEntity;

import lombok.Data;

@Data
public class PostResumeStats {
    private String postND;//岗位名称或所处部门
    private int resumeNum;//对应简历数
    public PostResumeStats(String postND,int resumeNum){
        this.postND=postND;this.resumeNum=resumeNum;
    }
}
