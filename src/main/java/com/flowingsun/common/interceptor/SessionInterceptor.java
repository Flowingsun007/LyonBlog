package com.flowingsun.common.interceptor;

import com.flowingsun.common.utils.InfoCountUtils;
import com.flowingsun.user.entity.User;
//import org.apache.shiro.SecurityUtils;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        //User user = (User) SecurityUtils.getSubject().getSession().getAttribute("userInfo");
        Long userId = (Long)request.getSession().getAttribute("userId");
        if((uri.indexOf("article")>=0) || (uri.indexOf("user")>=0) || (uri.indexOf("behavior")>=0)){
            if(userId!=null){
                System.out.println("\n--------------------------------------------访问信息统计------------------------------------------\n");
                String os = InfoCountUtils.getSysInfo(request);//用户使用系统
                String browser = InfoCountUtils.getBrowser(request);//用户使用的浏览器
                String ip = InfoCountUtils.getIp(request);//用户ip
                String source = request.getHeader("Referer");//访问来源
                String url = request.getRequestURI();//用户当前访问路径
                System.out.println("system:"+os+"\nbrowser:"+browser+"\naddress:"+ip+"\nurl:"+url+"\nsource:"+source);
                Enumeration<String> headerNames = request.getHeaderNames();
                System.out.println("\n----------------------------------------request.getHeaderNames()----------------------------------------------");
                while(headerNames.hasMoreElements()){
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);
                    System.out.println(headerName+"::"+headerValue);
                }
            }
            return true;
        }
        if((uri.startsWith("login"))||(uri.startsWith("register"))||(uri.startsWith("logout"))){
            request.getRequestDispatcher("user/login").forward(request,response);
            return false;
        }
        if((uri.indexOf("admin")>=0)){
            if(userId!=null){ return true; }
            response.sendRedirect("/user/login");
            return false;
        }

        request.getRequestDispatcher("/user/login").forward(request,response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
