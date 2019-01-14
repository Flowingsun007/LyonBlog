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
        System.out.println("开始startSpider...........");
        BasicCrawlController crawlController = new BasicCrawlController();
        String[] s1 = new String[4];
        //项目配置存放路径(运行后会自动生成配置，存放在路径下的frontier文件夹中)
        s1[0] = "C:\\Users\\flowi\\Desktop\\Heatmap3D";
        //爬虫线程数
        s1[1] = "4";
        //爬取内容的存放路径
        s1[2] = "C:\\Users\\flowi\\Desktop\\Heatmap3D\\lyonblog\\html";
        //要爬取的目标网址
        s1[3] = "http://www.flowingbit.com";
        try{
            crawlController.main(s1);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("结束startSpider...........");
    }

    @GetMapping("/crawlImage")
    @ResponseBody
    public void crawlImage(){
        //参数：项目配置存放路径；爬虫线程数；内容存储路径；目标网址
        System.out.println("开始startSpider...........");
        ImageCrawlController imageCrawlController = new ImageCrawlController();
        String[] s2 = new String[4];
        //项目配置存放路径(运行后会自动生成配置，存放在路径下的frontier文件夹中)
        s2[0] = "C:\\Users\\flowi\\Desktop\\Heatmap3D\\lyonblog";
        //爬虫线程数
        s2[1] = "4";
        //爬取内容的存放路径
        s2[2] = "C:\\Users\\flowi\\Desktop\\Heatmap3D\\lyonblog\\image";
        //要爬取的目标网址
        s2[3] = "http://www.flowingbit.com";
        try{
            imageCrawlController.main(s2);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("结束startSpider...........");
    }




}
