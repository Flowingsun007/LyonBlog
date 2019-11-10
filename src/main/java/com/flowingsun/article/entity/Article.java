package com.flowingsun.article.entity;

import com.flowingsun.article.dto.ArticleTag;
import com.flowingsun.article.dto.RegularRecommend;
import com.flowingsun.behavior.dto.BehaviorStatus;
import com.flowingsun.behavior.entity.Comment;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Article implements Serializable {
    public Article(){
        this.createDate = new Timestamp(new Date().getTime());
    }
    /**
     * 文章id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userid;
    /**
     * 用户id
     */
    private String userName;
    /**
     * 文章标题
     */
    private String articleTitle;
    /**
     * 文章摘要
     */
    private String articleAbstract;
    /**
     * 文章创建日期
     */
    private Date createDate;
    /**
     * 文章编辑日期
     */
    private Date editDate;
    /**
     * 文章二级分类id
     */
    private Integer articleSecondId;
    /**
     * 文章主分类id
     */
    private Integer articleMainId;
    /**
     * 主分类名称
     */
    private String mainCategoryName;
    /**
     * 二级分类名称
     */
    private String secondCategoryName;
    /**
     * 文章评论
     */
    private Integer articleComment;
    /**
     * 文章感谢
     */
    private Integer articleThank;
    /**
     * 文章收藏
     */
    private Integer articleCollection;
    /**
     * 文章内容主体
     */
    private String articleContent;
    /**
     * 文章内容备份
     */
    private String articleContentCopy;
    /**
     * 文章访问权限(0表示仅自己可见，1表示公开可见)
     */
    private Integer publicPermission;
    /**
     * 文章字数统计
     */
    private Integer characterCount;
    /**
     * 文章标签名
     */
    private String articleTags;
    /**
     * 文章标签实体
     */
    private ArticleTag articleTagBean;
    /**
     * 文章标签列表
     */
    private List<ArticleTag> articleTagList;
    /**
     * 文章评论列表
     */
    private List<Comment> articleCommentList;
    /**
     * 文章推荐：【上一页】、【下一页】
     */
    private RegularRecommend regularRecommend;
    /**
     * 用户文章行为
     */
    private BehaviorStatus behaviorStatus;

    public BehaviorStatus getBehaviorStatus() {
        return behaviorStatus;
    }

    public void setBehaviorStatus(BehaviorStatus behaviorStatus) {
        this.behaviorStatus = behaviorStatus;
    }

    public List<Comment> getArticleCommentList() {
        return articleCommentList;
    }

    public void setArticleCommentList(List<Comment> articleCommentList) {
        this.articleCommentList = articleCommentList;
    }

    public RegularRecommend getRegularRecommend() {
        return regularRecommend;
    }

    public void setRegularRecommend(RegularRecommend regularRecommend) {
        this.regularRecommend = regularRecommend;
    }

    public List<ArticleTag> getArticleTagList() {
        return articleTagList;
    }

    public void setArticleTagList(List<ArticleTag> articleTagList) {
        this.articleTagList = articleTagList;
    }

    public String getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(String articleTags) {
        this.articleTags = articleTags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public Integer getArticleSecondId() {
        return articleSecondId;
    }

    public void setArticleSecondId(Integer articleSecondId) {
        this.articleSecondId = articleSecondId;
    }

    public Integer getArticleMainId() {
        return articleMainId;
    }

    public void setArticleMainId(Integer articleMainId) {
        this.articleMainId = articleMainId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName;
    }

    public Integer getArticleComment() {
        return articleComment;
    }

    public void setArticleComment(Integer articleComment) {
        this.articleComment = articleComment;
    }

    public Integer getArticleThank() {
        return articleThank;
    }

    public void setArticleThank(Integer articleThank) {
        this.articleThank = articleThank;
    }

    public Integer getArticleCollection() {
        return articleCollection;
    }

    public void setArticleCollection(Integer articleCollection) {
        this.articleCollection = articleCollection;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleContentCopy() {
        return articleContentCopy;
    }

    public void setArticleContentCopy(String articleContentCopy) {
        this.articleContentCopy = articleContentCopy;
    }

    public Integer getPublicPermission() {
        return publicPermission;
    }

    public void setPublicPermission(Integer publicPermission) {
        this.publicPermission = publicPermission;
    }

    public Integer getCharacterCount() {
        return characterCount;
    }

    public void setCharacterCount(Integer characterCount) {
        this.characterCount = characterCount;
    }

    public ArticleTag getArticleTagBean() {
        return articleTagBean;
    }

    public void setArticleTagBean(ArticleTag articleTagBean) {
        this.articleTagBean = articleTagBean;
    }
    
}
