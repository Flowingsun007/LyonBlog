package com.flowingsun.article.service;

import com.flowingsun.admin.entity.AdminBlogQuery;
import com.flowingsun.article.dao.ArticleMapper;
import com.flowingsun.article.entity.*;
import com.flowingsun.article.vo.CategoryArticleQuery;
import com.flowingsun.article.vo.TagArticleQuery;
import com.flowingsun.behavior.dao.*;
import com.flowingsun.behavior.entity.Comment;
import com.flowingsun.common.dao.BlogVisitorMapper;
import com.flowingsun.common.dao.RedisDAO;
import com.flowingsun.common.annotation.MethodExcuteTimeLog;
import com.flowingsun.common.utils.InfoCountUtils;
import com.flowingsun.common.utils.changeListFormatUtils;
import com.flowingsun.user.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.*;


@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    private static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private static final Integer SUCCESS=1;

    private static final Integer FAIL=0;

    @Autowired
    private BlogVisitorMapper blogVisitorMapper;

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private CommentLikeMapper commentLikeMapper;

    @Autowired
    private DiscussionMapper discussionMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ThankMapper thankMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisDAO redisDAO;

    /**
     *@Param [mId]
     *@Return java.lang.String
     *@Description categorySelectAjax
     * 主要用于admin后台文章管理页面动态选择文章分类（Ajax二级联动）
     * 根据传入的mId参数来返回对应的所有二级分类cId及名称
     */
    @Override
    public String categorySelectAjax(Integer mId) {
        String categorystrs = "";
        List<Category> secondCats = articleMapper.selectSecondCatogoryByMid(mId);
        for(int i=0; i<secondCats.size(); i++){
            String item = secondCats.get(i).getSecondCategoryId().toString()+":"+secondCats.get(i).getSecondCategoryName();
            if(i<secondCats.size()-1){
                item+=";";
            }
            categorystrs+=item;
        }
        return categorystrs;
    }
    /**
     *@Param [cId, queryBean]
     *@Return com.flowingsun.article.vo.CategoryArticleQuery
     *@Description getCategoryArticles
     * 主要用于前台查看分类文章，主要根据cid查询除主体外文章的全部信息、分类信息、标签信息
     * CategoryArticleQuery 继承于PageQueryBean
     */
    @Override
    @MethodExcuteTimeLog
    public CategoryArticleQuery getCategoryArticles(Integer cId, CategoryArticleQuery queryBean) {
        Integer total = articleMapper.selectCategoryArticlesCount(cId);
        if(total!=null&&total>0){
            queryBean.setTotal(total);
            Integer pageSize = queryBean.getPageSize();
            Integer startNum = queryBean.getStartRow();
            List<Article> articleList = articleMapper.selectCategoryArticles(cId,startNum,pageSize);
            for(int i=0; i<articleList.size(); i++){
                Article article = articleList.get(i);
                //查询文章标签，并将标签信息放入bean中
                List<ArticleTag> articleTags = articleMapper.selectArticleTagsByPrimarykey(article.getId());
                article.setArticleTagList(articleTags);
            }
            queryBean.setDataList(articleList);
        }else{//对应cid下没有文章
            queryBean.setTotal(0);
        }
        return queryBean;
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/09/10 21:57
     *@Param [queryBean]
     *@Return com.flowingsun.article.vo.TagArticleQuery
     *@Description getTagArticles
     * 根据标签筛选该标签下所有文章，并分页返回前端显示
     */
    @Override
    public TagArticleQuery getTagArticles(TagArticleQuery queryBean) {
        Integer tagId = queryBean.getTagid();
        Integer total = articleMapper.selectTagCountByTagId(tagId);
        if(total!=null&&total>0){
            queryBean.setTotal(total);
            Integer pageSize = queryBean.getPageSize();
            Integer startNum = queryBean.getStartRow();
            List<Article> articleList = articleMapper.selectTagArticles(tagId,startNum,pageSize);
            for(Integer i=0; i<articleList.size(); i++){
                Article article = articleList.get(i);
                //查询文章标签，并将标签信息放入bean中
                List<ArticleTag> articleTags = articleMapper.selectArticleTagsByPrimarykey(article.getId());
                article.setArticleTagList(articleTags);
            }
            queryBean.setDataList(articleList);
        }else{
            //对应cid下没有文章
            queryBean.setTotal(0);
        }
        return queryBean;
    }

    /**
     *@Date 18/05/19 15:54
     *@Param [queryBean]
     *@Return com.flowingsun.admin.entity.AdminBlogQuery
     *@Description getAllArticles
     * 主要用于admin后台文章管理，主要根据页面查询参数查询除主体外文章的全部信息、分类信息、标签信息
     * AdminBlogQuery 继承于PageQueryBean，主要存放pageSize，cId，pageNum等页面查询参数信息。
     */
    @Override
    public AdminBlogQuery getAllArticles(AdminBlogQuery queryBean) {
        Integer total = 0;
        if(queryBean.getArticleCid()!=null&&queryBean.getArticleCid()!=0){
            total = articleMapper.selectCategoryArticlesCount(queryBean.getArticleCid());
        }else if(queryBean.getArticleCid()!=null&&queryBean.getArticleCid()==0&&queryBean.getArticleMid()!=null&&queryBean.getArticleMid()!=0){
            total = articleMapper.selectMainCategoryArticlesCount(queryBean.getArticleMid());
        }else {total = articleMapper.selectAllArticleCount();}
        if(total>0){
            queryBean.setTotal(total);
            List<Article> articleList = articleMapper.selectAllArticleByQueryCondition(queryBean);
            for(int i=0; i<articleList.size(); i++){
                Article article = articleList.get(i);
                Integer articleId = article.getId();
                //查询文章标签，并将标签信息放入bean中
                List<ArticleTag> articleTags = articleMapper.selectArticleTagsByPrimarykey(articleId);
                article.setArticleTagList(articleTags);
            }
            queryBean.setDataList(articleList);
        }
        return queryBean;

    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/09/10 21:59
     *@Param []
     *@Return java.util.List<com.flowingsun.article.entity.ArticleTag>
     *@Description selectAllTag
     * 获取所有文章标签，如果Redis开启服务则从Redis中获取，失败则从数据库获取。
     */
    @Override
    public List<ArticleTag> selectAllTag() {
        logger.info("\n----------------------正在尝试从redis获取文章标签...----------------------");
        List<ArticleTag> allTags = redisDAO.getList("allTags");
        if(allTags==null){
            logger.warn("\n----------------------从redis获取文章标签失败!----------------------");
            allTags = articleMapper.selectAllTag();
            String result = redisDAO.setList("allTags",allTags);
            logger.info("\n----------------------正在尝试将文章标签allTags放入redis缓存...----------------------\n结果:"+result);
        }else{
            logger.info("\n----------------------从redis获取文章标签成功!----------------------\n");
        }
        return allTags;
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/10/28 15:33
     *@Param []
     *@Return java.util.List<Infomation>
     *@Description 获取博客公共信息，如访客人数、文章总数、总评论数、总感谢数等
     */
    @Override
    public BlogInfo selectInfomation() {
        BlogInfo blogInfo = new BlogInfo();
        logger.info("\n----------------------正在尝试从redis获取博客公共信息...----------------------");
        String s1 = redisDAO.getString("articleCount");
        String s2 = redisDAO.getString("commentCount");
        String s3 = redisDAO.getString("thankCount");
        String s4 = redisDAO.getString("userCount");
        String s5 = redisDAO.getString("visitorCount");
        String s6 = redisDAO.getString("viewCount");
        logger.info("\n----------------------redis博客公共信息:----------------------\narticleCount："+s1+";commentCount："+s2+";thankCount："+s3+";userCount："+s4+";visitorCount："+s5+";viewCount："+s6);
        if(s1==null||s2==null||s3==null||s4==null||s5==null||s6==null){
            s1 = String.valueOf(articleMapper.selectAllArticleCount());
            s2 = String.valueOf(commentMapper.selectCommentCount());
            s3 = String.valueOf(thankMapper.selectThankCount());
            s4 = String.valueOf(userMapper.selectUserCount());
            s5 = String.valueOf(blogVisitorMapper.selectVisitorCount());
            s6 = String.valueOf(blogVisitorMapper.selectViewCount());
            redisDAO.setString("articleCount",s1);
            redisDAO.setString("commentCount",s2);
            redisDAO.setString("thankCount",s3);
            redisDAO.setString("userCount",s4);
            redisDAO.setString("visitorCount",s5);
            redisDAO.setString("viewCount",s6);
        }
        blogInfo.setArticleCount(s1);
        blogInfo.setCommentCount(s2);
        blogInfo.setThankCount(s3);
        blogInfo.setUserCount(s4);
        blogInfo.setVisitorCount(s5);
        blogInfo.setViewCount(s6);
        return blogInfo;
    }

    @Override
    public void updateAllTag() {
        List<ArticleTag> allTags = articleMapper.selectAllTag();
        if(allTags!=null&&allTags.size()>0)
            redisDAO.setList("allTags",allTags);
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/11/7 22:26
     *@Param [articleId]
     *@Return void
     *@Description deleteArticleRelations
     * 删除一篇文章的所有附属信息，包括文章感谢、评论、收藏，以及对评论区的点赞和讨论内容。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticleRelations(int articleId){
        thankMapper.deleteByArticleId(articleId);
        commentMapper.deleteByArticleId(articleId);
        collectionMapper.deleteByArticleId(articleId);
        List<Integer> commentidList = commentMapper.selectCommentsIdByArticleId(articleId);
        for(int i=0;i<commentidList.size();i++){
            commentLikeMapper.deleteByCommentId(commentidList.get(i));
            discussionMapper.deleteByCommentId(commentidList.get(i));
        }
    }


    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/19 15:39
     *@Param [articleId]
     *@Return java.lang.String
     *@Description deleteOneArticle
     * 主要用于admin后台文章管理，根据传入的文章id删除对应文章
     * 由于一篇文章id关联了评论和标签，故要删除文章，先将该文章附属的标签和评论删除，成功后再删除文章主体。
     * 【删除标签】：
     * 1.在article_tag_relation表中取出该文章articleId下的所有标签信息(tagId、tagName)放入列表中
     * 2.根据遍历列表中的tagId，查询该标签表中的数量。
     * 若数量>1，则证明有2篇以上的文章拥有该标签，故只需要在article_tag_relation表中删除标签关系即可;
     * 若数量=1，则证明该标签只在此文章中存在，故删除此标签关系的同时还需要在article_tag表中删除此标签。
     */
    @Override
    @MethodExcuteTimeLog
    @Transactional(rollbackFor = Exception.class)
    public String deleteOneArticle(Integer articleId) {
        try{
            //删除所有该文章标签
            deleteArticleAllTag(articleId);
            //删除该文章id下的所有评论、点赞、收藏信息(已经评论区的点赞和讨论内容)
            deleteArticleRelations(articleId);
            //后面可能添加的功能：给所有收藏此文章的人发送一封通知邮件
            if(1==articleMapper.deleteByPrimaryKey(articleId)){
                String s = String.valueOf(articleMapper.selectAllArticleCount());
                redisDAO.setString("articleCount",s);
                logger.info("deleteOneArticle():delete_success");
                return "delete_success";
            }
        }catch (Exception e){
            logger.error("deleteOneArticle失败:",e);
        }
        return "delete_fail";


    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/26 08:29
     *@Param [articleIdStr]
     *@Return java.lang.String
     *@Description 根据前台传进的articleId字符串，批量删除文章。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteBatchArticle (String articleIdStr){
        try{
            String[] idStrList = articleIdStr.split(",");
            Integer[] idIntList = new Integer[idStrList.length];
            for(Integer i=0;i<idStrList.length;i++){
                try {
                    idIntList[i] = Integer.parseInt(idStrList[i]);
                }catch (NumberFormatException e) {
                    logger.error("Integer.parseInt(idStrList[i])error:",e);
                }
            }
            for(Integer articleId : idIntList){
                deleteOneArticle(articleId);
            }
            updateAllTag();
            String s = String.valueOf(articleMapper.selectAllArticleCount());
            redisDAO.setString("articleCount",s);
            return "delete_batch_succ";
        }catch (Exception f){
            logger.error("deleteBatchArticle()方法Error：",f);
        }
        return "delete_batch_fail";
    }
    

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/14 09:41
     *@Param []
     *@Return java.util.List<com.flowingsun.article.entity.Category>
     *@Description getCategory
     * 主要用于前台，导航条上的文章分类标签。
     * 通过遍历查询所有主分类、二级分类，然后以List<Category>形式返回前台页面。
     */
    @Override
    public List<Category> getCategory(){
        logger.info("\n----------------------正在尝试从redis获取文章分类...----------------------");
        List<Category> categories = redisDAO.getList("categories");
        if(categories==null){
            logger.warn("\n----------------------从redis获取文章分类失败!----------------------");
            categories = articleMapper.selectMainCategory();
            for(int i=0;i<categories.size();i++){
                Integer mainId = categories.get(i).getMainCategoryId();
                List<Map<Integer,String>> secondCatsList = new ArrayList<Map<Integer,String>>();
                List<Category> secondCats = articleMapper.selectSecondCatogoryByMid(mainId);
                for(int j=0; j<secondCats.size(); j++){
                    Map<Integer,String>  secondCatMap = new HashMap<Integer,String>();
                    secondCatMap.put(secondCats.get(j).getSecondCategoryId(),secondCats.get(j).getSecondCategoryName());
                    secondCatsList.add(secondCatMap);
                }
                categories.get(i).setSecondCategorys(secondCatsList);
            }
            logger.info("\n----------------------正在尝试将所有文章主分类、二级分类信息放入Redis缓存...:----------------------");
            String result = redisDAO.setList("categories",categories);
            logger.info("\n----------------------尝试结果:----------------------"+result);
        }else {
            logger.info("\n----------------------从redis获取文章分类成功!----------------------\n");
        }
        return categories;
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/19 16:04
     *@Param []
     *@Return com.flowingsun.article.entity.Category
     *@Description getAllCategory
     * 主要用于admin后台文章管理、新增文章页,初始化分类选择框中的所有分类信息。
     */
    @Override
    public Category getAllCategory() {
        Category categorys = new Category();
        List<Category> mainCategorys = articleMapper.selectMainCategory();
        List<Map<Integer,String>> mainCatList = new ArrayList<Map<Integer,String>>();
        List<Map<Integer,String>> secondCatList = new ArrayList<Map<Integer,String>>();
        for(int i=0;i<mainCategorys.size();i++){
            Integer mId = mainCategorys.get(i).getMainCategoryId();
            Map<Integer,String>  mainCatMap = new HashMap<Integer,String>();
            mainCatMap.put(mId,mainCategorys.get(i).getMainCategoryName());
            mainCatList.add(mainCatMap);
            List<Category> secondCats = articleMapper.selectSecondCatogoryByMid(mId);
            for(int j=0; j<secondCats.size(); j++){
                Map<Integer,String>  secondCatMap = new HashMap<Integer,String>();
                secondCatMap.put(secondCats.get(j).getSecondCategoryId(),secondCats.get(j).getSecondCategoryName());
                secondCatList.add(secondCatMap);
            }

        }
        categorys.setMainCategorys(mainCatList);
        categorys.setSecondCategorys(secondCatList);
        return categorys;

    }

    /**
     *@Description getTagId
     * 根据标签名，从标签表中查询对应标签id
     */
    @Override
    public int getTagId(String tagName){
        return articleMapper.selectTagIdByTagName(tagName);
    }


    /**
     *@Description getArticle
     * 主要用于前台，单篇文章查看。根据文章id查询对应文章的标题、摘要、文章主体内容、标签等详细信息。
     */
    @Override
    @MethodExcuteTimeLog
    public Article getArticle(Integer id){
        Article article = articleMapper.selectByPrimaryKey(id);
        try{
            if(article.getArticleTitle()!=null){
                //中文字数统计
                int countChinese = InfoCountUtils.countChinese(article.getArticleContent());
                article.setCharacterCount(countChinese);
                //查询文章主分类、二级分类名称
                String mname = articleMapper.selectMainCategoryNameById(article.getArticleMainId());
                String cname = articleMapper.selectSecondCategoryNameById(article.getArticleSecondId());
                article.setMainCategoryName(mname);
                article.setSecondCategoryName(cname);
                //查询文章标签，并将标签信息放入bean中
                List<ArticleTag> articleTags = articleMapper.selectArticleTagsByPrimarykey(id);
                article.setArticleTagList(articleTags);
                //查询文章所有评论已经评论下面的所有讨论，汇总放入bean中
                //其中评论区默认按评论点赞次数降序排序：selectCommentByCommentLikeCountDesc
                //按时间排序可以用：List<Comment> commentList = commentMapper.selectAllCommentByArticleid(id);
                List<Comment> commentList = commentMapper.selectCommentByCommentLikeCountDesc(id);
                article.setArticleCommentList(commentList);
            }
        }catch (Exception e){
            logger.error("getArticle(Integer id)方法Error：",e);
        }
        return article;
    }
    //准备删除的方法
//    @Override
//    public ArticleInfo getArticleInfo(Integer articleId){
//        ArticleInfo articleInfo = articleMapper.selectInfoByPrimaryKey(articleId);
//        if(articleInfo.getArticleTitle()!=null){
//            articleInfo.setArticleTags(articleMapper.selectTagsNameByPrimarykey(articleId));
//        }
//        return articleInfo;
//    }


    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/19 16:08
     *@Param [article]
     *@Return java.lang.String
     *@Description createArticle
     * 主要用于后台文章管理——新增博客文章
     * * 前提：在Mysql中insert默认返回值为int型数字，数字大小等于插入成功的记录数，故flag==0时表示插入失败,flag==1时表示成功插入1条记录。
     * 1.新增文章insertNewArticle()方法：
     * 在ArticleMapper.xml中设置了：useGeneratedKeys="true" keyProperty="id"
     * 所以返回插入成功的记录主键id(这个id即为后面需要用到的articleId)，但是！！！需要注意的是：这个主键id并不是以int型的数字形式直接返回的，也不能直接传递给flag。
     * 而是直接映射到了Article这个对象的bean实体中id属性上去了，所以取这个id只能通过int articleId = article.getId();
     * 2.新增标签insertNewTag()方法：
     * 为了防止主键重复异常报错，加了"IGNORE"关键字。这样当出现主键重复异常时不报异常而返回0。
     * <insert id="insertNewArticle"  parameterType="com.flowingsun.article.entity.Article" >
     *INSERT IGNORE INTO article (id,userid, article_title,......
     */
    @Override
    public String createArticle(Article article){
        //此处默认的用户id为2，即目前只有管理员可以写文章
        article.setUserid(2);
        Timestamp dateTime = new Timestamp(new Date().getTime());
        article.setCreateDate(dateTime);
        article.setEditDate(dateTime);
        String[] taglist = article.getArticleTags().split(",");
        if(articleMapper.insertNewArticle(article)!=0){
            //！=0表示文章插入成功，插入成功后文章主键id可通过getter直接获取。
            int articleId = article.getId();
            int status = 0;
            for (String tag : taglist){
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagName(tag);
                articleTag.setCreateDate(dateTime);
                int tagId=0;
                if (articleMapper.insertNewTag(articleTag)==0){
                    //表示article_tag表中已存在此标签或新标签创建失败,则需要根据标签来名查询标签id
                    tagId = articleMapper.selectTagIdByTagName(tag);
                }else{
                    //表示标签已成功创建并插入article_tag表中(插入同时返回标签主键id，直接getter可取出标签id）
                    tagId = articleTag.getTagId();
                }
                if(tagId!=0){
                    //将标签、文章id、标签id、时间四个键值插入article_tag_ralation表中
                    articleTag.setArticleId(articleId);
                    articleTag.setTagName(tag);
                    articleTag.setTagId(tagId);
                    articleTag.setCreateDate(dateTime);
                    if(articleMapper.insertTagRelation(articleTag)==0)
                        status = 1;
                }
            }
            if(status==0){
                updateAllTag();
                String s = String.valueOf(articleMapper.selectAllArticleCount());
                redisDAO.setString("articleCount",s);
                return "article_write_succ";
            }
            return "article_write_fail";
        }
        return "article_write_fail";
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/21 19:23
     *@Param [article]
     *@Return java.lang.String
     *@Description editArticle
     * 【文章修改流程】
     * 1.后台admin/home文章管理页：点击【编辑】→
     * 2.文章修改页：编辑修改好全部内容点击【提交修改】→
     * 3.后台AdminController处理请求：
     *      a.准备SQL更改字段
     *          article_title,article_abstract,
     *          article_content,edit_date,article_second_id,
     *          article_main_id,article_content_copy
     *      b.用事务执行更改操作
     *      c.将更改结果返回前端 →
     * 4.前台Ajax接收后端传来的消息。
     *      若更改成功，则跳转到新的文章页面
     *      若失败，则提示失败信息，保留当前页面。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String editArticle(Article article) {
        try{
            Integer articleId = article.getId();
            Timestamp dateTime = new Timestamp(new Date().getTime());
            article.setEditDate(dateTime);
            List<ArticleTag> tags = articleMapper.selectArticleTagsByPrimarykey(articleId);
            //清除该文章之前创建的所有标签
            for(int i=0; i<tags.size(); i++){
                ArticleTag tag = tags.get(i);
                if(1==articleMapper.deleteTagRelation(articleId, tag.getTagId())&&0==articleMapper.selectTagCountByTagId(tag.getTagId())){
                    articleMapper.deleteTagByTagId(tag.getTagId());
                }
            }
            //给该文章逐个添加新标签
            String[] taglist = article.getArticleTags().split(",");
            for (String tag : taglist){
                ArticleTag articletag = new ArticleTag();
                articletag.setTagName(tag);
                articletag.setCreateDate(dateTime);
                int tagId=0;
                if (articleMapper.insertNewTag(articletag)==0){
                    tagId = articleMapper.selectTagIdByTagName(tag);
                }
                else{
                    tagId = articletag.getTagId();
                }
                //将标签、文章id、标签id、时间四个键值插入article_tag_ralation表中
                articletag.setArticleId(articleId);
                articletag.setTagName(tag);
                articletag.setTagId(tagId);
                articletag.setCreateDate(dateTime);
                if(articleMapper.insertTagRelation(articletag)==1){
                    logger.info("\n--------------------------------------给文章添加标签:" + tag + "成功！--------------------------------------\n");
                }
            }
            //插入更新后的文章
            if(1==articleMapper.updateByPrimaryKeySelective(article)){
                updateAllTag();
                return "edit_blog_success";
            }
        }catch (Exception e){
            logger.error("editArticle(Article article)方法执行Error：",e);
        }
        return "edit_blog_fail";
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/24 09:25
     *@Param [articles]
     *@Return java.lang.String
     *@Description changeArticleCategory
     * 更改文章分类
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String changeArticleCategory(Category articles) {
        String[] idStrList = articles.getArticleIdStr().split(",");
        Integer[] idIntList = new Integer[idStrList.length];
        for(Integer i=0;i<idStrList.length;i++){
            try {
                idIntList[i] = Integer.parseInt(idStrList[i]);
            }catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        articles.setIdIntList(idIntList);
        Integer result = articleMapper.changeArticleCategoryById(articles);
        if(result==idStrList.length){return "changeCategory_succ";}
        return "changeCategory_fail";

    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/26 17:48
     *@Param [tagBean]
     *@Return java.lang.String
     *@Description resetArticlesTag
     * 重置某篇文章的标签tag，需要的参数：articleId，tags。
     */
    @Override
    public String resetArticleTag(ArticleTag tagBean){
        try{
            Integer articleId = tagBean.getArticleId();
            String tagStrs = tagBean.getArticleTagsStr();
            String[] tagList = tagStrs.split(",");
            List<String> newTags = new ArrayList(Arrays.asList(tagList));
            List<String> oldTags = articleMapper.selectTagsNameByPrimarykey(articleId);
            //由于有的文章没有标签故此条select语句会抛出异常？？？？？？？？！！！！！！！！但是奇怪的是并不会抛异常，mybatis封装的oldTags即使是空
            //也不会抛异常，而且随后到来的for()循环遍历也不用异常捕捉，oldTags为空会自动跳过......
            for(int i=0; i<oldTags.size(); i++){
                String oldTag = oldTags.get(i);
                if(newTags.contains(oldTag)){
                    newTags.remove(oldTag);
                }else{
                    //else表示需要删除此条标签关系
                    if(0==deleteArticleOneTag(articleId, oldTag)){
                        return "reset_tag_fail";
                    }
                }
            }

            for(int j=0; j<newTags.size(); j++){
                String newTag = newTags.get(j);
                Integer tagId=0;
                if(checkTagExist(newTag)==true){
                    tagId = articleMapper.selectTagIdByTagName(newTag);
                }else{
                    tagId = createOneTag(newTag);
                }
                if(tagId!=0&&(0==addArticleTagQuick(articleId, tagId, newTag))){
                    return "reset_tag_fail";
                }else {return "reset_tag_fail";}
            }
            updateAllTag();
            return "reset_tag_succ";
        }catch (Exception e){
            logger.error("resetArticleTag(ArticleTag tagBean)执行Error：",e);
            return "reset_tag_fail";
        }
    }
    
    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/25 15:09
     *@Param [tagBean]
     *@Return java.lang.String
     *@Description  batchResetArticleTag
     *
     * 【批量更改文章标签】
     *
     * 一篇文章可以有1~n个标签。每个标签id和tagName都是唯一的。数据库中标签相关的有2张表：
     *tag表：存id和tagName
     *article_tag_relation表：存文章和标签的对应关系
     *a.插入一条标签
     *给文章新增标签时会根据标签名字查tag表中有无同名标签：
     *有则证明之前存在此标签，故只许在relation表中插入标签关系即可；
     *无则证明此标签为全新的，需要先在tag表中新建标签，然后再插入relation表中。
     *b.删除一条标签
     *先直接在relation表中删除此条标签关系，然后查此条标签在relation表中的记录数，若记录数>0，则pass；
     *若记录数=0，证明此标签关系是数据库中此标签的最后一条关系(即其他文章都没有用此标签)，
     *所以删除了此条标签关系后，还需要在tag表中删除此标签。
     *
     *【标签更改流程】
     *更改文章标签 → 提交修改 → 后台执行判断逻辑 → 完成新标签插入和旧标签删除 → 流程结束
     *对于提交上去的单个标签来说，结局就2种：
     *a.原文章存在此标签，故无需修改;
     *b.原文章没有此标签，需要给文章打上此标签。
     *
     *但是对于后台，可以用不同的逻辑来处理整个流程:
     *(原文章标签组oldTags，用户提交上去的标签组newTags。)
     *1.for(oldtag:oldTags){}遍历原文章标签组
     *若oldtag在newTags中存在，则证明用户保留了此标签，故无需改动，此时在newTags中删除此标签，表示无需进一步操作；
     *若oldtag在newTags中不存在，则用户删除了此标签，故需要同步进行删除操作。
     *遍历原文章标签组后，在newTags中剩下的都是需要执行插入操作的标签，故再for(newtag:newTags){}遍历一次newTags进行逐个插入即可。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String batchResetArticleTag(ArticleTag tagBean) {
        //idStrList装的是前端传来的String行文章id列表;articleIdList是经过转换的int行id列表；tagList装的是前端传来的tagName列表。
        try{
            String idStrs = tagBean.getArticleIdStr();
            String tagStrs = tagBean.getArticleTagsStr();
            String[] idStrList = idStrs.split(",");
            String[] tagList = tagStrs.split(",");
            Integer[] idIntList = new Integer[idStrList.length];
            List<String> TagsList = new ArrayList(Arrays.asList(tagList));
            for(Integer i=0;i<idStrList.length;i++){
                try {
                    idIntList[i] = Integer.parseInt(idStrList[i]);
                }catch (NumberFormatException e) {
                    logger.error("batchResetArticleTag()>>Integer.parseInt(idStrList[i])方法执行Error：",e);
                }
            }

            for(Integer articleId : idIntList){
                List<String> newTags = new ArrayList<String>();
                for(String item : TagsList){
                    newTags.add(item);
                }
                List<String> oldTags = articleMapper.selectTagsNameByPrimarykey(articleId);
                //由于有的文章没有标签故此条select语句会抛出异常？？？？？？？？！！！！！！！！但是奇怪的是并不会抛异常，mybatis封装的oldTags即使是空
                //也不会抛异常，而且随后到来的for()循环遍历也不用异常捕捉，oldTags为空会自动跳过......
                for(int i=0; i<oldTags.size(); i++){
                    String oldTag = oldTags.get(i);
                    if(newTags.contains(oldTag)){
                        newTags.remove(oldTag);
                    }else{//else表示需要删除此条标签关系
                        if(0==deleteArticleOneTag(articleId, oldTag)){ return "batchReset_tags_fail"; }
                    }
                }
                for(int j=0; j<newTags.size(); j++){
                    String newTag = newTags.get(j);
                    Integer tagId=0;
                    if(checkTagExist(newTag)==true){
                        tagId = articleMapper.selectTagIdByTagName(newTag);
                    }else{
                        tagId = createOneTag(newTag);
                    }
                    if(tagId!=0&&(0==addArticleTagQuick(articleId, tagId, newTag))){
                        return "batchReset_tags_fail";
                    }else {return "batchReset_tags_fail";}
                }
            }
            updateAllTag();
            return "batchReset_tags_succ";
        }catch (Exception f){
            logger.error("batchResetArticleTag(ArticleTag tagBean)方法执行Error：",f);
            return "batchReset_tags_fail";
        }
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/26 22:33
     *@Param [articleId, tagName]
     *@Return java.lang.Integer
     *@Description addArticleTagQuick
     * 【快速给文章添加标签】
     * 此版本为quick版，和addArticleTag的区别在于：
     * addArticleTag是用于某篇单独的文章新增标签时使用，故需要判断标签是否存在是否需要先新建标签再进行插入。
     * addArticleTagQuick则省略了判断的步骤，直接进行标签插入工作，用于批量给文章添加标签。
     * 不过在批量添加的程序逻辑中需要添加判断标签是否存在的步骤(在tag表中)，否则可能会引起异常。
     */
    @Override
    public Integer addArticleTagQuick(Integer articleId, String tagName){
        Integer tagRelationId = 0;
        Integer tagId = articleMapper.selectTagIdByTagName(tagName);
        if (checkTagRelationExist(articleId, tagId) == false) {
            tagRelationId = createOneTagRelation(articleId, tagId, tagName);
        } else {
            tagRelationId = 1;
        }
        updateAllTag();
        return tagRelationId;
    }


    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/26 22:33
     *@Param [articleId, tagId, tagName]
     *@Return java.lang.Integer
     *@Description 此版本为quick版，和addArticleTag的区别在于：
     * addArticleTag是用于某篇单独的文章新增标签时使用，故需要判断标签是否存在是否需要先新建标签再进行插入。
     * addArticleTagQuick则省略了判断的步骤，直接进行标签插入工作，用于批量给文章添加标签。
     * 不过在批量添加的程序逻辑中需要添加判断标签是否存在的步骤(在tag表中)，否则可能会引起异常。
     */
    @Override
    public Integer addArticleTagQuick(Integer articleId, Integer tagId, String tagName){
        int tagRelationId = 0;
        if (checkTagRelationExist(articleId, tagId) == false) {
            tagRelationId = createOneTagRelation(articleId, tagId, tagName);
        } else {
            tagRelationId = 1;
        }
        updateAllTag();
        return tagRelationId;
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/26 19:03
     *@Param [articleId, tagName]
     *@Return java.lang.Integer
     *@Description addArticleTag
     *
     * 给单个文章添加单个标签
     * 判断逻辑只有2点：
     * 1.先判断此标签是否在tag表中存在，若不存在，则证明此标签为全新标签，直接插入文章-标签关系表即可；
     * 若存在，则进行第二步判断；
     * 2.判断该文章是否创建过此标签，若是，则直接pass；若否，则在文章-标签关系表中创建此条记录
     *
     * 注意：
     * 此处用Integer返回值，返回0代表新增失败；返回1代表原先存在此标签无需新增；
     * 返回其他数字则代表新增成功，数字值即为新tagRelationId
     */
    public Integer addArticleTag(Integer articleId, String tagName){
        Integer tagId;
        Integer tagRelationId = 0;
        if (checkTagExist(tagName) == false) {
            tagId = createOneTag(tagName);
        } else {
            tagId = articleMapper.selectTagIdByTagName(tagName);
        }
        if (tagId != 0) {
            if (checkTagRelationExist(articleId, tagId) == false) {
                tagRelationId = createOneTagRelation(articleId, tagId, tagName);
            } else {
                tagRelationId = 1;
            }
        }
        updateAllTag();
        return tagRelationId;
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/26 09:17
     *@Param [tagBean]
     *@Return java.lang.String
     *@Description  batchAddArticleTag
     *
     * 给选中的文章批量新增一组标签(1~n个)，每个标签对于一篇文章只有2种可能：
     * 1.文章已持有该标签，故直接pass
     * 2.文章没有此标签，需要添加此标签。
     * 流程为：遍历标签列表 → 查看tag表中是否存在该标签，不存在则新增→用该标签遍历文章表(无则添加、有则pass)直到遍历完所有文章 → 标签遍历结束
     */
    @Override
    public String batchAddArticleTag(ArticleTag tagBean) {
        //idStrList装的是前端传来的String行文章id列表;articleIdList是经过转换的int行id列表；tagList装的是前端传来的tagName列表。
        try{
            String idStrs = tagBean.getArticleIdStr();
            String tagStrs = tagBean.getArticleTagsStr();
            String[] idStrList = idStrs.split(",");
            String[] tagList = tagStrs.split(",");
            Integer[] idIntList = new Integer[idStrList.length];
            List<String> tagl = Arrays.asList(tagList);
            List<String> TagsList = new ArrayList(tagl);
            for(Integer i=0;i<idStrList.length;i++){
                try {
                    idIntList[i] = Integer.parseInt(idStrList[i]);
                }catch (NumberFormatException e) {
                    logger.error("batchAddArticleTag()>>Integer.parseInt(idStrList[i])方法执行Error：",e);
                }
            }
            for(String tag : TagsList){
                //先判断tag表中是否有此tag，无则创建。通过tagName查tagId。
                // 若tagId==0表示没有此tag(此tag为新tag),数据库中没有，需要创建；!=0表示数据库中有此tag无需创建。
                Integer tagId=0;
                if(checkTagExist(tag)==true){
                    tagId = articleMapper.selectTagIdByTagName(tag);
                    for(Integer articleId : idIntList){
                        if(checkTagRelationExist(articleId,tagId)==false && 0==createOneTagRelation(articleId,tagId,tag))
                            return "batch_addTag_fail";
                    }
                }else{
                    tagId = createOneTag(tag);
                    for(Integer articleId : idIntList){
                        if(0==createOneTagRelation(articleId,tagId,tag))
                            return "batch_addTag_fail";
                    }
                }
            }
            updateAllTag();
            return "batch_addTag_succ";
        }catch (Exception f){
            logger.error("batchAddArticleTag(ArticleTag tagBean)方法执行Error：",f);
            return "batch_addTag_fail";
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteArticleAllTag(ArticleTag tagBean) {
        try{
            Integer articleId = tagBean.getArticleId();
            List<String> tagList = articleMapper.selectTagsNameByPrimarykey(articleId);
            for(String tag : tagList){
                if(deleteArticleOneTag(articleId,tag)==FAIL){
                    return "delete_allTag_fail";}
            }
            updateAllTag();
            return "delete_allTag_succ";
        }catch (Exception e){
            logger.error("deleteArticleAllTag(ArticleTag tagBean)方法执行Error：",e);
            return "delete_allTag_fail";
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteArticleAllTag(Integer articleId) {
        try{
            List<String> tagList = articleMapper.selectTagsNameByPrimarykey(articleId);
            if(tagList.size()>0){
                for(String tag : tagList){
                    if(deleteArticleOneTag(articleId,tag)==FAIL){
                        return FAIL;
                    }
                }
                updateAllTag();
            }
            return SUCCESS;
        }catch (Exception e){
            logger.error("deleteArticleAllTag(Integer articleId)方法执行Error：",e);
            return FAIL;
        }
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/28 09:28
     *@Param [tagBean]
     *@Return java.lang.String
     *@Description batchDeleteArticleTag
     * 批量删除标签，接收的参数主要有2个：
     * 1.需要删除的标签（1~多个） 2.需要批量删除的文章id(1~多个)
     */
    @Override
    public String batchDeleteArticleTag(ArticleTag tagBean) {
        String[] idStrList = tagBean.getArticleIdStr().split(",");
        String[] tagList = tagBean.getArticleTagsStr().split(",");
        List<String> TagsList = new ArrayList(Arrays.asList(tagList));
        Integer[] idIntList = changeListFormatUtils.str2intList(idStrList);
        for(String tag : TagsList){
            //先判断tag表中是否有此tag，若checkTagExist(tag)==false表示没有此tag(此tag为新tag),数据库中没有，无需删除。
            //若checkTagExist(tag)==true,表示数据库中有此tag,对所选文章逐个删除。
            if(checkTagExist(tag)==true){
                for(Integer articleId : idIntList){
                    if(0==deleteArticleOneTag(articleId, tag))
                        return "batch_deleteTag_fail";
                }
            }
        }
        updateAllTag();
        return "batch_deleteTag_succ";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String batchDeleteArticleAllTag(ArticleTag tagBean) {
        try{
            String idStrs = tagBean.getArticleIdStr();
            String[] idStrList = idStrs.split(",");
            Integer[] idIntList = new Integer[idStrList.length];
            for(Integer i=0;i<idStrList.length;i++){
                try {
                    idIntList[i] = Integer.parseInt(idStrList[i]);
                }catch (NumberFormatException e) {
                    logger.error("batchDeleteArticleAllTag()>>Integer.parseInt(idStrList[i])方法执行Error：",e);
                }
            }
            for(Integer articleId : idIntList){
                if(deleteArticleAllTag(articleId)==0){
                    return "batch_deleteAllTag_fail";
                }
            }
            updateAllTag();
            return "batch_deleteAllTag_succ";
        }catch (Exception f){
            logger.error("batchDeleteArticleAllTag(ArticleTag tagBean)方法执行Error：",f);
            return "batch_deleteAllTag_fail";
        }
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/09/10 22:18
     *@Param [articleId]
     *@Return com.flowingsun.article.entity.RegularRecommend
     *@Description getRegularRecommendArticle
     * 浏览文章时,上一篇下一篇推荐
     */
    @Override
    public RegularRecommend getRegularRecommendArticle(Integer articleId) {
        RegularRecommend recommend = new RegularRecommend();
        recommend.setArticleId(articleId);
        RegularRecommend result1 = articleMapper.selectPreviousArticle(recommend);
        if(result1!=null){
            recommend.setPreviousArticleId(result1.getPreviousArticleId());
            recommend.setPreviousTitle(result1.getPreviousTitle());
        }else{
            recommend.setPreviousArticleId(0);
            recommend.setPreviousTitle("亲,这是第一篇文章！");
        }
        RegularRecommend result2 = articleMapper.selectNextArticle(recommend);
        if(result2!=null){
            recommend.setNextArticleId(result2.getNextArticleId());
            recommend.setNextTitle(result2.getNextTitle());
        }else{
            recommend.setNextArticleId(0);
            recommend.setNextTitle("亲,这是最后一篇文章！");
        }

        return recommend;
    }


    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/28 15:58
     *@Description deleteArticleOneTag
     * 删除某篇文章下某个标签，若文章有此标签则删除成功返回1，失败返回0；
     * 若文章本身没有此标签，则直接返回1.
     */
    @Override
    public Integer deleteArticleOneTag(Integer articleId,String tagName) {
        try{
            Integer tagId = articleMapper.selectTagIdByTagName(tagName);
            if (checkTagRelationExist(articleId, tagId)==true){
                if(SUCCESS==deleteOneTagRelation(articleId,tagId)){
                    if(0==articleMapper.selectTagCountByTagId(tagId)){
                        return deleteOneTag(tagId);
                    }
                }else {return FAIL;}
            }
            updateAllTag();
            return SUCCESS;
        }catch (Exception e){
            logger.error("deleteArticleOneTag(Integer articleId,String tagName)执行Error：",e);
            return FAIL;
        }
    }

    /**
     *@Description deleteOneTagRelation
     * 删除单篇文章下某个tag，参数为Integer articleId, String tagName；
     * 返回值为Integer型：1表示删除成功；0表示删除失败
     */
    @Override
    public Integer deleteOneTagRelation(Integer articleId, Integer tagId) {
        return articleMapper.deleteTagRelation(articleId,tagId);
    }


    /**
     *@Description deleteOneTag
     * 删除tag表中某个tagId下的标签。
     */
    @Override
    public Integer deleteOneTag(Integer tagId) {
        Integer flag = 0;
        flag = articleMapper.deleteTagByTagId(tagId);
        if(1==flag){
            updateAllTag();
        }
        return flag;
    }

    /**
     *@Description createOneTag
     *在tag表中创建一条新标签
     *成功则返回article_tag表中的自增主键id(tagId)；失败返回0
     */
    @Override
    public Integer createOneTag(ArticleTag tagBean) {
        if(1==articleMapper.insertNewTag(tagBean)){
            updateAllTag();
            return tagBean.getTagId();
        }
        return FAIL;
    }

    /**
     *@Description createOneTag
     * 在tag表中创建一条新标签
     *成功则返回article_tag表中的自增主键id(tagId)；失败返回0
     */
    @Override
    public Integer createOneTag(String tagName) {
        ArticleTag tagBean = new ArticleTag();
        Date date = new Date();
        Timestamp dateTime = new Timestamp(date.getTime());
        tagBean.setCreateDate(dateTime);
        tagBean.setTagName(tagName);
        if(1==articleMapper.insertNewTag(tagBean)){
            updateAllTag();
            return tagBean.getTagId();
        }
        return FAIL;
    }

    /**
     *@Description createOneTagRelation
     *给文章打标签，即在article_tag_relation表中，创建一条文章-标签关系。
     *成功则返回article_tag_relation表中的自增主键id；失败返回0
     */
    @Override
    public Integer createOneTagRelation(ArticleTag tagBean) {
        return articleMapper.insertTagRelation(tagBean);
    }

    /**
     *@Description createOneTagRelation
     *方法同上
     */
    @Override
    public Integer createOneTagRelation(Integer articleId, Integer tagId, String tagName) {
        ArticleTag tagBean = new ArticleTag();
        Date date = new Date();
        Timestamp dateTime = new Timestamp(date.getTime());
        tagBean.setCreateDate(dateTime);
        tagBean.setArticleId(articleId);
        tagBean.setTagName(tagName);
        tagBean.setTagId(tagId);
        return articleMapper.insertTagRelation(tagBean);
    }

    /**
     *@Description checkTagExist
     *根据TagName检测标签名是否存在
     */
    @Override
    public boolean checkTagExist(String tagName) {
        if(0!=articleMapper.selectTagIdByTagName(tagName)){
            return true;
        }
        return false;
    }

    /**
     *@Description checkTagRelationExist
     *检测某文章是否包含某标签
     */
    @Override
    public boolean checkTagRelationExist(Integer articleId, Integer tagId) {
        if(0!=articleMapper.selectTagRelationByTagIdArticleId(articleId,tagId)){
            return true;
        }
        return false;
    }

    /**
     *@Description checkArticleExist
     *检测articleId下的文章是否存在
     */
    @Override
    public boolean checkArticleExist(Integer articleId) {
        boolean flag = false;
        if(articleMapper.selectArticleCountById(articleId)==1){
            flag = true;
        }
        return flag;
    }


}

