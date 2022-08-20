package com.example.volunteer.Service;

import com.example.volunteer.Entity.Announce;
import com.example.volunteer.Entity.Result;
import com.example.volunteer.Mapper.AnnounceMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnounceService {
    @Autowired
    private AnnounceMapper announceMapper;
    @Autowired
    private ObjectMapper objectMapper;
    public String getAnn() throws JsonProcessingException {
        List<Announce> a=announceMapper.getAllAnn();
        if(a==null){
            return objectMapper.writeValueAsString(new Result(null,"暂无任何公告！"));
        }else {
            return objectMapper.writeValueAsString(new Result(a,null));
        }
    }
    public String delAnn(String annid,String uid) throws JsonProcessingException {
        if (!verifyAuth(uid)){
            return objectMapper.writeValueAsString(new Result(null,"用户权限过低！"));
        }
        int i=announceMapper.delAnn(annid);
        if (i<=0){
            return objectMapper.writeValueAsString(new Result(null,"删除失败！"));
        }else{
            return objectMapper.writeValueAsString(new Result(i,null));
        }
    }
    public String updateAnn(String annid,String announce,String uid) throws JsonProcessingException {
        if (!verifyAuth(uid)){
            return objectMapper.writeValueAsString(new Result(null,"用户权限过低！"));
        }
        int i=announceMapper.updateAnn(annid,objectMapper.readValue(announce,Announce.class));
        if (i<=0){
            return objectMapper.writeValueAsString(new Result(null,"更新失败！"));
        }else{
            return objectMapper.writeValueAsString(new Result(i,null));
        }
    }
    public String addAnn(String announce,String uid) throws JsonProcessingException {
        if (!verifyAuth(uid)){
            return objectMapper.writeValueAsString(new Result(null,"用户权限过低！"));
        }
        int i=announceMapper.addAnn(objectMapper.readValue(announce,Announce.class));
        if (i<=0){
            return objectMapper.writeValueAsString(new Result(null,"添加失败！"));
        }else{
            return objectMapper.writeValueAsString(new Result(i,null));
        }
    }
    private boolean verifyAuth(String uid){
        int i=announceMapper.verifyAuth(uid);
        if (i==0)
            return true;
        else
            return false;
    }
}
