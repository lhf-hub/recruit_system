package com.example.school.mapper;

import com.example.school.entity.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {
    @Select("select * from news where news_title like  concat('%', #{newsTitle}, '%') ORDER BY time DESC")
    List<News> find(String newsTitle);
    @Insert("insert into news(news_id,news_title,news_content,time,department) values " +
            "(#{newsId},#{newsTitle},#{newsContent},#{time},#{department})")
    int insert(News news);

    @Delete("delete from news where news_id=#{newsId}")
    int delete(int newsId);

//    @Update("UPDATE news set news_title=#{newsTitle},news_content=#{newsContent},time=#{time},department=#{department}" +
//            " WHERE news_id=#{newsId}")
//    int update(News news);/*废案*/
}
