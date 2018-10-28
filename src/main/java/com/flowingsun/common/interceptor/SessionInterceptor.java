package com.flowingsun.common.interceptor;

import com.flowingsun.behavior.service.BehaviorServiceImpl;
import com.flowingsun.common.dao.BlogVisitorMapper;
import com.flowingsun.common.dao.RedisDAO;
import com.flowingsun.common.entity.BlogVisitor;
import com.flowingsun.common.utils.InfoCountUtils;
//import org.apache.shiro.SecurityUtils;

import com.flowingsun.user.dao.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

import static org.apache.log4j.Level.INFO;

public class SessionInterceptor implements HandlerInterceptor {

    private static Logger logger = Logger.getLogger(BehaviorServiceImpl.class);

    @Autowired
    private BlogVisitorMapper blogVisitorMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisDAO redisDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //访问目的url
        String uri = request.getRequestURI();
        if(uri.indexOf("user/userInfo")>=0){
            return true;
        }
        BlogVisitor blogVisitor = new BlogVisitor();
        blogVisitor.setTargeturl(uri);
        //访客操作系统
        blogVisitor.setOs(InfoCountUtils.getSysInfo(request));
        //访客浏览器
        blogVisitor.setBrowser(InfoCountUtils.getBrowser(request));
        //访客ip
        blogVisitor.setIp(InfoCountUtils.getIp(request));
        //访问来源url
        blogVisitor.setSourceurl(request.getHeader("Referer"));
        //request.getHeaderNames()请求Header信息
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName+"::"+headerValue);
        }
        //用户id信息
        Long userId = (Long)request.getSession().getAttribute("userId");
        if(userId!=null&&userId!=0){
            blogVisitor.setUserid(userId);
        }
        //访问文章信息
        if((uri.indexOf("article")>=0) || (uri.indexOf("user")>=0) || (uri.indexOf("behavior")>=0)){
            String articleId = request.getParameter("articleId");
            if(articleId!=null&&articleId!=""){
                blogVisitor.setArticleid(Integer.parseInt(articleId));
            }
            blogVisitorMapper.insertSelective(blogVisitor);
            logger.log(INFO,"---------------------------访客信息统计---------------------------"+blogVisitor.toString());
            String s1 = String.valueOf(userMapper.selectUserCount());
            String s2 = String.valueOf(blogVisitorMapper.selectVisitorCount());
            String s3 = String.valueOf(blogVisitorMapper.selectViewCount());
            redisDAO.setString("userCount",s1);
            redisDAO.setString("visitorCount",s2);
            redisDAO.setString("viewCount",s3);
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
