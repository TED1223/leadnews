package com.peng.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.common.enums.AppHttpCodeEnum;
import com.peng.model.user.dto.LoginDTO;
import com.peng.model.user.pojo.ApUser;
import com.peng.user.mapper.ApUserMapper;
import com.peng.user.service.ApUserService;
import com.peng.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Map;

/**
 * @Author: pf
 * @CreateTime: 2023-09-02  15:33
 * @Description:
 */
@Service
@Transactional
@Slf4j
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {


    @Override
    public ResponseResult login(LoginDTO loginDTO) {
        //游客 返回token id = 0
        if (StringUtils.isBlank(loginDTO.getPhone()) && StringUtils.isBlank(loginDTO.getPassword())) {
            Map<String, String> result = Maps.newHashMap();
            result.put("token", AppJwtUtil.getToken(0L));
            return ResponseResult.okResult(result);
        }
        //1.1查询用户
        ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, loginDTO.getPhone()));
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户不存在");
        }
        String salt = apUser.getSalt();
        String password = loginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        //1.2 比对密码
        if (!password.equals(apUser.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        //1.3 返回数据  jwt
        Map<String, Object> result = Maps.newHashMap();
        result.put("token", AppJwtUtil.getToken(apUser.getId().longValue()));
        apUser.setSalt("");
        apUser.setPassword("");
        result.put("user", apUser);
        return ResponseResult.okResult(result);
    }
}
