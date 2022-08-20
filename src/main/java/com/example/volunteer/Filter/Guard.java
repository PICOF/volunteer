package com.example.volunteer.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class Guard implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) request;
        String[] okurl={"/verify","/verifytest","/messages"};
        String URL= req.getRequestURI();
        boolean flag=true;
        for(String tar:okurl) {
            if (URL.contains(tar)) {
                flag = false;
                break;
            }
        }
        if (URL.length()<=1){
            flag=false;
        }
        if(flag) {
            HttpSession session = req.getSession();
            String user=(String)session.getAttribute("openid");
            if(user==null) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("{\"data\": null,\"err\": \"错误！请先进行身份验证！\"}");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
