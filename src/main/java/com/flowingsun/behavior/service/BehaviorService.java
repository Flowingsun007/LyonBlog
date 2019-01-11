package com.flowingsun.behavior.service;

import com.flowingsun.article.entity.Article;
import com.flowingsun.behavior.dto.BehaviorStatus;
import com.flowingsun.behavior.entity.*;
import com.flowingsun.behavior.vo.PictureQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

public interface BehaviorService {

    String setComment(Comment commentBean, HttpServletRequest request);

    String setThank(Thank thankBean, HttpServletRequest request);

    String setCommentLike(CommentLike commentLikeBean, HttpServletRequest request);

    boolean saveUserImage(Picture picture);

    String imageUpload(MultipartHttpServletRequest request, String description);

    PictureQuery getUserImages(PictureQuery pictureQuery) throws ParseException;

    Article getUserArticleBehavior(Article article, Long userId);

    List<Article> getUserArticleListBehavior(List<Article> articleList, Long userId);

    String getMoneyDonatePage(HttpServletRequest request);

    String setCommentDiscussion(Discussion discussion, HttpServletRequest request);

    String setCollect(Collection collectBean, HttpServletRequest request);

    BehaviorStatus getUserBehavior(Long userid);

    String getUserArticleCollections(HttpServletRequest request);
    /**
     * @author lyon
     * @date   2019/1/8 20:51
     * @detail 根据url截图网页快照
     */
    String getScreenShot(String url)throws Exception;

    UrlCollection collectUrl(UrlCollection bean)throws Exception;
    /**
     *  设置用户头像
     */
    String setUserHeadImage(MultipartFile multipartFile, HttpServletRequest request);
}
