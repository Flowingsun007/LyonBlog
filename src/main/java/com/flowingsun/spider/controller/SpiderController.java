package com.flowingsun.spider.controller;


import com.flowingsun.spider.service.BasicCrawlController;
import com.flowingsun.spider.service.ImageCrawlController;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("spider")
public class SpiderController {

    @GetMapping("/crawlHtml")
    @ResponseBody
    public void crawlHtml(){
        System.out.println("开始crawlHtml...........");
        BasicCrawlController crawlController = new BasicCrawlController();
        String[] s1 = new String[4];
        //项目配置存放路径(运行后会自动生成配置，存放在路径下的frontier文件夹中)
        s1[0] = "/usr/local/data/LyonBlog/spider/html";
        //爬虫线程数
        s1[1] = "2";
        //爬取内容的存放路径
        s1[2] = "/usr/local/data/LyonBlog/spider/html/developerworks";
        //要爬取的目标网址
        s1[3] = "https://www.ibm.com/developerworks/cn/java";
        try{
            crawlController.main(s1);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("结束crawlHtml...........");
    }

    @GetMapping("/crawlImage")
    @ResponseBody
    public void crawlImage(){
        //参数：项目配置存放路径；爬虫线程数；内容存储路径；目标网址
        System.out.println("开始crawlImage...........");
        ImageCrawlController imageCrawlController = new ImageCrawlController();
        String[] s = new String[3];
        String[] crawlDomains = new String[2];
        //项目配置存放路径(运行后会自动生成配置，存放在路径下的frontier文件夹中)
        s[0] = "/usr/local/data/LyonBlog/spider/image";
        //爬虫线程数
        s[1] = "2";
        //爬取内容的存放路径
        s[2] = "/usr/local/data/LyonBlog/spider/image/developerworks";
        //要爬取的目标网址
        crawlDomains[0] = "https://www.ibm.com/developerworks/cn/java";
        crawlDomains[1] = "https://www.ibm.com/developerworks/cn";
        try{
            imageCrawlController.main(s,crawlDomains);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("结束crawlImage...........");
    }




}
