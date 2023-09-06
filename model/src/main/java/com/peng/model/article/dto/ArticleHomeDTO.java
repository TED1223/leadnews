package com.peng.model.article.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 10:54
 * @description:
 */
@Data
public class ArticleHomeDTO {

    private Date maxBehotTime;

    private Date minBehotTime;

    private Integer size;

    private String tag;

}
