package com.example.school.controller;

import com.example.school.combineEntity.Result;
import com.example.school.entity.JobPosition;
import com.example.school.mapper.ApplicationInfoMapper;
import com.example.school.mapper.InformationMapper;
import com.example.school.mapper.JobPositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*（前后端链接）岗位管理*/
@RestController
@RequestMapping("/JobPosition")
public class JobPositionController {
    @Autowired
    private JobPositionMapper jobPositionMapper;
    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;

    @PostMapping("/insert")/*岗位添加*/
    public Result insert(@RequestBody JobPosition jobPosition){
        if(jobPosition.getPostName()==null||jobPosition.getPostName().isEmpty()){
            return Result.error("岗位名称不可为空");
        }
        if(jobPositionMapper.findOne(jobPosition.getPostId()).isEmpty()) {
            int result=jobPositionMapper.insert(jobPosition);/*岗位添加个数*/
            if(result>0){
                return Result.ok("岗位添加成功");
            }
        }
        return Result.error("岗位添加失败");
    }

    @PostMapping("/delete")/*岗位删除*/
    public Result delete(@RequestBody JobPosition jobPosition){
        applicationInfoMapper.deleteAllPost(jobPosition.getPostId());/*应聘信息表相关数据删除*/
        int result = jobPositionMapper.delete(jobPosition.getPostId());/*岗位删除个数*/
        if(result>0){
            return Result.ok("岗位删除成功");
        }
        return Result.error("岗位删除失败");
    }

    @PostMapping("/update")/*岗位更新*/
    public Result update(@RequestBody JobPosition jobPosition){
        if(jobPosition.getPostName()==null||jobPosition.getPostName().isEmpty()){
            return Result.error("岗位名称不可为空");
        }
        int result = jobPositionMapper.update(jobPosition);/*岗位更新个数*/
        if(result>0){
            return Result.ok("岗位更新成功");
        }
        return Result.error("岗位更新失败");
    }

    @PostMapping("/find")/*按照岗位名称查找并展示岗位信息ok*/
    public Result find(@RequestBody JobPosition jobPosition){
        return Result.ok().put("data",jobPositionMapper.find(jobPosition));
    }

//    @PostMapping("/findAll")/*所有岗位查找*/
//    public Result findAll(){
//        return Result.ok().put("data",jobPositionMapper.findAll());
//    }
}
