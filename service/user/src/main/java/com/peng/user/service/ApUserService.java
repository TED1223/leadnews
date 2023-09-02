package com.peng.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.user.dto.LoginDTO;
import com.peng.model.user.pojo.ApUser;

public interface ApUserService extends IService<ApUser> {


    ResponseResult login(LoginDTO loginDTO);
}
