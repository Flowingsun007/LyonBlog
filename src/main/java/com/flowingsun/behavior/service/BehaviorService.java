package com.flowingsun.behavior.service;

import com.flowingsun.article.entity.Article;
import com.flowingsun.article.vo.CategoryArticleQuery;
import com.flowingsun.behavior.entity.Comment;
import com.flowingsun.behavior.entity.CommentLike;
import com.flowingsun.behavior.entity.Picture;
import com.flowingsun.behavior.entity.Thank;
import com.flowingsun.behavior.vo.PictureQuery;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public interface BehaviorService {

    String setComment(Comment commentBean, HttpServletRequest request);

    String setThank(Thank thankBean,HttpServletRequest request);

    String setCommentLike(CommentLike commentLikeBean, HttpServletRequest request);

    boolean saveUserImage(Picture picture);

    String imageUpload(MultipartHttpServletRequest request,String description);

    PictureQuery getUserImages(PictureQuery pictureQuery) throws ParseException;

    Article getUserArticleBehavior(Article article,Long userId);

    CategoryArticleQuery getUserCategoryArticleBehavior(CategoryArticleQuery categoryArticleQuery, Long userId);

    String getMoneyDonatePage(HttpServletRequest request);
}
