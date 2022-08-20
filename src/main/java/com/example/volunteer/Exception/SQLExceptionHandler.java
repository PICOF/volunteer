package com.example.volunteer.Exception;

import com.example.volunteer.Entity.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@Component
@ControllerAdvice//全局异常处理
public class SQLExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @ExceptionHandler(SQLException.class)//异常处理器，参数为异常类型
    @ResponseBody
    public String hanleException(SQLException ex) throws JsonProcessingException {
        return objectMapper.writeValueAsString(new Result(null,new String(ex.getMessage().getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8)));
    }
}

