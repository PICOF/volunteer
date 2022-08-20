package com.example.volunteer.Service;

import com.example.volunteer.Entity.OpenidMsg;
import com.example.volunteer.Entity.VerifyRes;
import com.example.volunteer.Mapper.UserMapper;
import com.example.volunteer.Utils.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Service
public class VerifyService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JWTUtil jwtUtil;
    public String getOpenid(String tempCode, HttpSession session) throws JsonProcessingException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxcb5d34eef7cfdd4d&secret=b85c2f53531d9a7778bd7f015167598d&js_code="+tempCode+"&grant_type=authorization_code";
        String s = restTemplate.getForObject(url, String.class);
        OpenidMsg o=objectMapper.readValue(s, OpenidMsg.class);
        if (o.getErrcode()==0){
            session.setAttribute("openid",o.getOpenid());
            userMapper.checkUser(o.getOpenid());
            return objectMapper.writeValueAsString(new VerifyRes(true,new VerifyRes.Data(o.getOpenid(),o.getSession_key()),jwtUtil.getJWT(o.getOpenid(),userMapper.getAllInfo(o.getOpenid()).getNick_name())));
        }else {
            return objectMapper.writeValueAsString(new VerifyRes(false,null,"错误："+o.getErrmsg()+"  错误码："+o.getErrcode()));
        }
    }
    public String getOpenidESP(String tempCode) throws JsonProcessingException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx645564572654f5fd&secret=54e6f7cf0942208efd622a5300e8daf6&js_code="+tempCode+"&grant_type=authorization_code";
        String s = restTemplate.getForObject(url, String.class);
        OpenidMsg o=objectMapper.readValue(s, OpenidMsg.class);
        if (o.getErrcode()==0){
            return objectMapper.writeValueAsString(new VerifyRes(true,new VerifyRes.Data(o.getOpenid(),o.getSession_key()),null));
        }else {
            return objectMapper.writeValueAsString(new VerifyRes(false,null,"错误："+o.getErrmsg()+"  错误码："+o.getErrcode()));
        }
    }
    public String getOpenidtest(String tempCode, HttpSession session) throws JsonProcessingException {
        session.setAttribute("openid","jsikahyjnksjiayushiydjnfhtgs");
        userMapper.checkUser("jsikahyjnksjiayushiydjnfhtgs");
        return objectMapper.writeValueAsString(new VerifyRes(true,new VerifyRes.Data("jsikahyjnksjiayushiydjnfhtgs","fHy8XF1I43wGYHKeUiAqmg=="),jwtUtil.getJWT("jsikahyjnksjiayushiydjnfhtgs",userMapper.getAllInfo("jsikahyjnksjiayushiydjnfhtgs").getNick_name())));
    }
    public String getOpenidtest2(String openid) throws JsonProcessingException {
        return objectMapper.writeValueAsString(new VerifyRes(true,null,jwtUtil.getJWT(openid,"USER_"+openid)));
    }
}
