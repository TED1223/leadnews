package com.peng.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.wemedia.dto.WmNewsDTO;
import com.peng.model.wemedia.dto.WmNewsPageReqDTO;
import com.peng.model.wemedia.pojo.WmNews;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 13:59
 * @description:
 */
public interface WmNewsService extends IService<WmNews> {

    ResponseResult list(WmNewsPageReqDTO dto);

    ResponseResult saveNews(WmNewsDTO dto);
}
