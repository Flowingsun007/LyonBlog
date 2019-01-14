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

import java.io.File;
import java.io.FileWriter;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar
 */
public class BasicCrawler extends WebCrawler {

    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");

    //添加了文件分隔符和换行符，屏蔽win和linux差异
    private static final String FILE_SEPARATOR  = System.getProperty("file.separator");
    private static final String LINE_SEPARATOR  = System.lineSeparator();

    private static String crawlDomain;

    private static File storageFolder;

    public static void configure(String domain, String storageFolderName) {
        crawlDomain = domain;
        storageFolder = new File(storageFolderName);
        if (!storageFolder.exists()) {
            storageFolder.mkdirs();
        }
    }

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        // Ignore the url if it has an extension that matches our defined set of image extensions.
        if (IMAGE_EXTENSIONS.matcher(href).matches()) {
            return false;
        }

        // Only accept the url if it is in the "www.ics.uci.edu" domain and protocol is "http".
        return href.startsWith(crawlDomain);
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        System.out.println("进入visit方法......");
        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();

//        logger.debug("Docid: {}", docid);
//        logger.info("URL: {}", url);
//        logger.debug("Domain: '{}'", domain);
//        logger.debug("Sub-domain: '{}'", subDomain);
//        logger.debug("Path: '{}'", path);
//        logger.debug("Parent page: {}", parentUrl);
//        logger.debug("Anchor text: {}", anchor);

        System.out.println("------------------------URL:------------------------" + LINE_SEPARATOR + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            String name = storageFolder.getAbsolutePath() + FILE_SEPARATOR +"visiturl.txt";
            // 打开一个写文件器，true表示以追加的形式写文件,false表示覆盖
            try(FileWriter writer = new FileWriter(name, true)){
                writer.write(url+LINE_SEPARATOR);
            }catch (Exception e){
                e.printStackTrace();
            }

            String content = storageFolder.getAbsolutePath() + FILE_SEPARATOR + UUID.randomUUID() + ".html";
            // 打开一个写文件器，true表示以追加的形式写文件,false表示覆盖
            try(FileWriter writer = new FileWriter(content, false)){
                writer.write(html);
            }catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("纯文本长度: " + text.length());
            System.out.println("html长度: " + html.length());
            System.out.println("输出链接个数: " + links.size());

        }

//        //记录响应头信息
//        Header[] responseHeaders = page.getFetchResponseHeaders();
//        if (responseHeaders != null) {
//            logger.debug("Response headers:");
//            for (Header header : responseHeaders) {
//                logger.debug("\t{}: {}", header.getName(), header.getValue());
//            }
//        }

        logger.debug("=============");
    }
}
