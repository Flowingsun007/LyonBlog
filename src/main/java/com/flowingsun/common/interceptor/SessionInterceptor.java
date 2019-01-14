package com.flowingsun.common.interceptor;

import com.flowingsun.common.dao.BlogVisitorMapper;
import com.flowingsun.common.dao.RedisDAO;
import com.flowingsun.common.entity.BlogVisitor;
import com.flowingsun.common.utils.InfoCountUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.log4j.Level.INFO;

public class SessionInterceptor implements HandlerInterceptor {

    private static Logger logger = Logger.getLogger(SessionInterceptor.class);

    @Autowired
    private BlogVisitorMapper blogVisitorMapper;

    @Autowired
    private RedisDAO redisDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //访问目的url
        String uri = request.getRequestURI();
        //用户id信息
        Long userId = (Long)request.getSession().getAttribute("userId");
        if((uri.indexOf("admin")>=0)||uri.indexOf("user/userInfo")>=0||uri.indexOf("spider")>=0){
            if(userId!=null){ return true; }
            response.sendRedirect("/user/login");
            return false;
        }
        //访问文章信息
        if((uri.indexOf("article")>=0) || (uri.indexOf("user")>=0) || (uri.indexOf("behavior")>=0)){
            //获取请求详细信息
            BlogVisitor blogVisitor = InfoCountUtils.getVisitorInfo(request);
            if(userId!=null&&userId!=0){
                blogVisitor.setUserid(userId);
            }
            String articleId = request.getParameter("articleId");
            if(articleId!=null&&articleId!=""){
                blogVisitor.setArticleid(Integer.parseInt(articleId));
            }
            blogVisitorMapper.insertSelective(blogVisitor);
            this.updateBlogViewCount();
            this.updateBlogVisitorCount();
            logger.log(INFO,"\n---------------------------访客信息统计---------------------------\n"+blogVisitor.toString());
            return true;
        }
        if((uri.startsWith("login"))||(uri.startsWith("register"))||(uri.startsWith("logout"))){
            request.getRequestDispatcher("user/login").forward(request,response);
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

    @Async
    public void updateBlogVisitorCount(){
        String s = String.valueOf(blogVisitorMapper.selectVisitorCount());
        redisDAO.setString("visitorCount",s);
    }

    @Async
    public void updateBlogViewCount(){
        String s = String.valueOf(blogVisitorMapper.selectViewCount());
        redisDAO.setString("viewCount",s);
    }
}
