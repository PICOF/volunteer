package com.example.volunteer.Controller;

import com.example.volunteer.Entity.Result;
import com.example.volunteer.Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @GetMapping("/Msg/getUnreadMessage")
    public String getUnreadMessage(HttpSession session) throws JsonProcessingException {
        return messageService.getUnreadMessage((String) session.getAttribute("openid"));
    }
    @GetMapping("/Msg/getReadMessage")
    public String getReadMessage(HttpSession session) throws JsonProcessingException {
        return messageService.getReadMessage((String) session.getAttribute("openid"));
    }
}
