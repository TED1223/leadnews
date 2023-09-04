package com.peng.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peng.model.article.dto.ArticleHomeDTO;
import com.peng.model.article.pojo.ApActicle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 11:22
 * @description:
 */
@Mapper
public interface ApArticleMapper extends BaseMapper<ApActicle>  {

    List<ApActicle> loadArticleList(@Param("articleHome")ArticleHomeDTO articleHomeDTO, @Param("type") Integer type);
}
