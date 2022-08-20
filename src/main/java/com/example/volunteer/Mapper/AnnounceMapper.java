package com.example.volunteer.Mapper;

import com.example.volunteer.Entity.Announce;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AnnounceMapper {
    @Select("SELECT auth FROM User where user_id = #{uid}")
    int verifyAuth(@Param("uid") String uid);
    @Select("SELECT * FROM Announcement")
    List<Announce> getAllAnn();
    @Delete("DELETE FROM Announcement where annid = #{annid}")
    int delAnn(@Param("annid") String annid);
    @Update("UPDATE Announcement SET img = #{announce.img},Ptime = NOW(),content = #{announce.content} where annid = #{annid}")
    int updateAnn(@Param("annid") String annid,@Param("announce") Announce announce);
    @Insert("INSERT INTO Announcement values (DEFAULT,#{announce.img},NOW(),#{announce.content});")
    int addAnn(@Param("announce") Announce announce);
}
