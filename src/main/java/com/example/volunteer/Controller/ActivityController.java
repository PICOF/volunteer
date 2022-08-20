package com.example.volunteer.Controller;

import com.example.volunteer.Entity.Activity;
import com.example.volunteer.Mapper.ActivityMapper;
import com.example.volunteer.Service.ActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @GetMapping("/getInfo/getActivityInfo")
    @ResponseBody
    public String getActivityInfo() throws JsonProcessingException {
        return activityService.getActivityInfo();
    }
    @GetMapping("/getInfo/getActivityInfo/byAid")
    @ResponseBody
    public String getActivityInfoByAid(@RequestParam("aid") String aid) throws JsonProcessingException {
        return activityService.getActivityInfoByAid(aid);
    }
    @GetMapping("/getInfo/getActivityInfo/byUser")
    @ResponseBody
    public String getActivityInfoByUid(HttpSession session) throws JsonProcessingException {
        return activityService.getActivityInfoByUid(session.getAttribute("openid").toString());
    }
    @GetMapping("/getInfo/getActivityInfo/fromOtherSite")
    @ResponseBody
    public String getActivityInfoFromOtherSite(int size) throws JsonProcessingException {
        return activityService.getActivityInfoFromOtherSite(size);
    }
    @GetMapping("/getInfo/getParticipants")
    @ResponseBody
    public String getParticipants(@RequestParam("aid") String aid) throws JsonProcessingException {
        return activityService.getParticipants(aid);
    }
    @PostMapping("/operateInfo/activity/signUp")
    @ResponseBody
    public String signUp(HttpSession session,@RequestParam("aid") String aid) throws JsonProcessingException {
        return activityService.signUp(session.getAttribute("openid").toString(),aid);
    }
    @PostMapping("/operateInfo/activity/accomplish")
    @ResponseBody
    public String accomplish(HttpSession session,@RequestParam("aid") String aid,@RequestParam("uid") String uid,@RequestParam("status") Boolean status) throws JsonProcessingException, SQLException {
        return activityService.accomplish(aid,session.getAttribute("openid").toString(),uid,status);
    }
    @PostMapping("/operateInfo/activity/accomplishAll")
    @ResponseBody
    public String accomplishAll(HttpSession session,@RequestParam("aid") String aid,@RequestParam("status") Boolean status) throws JsonProcessingException {
        return activityService.accomplishAll(aid,session.getAttribute("openid").toString(),status);
    }
    @PostMapping("/operateInfo/activity/cancel")
    @ResponseBody
    public String cancel(HttpSession session,@RequestParam("aid") String aid) throws JsonProcessingException {
        return activityService.cancel(session.getAttribute("openid").toString(),aid);
    }
    @PostMapping("/operateInfo/activity/publish")
    @ResponseBody
    public String publish(HttpSession session,@RequestParam("actJson") String actJson) throws JsonProcessingException {
        return activityService.publishActivity(session.getAttribute("openid").toString(),actJson);
    }
    @PostMapping("/operateInfo/activity/editPublished")
    @ResponseBody
    public String editPublished(HttpSession session,@RequestParam("actJson") String actJson) throws JsonProcessingException {
        return activityService.editPublished(session.getAttribute("openid").toString(),actJson);
    }
}
