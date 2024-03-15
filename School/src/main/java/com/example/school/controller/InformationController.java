package com.example.school.controller;

import com.example.school.combineEntity.Result;
import com.example.school.entity.Information;
import com.example.school.mapper.ApplicationInfoMapper;
import com.example.school.mapper.InformationMapper;
import com.example.school.mapper.JobPositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


@RestController
@RequestMapping("/Information")
public class InformationController {
    @Autowired
    private InformationMapper informationMapper;
    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;
    @Autowired
    private JobPositionMapper jobPositionMapper;

    public void insert(int applicationId){/*应聘消息通知添加*/
        int postId=applicationInfoMapper.find_one(applicationId).getPostId();
        int userId=applicationInfoMapper.find_one(applicationId).getUserId();
        String status=applicationInfoMapper.find_one(applicationId).getStatus();
        String postName=jobPositionMapper.find_one(postId).getPostName();
        Information information=new Information();
        information.setPostId(postId);
        information.setUserId(userId);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        information.setInformTime(formatter.format(date));
        if(Objects.equals(status, "初审中")){
            information.setInformTitle("应聘消息");
            information.setInformContent("应聘"+postName+"申请提交成功，请等待初审结果");
            information.setInformState(0);
            informationMapper.insert(information);
        }
        else if(Objects.equals(status, "笔试中")){
            information.setInformTitle("初审消息");
            information.setInformContent("应聘"+postName+"初审通过，请在"+
                    applicationInfoMapper.find_one(applicationId).getWrittenTime()+
                    "在XXX网站上参加笔试");
            information.setInformState(0);
            informationMapper.insert(information);
        }
        else if(Objects.equals(status, "面试中")){
            information.setInformTitle("笔试消息");
            information.setInformContent(postName+"笔试通过，您的笔试成绩为"+
                    applicationInfoMapper.find_one(applicationId).getWrittenScore()+",请在"+
                    applicationInfoMapper.find_one(applicationId).getWrittenTime()+
                    "在xx楼xx号参加面试");
            information.setInformState(0);
            informationMapper.insert(information);
        }
        else if(Objects.equals(status, "录用")){
            information.setInformTitle("面试消息");
            information.setInformContent("恭喜面试者通过本高校"+postName+"招聘，请到xx楼xx号登记");
            information.setInformState(0);
            informationMapper.insert(information);
        }
        else if(Objects.equals(status,"淘汰")){
            information.setInformTitle("淘汰消息");
            information.setInformContent("很遗憾应聘者未能通过"+postName+"的招聘，请申请其他更符合的岗位");
            information.setInformState(0);
            informationMapper.insert(information);
        }
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Information information){
        int result = informationMapper.delete(information.getUserId());
        if(result > 0)
        {
            return Result.ok("应聘消息删除成功");
        }
        return Result.error("应聘消息删除失败");
    }

    @PostMapping("/find")/*消息标题模糊查询*/
    public Result find(@RequestBody Information information){
        return Result.ok().put("data", informationMapper.find(information));
    }

    @PostMapping("/update")/*更新阅读状态*/
    public Result update(@RequestBody Information information){
        int result = informationMapper.update(information);
        if(result > 0)
        {
            return Result.ok("应聘消息更新成功");
        }
        return Result.error("应聘消息更新失败");
    }
}
