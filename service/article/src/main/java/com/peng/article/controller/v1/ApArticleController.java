package com.peng.article.controller.v1;

import com.peng.article.service.ApArticleService;
import com.peng.model.article.dto.ArticleHomeDTO;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.constants.ArticleConstants;
import org.springframework.web.bind.annotation.*;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 14:17
 * @description:
 */
@RestController
@RequestMapping("/api/v1/article")
public class ApArticleController {

    private ApArticleService apArticleService;

    public ApArticleController(ApArticleService apArticleService) {
        this.apArticleService = apArticleService;
    }


    @GetMapping("/load")
    public ResponseResult load(@RequestParam ArticleHomeDTO articleHomeDTO){
        return apArticleService.load(articleHomeDTO, ArticleConstants.LOAD_MORE_TYPE);
    }

    @GetMapping("/load/more")
    public ResponseResult loadMore(@RequestParam ArticleHomeDTO articleHomeDTO){
        return apArticleService.load(articleHomeDTO, ArticleConstants.LOAD_MORE_TYPE);
    }

    @GetMapping("/load/new")
    public ResponseResult loadNew(@RequestParam ArticleHomeDTO articleHomeDTO){
        return apArticleService.load(articleHomeDTO, ArticleConstants.LOAD_NEW_TYPE);
    }
}
