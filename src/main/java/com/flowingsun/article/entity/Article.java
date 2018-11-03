package com.flowingsun.article.entity;

import com.flowingsun.behavior.entity.BehaviorStatus;
import com.flowingsun.behavior.entity.Comment;

import java.util.Date;
import java.util.List;

public class Article {
    private Integer id;

    private Integer userid;

    private String articleTitle;

    private String articleAbstract;

    private Date createDate;

    private Date editDate;

    private Integer articleSecondId;

    private Integer articleMainId;

    private String mainCategoryName;

    private String secondCategoryName;

    private Integer articleComment;

    private Integer articleThank;

    private Integer articleCollection;

    private String articleContent;

    private String articleContentCopy;

    private Integer characterCount;

    private String articleTags;

    private ArticleTag articleTagBean;

    private List<ArticleTag> articleTagList;

    private List<Comment> articleCommentList;

    private RegularRecommend regularRecommend;

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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", userid=" + userid +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleAbstract='" + articleAbstract + '\'' +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                ", articleSecondId=" + articleSecondId +
                ", articleMainId=" + articleMainId +
                ", mainCategoryName='" + mainCategoryName + '\'' +
                ", secondCategoryName='" + secondCategoryName + '\'' +
                ", articleComment=" + articleComment +
                ", articleThank=" + articleThank +
                ", articleCollection=" + articleCollection +
                ", characterCount=" + characterCount +
                ", articleTags='" + articleTags + '\'' +
                ", articleTagBean=" + articleTagBean +
                ", articleTagList=" + articleTagList +
                ", articleCommentList=" + articleCommentList +
                ", regularRecommend=" + regularRecommend +
                ", behaviorStatus=" + behaviorStatus +
                '}';
    }
}