package com.example.volunteer.Mapper;

import com.example.volunteer.Entity.Activity;
import com.example.volunteer.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface ActivityMapper{
    @Select("SELECT * FROM VolunteerA WHERE acti_id = #{actiId} FOR UPDATE")
    Activity getInfoByAid(@Param("actiId") String actiId);
    @Select("SELECT * FROM VolunteerA INNER JOIN Ahistory ON acti_id = aid WHERE user_id = #{uid}")
    List<Activity> getInfoByUid(@Param("uid") String uid);
    @Select("SELECT * FROM VolunteerA WHERE start_time > NOW()")
    List<Activity> getAllInfo();
    @Insert("INSERT INTO Ahistory VALUES(#{uid},#{aid})")
    int signUp(@Param("uid") String uid,@Param("aid") String aid);
    @Delete("DELETE FROM Ahistory where user_id = #{uid} AND aid = #{aid}")
    int cancel(@Param("uid") String uid,@Param("aid") String aid);
    @Delete("DELETE FROM Ahistory where aid = #{aid}")
    int cancelAll(@Param("aid") String aid);
    @Update("UPDATE VolunteerA SET joined = joined+#{num} where acti_id = #{aid}")
    int changeNum(@Param("num") int num,@Param("aid") String aid);
    @Insert("insert into VolunteerA (acti_id, acti_name, introduction, start_time, finish_time, score, place, joined, total, img, publisher)\n" +
            "select lpad(max(acti_id)+1,8,0),#{a.acti_name},#{a.introduction},#{a.start_time},#{a.finish_time},#{score},#{a.place},0,#{a.total},#{a.img},#{uid} FROM VolunteerA;")
    int publishA(@Param("uid") String uid,@Param("a") Activity a,@Param("score") long score);
    @Update("UPDATE VolunteerA SET acti_name=#{a.acti_name},introduction=#{a.introduction},start_time=#{a.start_time},finish_time=#{a.finish_time},score=#{score},place=#{a.place},img=#{a.img} WHERE acti_id=#{a.acti_id}")
    int editPublished(@Param("a") Activity a,@Param("score") long score);
    @Update("UPDATE User SET score = score+#{score} where user_id = #{uid}")
    int changeScore(@Param("uid") String uid,@Param("score") int score);
    @Select("SELECT user_id FROM Ahistory WHERE aid=#{aid};")
    List<String> getAllVolunteers(@Param("aid") String aid);
    @Select("SELECT * FROM Ahistory INNER JOIN User on Ahistory.user_id=User.user_id where aid=#{aid}")
    List<User> getParticipants(@Param("aid") String aid);
    @Select("SELECT auth FROM User where user_id = #{uid}")
    int verifyAuth(@Param("uid") String uid);
}
