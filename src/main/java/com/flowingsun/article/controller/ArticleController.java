package com.flowingsun.article.controller;

import com.alibaba.fastjson.JSONObject;
import com.flowingsun.article.entity.Article;
import com.flowingsun.article.entity.ArticleTag;
import com.flowingsun.article.entity.Category;
import com.flowingsun.article.entity.RegularRecommend;
import com.flowingsun.article.service.ArticleService;
import com.flowingsun.article.vo.CategoryArticleQuery;

import com.flowingsun.article.vo.TagArticleQuery;
import com.flowingsun.behavior.service.BehaviorService;
import com.flowingsun.user.entity.User;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BehaviorService behaviorService;

    private final String UPLOAD_IMAGE_PATH = "/static/uploadBlogFile/image";

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/07/15 22:26
     *@Param [cId, pageNum, pageSize, model]
     *@Return java.lang.String
     *@Description 根据cid查询分类文章,pageNum和pageSize是页面传参,是分页查询的参数,默认从第一页开始,每页显示10条
     */
    @RequestMapping("/category")
    public String categoryArticle(
            @RequestParam("cId") Integer cId,
            @RequestParam(value="pageNum",required=false,defaultValue = "1")Integer pageNum,
            @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize,
            Model model){
        CategoryArticleQuery queryBean = new CategoryArticleQuery();
        queryBean.setPageSize(pageSize);
        queryBean.setPageNum(pageNum);
        queryBean.setcId(cId);
        List<Category> categorys = articleService.getCategory();
        CategoryArticleQuery categoryArticleQuery = articleService.getCategoryArticles(cId,queryBean);
        model.addAttribute("categorys",categorys);
        Long userId = (Long)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<ArticleTag> allTags = articleService.selectAllTag();
        model.addAttribute("allTags",allTags);
        if(userId!=null&&categoryArticleQuery.getTotal()!=0){
            CategoryArticleQuery result = behaviorService.getUserCategoryArticleBehavior(categoryArticleQuery,userId);
            model.addAttribute("pageQueryBean",result);
        }else{
            model.addAttribute("pageQueryBean",categoryArticleQuery);
        }
        return "/article/categoryArticle";
    }

    /**
     *@Param [articleId, model]
     *@Return java.lang.String
     *@Description 浏览某一篇文章
     */
    @RequestMapping("/single")
    public String singleArticle(@RequestParam("articleId") Integer articleId,Model model){
        List<Category> categorys = articleService.getCategory();
        model.addAttribute("categorys",categorys);
        if(articleService.checkArticleExist(articleId)){
            Article article = articleService.getArticle(articleId);
            List<ArticleTag> allTags = articleService.selectAllTag();
            model.addAttribute("allTags",allTags);
            if(article!=null){
                RegularRecommend regularRecommend = articleService.getRegularRecommendArticle(articleId);
                if(regularRecommend!=null){article.setRegularRecommend(regularRecommend);}
                Long userId = (Long)SecurityUtils.getSubject().getSession().getAttribute("userId");
                if(userId!=null){
                    Article result = behaviorService.getUserArticleBehavior(article,userId);
                    model.addAttribute("article",result);
                }else{model.addAttribute("article",article);}
            }
        }
        return "/article/singleArticle";
    }

    @RequiresPermissions("article:changeCategory")
    @RequestMapping("/changeCategory")
    @ResponseBody
    public String changeArticleCategory(@RequestBody Category articles){
        return articleService.changeArticleCategory(articles);
    }

    /**
     *@Param [tagId, pageNum, pageSize, model]
     *@Return java.lang.String
     *@Description 浏览某个文章标签
     */
    @RequestMapping("/tag")
    public String tagArticle(@RequestParam("tagId") Integer tagId,
                             @RequestParam(value="pageNum",required=false,defaultValue = "1")Integer pageNum,
                             @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize,
                             Model model){
        TagArticleQuery queryBean = new TagArticleQuery();
        queryBean.setPageSize(pageSize);
        queryBean.setPageNum(pageNum);
        queryBean.setTagid(tagId);
        TagArticleQuery tagArticleQuery = articleService.getTagArticles(queryBean);
        List<Category> categorys = articleService.getCategory();
        List<ArticleTag> allTags = articleService.selectAllTag();
        model.addAttribute("allTags",allTags);
        model.addAttribute("categorys",categorys);
        model.addAttribute("pageQueryBean",tagArticleQuery);
        return "/article/tagArticle";
    }

    /**
     *@Param [request, response, attach]
     *@Return void
     *@Description 写Blog文章时上传图片
     */
    //@RequiresPermissions("admin:home")
    @RequestMapping("/uploadBlogFile")
    public void uploadArticleImage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "editormd-image-file", required = false)MultipartFile attach){
        try {
            request.setCharacterEncoding( "UTF-8" );
            response.setHeader( "Content-Type" , "text/html" );
            String fileName = attach.getOriginalFilename();
            String rootPath = request.getSession().getServletContext().getRealPath(UPLOAD_IMAGE_PATH);
            /**
             * 将图片上传至Tomcat所在的服务器上，然后通过ftp上传至Nginx所在服务器的静态目录下
             */
            File filePath=new File(rootPath);
            if(!filePath.exists())
                filePath.mkdirs();
            File realFile=new File(rootPath,fileName);
            FileUtils.copyInputStreamToFile(attach.getInputStream(), realFile);

            /**
             * 用ftp将图片上传至Nginx所在服务器的静态目录,此处直接写死为本地路径，用作模拟
             */
            String npath = "/usr/local/nginx/html" + UPLOAD_IMAGE_PATH + File.separator + fileName;
            File nginxFile = new File(npath);
            FileUtils.copyInputStreamToFile(attach.getInputStream(), nginxFile);
            nginxFile.setReadable(true, false);
            //下面response返回的json格式是editor.md所限制的，规范输出就OK
            response.getWriter().write( "{\"success\": 1, \"message\":\"上传成功\",\"url\":\"" + UPLOAD_IMAGE_PATH + File.separator + fileName + "\"}" );

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write( "{\"success\":0}" +e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


}
