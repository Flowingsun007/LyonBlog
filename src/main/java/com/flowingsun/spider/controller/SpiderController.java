package com.flowingsun.spider.controller;


import com.flowingsun.spider.service.BasicCrawlController;
import com.flowingsun.spider.service.ImageCrawlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("spider")
public class SpiderController {

    @Autowired ImageCrawlController imageCrawlService;

    @Autowired BasicCrawlController basicCrawlService;

    @GetMapping("/crawlHtml")
    @ResponseBody
    public String crawlHtml(){
        String[] s = new String[3];
        String[] crawlDomains = new String[2];
        //项目配置存放路径(运行后会自动生成配置，存放在路径下的frontier文件夹中)
        s[0] = "C:\\Users\\flowi\\Desktop\\Heatmap3D\\data";
        //爬虫线程数
        s[1] = "4";
        //爬取内容的存放路径
        s[2] = "C:\\Users\\flowi\\Desktop\\Heatmap3D\\data\\html";
        //要爬取的目标网址
        crawlDomains[0] = "https://github.com/search";
        crawlDomains[1] = "https://github.com/search?p=1&q=todo&type=Repositories";
        try{
            basicCrawlService.crawlHtml(s,crawlDomains);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "正在爬取中ing...";
    }

    @GetMapping("/crawlImage")
    @ResponseBody
    public String crawlImage(){
        //参数：项目配置存放路径；爬虫线程数；内容存储路径；目标网址
        String[] s = new String[3];
        String[] crawlDomains = new String[2];
        //项目配置存放路径(运行后会自动生成配置，存放在路径下的frontier文件夹中)
        s[0] = "C:\\Users\\flowi\\Desktop\\Heatmap3D\\data\\";
        //爬虫线程数
        s[1] = "4";
        //爬取内容的存放路径
        s[2] = "C:\\Users\\flowi\\Desktop\\Heatmap3D\\data\\image";
        //设置要爬取的目标网址
        crawlDomains[0] = "http://www.umei.cc/bizhitupian/meinvbizhi/186528.htm";
        crawlDomains[1] = "http://www.umei.cc/bizhitupian/meinvbizhi";
        try{
            imageCrawlService.crawlImage(s,crawlDomains);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "正在爬取中ing...";
    }




}
