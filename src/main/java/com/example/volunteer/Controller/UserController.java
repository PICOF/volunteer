package com.example.volunteer.Controller;

import com.example.volunteer.Entity.User;
import com.example.volunteer.Mapper.UserMapper;
import com.example.volunteer.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userservice;
    @PostMapping("/getInfo/getUserInfo")
    @ResponseBody
    public String getUserInfo(HttpSession session) throws JsonProcessingException {
        return userservice.getUserInfo((String)session.getAttribute("openid"));
    }
    @PostMapping("/operateInfo/setNickName")
    @ResponseBody
    public String setNickname(@RequestParam("nick_name") String nickName, HttpSession session) throws JsonProcessingException {
        return userservice.setNickName(nickName,(String)session.getAttribute("openid"));
    }
    @GetMapping("/getInfo/getPublishedActi")
    @ResponseBody
    public String getPublishedActi(HttpSession session) throws JsonProcessingException {
        return userservice.getPublishedActi((String) session.getAttribute("openid"));
    }
    @GetMapping("/getInfo/getUsersActi1")
    @ResponseBody
    public String getAct1(HttpSession session) throws JsonProcessingException {
        return userservice.getUsersActi1((String)session.getAttribute("openid"));
    }
    @GetMapping("/getInfo/getUsersActi2")
    @ResponseBody
    public String getAct2(HttpSession session) throws JsonProcessingException {
        return userservice.getUsersActi2((String)session.getAttribute("openid"));
    }
}
