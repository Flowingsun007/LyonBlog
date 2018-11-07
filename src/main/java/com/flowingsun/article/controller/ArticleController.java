package com.flowingsun.article.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flowingsun.article.entity.*;
import com.flowingsun.article.service.ArticleService;
import com.flowingsun.article.vo.CategoryArticleQuery;

import com.flowingsun.article.vo.TagArticleQuery;
import com.flowingsun.behavior.service.BehaviorService;
import com.flowingsun.user.entity.User;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHost;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BehaviorService behaviorService;

    private final String UPLOAD_IMAGE_PATH = "/static/uploadBlogFile/image";

    private final String SEARCH_FIELD_TITLE = "title";

    private final String SEARCH_FIELD_ABSTRACT = "abstract";

    private final String SEARCH_FIELD_CONTENT = "content";

    private final String DB_FIELD_TITLE = "article_title";

    private final String DB_FIELD_ABSTRACT = "article_abstract";

    private final String DB_FIELD_CONTENT = "article_content";

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/11/4 14:21
     *@Param [model, keywords]
     *@Return java.lang.String
     *@Description elasticCategorySearch
     * 用于博客前台搜索，在此方法中调用ElasticSearch的Java客户端RestHighLevelClient，来实现从Es搜索引擎中查询文章信息的效果
     * 目前设计的是支持查询博客文章中的标题+摘要+正文字段，默认是精准查找；
     * 可以通过搜索关键字中添加"+"来控制搜索范围，以下三种模式分别支持在标题中、在摘要中、在正文中查找关键字为key的内容。
     * title     +   key
     * abstract  +   key
     * content  +    key
     */
    //@RequiresPermissions("behavior:elasticSearch")
    @RequestMapping("/elastic/category")
    public String elasticCategorySearch(Model model,@RequestParam(value="keywords")String keywords)throws IOException{
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置搜索结果起始行数下标，默认从0开始。
        sourceBuilder.from(0);
        //设置搜索结果显示条数
        sourceBuilder.size(10);
        //设置搜索结果超时时间
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        //构建QueryBuilder的子类——匹配查询MatchQueryBuilder
//        MatchQueryBuilder matchBuilder = new MatchQueryBuilder("article_content",keywords);
//        //在匹配查询上启用模糊匹配
//        matchBuilder.fuzziness(Fuzziness.ZERO);
//        //在匹配查询上设置前缀长度选项
//        matchBuilder.prefixLength(3);
//        //设置最大扩展选项以控制查询的模糊过程
//        matchBuilder.maxExpansions(6);
//        //也可以采用流畅式编程的方式创建QueryBuilder的子类对象：
//        QueryBuilder matchBuilder = QueryBuilders
//                .matchQuery("article_content","encoding")
//                .fuzziness(Fuzziness.AUTO)
//                .prefixLength(5)
//                .maxExpansions(10)
//        sourceBuilder.query(matchBuilder);
//
        //默认情况下，搜索请求会返回文档的内容,可以使用fetchSource(false);来关闭检索全文档；
        // 通过includeFields和excludeFields来设置要检索的字段：
        //sourceBuilder.fetchSource(false);
        //String [] includeFields = new String [] {"article_title","article_abstract","article_content"};
        //String [] excludeFields = new String [] {"id","article_content_copy","article_main_id","userid","article_second_id"};
        //sourceBuilder.fetchSource(includeFields,excludeFields);

        QueryBuilder queryBuilder = null;
        MatchPhraseQueryBuilder mpqBuilder1 = null;
        MatchPhraseQueryBuilder mpqBuilder2 = null;
        MatchPhraseQueryBuilder mpqBuilder3 = null;
        String keyword = keywords;
        //如果关键词中包含"+"，则默认将其拆分为2部分，+的左半部分用来标识查找范围，+的右半部分是关键词
        if(keywords.indexOf("+")>-1){
            String[] slist = keywords.split("\\+");
            String range = slist[0];
            keyword = slist[1];
            if(range.equals(SEARCH_FIELD_TITLE)){
                mpqBuilder1 = new MatchPhraseQueryBuilder(DB_FIELD_TITLE,keyword);
                queryBuilder = QueryBuilders.boolQuery().must(mpqBuilder1);
            }else if(range.equals(SEARCH_FIELD_ABSTRACT)){
                mpqBuilder2 = new MatchPhraseQueryBuilder(DB_FIELD_ABSTRACT,keyword);
                queryBuilder = QueryBuilders.boolQuery().must(mpqBuilder2);
            }else if(range.equals(SEARCH_FIELD_CONTENT)){
                mpqBuilder3 = new MatchPhraseQueryBuilder(DB_FIELD_CONTENT,keyword);
                queryBuilder = QueryBuilders.boolQuery().must(mpqBuilder3);
            }
        }else{
            mpqBuilder1 = new MatchPhraseQueryBuilder(DB_FIELD_ABSTRACT,keyword);
            mpqBuilder2 = new MatchPhraseQueryBuilder(DB_FIELD_ABSTRACT,keyword);
            mpqBuilder3 = new MatchPhraseQueryBuilder(DB_FIELD_CONTENT,keyword);
            queryBuilder = QueryBuilders.boolQuery().should(mpqBuilder1).should(mpqBuilder2).should(mpqBuilder3);
        }

        List<Article> articleList = new ArrayList<>();
        try{
            sourceBuilder.query(queryBuilder);
            //将搜索构造器类sourceBuilder装入SearchRequest类
            searchRequest.source(sourceBuilder);
            //client的search方法执行搜索，结果返回到SearchResponse类
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            //获取搜索结果
            SearchHits hits = searchResponse.getHits();
            //搜索到的结果数
            long totalHits = hits.getTotalHits();
            //处理搜索到的文档结果
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits){
                Article article = new Article();
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                Integer articleId = (Integer) sourceAsMap.get("id");
                Integer uid = (Integer) sourceAsMap.get("userid");
                //String createDate = (String) sourceAsMap.get("create_date");
                String articleTitle = (String) sourceAsMap.get("article_title");
                String articleAbstract = (String) sourceAsMap.get("article_abstract");
                String articleContent = (String) sourceAsMap.get("article_content");
                article.setId(articleId);
                article.setUserid(uid);
                article.setArticleTitle(articleTitle);
                article.setArticleAbstract(articleAbstract);
                article.setArticleContent(articleContent);
                articleList.add(article);
            }
        }catch (Exception f){
            System.out.println("-------------------------------------------搜索结果为空或异常\n-------------------------------------------");
        }

        List<ArticleTag> allTags = articleService.selectAllTag();
        BlogInfo blogInfo = articleService.selectInfomation();
        List<Category> categorys = articleService.getCategory();
        String s1 = JSON.toJSONString(allTags);
        String s2 = JSON.toJSONString(blogInfo);
        String s3 = JSON.toJSONString(categorys);
        String s4 = JSON.toJSONString(articleList);
        model.addAttribute("allTags",JSON.parseArray(s1));
        model.addAttribute("blogInfo",JSON.parseObject(s2,BlogInfo.class));
        model.addAttribute("categorys",JSON.parseArray(s3));
        model.addAttribute("searchResults",JSON.parseArray(s4));
        return "/article/elasticSearch";

    }

    @RequestMapping("/json/category")
    public String jsonCategoryArticle(@RequestParam("cId") Integer cId,
                                  @RequestParam(value="pageNum",required=false,defaultValue = "1")Integer pageNum,
                                  @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize,
                                  Model model) throws IOException {
        CategoryArticleQuery queryBean = new CategoryArticleQuery();
        queryBean.setPageSize(pageSize);
        queryBean.setPageNum(pageNum);
        queryBean.setcId(cId);
        List<Category> categorys = articleService.getCategory();
        CategoryArticleQuery categoryArticleQuery = articleService.getCategoryArticles(cId,queryBean);
        Long userId = (Long)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<ArticleTag> allTags = articleService.selectAllTag();
        BlogInfo blogInfo = articleService.selectInfomation();
        String s1 = JSON.toJSONString(allTags);
        String s2 = JSON.toJSONString(blogInfo);
        String s3 = JSON.toJSONString(categoryArticleQuery);
        String s4 = JSON.toJSONString(categorys);
        if(userId!=null&&categoryArticleQuery.getTotal()!=0){
            CategoryArticleQuery result = behaviorService.getUserCategoryArticleBehavior(categoryArticleQuery,userId);
            s3 = JSON.toJSONString(result);
        }
        model.addAttribute("allTags",JSON.parseArray(s1));
        model.addAttribute("blogInfo",JSON.parseObject(s2,BlogInfo.class));
        model.addAttribute("pageQueryBean",JSON.parseObject(s3,CategoryArticleQuery.class));
        model.addAttribute("categorys",JSON.parseArray(s4));


        return "/article/categoryArticle-json";

    }

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
        BlogInfo blogInfo = articleService.selectInfomation();
        model.addAttribute("allTags",allTags);
        model.addAttribute("blogInfo",blogInfo);
        model.addAttribute("pageQueryBean",categoryArticleQuery);
        if(userId!=null&&categoryArticleQuery.getTotal()!=0){
            CategoryArticleQuery result = behaviorService.getUserCategoryArticleBehavior(categoryArticleQuery,userId);
            model.addAttribute("pageQueryBean",result);
        }
        return "/article/categoryArticle";
    }

    @RequestMapping("/json/single")
    public String jsonSingleArticle(@RequestParam("articleId") Integer articleId,Model model){
        List<Category> categorys = articleService.getCategory();
        model.addAttribute("categorys",categorys);
        if(articleService.checkArticleExist(articleId)){
            Article article = articleService.getArticle(articleId);
            List<ArticleTag> allTags = articleService.selectAllTag();
            BlogInfo blogInfo = articleService.selectInfomation();
            String s1 = JSON.toJSONString(allTags);
            String s2 = JSON.toJSONString(blogInfo);
            String s3 = JSON.toJSONString(article);
            if(article!=null){
                RegularRecommend regularRecommend = articleService.getRegularRecommendArticle(articleId);
                if(regularRecommend!=null){article.setRegularRecommend(regularRecommend);}
                Long userId = (Long)SecurityUtils.getSubject().getSession().getAttribute("userId");
                if(userId!=null){
                    Article result = behaviorService.getUserArticleBehavior(article,userId);
                    s3 = JSON.toJSONString(result);
                }
            }
            model.addAttribute("allTags",JSON.parseArray(s1));
            model.addAttribute("blogInfo",JSON.parseObject(s2,BlogInfo.class));
            model.addAttribute("article",JSON.parseObject(s3,Article.class));
        }
        return "/article/singleArticle-json";
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
            BlogInfo blogInfo = articleService.selectInfomation();
            model.addAttribute("allTags",allTags);
            model.addAttribute("blogInfo",blogInfo);
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
        BlogInfo blogInfo = articleService.selectInfomation();
        model.addAttribute("allTags",allTags);
        model.addAttribute("blogInfo",blogInfo);
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
