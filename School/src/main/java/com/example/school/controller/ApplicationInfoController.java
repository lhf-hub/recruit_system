package com.example.school.controller;

import com.example.school.combineEntity.*;
import com.example.school.entity.ApplicationInfo;
import com.example.school.entity.JobPosition;
import com.example.school.entity.NormalUser;
import com.example.school.mapper.ApplicationInfoMapper;
import com.example.school.mapper.JobPositionMapper;
import com.example.school.mapper.NormalUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.List;


/*（前后端链接）应聘流程信息管理*/
@RestController
@RequestMapping("/ApplicationInfo")
public class ApplicationInfoController {
    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;
    @Autowired
    private InformationController informationController;
    @Autowired
    private JobPositionMapper jobPositionMapper;
    @Autowired
    private NormalUserMapper normalUserMapper;

    @PostMapping("/insert")/*应聘流程信息添加(应聘申请的提交)*/
    public Result insert(@RequestBody ApplicationInfo applicationInfo){
        try{
            int userId=applicationInfo.getUserId();
            int postId=applicationInfo.getPostId();
            List<NormalUser> normalUsers=normalUserMapper.findOne(userId);
            NormalUser normalUser=normalUsers.get(0);
            if(Objects.equals(normalUser.getName(), "请添加姓名")){
                return Result.error(500,"请先完成简历填写");
            }

            if(!applicationInfoMapper.findOne(postId,userId).isEmpty()){
                if(Objects.equals(applicationInfoMapper.findOne(postId, userId).get(0).getStatus(), "录用")) {
                    return Result.error(502, "您已被该岗位录取，请勿重复投递");
                }
            }

            List<ApplicationInfo> applicationInfoList=applicationInfoMapper.findAllU(userId);//检查是否已被岗位录取
            for(int i=0;i<applicationInfoList.size();++i){
                ApplicationInfo applicationInfoTemp=applicationInfoList.get(i);
                if(Objects.equals(applicationInfoTemp.getStatus(), "录用")){
                   return Result.error(503,"你已被其他岗位录取，请勿重复投递");
                }
            }

            int num=jobPositionMapper.find_one(postId).getNumberRequirement();/*检查剩余名额*/
            if(num==0){
                return Result.error(505,"岗位剩余申请名额不足，请等待名额");
            }

            String state=jobPositionMapper.find_one(postId).getPostState();/*检查岗位招聘状态*/
            if(Objects.equals(state, "未开始")){
                return Result.error("招聘未开始请耐心等待");
            }else if(Objects.equals(state, "已结束")){
                return Result.error("招聘已结束");
            }

            if(applicationInfoMapper.findOne(postId,userId).isEmpty()) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                applicationInfo.setApplicationTime(formatter.format(date));
                applicationInfo.setStatus("初审中");

                int score=getSimilarity(applicationInfo);/*岗位相似度*/
                applicationInfo.setSimilarity(score);

                int result = applicationInfoMapper.insert(applicationInfo);/*应聘流程信息的添加*/

                ApplicationInfo appId=applicationInfoMapper.find_id(userId,postId);
                System.out.println(appId);
                informationController.insert(appId.getApplicationId());

                if(result>0) {
                    return Result.ok("应聘流程添加成功");
                }
                return Result.error("应聘失败");/*否则为0表示无法添加*/
            }
            return Result.error(501,"为了减少系统维护量，请勿重复某一岗位投递");
        }catch (Exception e){
            System.out.println("问题："+e);
            return Result.error(500,"用户应聘不完整信息");
        }
    }

    @PostMapping("/delete")/*应聘申请被淘汰后可删除ok*/
    public Result delete(@RequestBody ApplicationInfo applicationInfo){
        int result = applicationInfoMapper.delete(applicationInfo);
        if(result > 0)
        {
            return Result.ok("应聘流程信息删除成功");
        }
        return Result.error("应聘流程信息删除失败");
    }

    @PostMapping("/updateIn")/*初审，笔试，面试更新*/
    public Result updateIn(@RequestBody ApplicationInfo applicationInfo){
        System.out.println(applicationInfo.getWrittenTime());
        try{
            if(applicationInfo.getWrittenTime()!=null){/*初审通过更新笔试时间*/
                applicationInfo.setStatus("笔试中");
                int result = applicationInfoMapper.updateWritten(applicationInfo);
                informationController.insert(applicationInfo.getApplicationId());
                if(result > 0)
                {
                    return Result.ok("应聘流程信息更新成功");
                }
            }
            else if(applicationInfo.getInterviewTime()!=null){/*笔试通过更新面试时间*/
                applicationInfo.setStatus("面试中");
                int result = applicationInfoMapper.updateInterview(applicationInfo);
                informationController.insert(applicationInfo.getApplicationId());
                if(result > 0)
                {
                    return Result.ok("应聘流程信息更新成功");
                }
            }
            applicationInfo.setStatus("录用");
            int result = applicationInfoMapper.updateFinal(applicationInfo);
            informationController.insert(applicationInfo.getApplicationId());
            ApplicationInfo applicationInfo1=applicationInfoMapper.find_one(applicationInfo.getApplicationId());
            int postId=applicationInfo1.getPostId();
            if(result > 0)
            {
                int num=jobPositionMapper.find_one(postId).getNumberRequirement();/*检查剩余名额*/
                if(num==1){//只剩下一个岗位的时候
                    out_other(applicationInfo.getApplicationId());//淘汰其他人
                }
                jobPositionMapper.updateNumReduce(num-1,postId);
                out_SelfOther(applicationInfo.getApplicationId());
                return Result.ok("应聘流程信息更新成功");
            }
            return Result.error("应聘流程信息更新失败");
        }catch (Exception e){
            System.out.println("应聘信息更新出错，请检查代码");
        }
        return null;
    }

    @PostMapping("/updateOut")/*淘汰更新*/
    public Result updateOut(@RequestBody ApplicationInfo applicationInfo){
        applicationInfo.setStatus("淘汰");
        int result = applicationInfoMapper.updateFinal(applicationInfo);
        informationController.insert(applicationInfo.getApplicationId());

        int postId=applicationInfoMapper.find_one(applicationInfo.getApplicationId()).getPostId();
        int num=jobPositionMapper.find_one(postId).getNumberRequirement();/*检查剩余名额*/
        jobPositionMapper.updateNumIncrease(num+1,postId);
        if(result > 0)
        {
            return Result.ok("应聘流程信息更新成功");
        }
        return Result.error("应聘流程信息更新失败");
    }

    @PostMapping("/find")/*按照用户姓名查询简历所有有关信息*/
    public Result find(@RequestBody CV cv){
        return Result.ok().put("data", applicationInfoMapper.find(cv));
    }

    @PostMapping("/stats")/*招聘总数据返回ok*/
    public Result stats(@RequestBody JobPosition jobPosition){
//        System.out.println(jobPosition.getCategory());
        List<ApplicationInfoStats> applicationInfoStatsList=new ArrayList<>();
        ApplicationInfoStats applicationInfoStats=new ApplicationInfoStats();
        String category=jobPosition.getCategory();

        applicationInfoStats.setResumeNum(applicationInfoMapper.get_resumeNum(category));
        applicationInfoStats.setPostNum(applicationInfoMapper.get_postNum(category));
        applicationInfoStats.setPreRecruitsNum(get_pNum(category));

        applicationInfoStats.setTrialNum(applicationInfoMapper.get_statusNum("初审中",category));
        applicationInfoStats.setWrittenNum(applicationInfoMapper.get_statusNum("笔试中",category));
        applicationInfoStats.setInterviewNum(applicationInfoMapper.get_statusNum("面试中",category));
        applicationInfoStats.setHiredNum(applicationInfoMapper.get_statusNum("录用",category));
        applicationInfoStats.setOutNum(applicationInfoMapper.get_statusNum("淘汰",category));

        applicationInfoStats.setSpecialistNum(applicationInfoMapper.get_eduNum("专科",category));
        applicationInfoStats.setUndergraduateNum(applicationInfoMapper.get_eduNum("本科",category));
        applicationInfoStats.setMasterNum(applicationInfoMapper.get_eduNum("硕士",category));
        applicationInfoStats.setDoctorNum(applicationInfoMapper.get_eduNum("博士",category));

        applicationInfoStats.setNameNum(get_nameStats(category));
        applicationInfoStats.setDepartNum(get_departStats(category));

        applicationInfoStatsList.add(applicationInfoStats);
        return Result.ok().put("data",applicationInfoStatsList);
    }
public void out_SelfOther(int applicationId){
        ApplicationInfo applicationInfo=applicationInfoMapper.find_one(applicationId);
        int userId=applicationInfo.getUserId();
        int postId=applicationInfo.getPostId();
        List<ApplicationInfo> applicationInfoList=applicationInfoMapper.findAllU(userId);
        for(int i=0;i<applicationInfoList.size();++i){
            ApplicationInfo applicationInfoTemp=applicationInfoList.get(i);
            if(applicationInfoTemp.getPostId()!=postId){
                applicationInfoTemp.setStatus("淘汰");
                applicationInfoMapper.updateFinal(applicationInfoTemp);
                informationController.insert(applicationInfoTemp.getApplicationId());
            }
        }
}
    public void out_other(int applicationId){
        ApplicationInfo applicationInfo=applicationInfoMapper.find_one(applicationId);
        int userId=applicationInfo.getUserId();
        int postId=applicationInfo.getPostId();
        List<ApplicationInfo> applicationInfoList=applicationInfoMapper.findAllP(postId);
        for(int i=0;i<applicationInfoList.size();++i){
            ApplicationInfo applicationInfoTemp=applicationInfoList.get(i);
            if(applicationInfoTemp.getUserId()!=userId){
                if(!Objects.equals(applicationInfoTemp.getStatus(), "录用")){
                    applicationInfoTemp.setStatus("淘汰");
                    applicationInfoMapper.updateFinal(applicationInfoTemp);
                    informationController.insert(applicationInfoTemp.getApplicationId());
                }
            }
        }
    }

public int get_pNum(String category){
        JobPosition jobPosition=new JobPosition();
        jobPosition.setPostName("");
        List<JobPosition> jobPositionList=jobPositionMapper.find(jobPosition);
        int num=0;
        for(int i=0;i<jobPositionList.size();++i){
            JobPosition jobPositionTemp=jobPositionList.get(i);
            if(Objects.equals(jobPositionTemp.getCategory(), category)){
                num=num+jobPositionTemp.getNumberRequirement();
            }
            else if(Objects.equals(category, "")){
                num=num+jobPositionTemp.getNumberRequirement();
            }
        }
        num=num+applicationInfoMapper.get_statusNum("",category)-applicationInfoMapper.get_statusNum("淘汰",category);
        return num;
}
public List<PostResumeStats> get_nameStats(String category){
            List<PostResumeStats> postResumeStatslist = new ArrayList<>();
            JobPosition jobPosition=new JobPosition();
            jobPosition.setPostName("");
            List<JobPosition> jobPositionList=jobPositionMapper.find(jobPosition);
            for(int i=0;i<jobPositionList.size();++i){
                JobPosition jobPositionTemp=jobPositionList.get(i);
                String name=jobPositionTemp.getPostName();
                PostResumeStats postResumeStats=new PostResumeStats(name,applicationInfoMapper.get_postNameResumeNum(name,category));
                postResumeStatslist.add(postResumeStats);
            }
            return postResumeStatslist;
}
public List<PostResumeStats> get_departStats(String category) {
    List<PostResumeStats> postResumeStatslist = new ArrayList<>();
    List<JobPosition> jobPositionList=jobPositionMapper.find_depart();
    for(int i=0;i<jobPositionList.size();++i){
        JobPosition jobPositionTemp=jobPositionList.get(i);
        String depart=jobPositionTemp.getDepartment();
        PostResumeStats postResumeStats=new PostResumeStats(depart,applicationInfoMapper.get_departResumeNum(depart,category));
        postResumeStatslist.add(postResumeStats);
    }
    return postResumeStatslist;
}

public int getSimilarity(ApplicationInfo applicationInfo)/*岗位相似度算法*/
{
    //学历 50%，专业30%，年龄20%
    Similarity sim = applicationInfoMapper.getSimilarity(applicationInfo);
    System.out.println(sim.getEducation() + sim.getMajor() + sim.getAge());
    JobPosition job = applicationInfoMapper.getJobInfo(applicationInfo);
    System.out.println(job.getPostId()+job.getEducationRequirement()+job.getMajorRequirement()+job.getAgeRequirement());

    int user_education, requirement_education;
    int edu_sim, maj_sim, age_sim;
    user_education = eductionLevel(sim.getEducation());
    requirement_education = eductionLevel(job.getEducationRequirement());
    int edu = user_education - requirement_education;
    if(edu == 3)
    {
        edu_sim = 50;
    }
    else if(edu == 2)
    {
        edu_sim = 45;
    }
    else if(edu == 1)
    {
        edu_sim = 40;
    }
    else if(edu == 0)
    {
        edu_sim = 38;
    }
    else
    {
        edu_sim = 30;
    }

    if(sim.getMajor().equals(job.getMajorRequirement()))
    {
        maj_sim = 28;
    }
    else
    {
        maj_sim = 19;
    }

    if((sim.getAge() - job.getAgeRequirement()) < 5 && (sim.getAge() - job.getAgeRequirement()) > -5 )
    {
        age_sim = 18;
    }
    else
    {
        age_sim = 18 - ((sim.getAge() - job.getAgeRequirement()) > 0 ? (sim.getAge() - job.getAgeRequirement()) - 5: -(sim.getAge() - job.getAgeRequirement()) - 5);
    }
    System.out.println(age_sim + " "+maj_sim+" "+edu_sim);
    return age_sim + maj_sim + edu_sim ;
}
public int eductionLevel(String edu)
{
        int result;
        if(edu.equals("大专"))
        {
            result = 0;

        }
        else if(edu.equals("本科"))
        {
            result = 1;
        }
        else if (edu.equals("硕士"))
        {
            result = 2;
        }
        else
        {
            result = 3;
        }
        return  result;
    }
}
