package com.example.school.mapper;

import com.example.school.combineEntity.CV;
import com.example.school.combineEntity.Similarity;
import com.example.school.entity.ApplicationInfo;
import com.example.school.entity.JobPosition;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*应聘流程信息数据库接口*/
@Mapper
public interface ApplicationInfoMapper {
    @Select("SELECT user_id, name, age, education, major from normaluser WHERE user_id=#{userId}")
    Similarity getSimilarity(ApplicationInfo applicationInfo);

    @Select("SELECT * from jobposition WHERE post_id=#{postId} ")
    JobPosition getJobInfo(ApplicationInfo applicationInfo);

    @Select("SELECT * from normaluser,applicationinfo,jobposition WHERE " +
            "normaluser.user_id=applicationinfo.user_id and applicationinfo.post_id=jobposition.post_id " +
            "and name like concat('%', #{name}, '%')")
    List<CV> find(CV cv);

    @Select("select * from applicationinfo where user_id=#{userId}")
    List<ApplicationInfo> findAllU(int userId);

    @Select("select * from applicationinfo where post_id=#{postId}")
    List<ApplicationInfo> findAllP(int postId);

    @Select("select * from applicationinfo where post_id=#{postId} and user_id=#{userId}")
    List<ApplicationInfo> findOne(int postId,int userId);/*避免岗位重复申请*/

    @Select("select * from applicationinfo where application_id=#{applicationId}")
    ApplicationInfo find_one(int applicationId);/*查找一个对象(information)*/

    @Select("select * from applicationinfo where post_id=#{postId} and user_id=#{userId}")
    ApplicationInfo find_id(int userId,int postId);/*查找一个对象的主键*/

    @Insert("insert into applicationinfo" +
            "(post_id,user_id,application_time," +
            "status,similarity,written_time,written_score,interview_time)" +
            " values " +
            "(#{postId},#{userId},#{applicationTime}," +
            "#{status},#{similarity},#{writtenTime},#{writtenScore},#{interviewTime})")
    int insert(ApplicationInfo applicationInfo);

    @Update("update applicationinfo set status=#{status},written_time=#{writtenTime}" +
            " where (post_id=#{postId} and user_id=#{userId}) or application_id=#{applicationId}")
    int updateWritten(ApplicationInfo applicationInfo);/*初审通过更新笔试时间*/

    @Update("update applicationinfo set status=#{status},written_score=#{writtenScore},interview_time=#{interviewTime}" +
            " where (post_id=#{postId} and user_id=#{userId}) or application_id=#{applicationId}")
    int updateInterview(ApplicationInfo applicationInfo);/*笔试通过更新面试时间*/

    @Update("update applicationinfo set status=#{status}" +
            " where (post_id=#{postId} and user_id=#{userId}) or application_id=#{applicationId}")
    int updateFinal(ApplicationInfo applicationInfo);/*录用还是淘汰*/

    @Delete("delete from applicationinfo where (post_id=#{postId} and user_id=#{userId})" +
            " or application_id=#{applicationId}")/*删除一个应聘信息*/
    int delete(ApplicationInfo applicationInfo);
    @Delete("delete from applicationinfo where  user_id=#{userId}")/*用户删除时删除某用户所有的应聘信息*/
    void deleteAllUser(int userId);
    @Delete("delete from applicationinfo where  post_id=#{postId}")/*岗位删除时删除某用户所有的应聘信息*/
    void deleteAllPost(int postId);
//-------------------------------------------统计sql---------------------------------------------------------
    @Select("SELECT COUNT(*) FROM applicationinfo,jobposition where " +
            "applicationinfo.post_id=jobposition.post_id and category like concat('%', #{category}, '%')")
    int get_resumeNum(String category);
    @Select("SELECT COUNT(*) FROM jobposition where category like concat('%', #{category}, '%')")
    int get_postNum(String category);
    @Select("SELECT COUNT(*) FROM applicationinfo,jobposition where applicationinfo.post_id=jobposition.post_id and " +
            "status like concat('%', #{status}, '%') and category like concat('%', #{category}, '%')")
    int get_statusNum(String status,String category);
    @Select("SELECT COUNT(*) FROM applicationinfo,normaluser,jobposition where " +
            "applicationinfo.user_id=normaluser.user_id and applicationinfo.post_id=jobposition.post_id and " +
            "education=#{edu} and category like concat('%', #{category}, '%')")
    int get_eduNum(String edu,String category);
    @Select("SELECT count(*) FROM applicationinfo,jobposition where " +
            "applicationinfo.post_id=jobposition.post_id " +
            "and post_name=#{name} and category like concat('%', #{category}, '%')")
    int get_postNameResumeNum(String name,String category);

    @Select("SELECT count(*) FROM applicationinfo,jobposition where " +
            "applicationinfo.post_id=jobposition.post_id and department=#{department} and category like concat('%', #{category}, '%')")
    int get_departResumeNum(String department,String category);
//    @Update("update applicationinfo set " +
//            "application_time=#{applicationTime},status=#{status}," +
//            "similarity=#{similarity},written_time=#{writtenTime},written_score=#{writtenScore}," +
//            "interview_time=#{interviewTime}" +
//            " where post_id=#{postId} and user_id=#{userId}")
//    int update(ApplicationInfo applicationInfo);
}
