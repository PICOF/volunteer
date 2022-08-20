package com.example.volunteer.Controller;

import com.example.volunteer.Exception.TeamOperateException;
import com.example.volunteer.Service.TeamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class TeamController {
    @Autowired
    private TeamService teamService;
    @GetMapping("/getInfo/getTeamInfo")
    @ResponseBody
    public String getTeamInfo() throws JsonProcessingException {
        return teamService.getTeamInfo();
    }
    @GetMapping("/getInfo/getTeamInfo/getMembers")
    @ResponseBody
    public String getMembers(@RequestParam("tid") String tid) throws JsonProcessingException {
        return teamService.getMembers(tid);
    }
    @GetMapping("/getInfo/getTeamInfo/byTid")
    @ResponseBody
    public String getTeamInfoByTid(@RequestParam("tid") String tid) throws JsonProcessingException {
        return teamService.getTeamInfoByTid(tid);
    }
    @GetMapping("/operateInfo/team/joinTeam")
    @ResponseBody
    public String joinTeam(@RequestParam("tid") String tid, HttpSession session) throws JsonProcessingException, TeamOperateException {
        return teamService.joinTeam(tid,session.getAttribute("openid").toString());
    }
    @GetMapping("/operateInfo/team/quitTeam")
    @ResponseBody
    public String quitTeam(HttpSession session) throws JsonProcessingException, TeamOperateException {
        return teamService.quitTeam(session.getAttribute("openid").toString());
    }
    @GetMapping("/operateInfo/team/getReq")
    @ResponseBody
    public String getTeamReq(HttpSession session) throws JsonProcessingException {
        return teamService.getReq(session.getAttribute("openid").toString());
    }
    @GetMapping("/operateInfo/team/approveJoinReq")
    @ResponseBody
    public String approveJoinReq(@RequestParam("memUid") String memUid,Boolean decide,HttpSession session) throws JsonProcessingException, TeamOperateException {
        return teamService.approveJoinReq(memUid,session.getAttribute("openid").toString(),decide);
    }
    @PostMapping("/operateInfo/team/createTeam")
    @ResponseBody
    public String createTeam(HttpSession session,@RequestParam("tname") String tname,@RequestParam("intro") String intro) throws TeamOperateException, JsonProcessingException {
        return teamService.createTeam(tname,intro,session.getAttribute("openid").toString());
    }
}
