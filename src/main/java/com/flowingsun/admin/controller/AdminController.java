package com.flowingsun.admin.controller;

import com.flowingsun.admin.entity.AdminBlogQuery;
import com.flowingsun.article.entity.Article;
import com.flowingsun.article.entity.ArticleTag;
import com.flowingsun.article.entity.Category;
import com.flowingsun.article.service.ArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@Author Lyon[flowingsun007@163.com]
 *@Date 18/05/19 21:46
 *@Description 系统管理页面,文章的镇删改查
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private ArticleService articleService;

    @RequiresPermissions("admin:home")
    @RequestMapping("")
    public String adminIndex(){
        return "redirect:admin/home";
    }

    @RequiresPermissions("admin:editTag")
    @RequestMapping("/editTag")
    public String editArticleTag(@RequestParam(value="pageNum",required=false,defaultValue = "1")Integer pageNum,
                                 @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize,
                                 @RequestParam(value="articleCid",required=false,defaultValue = "0")Integer articleCid,
                                 @RequestParam(value="articleMid",required=false,defaultValue = "0")Integer articleMid,
                                 Model model){
        AdminBlogQuery queryBean = new AdminBlogQuery();
        queryBean.setArticleCid(articleCid);
        queryBean.setArticleMid(articleMid);
        queryBean.setPageSize(pageSize);
        queryBean.setPageNum(pageNum);
        AdminBlogQuery pageQueryBean = articleService.getAllArticles(queryBean);
        pageQueryBean.setCategoryChoice(articleService.getAllCategory());
        model.addAttribute("pageQueryBean",pageQueryBean);
        List<Category> categorys = articleService.getCategory();
        model.addAttribute("categorys",categorys);
        return "admin/editTag";
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/editTag/reset")
    @ResponseBody
    public String batchResetTagSubmit(@RequestBody ArticleTag tagBean){
        return articleService.resetArticleTag(tagBean);
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/editTag/batchReset")
    @ResponseBody
    public String resetTagsubmit(@RequestBody ArticleTag tagBean){
        return articleService.batchResetArticleTag(tagBean);
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/editTag/batchAdd")
    @ResponseBody
    public String batchAddTag(@RequestBody ArticleTag tagBean){
        return articleService.batchAddArticleTag(tagBean);
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/editTag/batchDelete")
    @ResponseBody
    public String batchDeleteTag(@RequestBody ArticleTag tagBean){
        return articleService.batchDeleteArticleTag(tagBean);
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/editTag/article/deleteAll")
    @ResponseBody
    public String deleteArticleAllTag(@RequestBody ArticleTag tagBean){
        return articleService.deleteArticleAllTag(tagBean);
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/editTag/article/batchDeleteAll")
    @ResponseBody
    public String batchDeleteArticleAllTag(@RequestBody ArticleTag tagBean){
        return articleService.batchDeleteArticleAllTag(tagBean);
    }

    @RequiresPermissions("admin:home")
    @RequestMapping("/home")
    public String adminHome(@RequestParam(value="pageNum",required=false,defaultValue = "1")Integer pageNum,
                            @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize,
                            @RequestParam(value="articleCid",required=false,defaultValue = "0")Integer articleCid,
                            @RequestParam(value="articleMid",required=false,defaultValue = "0")Integer articleMid,
                            Model model){
        AdminBlogQuery queryBean = new AdminBlogQuery();
        queryBean.setArticleCid(articleCid);
        queryBean.setArticleMid(articleMid);
        queryBean.setPageSize(pageSize);
        queryBean.setPageNum(pageNum);
        AdminBlogQuery pageQueryBean = articleService.getAllArticles(queryBean);
        pageQueryBean.setCategoryChoice(articleService.getAllCategory());
        model.addAttribute("pageQueryBean",pageQueryBean);
        List<Category> categorys = articleService.getCategory();
        model.addAttribute("categorys",categorys);
        return "admin/adminHome";
    }

    @RequiresPermissions("admin:home")
    @RequestMapping("/categorySelect")
    @ResponseBody
    public String submitArticle(@RequestParam("mId") Integer mId){
        return articleService.categorySelectAjax(mId);
    }

    @RequiresPermissions("admin:home")
    @RequestMapping("/writeBlog")
    public String writeArticle(Model model){
        Category categoryChoice = articleService.getAllCategory();
        model.addAttribute("categoryChoice",categoryChoice);
        return "admin/writeBlog";
    }

    @RequiresPermissions("admin:home")
    @RequestMapping("/writeBlog/submit")
    @ResponseBody
    public String submitArticle(@RequestBody Article article){
        return articleService.createArticle(article);
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/delete/oneBlog")
    @ResponseBody
    public String deleteOneArticle(@RequestParam("articleId") Integer articleId){
        return articleService.deleteOneArticle(articleId);
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/delete/batchBlog")
    @ResponseBody
    public String deleteOneArticle(@RequestParam("articleIdStr") String articleIdStr){
        return articleService.deleteBatchArticle(articleIdStr);
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/editBlog")
    public String editArticle(@RequestParam("articleId") Integer articleId , Model model){
        Category categoryChoice = articleService.getAllCategory();
        Article article = articleService.getArticle(articleId);
        model.addAttribute("categoryChoice",categoryChoice);
        model.addAttribute("article",article);
        return "admin/editBlog";
    }

    @RequiresRoles("blogAdmin")
    @RequestMapping("/editBlog/submit")
    @ResponseBody
    public String editSubmit(@RequestBody Article article){
        return articleService.editArticle(article);
    }


}


