package com.peng.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.wemedia.pojo.WmChannel;
import com.peng.wemedia.mapper.WmChannelMapper;
import com.peng.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 13:53
 * @description:
 */
@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {

    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }
}
