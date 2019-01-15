package com.flowingsun.spider.service;

/**
 * @author Lyon
 * @date 2019/1/13 20:33
 * @description ImageCrawlController
 **/
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.net.CookieStore;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Yasser Ganjisaffar
 */
@Service
public class ImageCrawlController {

    private static final Logger logger = LoggerFactory.getLogger(ImageCrawlController.class);

    @Async
    public void crawlImage(String[] args,String[] crawlDomains)throws Exception{
        System.out.println("开始crawlImage...........");

        String rootFolder = args[0];
        int numberOfCrawlers = Integer.parseInt(args[1]);
        String storageFolder = args[2];

        CrawlConfig config = new CrawlConfig();
        Collection<BasicHeader> headers = new HashSet<BasicHeader>();
        BasicHeader h0 = new BasicHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        BasicHeader h1 = new BasicHeader("Accept-Encoding","gzip, deflate, br");
        BasicHeader h2 = new BasicHeader("Accept-Language","zh-CN,zh;q=0.9");
        BasicHeader h3 = new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        BasicHeader h4 = new BasicHeader("Connection","keep-alive");
        //BasicHeader h5 = new BasicHeader("Host","www.umei.cc");
        BasicHeader h6 = new BasicHeader("Cookie","Hm_lvt_c605a31292b623d214d012ec2a737685=1547474224; Hm_lpvt_c605a31292b623d214d012ec2a737685=1547474473");
        headers.add(h0);
        headers.add(h1);
        headers.add(h2);
        headers.add(h3);
        headers.add(h4);
        //headers.add(h5);
        headers.add(h6);
        //遵守网站的禁止爬虫提示？false
        config.setRespectNoIndex(false);
        config.setRespectNoFollow(false);
        //设置默认请求头
        config.setDefaultHeaders(headers);
        //设置内容存储文件夹
        config.setCrawlStorageFolder(rootFolder);
        config.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");

        /*
         * Since images are binary content, we need to set this parameter to
         * true to make sure they are included in the crawl.
         */
        //是否提取页面二进制数据(图像、视频)
        config.setIncludeBinaryContentInCrawling(true);



        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setUserAgentName("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        //忽略网站的robots.txt
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

//        for (String domain : crawlDomains) {
//            controller.addSeed(domain);
//        }
        controller.addSeed(crawlDomains[0]);

        ImageCrawler.configure(crawlDomains, storageFolder);

        controller.start(ImageCrawler.class, numberOfCrawlers);

        System.out.println("结束crawlImage...........");

    }


}

