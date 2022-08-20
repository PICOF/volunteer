package com.example.volunteer.Service;

import com.example.volunteer.Entity.Activity;
import com.example.volunteer.Entity.Result;
import com.example.volunteer.Entity.User;
import com.example.volunteer.Mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    public String getUserInfo(String uid) throws JsonProcessingException {
        User u=userMapper.getAllInfo(uid);
        if(u==null){
            return objectMapper.writeValueAsString(new Result(null,"不存在用户 id 为 "+uid+" 的用户！"));
        }
        return objectMapper.writeValueAsString(new Result(u,null));
    }
    public String getPublishedActi(String uid) throws JsonProcessingException {
        List<Activity> a=userMapper.getPublishedActi(uid);
        if (a.size()!=0){
            return objectMapper.writeValueAsString(new Result(a,null));
        }else {
            return objectMapper.writeValueAsString(new Result(null,"用户并未发布任何活动！"));
        }
    }
    public String getUsersActi1(String uid) throws JsonProcessingException {
        List<Activity> a=userMapper.getActi1(uid);
        if (a.size()>0){
            return objectMapper.writeValueAsString(new Result(a,null));
        }else {
            return objectMapper.writeValueAsString(new Result(null,"用户没有任何未完成活动！"));
        }
    }
    public String getUsersActi2(String uid) throws JsonProcessingException {
        List<String> aList=redisTemplate.opsForValue().multiGet(redisTemplate.keys(uid+"-Accomplished-*"));
        if (aList.size()>0){
            return objectMapper.writeValueAsString(new Result(aList,null));
        }else {
            return objectMapper.writeValueAsString(new Result(null,"用户没有任何未完成活动！"));
        }
    }
    public String setNickName(String Nname,String uid) throws JsonProcessingException {
        int i=userMapper.setNickName(Nname,uid);
        if (i==1){
            return objectMapper.writeValueAsString(new Result(i,null));
        }else {
            return objectMapper.writeValueAsString(new Result(null,"未找到对应用户！"));
        }
    }
}
