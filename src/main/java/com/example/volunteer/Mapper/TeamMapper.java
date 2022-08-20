package com.example.volunteer.Mapper;

import com.example.volunteer.Entity.Team;
import com.example.volunteer.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TeamMapper {
    @Select("SELECT * FROM Team WHERE teamid = #{teamId} FOR UPDATE")
    Team getTeamInfoByTid(@Param("teamId") String teamId);
    @Select("SELECT user_id,nick_name,score,auth FROM User WHERE teamid = #{teamId} ORDER BY score DESC")
    List<User> getMembers(@Param("teamId") String teamId);
    @Select("SELECT * FROM Team")
    List<Team> getTeamInfo();
    @Select("SELECT teamid FROM User where user_id = #{uid}")
    String GetTeamByUid(@Param("uid") String uid);
    @Update("UPDATE User SET teamid= #{teamId} where user_id = #{uid}")
    int joinTeam(@Param("teamId") String teamId,@Param("uid") String uid);
    @Update("UPDATE User SET teamid= null where user_id = #{uid}")
    int quitTeam(@Param("uid") String uid);
    @Update("UPDATE Team SET member = member+#{num} where teamid = #{tid}")
    int changeMemNum(@Param("num") int num,@Param("tid") String tid);
    @Update("UPDATE Team SET teamscore = member+#{score} where teamid = #{tid}")
    int changeScore(@Param("score") int score,@Param("tid") String tid);
    @Insert("insert into Team (teamid, teamname, introduction, member, teamscore, leader)\n" +
            "select CONCAT('T',lpad(SUBSTRING(max(teamid),2)+1,7,0)),#{tname},#{intro},1,0,#{uid} from Team;")
    int createTeam(@Param("tname") String tname,@Param("intro") String intro,@Param("uid") String uid);
    @Update("UPDATE User SET auth = 2 where user_id = #{uid}")
    int setTeamLeader(@Param("uid") String uid);
    @Select("select teamid from Team where leader=#{uid}")
    String getTid(@Param("uid") String uid);
    @Select("SELECT auth FROM User where user_id = #{uid}")
    int verifyAuth(@Param("uid") String uid);
}
