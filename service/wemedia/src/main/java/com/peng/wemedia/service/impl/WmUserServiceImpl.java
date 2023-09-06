package com.peng.wemedia.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.common.enums.AppHttpCodeEnum;
import com.peng.model.wemedia.dto.WmLoginDTO;
import com.peng.model.wemedia.pojo.WmUser;
import com.peng.utils.common.AppJwtUtil;
import com.peng.wemedia.mapper.WmUserMapper;
import com.peng.wemedia.service.WmUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 9:57
 * @description:
 */
@Service
@Transactional
@Slf4j
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {


    @Override
    public ResponseResult login(WmLoginDTO wmLoginDTO) {
        if (wmLoginDTO == null || wmLoginDTO.getName() == null) {
            return null;
        }
        WmUser wmUser = getOne(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, wmLoginDTO.getName()));
        if (wmUser == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        String salt = wmUser.getSalt();
        String password = wmLoginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());

        if (!wmUser.getPassword().equals(password)){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        Map<String,Object> map  = new HashMap<>();
        map.put("token", AppJwtUtil.getToken(wmUser.getId().longValue()));
        wmUser.setSalt("");
        wmUser.setPassword("");
        map.put("user",wmUser);
        return ResponseResult.okResult(map);

    }
}
