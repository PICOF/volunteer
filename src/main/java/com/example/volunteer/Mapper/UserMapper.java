package com.example.volunteer.Mapper;

import com.example.volunteer.Entity.Activity;
import com.example.volunteer.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper{
    @Select("SELECT * FROM User WHERE user_id = #{userId}")
    User getAllInfo(@Param("userId") String userId);
    @Insert("INSERT IGNORE INTO User values (#{userId},concat('USER_',user_id),null,null,null,null,DEFAULT,null,DEFAULT);")
    int checkUser(@Param("userId") String openid);
    @Select("SELECT * FROM VolunteerA INNER JOIN User U on VolunteerA.publisher = U.user_id WHERE user_id = #{uid};")
    List<Activity> getPublishedActi(@Param("uid") String uid);
    @Select("SELECT * FROM Ahistory INNER JOIN VolunteerA ON aid=acti_id WHERE user_id = #{uid}")
    List<Activity> getActi1(@Param("uid") String uid);
    @Update("UPDATE User SET nick_name = #{nickName} WHERE user_id = #{uid}")
    int setNickName(@Param("nickName") String nickName,@Param("uid") String uid);
}

