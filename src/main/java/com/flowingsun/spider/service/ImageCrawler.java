package com.flowingsun.spider.service;

/**
 * @author Lyon
 * @date 2019/1/13 20:33
 * @description ImageCrawler
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


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import com.google.common.io.Files;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar
 */

/*
 * This class shows how you can crawl images on the web and store them in a
 * folder. This is just for demonstration purposes and doesn't scale for large
 * number of images. For crawling millions of images you would need to store
 * downloaded images in a hierarchy of folders
 */
public class ImageCrawler extends WebCrawler {

    private static final Pattern filters = Pattern.compile(
            ".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
                    "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpeg|jpg|png|tiff?))$");

    private static File storageFolder;
    private static String[] crawlDomains;
    //添加了文件分隔符和换行符，屏蔽win和linux差异
    private static final String FILE_SEPARATOR  = System.getProperty("file.separator");
    private static final String LINE_SEPARATOR  = System.lineSeparator();

    public static void configure(String[] domain, String storageFolderName) {
        crawlDomains = domain;

        storageFolder = new File(storageFolderName);
        if (!storageFolder.exists()) {
            storageFolder.mkdirs();
        }
    }

    /**
     * @author lyon
     * @date   2019/1/15 18:43
     * @detail 判断一个url链接是否需要被访问
     * 如果页面后缀在filters中，则不访问；如果页面是图片则访问；剩下的遍历crawlDomains中的网址，
     *
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (filters.matcher(href).matches()) {
            return false;
        }

        if (imgPatterns.matcher(href).matches()) {
            return true;
        }

        for (String domain : crawlDomains) {
            if (href.startsWith(domain)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("-------------------------------visiturl:-------------------------------" + LINE_SEPARATOR + url);
        String name = storageFolder.getAbsolutePath() + FILE_SEPARATOR + "visiturl.txt";
        // 打开一个写文件器，true表示以追加的形式写文件,false表示覆盖
        try(FileWriter writer = new FileWriter(name, true)){
            writer.write(url+LINE_SEPARATOR);
        }catch (Exception e){
            e.printStackTrace();
        }




        // We are only interested in processing images which are bigger than 10k
        if (!imgPatterns.matcher(url).matches() || !((page.getParseData() instanceof BinaryParseData))) {
            return;
        }
        if(page.getContentData().length < (10 * 1024)){
            return;
        } else{
            // get a unique name for storing this image
            String extension = url.substring(url.lastIndexOf('.'));
            String hashedName = UUID.randomUUID() + extension;
            // store image
            String filename = storageFolder.getAbsolutePath() + "/" + hashedName;
            System.out.println("---------------------------filename:------------------------------" + LINE_SEPARATOR + filename);

            try {
                Files.write(page.getContentData(), new File(filename));
                System.out.println("爬取图片的url:"+url);
                //WebCrawler.logger.info("Stored: {}", url);
            } catch (IOException iox) {
                //WebCrawler.logger.error("Failed to write file: " + filename, iox);
            }

            System.out.println("-------------------------------imagePath:-------------------------------"+LINE_SEPARATOR+filename);
            String s = storageFolder.getAbsolutePath() + FILE_SEPARATOR + "imagePath.txt";
            // 打开一个写文件器，true表示以追加的形式写文件,false表示覆盖
            try(FileWriter writer = new FileWriter(s, true)){
                writer.write(filename + "图像大小：" + page.getContentData().length + LINE_SEPARATOR);
            }catch (Exception e){
                e.printStackTrace();
            }
        }





    }
}
