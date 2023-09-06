package com.peng.wemedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.peng.common.WemediaConstants;
import com.peng.common.exception.CustomException;
import com.peng.model.common.dtos.PageResponseResult;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.common.enums.AppHttpCodeEnum;
import com.peng.model.wemedia.dto.WmNewsDTO;
import com.peng.model.wemedia.dto.WmNewsPageReqDTO;
import com.peng.model.wemedia.pojo.WmMaterial;
import com.peng.model.wemedia.pojo.WmNews;
import com.peng.model.wemedia.pojo.WmNewsMaterial;
import com.peng.model.wemedia.pojo.WmUser;
import com.peng.utils.thread.WmThreadLocalUtil;
import com.peng.wemedia.mapper.WmMaterialMapper;
import com.peng.wemedia.mapper.WmNewsMapper;
import com.peng.wemedia.mapper.WmNewsMaterialMapper;
import com.peng.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 14:00
 * @description:
 */
@Service
@Transactional
@Slf4j
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    private final WmNewsMaterialMapper wmNewsMaterialMapper;

    private final WmMaterialMapper wmMaterialMapper;

    @Autowired
    public WmNewsServiceImpl(WmNewsMaterialMapper wmNewsMaterialMapper, WmMaterialMapper wmMaterialMapper) {
        this.wmNewsMaterialMapper = wmNewsMaterialMapper;
        this.wmMaterialMapper = wmMaterialMapper;
    }

    @Override
    public ResponseResult list(WmNewsPageReqDTO dto) {
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        //分页参数检查
        dto.checkParam();
        //获取当前登录人的信息
        WmUser wmUser = WmThreadLocalUtil.getWmUser();
        if (wmUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        //分页条件查询
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmNews> queryWrapper = new LambdaQueryWrapper<>();

        //状态精确查询
        if (dto.getStatus() != null) {
            queryWrapper.eq(WmNews::getStatus, dto.getStatus());
        }

        //频道精确查询
        if (dto.getChannelId() != null) {
            queryWrapper.eq(WmNews::getChannelId, dto.getChannelId());
        }

        //时间范围查询
        if (dto.getBeginPubDate() != null && dto.getEndPubDate() != null) {
            queryWrapper.between(WmNews::getCreatedTime, dto.getBeginPubDate(), dto.getEndPubDate());
        }

        //关键字模糊查询
        if (dto.getKeyword() != null) {
            queryWrapper.like(WmNews::getTitle, dto.getKeyword());
        }

        //查询当前登录用户的文章
        queryWrapper.eq(WmNews::getUserId, wmUser.getId());

        //发布时间倒序查询
        queryWrapper.orderByDesc(WmNews::getCreatedTime);

        page = page(page, queryWrapper);

        ResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    @Override
    public ResponseResult saveNews(WmNewsDTO dto) {
        //条件判断
        if (dto == null || dto.getContent() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //1.保存或修改文章
        WmNews wmNews = new WmNews();
        BeanUtils.copyProperties(dto,wmNews);

        if (dto.getImages() != null && dto.getImages().size() > 0){
            String images = StringUtils.join(dto.getImages(), ",");
            wmNews.setImages(images);
        }

        //如果当前封面类型为自动 -1
        if(WemediaConstants.WM_NEWS_TYPE_AUTO.equals(dto.getType())){
            wmNews.setType(null);
        }

        saveOrUpdateNews(wmNews);

        //2.判断是否为草稿  如果为草稿结束当前方法
        if (dto.getStatus().equals(WmNews.Status.NORMAL.getCode())){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        //3.不是草稿，保存文章内容图片与素材的关系
        //获取到文章内容中的图片信息
        List<String> materialList = extractUrlInfo(dto.getContent());
        saveRelativeInfoForContent(materialList,wmNews.getId());

        //4.不是草稿，保存文章封面图片与素材的关系，如果当前布局是自动，需要匹配封面图片
        saveRelativeInfoForCover(dto,wmNews,materialList);

        return  ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 第一个功能：如果当前封面类型为自动，则设置封面类型的数据
     * 匹配规则：
     * 1，如果内容图片大于等于1，小于3  单图  type 1
     * 2，如果内容图片大于等于3  多图  type 3
     * 3，如果内容没有图片，无图  type 0
     *
     * 第二个功能：保存封面图片与素材的关系
     * @param dto
     * @param wmNews
     * @param materialList
     */
    private void saveRelativeInfoForCover(WmNewsDTO dto, WmNews wmNews, List<String> materialList) {
        List<String> imageList = dto.getImages();
        if (WemediaConstants.WM_NEWS_TYPE_AUTO.equals(dto.getType())){
            //多图
            if (materialList.size() >= 3){
                wmNews.setType(WemediaConstants.WM_NEWS_MANY_IMAGE);
                imageList = materialList.stream().limit(3).collect(Collectors.toList());
            }else if ( materialList.size() >= 1 && materialList.size() < 3){
                wmNews.setType(WemediaConstants.WM_COVER_REFERENCE);
                imageList = Collections.singletonList(materialList.get(0));
            }else {
                wmNews.setType(WemediaConstants.WM_NEWS_NONE_IMAGE);
            }

            if (imageList != null && imageList.size() == 0){
                String images = StringUtils.join(imageList, ",");
                wmNews.setImages(images);
            }
            updateById(wmNews);
        }

        if (imageList != null && imageList.size() > 0){
            saveRelativeInfo(imageList,wmNews.getId(), WemediaConstants.WM_COVER_REFERENCE);
        }
    }

    /**
     * 处理文章内容图片与素材的关系
     * @param materialList
     * @param id
     */
    private void saveRelativeInfoForContent(List<String> materialList, Integer id) {
        saveRelativeInfo(materialList,id,WemediaConstants.WM_CONTENT_REFERENCE);
    }

    /**
     * 保存文章图片与素材的关系到数据库中
     * @param materialList
     * @param id
     * @param type
     */
    private void saveRelativeInfo(List<String> materialList, Integer id, Short type) {
        //1.通过url 获取到所有素材的id
        List<WmMaterial> wmMaterialList = wmMaterialMapper.selectList(
                Wrappers.<WmMaterial>lambdaQuery().eq(WmMaterial::getUrl, materialList));
        if (wmMaterialList == null || wmMaterialList.size() == 0){
            throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
        }

        if (materialList.size() != wmMaterialList.size()){
            throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
        }

        List<Integer> idList = wmMaterialList.stream().map(WmMaterial::getId).collect(Collectors.toList());
        wmNewsMaterialMapper.saveRelations(idList,id,type);
    }

    private List<String> extractUrlInfo(String content) {
        List<String> materialList = Lists.newArrayList();
        List<Map> mapList = JSONArray.parseArray(content, Map.class);
        for (Map map : mapList) {
            if (map.get("type").equals("image")){
                String imgUrl = (String) map.get("value");
                materialList.add(imgUrl);
            }
        }
        return materialList;
    }

    /**
     * 保存或修改文章
     * @param wmNews
     */
    private void saveOrUpdateNews(WmNews wmNews) {
        //补全参数
        wmNews.setUserId(WmThreadLocalUtil.getWmUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setPublishTime(new Date());
        wmNews.setEnable((short) 1);//默认上架

        if (wmNews.getId() == null){
            //保存
            save(wmNews);
        }else {
            //修改
            //删除文章图片与素材的关系
            wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId,wmNews.getId()));
            updateById(wmNews);
        }
    }
}
