package com.peng.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peng.article.mapper.ApArticleMapper;
import com.peng.article.service.ApArticleService;
import com.peng.model.article.dto.ArticleHomeDTO;
import com.peng.model.article.pojo.ApActicle;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.constants.ArticleConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 14:02
 * @description:
 */
@Service
@Transactional
@Slf4j
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApActicle> implements ApArticleService {

    private static final Integer MAX_PAGE_SIZE = 50;

    private ApArticleMapper apArticleMapper;

    public ApArticleServiceImpl(ApArticleMapper apArticleMapper) {
        this.apArticleMapper = apArticleMapper;
    }

    @Override
    public ResponseResult load(ArticleHomeDTO articleHomeDTO, Integer type) {
        Integer size = articleHomeDTO.getSize();
        if (size == null || size == 0){
            size = 10;
        }
        size = Math.min(size,MAX_PAGE_SIZE);
        articleHomeDTO.setSize(size);

        if (!ArticleConstants.LOAD_MORE_TYPE.equals(type) && !ArticleConstants.LOAD_NEW_TYPE.equals(type)){
            type = ArticleConstants.LOAD_MORE_TYPE;
        }

        if (StringUtils.isEmpty(articleHomeDTO.getTag())){
            articleHomeDTO.setTag(ArticleConstants.DAFAULT_TAG);
        }

        if (articleHomeDTO.getMinBehotTime() == null){
            articleHomeDTO.setMinBehotTime(new Date());
        }
        if (articleHomeDTO.getMaxBehotTime() == null){
            articleHomeDTO.setMaxBehotTime(new Date());
        }

        List<ApActicle> apActicleList = apArticleMapper.loadArticleList(articleHomeDTO, type);
        return ResponseResult.okResult(apActicleList);
    }
}
