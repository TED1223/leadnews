package com.peng.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peng.model.common.dtos.PageResponseResult;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.common.enums.AppHttpCodeEnum;
import com.peng.model.wemedia.dto.WmNewsPageReqDTO;
import com.peng.model.wemedia.pojo.WmNews;
import com.peng.model.wemedia.pojo.WmUser;
import com.peng.utils.thread.WmThreadLocalUtil;
import com.peng.wemedia.mapper.WmNewsMapper;
import com.peng.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 14:00
 * @description:
 */
@Service
@Transactional
@Slf4j
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {
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
}
