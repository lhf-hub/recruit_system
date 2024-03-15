package com.example.school.mapper;

import com.example.school.entity.NormalUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
/*简历管理的数据库接口*/
@Mapper
public interface NormalUserMapper {
    @Select("select * from normaluser")
    List<NormalUser> findAll();
    @Select("select * from normaluser where user_id=#{userId}")
    List<NormalUser> findOne(int userId);

    @Insert("insert into normaluser(user_id) values (#{uerId})")/*ok*/
    int insert(int userId);

    @Update("update normaluser set " +/*ok*/
            "name=#{name},sex=#{sex},id_number=#{idNumber},place=#{place},phone_number=#{phoneNumber}," +
            "age=#{age},education=#{education},major=#{major},academy=#{academy}," +
            "birthdate=#{birthdate},politics_status=#{politicsStatus},other_info=#{otherInfo}" +
            " where user_id=#{userId}")
    int update(NormalUser normalUser);

    @Delete("delete from normaluser where user_id=#{userId}")/*ok*/
    int delete(int userId);

//    @Insert("insert into normaluser" +
//            "(user_id,name,sex,id_number,place,phone_number," +
//            "age,education,major,academy,birthdate,politics_status,other_info)" +
//            " values " +
//            "(#{userId},#{name},#{sex},#{idNumber},#{place},#{phoneNumber}," +
//            "#{age},#{education},#{major},#{academy},#{birthdate},#{politicsStatus},#{otherInfo})")
//    int insert(NormalUser normalUser);
}
