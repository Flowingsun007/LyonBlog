package com.flowingsun.behavior.controller;

import com.flowingsun.article.entity.ArticleTag;
import com.flowingsun.article.entity.BlogInfo;
import com.flowingsun.article.entity.Category;
import com.flowingsun.article.service.ArticleService;
import com.flowingsun.behavior.entity.*;
import com.flowingsun.behavior.service.BehaviorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("behavior")
public class BehaviorController {

    @Autowired
    private BehaviorService behaviorService;

    @Autowired
    private ArticleService articleService;

    //@RequiresPermissions("article:comment")
    @RequestMapping("/comment")
    @ResponseBody
    public String commentToArticle(@RequestBody Comment commentBean, HttpServletRequest request){
        return behaviorService.setComment(commentBean,request);
    }

    //@RequiresPermissions("behavior:thank")
    @RequestMapping("/thank")
    @ResponseBody
    public String thankToArticle(@RequestBody Thank thankBean, HttpServletRequest request){
        return behaviorService.setThank(thankBean,request);
    }

    //@RequiresPermissions("article:collect")
    @RequestMapping("/collect")
    @ResponseBody
    public String collectArticle(@RequestBody Collection collectionBean, HttpServletRequest request){
        return behaviorService.setCollect(collectionBean,request);
    }

    //@RequiresPermissions("article:comment")
    @RequestMapping("/commentLike")
    @ResponseBody
    public String setCommentLike(@RequestBody CommentLike commentLikeBean, HttpServletRequest request){
        return behaviorService.setCommentLike(commentLikeBean,request);
    }
    //@RequiresPermissions("article:comment")
    @RequestMapping("/commentDiscuss")
    @ResponseBody
    public String setCommentDiscussion(@RequestBody Discussion discussion, HttpServletRequest request){
        return behaviorService.setCommentDiscussion(discussion,request);
    }

    @RequestMapping("/donateMoney")
    public String getMoneyDonatePage(HttpServletRequest request,Model model){
        List<Category> categorys = articleService.getCategory();
        List<ArticleTag> allTags = articleService.selectAllTag();
        BlogInfo blogInfo = articleService.selectInfomation();
        model.addAttribute("blogInfo",blogInfo);
        model.addAttribute("allTags",allTags);
        model.addAttribute("categorys",categorys);
        return behaviorService.getMoneyDonatePage(request);
    }

    @RequiresPermissions("article:collect")
    @RequestMapping("/getColletions")
    @ResponseBody
    public String getUserArticleCollections(HttpServletRequest request){
        return behaviorService.getUserArticleCollections(request);
    }

    /*
     * MultipartHttpServletRequest: 继承于HttpServletRequest以及MultipartRequest.
     * 其中MultipartRequest中定义了相关的访问操作. MultipartHttpServletRequest重写
     * 了HttpServletRequest中的方法, 并进行了扩展. 如果以HttpServletRequest来接收参
     * 数, 则需要先将其转为MultipartHttpServletRequest类型
     * MultipartHttpServletRequest request = (MultipartHttpServletRequest) HttpServletRequest;
     */
    @RequiresPermissions("behavior:uploadImage")
    @RequestMapping("/uploadImage")
    public String imageUpload(HttpServletRequest request, @RequestParam(value="description",required = false) String description){
        //MultipartHttpServletRequest
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            System.out.println("is-Multipart");
        }else{
            System.out.println("not-Multipart");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String resultInfo = behaviorService.imageUpload(multipartRequest,description);
        System.out.println(resultInfo);
        request.setAttribute("resultInfo",resultInfo);
        return "forward:/user/manageCenter";
    }





}
