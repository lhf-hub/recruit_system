package com.example.school.controller;

import com.example.school.entity.News;
import com.example.school.combineEntity.Result;
import com.example.school.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/*（前后端链接）系统消息管理*/
@RestController
@RequestMapping("/News")
public class NewsController {
    @Autowired
    private NewsMapper newsMapper;

    @PostMapping("/insert")/*通知公告添加ok*/
    public Result insert(@RequestBody News news){
        if(news.getNewsTitle()==null||news.getNewsTitle().isEmpty()){
            return Result.error("消息标题不可为空");
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        news.setTime(formatter.format(date));
        int result = newsMapper.insert(news);
        if(result>0){
            return Result.ok("添加成功");
        }
        return Result.error(500,"添加失败");
    }

    @PostMapping("/delete")/*ok*/
    public Result delete(@RequestBody News news){
        int result = newsMapper.delete(news.getNewsId());
        if(result>0){
            return Result.ok("消息通知删除成功");
        }
        return Result.error("消息通知删除失败");
    }

    @PostMapping("/find")/*按照通知公告标题模糊查询ok*/
    public Result find(@RequestBody News news){
        return Result.ok().put("data",newsMapper.find(news.getNewsTitle()));
    }

//    @PostMapping("/update")/*老版式不用管*/
//    public Integer update(@RequestBody News news){
//        return newsMapper.update(news);
//    }
}
