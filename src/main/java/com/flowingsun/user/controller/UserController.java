package com.flowingsun.user.controller;

import com.flowingsun.article.entity.Category;
import com.flowingsun.article.service.ArticleService;
import com.flowingsun.article.vo.PageNotice;
import com.flowingsun.behavior.entity.BehaviorStatus;
import com.flowingsun.behavior.service.BehaviorService;
import com.flowingsun.behavior.vo.PictureQuery;
import com.flowingsun.user.entity.User;
import com.flowingsun.user.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;


@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BehaviorService behaviorService;


    @RequestMapping("/userInfo")
    @ResponseBody
    public User getUserInfo(HttpServletRequest request){
        return userService.getUserInfo(request);
    }

    @RequestMapping(value={"/login","/logout","/register"})
    public String toHome(Model model){
        List<Category> categorys = articleService.getCategory();
        model.addAttribute("categorys",categorys);
        return "index";
    }


    @RequestMapping("/login/check")
    @ResponseBody
    public String checkLogin(@RequestBody User user, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.UserLogin(user, request);
    }

    @RequestMapping("/logout/check")
    @ResponseBody
    public String checkLogin(HttpServletRequest request){
        return userService.UserLogout(request);
    }

    @RequestMapping("/register/check")
    @ResponseBody
    public String checkRegister(@RequestBody User user,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.UserRegister(user,request);
    }

    @RequestMapping("/register/activate")
    public String userActivate(@RequestParam(value="code",required = true) Integer code,@RequestParam(value="userphone",required = true) String userphone,HttpServletRequest request,Model model){
        PageNotice pageNotice = userService.userActivate(code,userphone,request);
        model.addAttribute("pageNotice",pageNotice);
        return "/user/activiate";
    }


    @RequiresRoles(value={"blogAdmin","blogManager","register","consumer"},logical = Logical.OR)
    @RequestMapping("/manageCenter")
    public String userManageCenter(
            Model model,HttpServletRequest request,
            @RequestParam(value="pageNum",required=false,defaultValue = "1")Integer pageNum,
            @RequestParam(value="pageSize",required=false,defaultValue = "20")Integer pageSize
    ) throws ParseException {
        Long userid = (Long)request.getSession().getAttribute("userId");
        PictureQuery pictureQuery = new PictureQuery();
        pictureQuery.setPageNum(pageNum);
        pictureQuery.setPageSize(pageSize);
        List<Category> categorys = articleService.getCategory();
        PictureQuery pictureQueryList = behaviorService.getUserImages(pictureQuery);
        String resultInfo = (String) request.getAttribute("resultInfo");
        BehaviorStatus behaviorBean = behaviorService.getUserBehavior(userid);
        model.addAttribute("behaviorBean",behaviorBean);
        model.addAttribute("resultInfo",resultInfo);
        model.addAttribute("categorys",categorys);
        model.addAttribute("pictureQueryList",pictureQueryList);
        return "/user/manageCenter";
    }


}
