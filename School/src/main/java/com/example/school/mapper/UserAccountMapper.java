package com.example.school.mapper;

import com.example.school.combineEntity.AccountAndUser;
import com.example.school.entity.UserAccount;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*账号管理的数据库接口*/
@Mapper
public interface UserAccountMapper {
//    @Select("select useraccount.user_id,password,name,sex,phone_number" +
//            " from useraccount,normaluser where useraccount.user_id=normaluser.user_id")
//    List<AccountAndUser> findAll();

    @Select("select * from useraccount,normaluser where (useraccount.user_id=normaluser.user_id and name like concat('%', #{name}, '%') and role = 'user')\n" +
            "or (useraccount.user_id=normaluser.user_id and name is NULL)")
    List<AccountAndUser> find(AccountAndUser accountAndUser);

    @Select("select * from useraccount where user_id=#{userId}")
    List<UserAccount> findOne(int userId);

    @Insert("insert into useraccount(user_id,password,role) values (#{userId},#{password},'user')")/*ok*/
    int insert(UserAccount userAccount);

    @Update("UPDATE useraccount set password=#{password} WHERE user_id=#{userId}")/*ok*/
    int update(UserAccount userAccount);

    @Delete("delete from useraccount where user_id=#{userId}")/*ok*/
    int delete(int userId);
}
