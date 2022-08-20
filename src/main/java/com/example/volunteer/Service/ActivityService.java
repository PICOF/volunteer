package com.example.volunteer.Service;

import com.example.volunteer.Entity.Activity;
import com.example.volunteer.Entity.Result;
import com.example.volunteer.Entity.ResultFromWeb;
import com.example.volunteer.Entity.User;
import com.example.volunteer.Mapper.ActivityMapper;
import com.example.volunteer.Mapper.TeamMapper;
import com.example.volunteer.Mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    public String getActivityInfoByAid(String aid) throws JsonProcessingException {
        Activity a=activityMapper.getInfoByAid(aid);
        if(a==null){
            return  objectMapper.writeValueAsString(new Result(null,"不存在活动 id 为 "+aid+" 的活动！"));
        }
        return objectMapper.writeValueAsString(new Result(a,null));
    }
    public String getActivityInfoByUid(String uid) throws JsonProcessingException {
        List<Activity> a=activityMapper.getInfoByUid(uid);
        if(a.size()==0){
            return  objectMapper.writeValueAsString(new Result(null,"用户 "+uid+" 未参加任何活动！"));
        }
        return objectMapper.writeValueAsString(new Result(a,null));
    }
    public String getActivityInfo() throws JsonProcessingException {
        List<Activity> a=activityMapper.getAllInfo();
        if(a==null){
            return objectMapper.writeValueAsString(new Result(null,"不存在活动！"));
        }
        return objectMapper.writeValueAsString(new Result(a,null));
    }
    public String signUp(String uid,String aid) throws JsonProcessingException {
        if(redisTemplate.opsForValue().get(uid+"-Accomplished-"+aid)!=null){
            return objectMapper.writeValueAsString(new Result(null,"用户已参与过活动！"));
        }
        Activity a=activityMapper.getInfoByAid(aid);
        if (a.getJoined()<a.getTotal()){
            int n=activityMapper.changeNum(1,aid);
            if (n==0){
                return objectMapper.writeValueAsString(new Result(null,"报名失败！"));
            }
            int i=activityMapper.signUp(uid,aid);
            if(i==0){
                return objectMapper.writeValueAsString(new Result(null,"报名失败！"));
            }
            return objectMapper.writeValueAsString(new Result(i,null));
        }else {
            return objectMapper.writeValueAsString(new Result(null,"人数已达上限！"));
        }
    }
    public String accomplish(String aid,String publisherId,String uid,Boolean status) throws JsonProcessingException, SQLException {
        if(!verifyAuth(publisherId)){
            return objectMapper.writeValueAsString(new Result(null,"用户权限不足！"));
        }
        Activity a=activityMapper.getInfoByAid(aid);
        if (publisherId.equals(a.getPublisher())){
            String aJson="{\"acti_id\":\""+a.getActi_id()+"\",\"acti_name\":\""+a.getActi_name()+"\",\"accomplish_time\":"+System.currentTimeMillis()+",\"score\":"+a.getScore()+",";
            activityMapper.changeNum(-1,aid);
            int i=activityMapper.cancel(uid,aid);
            if (i!=1){
                throw new SQLException("未找到相关活动参与记录！");
            }
            if (status){
                activityMapper.changeScore(uid,a.getScore());
                teamMapper.changeScore(a.getScore(),userMapper.getAllInfo(uid).getTeamid());
                redisTemplate.opsForValue().set(uid+"-Accomplished-"+aid,aJson+"\"status\":\"succeed\"}",30, TimeUnit.DAYS);
            }else {
                redisTemplate.opsForValue().set(uid+"-Accomplished-"+aid,aJson+"\"status\":\"fail\"}",30, TimeUnit.DAYS);
            }
            return objectMapper.writeValueAsString(new Result(1,null));
        }else {
            return objectMapper.writeValueAsString(new Result(null,"未找到相关活动！"));
        }
    }
    public String accomplishAll(String aid,String publisherId,Boolean status) throws JsonProcessingException {
        if(!verifyAuth(publisherId)){
            return objectMapper.writeValueAsString(new Result(null,"用户权限不足！"));
        }
        Activity a=activityMapper.getInfoByAid(aid);
        if (publisherId.equals(a.getPublisher())){
            String aJson="{\"acti_id\":\""+a.getActi_id()+"\",\"acti_name\":\""+a.getActi_name()+"\",\"accomplish_time\":"+System.currentTimeMillis()+",\"score\":"+a.getScore();
            List<String> uList=activityMapper.getAllVolunteers(aid);
            activityMapper.cancelAll(aid);
            if (status) {
                for (String uid : uList) {
                    activityMapper.changeNum(-1, aid);
                    activityMapper.changeScore(uid, a.getScore());
                    teamMapper.changeScore(a.getScore(),userMapper.getAllInfo(uid).getTeamid());
                    redisTemplate.opsForValue().set(uid + "-Accomplished-" + aid, aJson+"\"status\":\"succeed\"}", 30, TimeUnit.DAYS);
                }
            }else {
                for (String uid : uList) {
                        activityMapper.changeNum(-1, aid);
                        redisTemplate.opsForValue().set(uid + "-Accomplished-" + aid, aJson +"\"status\":\"fail\"}", 30, TimeUnit.DAYS);
                    }
                }
            return objectMapper.writeValueAsString(new Result(uList.size(),null));
        }else {
            return objectMapper.writeValueAsString(new Result(null,"未找到相关活动！"));
        }
    }
    public String cancel(String uid,String aid) throws JsonProcessingException {
        int i=activityMapper.cancel(uid,aid);
        if(i==0){
            return objectMapper.writeValueAsString(new Result(null,"没有报名相关活动！"));
        }
        int n=activityMapper.changeNum(-1,aid);
        if (n==0){
            return objectMapper.writeValueAsString(new Result(null,"取消活动失败！"));
        }
        return objectMapper.writeValueAsString(new Result(i,null));
    }
    public String publishActivity(String uid,String actJson) throws JsonProcessingException {
        String n=(String) redisTemplate.opsForValue().get(uid+"-PKey");
        if (n==null){
            redisTemplate.opsForValue().set(uid+"-PKey",String.valueOf(1),1,TimeUnit.DAYS);
        }else if (Integer.parseInt(n)<3){
            redisTemplate.opsForValue().set(uid+"-PKey",String.valueOf(Integer.parseInt(n)+1),1,TimeUnit.DAYS);
        }else {
            return objectMapper.writeValueAsString(new Result(null,"24小时内发布操作不得超过三次！"));
        }
        Activity a=objectMapper.readValue(actJson,Activity.class);
        if (a.getFinish_time().getTime()<a.getStart_time().getTime()){
            return objectMapper.writeValueAsString(new Result(null,"活动开始时间不得晚于结束时间！"));
        }
        long score=(a.getFinish_time().getTime()-a.getStart_time().getTime())/(1000*60*60);
        score=score==0?1:score;
        score=score>8?8:score;
        int i=activityMapper.publishA(uid,a,score);
        if (i==1){
            return objectMapper.writeValueAsString(new Result(i,null));
        }else {
            return objectMapper.writeValueAsString(new Result(null,"活动发布失败！"));
        }
    }
    public String editPublished(String uid,String actJson) throws JsonProcessingException {
        Activity a=objectMapper.readValue(actJson,Activity.class);
        if (uid.equals(activityMapper.getInfoByAid(a.getActi_id()).getPublisher())){
            if (a.getFinish_time().getTime()<a.getStart_time().getTime()){
                return objectMapper.writeValueAsString(new Result(null,"活动开始时间不得晚于结束时间！"));
            }
            long score=(a.getFinish_time().getTime()-a.getStart_time().getTime())/(1000*60*60);
            score=score==0?1:score;
            score=score>8?8:score;
            int i=activityMapper.editPublished(a,score);
            if (i==1){
                return objectMapper.writeValueAsString(new Result(i,null));
            }else {
                return objectMapper.writeValueAsString(new Result(null, "活动修改失败！"));
            }
        }else {
            return objectMapper.writeValueAsString(new Result(null,"发布者身份不正确！"));
        }
    }
    public String getParticipants(String aid) throws JsonProcessingException {
        List<User> userList=activityMapper.getParticipants(aid);
        return objectMapper.writeValueAsString(new Result(userList,null));
    }
    private boolean verifyAuth(String uid){
        int i=activityMapper.verifyAuth(uid);
        return i != 1;
    }

    public String getActivityInfoFromOtherSite(int size) throws JsonProcessingException {
        String url="https://sczyz.org.cn/apiv2/zysc/index/work?workStatus=signing&page=1&size="+size;
        ResultFromWeb result=restTemplate.getForObject(url,ResultFromWeb.class);
        return objectMapper.writeValueAsString(new Result(result.getData(),null));
    }
}
