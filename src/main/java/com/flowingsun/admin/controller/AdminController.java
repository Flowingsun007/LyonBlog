package com.flowingsun.admin.controller;

import com.flowingsun.admin.dto.AdminBlogQuery;
import com.flowingsun.article.entity.Article;
import com.flowingsun.article.dto.ArticleTag;
import com.flowingsun.article.entity.Category;
import com.flowingsun.article.service.ArticleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
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

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @GetMapping("")
    public String adminIndex(){
        return "redirect:admin/home";
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
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
        List<Category> categorys = articleService.getCategory();
        model.addAttribute("pageQueryBean",pageQueryBean);
        model.addAttribute("categorys",categorys);
        return "admin/editTag";
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @PostMapping("/editTag/reset")
    @ResponseBody
    public String batchResetTagSubmit(@RequestBody ArticleTag tagBean){
        return articleService.resetArticleTag(tagBean);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @PostMapping("/editTag/batchReset")
    @ResponseBody
    public String resetTagsubmit(@RequestBody ArticleTag tagBean){
        return articleService.batchResetArticleTag(tagBean);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @PostMapping("/editTag/batchAdd")
    @ResponseBody
    public String batchAddTag(@RequestBody ArticleTag tagBean){
        return articleService.batchAddArticleTag(tagBean);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @PostMapping("/editTag/batchDelete")
    @ResponseBody
    public String batchDeleteTag(@RequestBody ArticleTag tagBean){
        return articleService.batchDeleteArticleTag(tagBean);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @PostMapping("/editTag/article/deleteAll")
    @ResponseBody
    public String deleteArticleAllTag(@RequestBody ArticleTag tagBean){
        return articleService.deleteArticleAllTag(tagBean);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @PostMapping("/editTag/article/batchDeleteAll")
    @ResponseBody
    public String batchDeleteArticleAllTag(@RequestBody ArticleTag tagBean){
        return articleService.batchDeleteArticleAllTag(tagBean);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @GetMapping("/home")
    public String adminHome(@RequestParam(value="pageNum",required=false,defaultValue = "1")Integer pageNum,
                            @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize,
                            @RequestParam(value="articleCid",required=false,defaultValue = "0")Integer articleCid,
                            @RequestParam(value="articleMid",required=false,defaultValue = "0")Integer articleMid,
                            Model model){
        Long userId = (Long) SecurityUtils.getSubject().getSession().getAttribute("userId");
        if(userId!=null){
            AdminBlogQuery queryBean = new AdminBlogQuery();
            queryBean.setArticleCid(articleCid);
            queryBean.setArticleMid(articleMid);
            queryBean.setPageSize(pageSize);
            queryBean.setPageNum(pageNum);
            //原方法用于管理员查看所有文章AdminBlogQuery pageQueryBean = articleService.getAllArticles(queryBean);
            //改进后getUserAllArticles则只查看当前登录用户下所有文章
            AdminBlogQuery pageQueryBean = articleService.getUserAllArticles(queryBean, userId);
            pageQueryBean.setCategoryChoice(articleService.getAllCategory());
            List<Category> categorys = articleService.getCategory();
            model.addAttribute("pageQueryBean",pageQueryBean);
            model.addAttribute("categorys",categorys);
        }
        return "admin/adminHome";
    }

    @GetMapping("/categorySelect")
    @ResponseBody
    public String submitArticle(@RequestParam("mId") Integer mId){
        return articleService.categorySelectAjax(mId);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @GetMapping("/writeBlog")
    public String writeArticle(Model model){
        Category categoryChoice = articleService.getAllCategory();
        model.addAttribute("categoryChoice",categoryChoice);
        return "admin/writeBlog";
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @PostMapping("/writeBlog/submit")
    @ResponseBody
    public String submitArticle(@RequestBody Article article){
        return articleService.createArticle(article);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @GetMapping("/delete/oneBlog")
    @ResponseBody
    public String deleteOneArticle(@RequestParam("articleId") Integer articleId){
        return articleService.deleteOneArticle(articleId);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @GetMapping("/delete/batchBlog")
    @ResponseBody
    public String deleteOneArticle(@RequestParam("articleIdStr") String articleIdStr){
        return articleService.deleteBatchArticle(articleIdStr);
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @GetMapping("/editBlog")
    public String editArticle(@RequestParam("articleId") Integer articleId , Model model){
        Category categoryChoice = articleService.getAllCategory();
        Article article = articleService.getArticle(articleId);
        model.addAttribute("categoryChoice",categoryChoice);
        model.addAttribute("article",article);
        return "admin/editBlog";
    }

    @RequiresRoles(value={"blogAdmin","blogManager","consumer","register"},logical = Logical.OR)
    @PostMapping("/editBlog/submit")
    @ResponseBody
    public String editSubmit(@RequestBody Article article){
        return articleService.editArticle(article);
    }


}


