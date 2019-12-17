package com.flowingsun.behavior.controller;

import com.flowingsun.article.dto.ArticleTag;
import com.flowingsun.article.dto.BlogInfo;
import com.flowingsun.article.entity.Article;
import com.flowingsun.article.entity.Category;
import com.flowingsun.article.service.ArticleService;
import com.flowingsun.article.vo.CategoryArticleQuery;
import com.flowingsun.behavior.entity.*;
import com.flowingsun.behavior.service.BehaviorService;
import com.flowingsun.common.dto.ResponseDto;
import com.flowingsun.common.utils.ResultUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
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
        request.setAttribute("resultInfo",resultInfo);
        return "forward:/user/manageCenter";
    }

    /**
     * @date   2019/1/8 20:54
     * @detail 根据给定url，生成网页截图存服务器
     * (原理：调用phantomjs访问指定url的网站，并截图)
     * 截图尺寸默认全屏：width: 1920, height: 1080，在phantomjs文件夹下rasterize.js中设定
     */
    @RequestMapping("/screenShot")
    public String getScreenShot(@RequestParam(value="url") String url, HttpServletResponse response)throws Exception{
        String imgagePath = behaviorService.getScreenShot(url);
        //不按原图比例，对图片进行处理，处理后尺寸固定为640*480
        Thumbnails.of(new File(imgagePath))
                .size(640, 480)
                .keepAspectRatio(false)
                .outputQuality(1.0)
                .toFile(imgagePath+"thumbnail.jpg");
//        //按照原图的比例对图片进行缩放，保证缩放后尺寸不超过640*480
//        Thumbnails.of(new File(imgagePath))
//                .size(640, 480)
//                .outputQuality(1.0)
//                .toFile(imgagePath+"thumbnail.jpg");
        if(imgagePath!=null&&imgagePath.length()>0){
            File imageLocal = new File(imgagePath);
            byte[] imgdata = FileUtils.readFileToByteArray(imageLocal);
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            os.write(imgdata);
            os.flush();
            os.close();
            return "success:截图成功";
        }else{
            return "fail:图片失败";
        }
    }

    @PostMapping("/collectUrl")
    @ResponseBody
    public ResponseDto collectUrl(@RequestBody UrlCollection bean){
        try{
            UrlCollection result = behaviorService.collectUrl(bean);
            return ResultUtils.getResult(result);
        }catch (Exception e){
            return ResultUtils.getResultEx(e);
        }
    }


    @PostMapping("/writeBlog/submit")
    @ResponseBody
    public ResponseDto submitArticle(@RequestBody Article article){
        try{
            ResponseDto result = articleService.createUserArticle(article);
            return ResultUtils.getResult(result);
        }catch (Exception e){
            return ResultUtils.getResultEx(e);
        }
    }

    @GetMapping("/category")
    public String categoryArticle(
            @RequestParam("cId") Integer cId,
            @RequestParam(value="pageNum",required=false,defaultValue = "1")Integer pageNum,
            @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize,
            Model model){
        Long userId = (Long)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<Category> categorys = articleService.getCategory();
        List<ArticleTag> allTags = articleService.selectAllTag();
        BlogInfo blogInfo = articleService.selectInfomation();
        model.addAttribute("categorys",categorys);
        model.addAttribute("allTags",allTags);
        model.addAttribute("blogInfo",blogInfo);
        if(userId!=null){
            CategoryArticleQuery queryBean = new CategoryArticleQuery();
            queryBean.setPageSize(pageSize);
            queryBean.setPageNum(pageNum);
            queryBean.setcId(cId);
            CategoryArticleQuery categoryArticleQuery = new CategoryArticleQuery();
            categoryArticleQuery = articleService.getUserCategoryArticles(cId,queryBean,userId);
            List<Article> articleList = (List<Article>) categoryArticleQuery.getDataList();
            if(articleList!=null){
                List<Article> articles = behaviorService.getUserArticleListBehavior(articleList,userId);
                categoryArticleQuery.setDataList(articles);
            }
            model.addAttribute("pageQueryBean",categoryArticleQuery);
        }
        return "/user/categoryArticle";
    }

    @RequiresPermissions("behavior:uploadImage")
    @PostMapping("/headImage")
    public String setUserHeadImage(@RequestParam("userHeadImage") MultipartFile multipartFile, HttpServletRequest request){
        String resultInfo = behaviorService.setUserHeadImage(multipartFile, request);
        request.setAttribute("resultInfo",resultInfo);
        return "forward:/user/manageCenter";
    }


    /**
     * @date 下午7:07 2019/12/17
     * @description 此页面(onlineUtils)集合了以下小工具：
     * 1.图片目标检测
     * 2.视频下载器
     * 3.网页转图片/pdf
     *
     * 1.图片目标检测器
     * 底层用到的组件：opencv;darknet
     * https://github.com/pjreddie/darknet
     * 原理，将上传的图片用darknet训练好的模型yolov3进行检测，支持对ImageNet数据集上的80种目标检测
     * 并利用opencv框出检测出的目标框（只显示置信度>80%的框），最后将画出检测框的图片返回前端显示
     *
     * 2.视频下载器
     * 底层需要用到的组件：ffmpeg,python3,github上开源的视频下载项目：annie和Video-Downloader
     * 需要将ffmpeg、annie、python3加入PATH
     * https://github.com/iawia002/annie
     * https://github.com/CharlesPikachu/Video-Downloader
     *
     * 3.网页转图片/pdf
     * 将给定url网页的html转化成pdf或者图片保存,底层用到的组件：wkhtmltopdf
     * 需要将wkhtmltopdf加入PATH
     * https://github.com/wkhtmltopdf/wkhtmltopdf
     **/
    @RequestMapping("/onlineUtils")
    public String onlineUtils(HttpServletRequest request,Model model){
        List<Category> categorys = articleService.getCategory();
        List<ArticleTag> allTags = articleService.selectAllTag();
        BlogInfo blogInfo = articleService.selectInfomation();
        model.addAttribute("blogInfo",blogInfo);
        model.addAttribute("allTags",allTags);
        model.addAttribute("categorys",categorys);
        model.addAttribute("resultInfo",request.getAttribute("resultInfo"));
        model.addAttribute("detectedImagePath",request.getAttribute("detectedImagePath"));
        model.addAttribute("videoPaths",request.getAttribute("videoPaths"));
        return behaviorService.onlineUtils(request);
    }


    /**
     * @date 下午7:22 2019/12/17
     * @description 图片目标检测器
     **/
    @RequiresPermissions("behavior:uploadImage")
    @RequestMapping("/detectImage")
    public String detectImage(HttpServletRequest request, @RequestParam(value="description",required = false) String description){
        //MultipartHttpServletRequest
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            System.out.println("is-Multipart");
        }else{
            System.out.println("not-Multipart");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;


        List<String> resultInfo = behaviorService.detectImage(multipartRequest,description);
        if(resultInfo.size()==2){
            request.setAttribute("resultInfo",resultInfo.get(1));
            request.setAttribute("detectedImagePath",resultInfo.get(0));
        }else if(resultInfo.size()==1){
            request.setAttribute("resultInfo",resultInfo.get(0));
        }
        return "forward:/behavior/onlineUtils";
    }


    /**
     * @date 下午7:23 2019/12/17
     * @description 视频下载器
     **/
    @GetMapping("/video/download")
    public String downloadVideo(HttpServletRequest request, @RequestParam(value="url",required = true) String url)throws Exception{
        List<String> results = behaviorService.downloadVideo(url);
        if(results!=null&&results.size()>=1){
            request.setAttribute("videoPaths",results);
        }
        return "forward:/behavior/onlineUtils";
    }



}
