package com.example.volunteer.Controller;

import com.example.volunteer.Service.VerifyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class VerifyController {
    @Autowired
    private VerifyService verifyService;
    @GetMapping("/verify")
    @ResponseBody
    public String verify(@RequestParam("temp_code") String tempCode, HttpSession session) throws JsonProcessingException {
        return verifyService.getOpenid(tempCode,session);
    }
    @GetMapping("/verifyESP")
    @ResponseBody
    public String verifyESP(@RequestParam("temp_code") String tempCode) throws JsonProcessingException {
        return verifyService.getOpenidESP(tempCode);
    }
    @GetMapping("/verifytest")
    @ResponseBody
    public String verifytest(@RequestParam("temp_code") String tempCode, HttpSession session) throws JsonProcessingException {
        return verifyService.getOpenidtest(tempCode,session);
    }
    @GetMapping("/verifytest2")
    @ResponseBody
    public String verifytest2(@RequestParam("openid") String openid) throws JsonProcessingException {
        return verifyService.getOpenidtest2(openid);
    }
}
