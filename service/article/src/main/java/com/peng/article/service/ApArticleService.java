package com.peng.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.peng.model.article.dto.ArticleHomeDTO;
import com.peng.model.article.pojo.ApActicle;
import com.peng.model.common.dtos.ResponseResult;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 14:00
 * @description:
 */
public interface ApArticleService extends IService<ApActicle> {

    ResponseResult load(ArticleHomeDTO articleHomeDTO , Integer type);
}
