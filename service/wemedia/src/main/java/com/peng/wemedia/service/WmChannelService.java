package com.peng.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.wemedia.pojo.WmChannel;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 13:53
 * @description:
 */
public interface WmChannelService extends IService<WmChannel> {

    ResponseResult findAll();
}
