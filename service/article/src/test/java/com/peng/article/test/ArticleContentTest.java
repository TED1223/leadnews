package com.peng.article.test;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.peng.article.mapper.ApArticleContentMapper;
import com.peng.article.mapper.ApArticleMapper;
import com.peng.file.service.FileStorageService;
import com.peng.model.article.pojo.ApActicle;
import com.peng.model.article.pojo.ApArticleContent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Wrapper;
import java.util.Map;

/**
 * @author: pengshengfeng
 * @date: 2023/9/5 17:05
 * @description:
 */
@SpringBootTest(classes = ApArticleContentMapper.class)
@RunWith(SpringRunner.class)
public class ArticleContentTest {

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private Configuration configuration;

    @Test
    public void createStaticUrlTest() throws IOException, TemplateException {
        //1.获取文章内容
        ApArticleContent apArticleContent = apArticleContentMapper.selectOne(
                Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, 1390536764510310401L));

        if (apArticleContent != null && StringUtils.isNotBlank(apArticleContent.getArticleContent())){
            //2.文章内容通过freemarker生成html文件
            StringWriter sw = new StringWriter();
            Template template = configuration.getTemplate("article.ftl");
            Map<String,Object> map = Maps.newHashMap();
            map.put("content", JSONArray.parseArray(apArticleContent.getArticleContent()));
            //3.把html文件上传到minio中
            template.process(map,sw);
            ByteArrayInputStream bais = new ByteArrayInputStream(sw.toString().getBytes());
            String filePath = fileStorageService.uploadHtmlFile("", apArticleContent.getArticleContent() + ".html", bais);
            //4.修改ap_article表，保存static_url字段
            ApActicle apActicle = new ApActicle();
            apActicle.setId(apArticleContent.getId());
            apActicle.setStaticUrl(filePath);
            apArticleMapper.updateById(apActicle);
        }
    }

}
