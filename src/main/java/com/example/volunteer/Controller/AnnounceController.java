package com.example.volunteer.Controller;

import com.example.volunteer.Service.AnnounceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class AnnounceController {
    @Autowired
    private AnnounceService announceService;
    @GetMapping("/getInfo/getAnnouncement")
    @ResponseBody
    public String getAnn() throws JsonProcessingException {
        return announceService.getAnn();
    }
    @PostMapping("/getInfo/delAnnouncement")
    @ResponseBody
    public String delAnn(@RequestParam("annid") String annid, HttpSession session) throws JsonProcessingException {
        return announceService.delAnn(annid,session.getAttribute("openid").toString());
    }
    @PostMapping("/getInfo/updateAnnouncement")
    @ResponseBody
    public String updateAnn(@RequestParam("annid") String annid,@RequestParam("announce") String announce,HttpSession session) throws JsonProcessingException {
        return announceService.updateAnn(annid,announce,session.getAttribute("openid").toString());
    }
    @PostMapping("/getInfo/addAnnouncement")
    @ResponseBody
    public String updateAnn(@RequestParam("announce") String announce,HttpSession session) throws JsonProcessingException {
        return announceService.addAnn(announce,session.getAttribute("openid").toString());
    }
}
