package com.example.volunteer.Service;

import com.example.volunteer.Entity.Result;
import com.example.volunteer.Entity.Team;
import com.example.volunteer.Exception.TeamOperateException;
import com.example.volunteer.Mapper.TeamMapper;
import com.example.volunteer.Mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class TeamService {
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    public String getTeamInfoByTid(String tid) throws JsonProcessingException {
        Team t=teamMapper.getTeamInfoByTid(tid);
        if(t==null){
            return objectMapper.writeValueAsString(new Result(null,"不存在队伍 id 为 "+tid+" 的队伍！"));
        }
        t.setLeaderNickname(userMapper.getAllInfo(t.getLeader()).getNick_name());
        return objectMapper.writeValueAsString(new Result(t,null));
    }
    public String getTeamInfo() throws JsonProcessingException {
        List<Team> t=teamMapper.getTeamInfo();
        if(t==null){
            return objectMapper.writeValueAsString(new Result(null,"不存在任何队伍！"));
        }
        t.forEach(e->e.setLeaderNickname(userMapper.getAllInfo(e.getLeader()).getNick_name()));
        return objectMapper.writeValueAsString(new Result(t,null));
    }
    public String approveJoinReq(String uid,String leaderuid,boolean decide) throws JsonProcessingException, TeamOperateException {
        String tid=getTid(leaderuid);
        if (tid==null){
            return objectMapper.writeValueAsString(new Result(null, "用户权限不足！"));
        }
        if (decide) {
            String s = teamMapper.GetTeamByUid(uid);
            if (s != null) {
                return objectMapper.writeValueAsString(new Result(null, "用户已加入队伍！"));
            } else {
                int i = teamMapper.joinTeam(tid, uid);
                if (i == 0) {
                    return objectMapper.writeValueAsString(new Result(null, "加入队伍失败！请稍后重试"));
                } else {
                    i = teamMapper.changeMemNum(1, tid);
                    if (i == 1) {
                        redisTemplate.delete(tid+"-Req:"+uid);
                        return objectMapper.writeValueAsString(new Result(i, null));
                    } else {
                        throw new TeamOperateException();
                    }
                }
            }
        }else {
            redisTemplate.delete(tid+"-Req:"+uid);
            return objectMapper.writeValueAsString(new Result(null, "已拒绝请求"));
        }
    }
    public String joinTeam(String tid, String uid) throws JsonProcessingException {
        redisTemplate.opsForValue().set(tid+"-Req:"+uid,uid,30,TimeUnit.DAYS);
        return objectMapper.writeValueAsString(new Result(1, null));
    }
    public String getReq(String uid) throws JsonProcessingException {
        String tid=getTid(uid);
        if (tid==null){
            return objectMapper.writeValueAsString(new Result(null, "用户权限不足！"));
        }
        List<String> list=redisTemplate.opsForValue().multiGet(redisTemplate.keys(tid + "-Req:*"));
        return objectMapper.writeValueAsString(new Result(list, null));
    }
    public String quitTeam(String uid) throws JsonProcessingException, TeamOperateException {
        String s = teamMapper.GetTeamByUid(uid);
        if (s == null) {
            return objectMapper.writeValueAsString(new Result(null, "用户未加入队伍！"));
        } else {
            int i = teamMapper.quitTeam(uid);
            if (i == 0) {
                return objectMapper.writeValueAsString(new Result(null, "退出队伍失败！请稍后重试"));
            } else {
                i = teamMapper.changeMemNum(-1, s);
                if (i == 1) {
                    return objectMapper.writeValueAsString(new Result(i, null));
                } else {
                    throw new TeamOperateException();
                }
            }
        }
    }
    public String createTeam(String tname,String intro,String uid) throws JsonProcessingException, TeamOperateException {
        int i=teamMapper.createTeam(tname,intro,uid);
        if (i==1){
            if(teamMapper.verifyAuth(uid)==1){
                i=teamMapper.setTeamLeader(uid);
            }
            if(i==1){
                teamMapper.joinTeam(teamMapper.getTid(uid),uid);
                return objectMapper.writeValueAsString(new Result(i, null));
            }else {
                throw new TeamOperateException();
            }
        }else {
            return objectMapper.writeValueAsString(new Result(null, "队伍创建失败！"));
        }
    }
    public String getTid(String uid){
        return teamMapper.getTid(uid);
    }

    public String getMembers(String tid) throws JsonProcessingException {
        return objectMapper.writeValueAsString(new Result(teamMapper.getMembers(tid), null));
    }
}
