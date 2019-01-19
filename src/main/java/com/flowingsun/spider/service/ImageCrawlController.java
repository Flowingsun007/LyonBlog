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

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Yasser Ganjisaffar
 */
public class ImageCrawlController {
    private static final Logger logger = LoggerFactory.getLogger(ImageCrawlController.class);

    public static void main(String[] args, String[] crawlDomains) throws Exception {
        if (args.length < 3) {
            logger.info("Needed parameters: ");
            logger.info("\t rootFolder (it will contain intermediate crawl data)");
            logger.info("\t numberOfCrawlers (number of concurrent threads)");
            logger.info("\t storageFolder (a folder for storing downloaded images)");
            return;
        }

        String rootFolder = args[0];
        int numberOfCrawlers = Integer.parseInt(args[1]);
        String storageFolder = args[2];

        CrawlConfig config = new CrawlConfig();
        //遵守网站的禁止爬虫提示？false
        config.setRespectNoFollow(false);
        config.setRespectNoIndex(false);
        Collection<BasicHeader> headers = new HashSet<BasicHeader>();
        BasicHeader h0 = new BasicHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        BasicHeader h1 = new BasicHeader("Accept-Encoding","gzip, deflate, br");
        BasicHeader h2 = new BasicHeader("Accept-Language","zh-CN,zh;q=0.9");
        BasicHeader h3 = new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        headers.add(h0);
        headers.add(h1);
        headers.add(h2);
        headers.add(h3);
        //遵守网站的禁止爬虫提示？false
        config.setRespectNoIndex(false);
        config.setRespectNoFollow(false);
        //设置默认请求头(但是没什么用，源码发请求时没用到)
        config.setDefaultHeaders(headers);
        //设置内容存储文件夹
        config.setCrawlStorageFolder(rootFolder);
        //设置请求头user-angent
        config.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");

        /*
         * Since images are binary content, we need to set this parameter to
         * true to make sure they are included in the crawl.
         */
        //是否提取页面二进制数据(图像、视频)
        config.setIncludeBinaryContentInCrawling(true);
        //爬取请求延时1秒
        config.setPolitenessDelay(1000);
//        //最大爬取深度：10
//        config.setMaxDepthOfCrawling(10);
        //设置最大页面爬取数量
        config.setMaxPagesToFetch(5000);


        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setUserAgentName("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        for (String domain : crawlDomains) {
            controller.addSeed(domain);
        }

        ImageCrawler.configure(crawlDomains, storageFolder);

        controller.start(ImageCrawler.class, numberOfCrawlers);
    }
}

