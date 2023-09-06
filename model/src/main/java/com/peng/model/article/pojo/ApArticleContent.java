package com.peng.model.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: pengshengfeng
 * @date: 2023/9/5 17:07
 * @description:
 */
@Data
@TableName("ap_article_content")
public class ApArticleContent implements Serializable {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("article_id")
    private Long articleId;

    private String articleContent;

}
