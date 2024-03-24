package com.example.school.mapper;

import com.example.school.entity.Information;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InformationMapper {
    @Select("select * from information where user_id=#{userId} and " +
            "inform_title like concat('%', #{informTitle}, '%') and user_id=#{userId} ORDER BY inform_time DESC")
    List<Information> find(Information information);

    @Insert("insert into information(user_id,post_id,inform_title,inform_content,inform_time,inform_state) values " +
            "(#{userId},#{postId},#{informTitle},#{informContent},#{informTime},#{informState})")
    int insert(Information information);

    @Delete("delete from information where user_id=#{userId}")
    int delete(int userId);

    @Update("UPDATE information set inform_state=#{informState} WHERE " +
            "(user_id=#{userId} and post_id=#{postId}) or inform_id=#{informId}")
    int update(Information information);
}
