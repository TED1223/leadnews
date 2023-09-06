package com.peng.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.wemedia.dto.WmLoginDTO;
import com.peng.model.wemedia.pojo.WmUser;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 9:57
 * @description:
 */
public interface WmUserService extends IService<WmUser> {

    ResponseResult login(WmLoginDTO wmLoginDTO);

}
