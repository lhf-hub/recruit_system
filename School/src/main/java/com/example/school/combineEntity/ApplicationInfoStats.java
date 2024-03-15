package com.example.school.combineEntity;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationInfoStats {
    private int resumeNum;//已投简历总数
    private int postNum;//岗位总数
    private int preRecruitsNum;//预招人数

    private int trialNum;//在初审中的简历数
    private int writtenNum;//在笔试的简历数
    private int interviewNum;//在面试的简历数
    private int hiredNum;//在录用的简历数
    private int outNum;//淘汰简历数

    private int specialistNum;//专科简历数
    private int undergraduateNum;//本科简历数
    private int masterNum;//硕士简历数
    private int doctorNum;//博士简历数

    private List<PostResumeStats> nameNum;//各岗位简历数
    private List<PostResumeStats> departNum;//各部门简历数
}
