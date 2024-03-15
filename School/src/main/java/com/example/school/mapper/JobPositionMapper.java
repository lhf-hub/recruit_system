package com.example.school.mapper;

import com.example.school.entity.JobPosition;
import org.apache.ibatis.annotations.*;

import java.util.List;
/*岗位数据库接口*/
@Mapper
public interface JobPositionMapper {
    @Select("select * from jobposition where post_id=#{postId}")
    JobPosition find_one(int postId);/*应聘消息查找单个岗位*/
    @Select("select * from jobposition where post_id=#{postId}")
    List<JobPosition> findOne(int postId);/*添加查重用（避免不必要报错）*/
    @Select("select * from jobposition where post_name like concat('%', #{postName}, '%')")
    List<JobPosition> find(JobPosition jobPosition);/*使用岗位名称模糊查询*/

    @Select("SELECT DISTINCT department FROM jobposition")
    List<JobPosition> find_depart();/*查询部门个数*/

    @Insert("insert into jobposition" +
            "(post_id,post_name,min_salary,max_salary,department," +
            "age_requirement,education_requirement,major_requirement," +
            "post_state,category,start_time,post_synopsis,end_time,number_requirement)" +
            " values " +
            "(#{postId},#{postName},#{minSalary},#{maxSalary},#{department}," +
            "#{ageRequirement},#{educationRequirement},#{majorRequirement}," +
            "#{postState},#{category},#{startTime},#{postSynopsis},#{endTime},#{numberRequirement})")
    int insert(JobPosition jobPosition);

    @Update("update jobposition set " +
            "post_name=#{postName},min_salary=#{minSalary},max_salary=#{maxSalary},department=#{department}," +
            "age_requirement=#{ageRequirement},education_requirement=#{educationRequirement},major_requirement=#{majorRequirement}," +
            "post_state=#{postState},category=#{category},start_time=#{startTime},post_synopsis=#{postSynopsis}," +
            "end_time=#{endTime},number_requirement=#{numberRequirement}" +
            " where post_id=#{postId}")
    int update(JobPosition jobPosition);

    @Update("update jobposition set number_requirement=#{num} where post_id=#{postId}")
    void updateNumReduce(int num,int postId);

    @Update("update jobposition set number_requirement=#{num} where post_id=#{postId}")
    void updateNumIncrease(int num,int postId);

    @Delete("delete from jobposition where post_id=#{postId}")
    int delete(int postId);
}
