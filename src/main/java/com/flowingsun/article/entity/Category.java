package com.flowingsun.article.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Category implements Serializable{
    private Integer articleId;
    private Integer mainCategoryId;
    private Integer secondCategoryId;
    private String mainCategoryName;
    private String secondCategoryName;
    private Map<Integer,String> mainCategoryMap;
    private Map<Integer,String> secondCategoryMap;
    private List<Map<Integer,String>> mainCategorys;
    private List<Map<Integer,String>> secondCategorys;
    private String articleIdStr;
    private Integer[] idIntList;

    public Integer[] getIdIntList() {
        return idIntList;
    }

    public void setIdIntList(Integer[] idIntList) {
        this.idIntList = idIntList;
    }

    public String getArticleIdStr() {
        return articleIdStr;
    }

    public void setArticleIdStr(String articleIdStr) {
        this.articleIdStr = articleIdStr;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Integer mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public Integer getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Integer secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
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

    public Map<Integer, String> getMainCategoryMap() {
        return mainCategoryMap;
    }

    public void setMainCategoryMap(Map<Integer, String> mainCategoryMap) {
        this.mainCategoryMap = mainCategoryMap;
    }

    public Map<Integer, String> getSecondCategoryMap() {
        return secondCategoryMap;
    }

    public void setSecondCategoryMap(Map<Integer, String> secondCategoryMap) {
        this.secondCategoryMap = secondCategoryMap;
    }

    public List<Map<Integer, String>> getMainCategorys() {
        return mainCategorys;
    }

    public void setMainCategorys(List<Map<Integer, String>> mainCategorys) {
        this.mainCategorys = mainCategorys;
    }

    public List<Map<Integer, String>> getSecondCategorys() {
        return secondCategorys;
    }

    public void setSecondCategorys(List<Map<Integer, String>> secondCategorys) {
        this.secondCategorys = secondCategorys;
    }

    @Override
    public String toString() {
        return "Category{" +
                "articleId=" + articleId +
                ", mainCategoryId=" + mainCategoryId +
                ", secondCategoryId=" + secondCategoryId +
                ", mainCategoryName='" + mainCategoryName + '\'' +
                ", secondCategoryName='" + secondCategoryName + '\'' +
                ", mainCategoryMap=" + mainCategoryMap +
                ", secondCategoryMap=" + secondCategoryMap +
                ", mainCategorys=" + mainCategorys +
                ", secondCategorys=" + secondCategorys +
                ", articleIdStr='" + articleIdStr + '\'' +
                '}';
    }
}
