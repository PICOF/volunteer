package com.example.volunteer.Service;

import com.example.volunteer.Entity.Msg;
import com.example.volunteer.Entity.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    public String getUnreadMessage(String openid) throws JsonProcessingException {
        List<String> Mlist=redisTemplate.opsForList().range("Unread-"+openid,0,-1);
        if(Mlist.size()>0) {
            redisTemplate.opsForList().rightPushAll("Read-"+openid,Mlist);
        }
        return objectMapper.writeValueAsString(new Result(Mlist,null));
    }
    public String getReadMessage(String openid) throws JsonProcessingException {
        List<String> Mlist=redisTemplate.opsForList().range("Read-"+openid,0,-1);
        return objectMapper.writeValueAsString(new Result(Mlist,null));
    }
}
