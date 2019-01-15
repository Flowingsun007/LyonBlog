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

package com.flowingsun.spider.service;

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

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Yasser Ganjisaffar
 */
@Service
public class BasicCrawlController {

    private static final Logger logger = LoggerFactory.getLogger(BasicCrawlController.class);

    @Async
    public void crawlHtml(String[] args, String[] crawlDomains) throws Exception{

        System.out.println("开始crawlHtml...........");
        /*
         * crawlStorageFolder is a folder where intermediate crawl data is
         * stored.
         */
        String crawlStorageFolder = args[0];
        //String crawlStorageFolder = "C:\\Users\\flowi\\Desktop\\Heatmap3D";

        /*
         * numberOfCrawlers shows the number of concurrent threads that should
         * be initiated for crawling.
         */
        int numberOfCrawlers = Integer.parseInt(args[1]);
        //int numberOfCrawlers = 4;

        CrawlConfig config = new CrawlConfig();
        Collection<BasicHeader> headers = new HashSet<BasicHeader>();
        BasicHeader h0 = new BasicHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        BasicHeader h1 = new BasicHeader("Accept-Encoding","gzip, deflate, br");
        BasicHeader h2 = new BasicHeader("Accept-Language","zh-CN,zh;q=0.9");
        BasicHeader h3 = new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        BasicHeader h4 = new BasicHeader("Connection","keep-alive");
        BasicHeader h5 = new BasicHeader("Host","github.com");
        //BasicHeader h6 = new BasicHeader("Referer","https://github.com/");
        BasicHeader h7 = new BasicHeader("Cookie","_gh_sess=TjJUb2oxNmU0UnpIdkFGYk1GVEU5VkFRcHZmMTJzMitjYUY4bisyZldpRE9uVm5YVzRUdXJYZ2lTSkgwTWhXaUQ1RCtNcm9taW1iQ01FZFBMWWVBdnkwUENSTGpIb3pqb2JiMFpYNFc3eHRJRjhKSExOcERMOTFOZks1TUNXV1AyNUc1ZVRzWmt4TUZ6RTdiNTdndXFOT2pQQUFyRm1aSU5EUmJsL1BhUUxVNXNpMlMwanRNOFhMNEFDaU1XUFBLdTRyQzZzaFFSZTl1K0hxbHhVZ1JNVmEyUkZ1R0R4UW5KcGRyNlVlZEk5MD0tLVhlU1lLKzNhamV2eGVtRnZEdzdVcHc9PQ%3D%3D--f7f0a0ffe9c7991ee5c788e75ef0f1ab391a83bc; path=/; secure; HttpOnly");
        headers.add(h0);
        headers.add(h1);
        headers.add(h2);
        headers.add(h3);
        headers.add(h4);
        headers.add(h5);
        //headers.add(h6);
        headers.add(h7);
        //遵守网站的禁止爬虫提示？false
        config.setRespectNoFollow(false);
        config.setRespectNoIndex(false);
        //设置默认请求头
        config.setDefaultHeaders(headers);
        //设置内容存储文件夹
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Be polite: Make sure that we don't send more than 1 request per
         * second (1000 milliseconds between requests).
         */
        config.setPolitenessDelay(1000);

        /*
         * You can set the maximum crawl depth here. The default value is -1 for
         * unlimited depth
         */
        //最大爬取深度：8
        config.setMaxDepthOfCrawling(8);

        /*
         * You can set the maximum number of pages to crawl. The default value
         * is -1 for unlimited number of pages
         */
        config.setMaxPagesToFetch(1000);

        /**
         * Do you want crawler4j to crawl also binary data ?
         * example: the contents of pdf, or the metadata of images etc
         */
        config.setIncludeBinaryContentInCrawling(false);

        /*
         * Do you need to set a proxy? If so, you can use:
         * config.setProxyHost("proxyserver.example.com");
         * config.setProxyPort(8080);
         *
         * If your proxy also needs authentication:
         * config.setProxyUsername(username); config.getProxyPassword(password);
         */

        /*
         * This config parameter can be used to set your crawl to be resumable
         * (meaning that you can resume the crawl from a previously
         * interrupted/crashed crawl). Note: if you enable resuming feature and
         * want to start a fresh crawl, you need to delete the contents of
         * rootFolder manually.
         */
        config.setResumableCrawling(false);

        /*
         * Set this to true if you want crawling to stop whenever an unexpected error
         * occurs. You'll probably want this set to true when you first start testing
         * your crawler, and then set to false once you're ready to let the crawler run
         * for a long time.
         */
        //config.setHaltOnError(true);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setUserAgentName("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        //忽略网站的robots.txt
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        String storageFolder = args[2];
        String crawlDomain = crawlDomains[0];
        String crawlStartUrl = crawlDomains[1];
        controller.addSeed(crawlStartUrl);
        BasicCrawler.configure(crawlDomain, storageFolder);

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(BasicCrawler.class, numberOfCrawlers);

        System.out.println("结束crawlHtml...........");
    }

}