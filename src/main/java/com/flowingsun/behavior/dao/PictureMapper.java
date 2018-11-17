package com.flowingsun.behavior.dao;

import com.flowingsun.behavior.entity.Picture;
import com.flowingsun.behavior.vo.PictureQuery;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public interface PictureMapper {

    Picture selectByPrimaryKey(Integer id);

    List<Picture> selectByImageCreateDate(PictureQuery pictureQuery);

    List<PictureQuery> selectByQueryDateRange(PictureQuery pictureQuery);

    int selectCountByQueryDateRange(PictureQuery pictureQuery);

    int insert(Picture record);

    int insertSelective(Picture record);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);

    int deleteByPrimaryKey(Integer id);

}