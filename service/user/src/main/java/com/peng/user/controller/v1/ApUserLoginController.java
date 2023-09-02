package com.peng.user.controller.v1;

import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.user.dto.LoginDTO;
import com.peng.user.service.ApUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: pf
 * @CreateTime: 2023-09-02  16:11
 * @Description:
 */
@RestController
@RequestMapping("/api/v1/login")
@Api(value = "app端登录",tags = "ap_user")
public class ApUserLoginController {

    private ApUserService apUserService;

    public ApUserLoginController(ApUserService apUserService) {
        this.apUserService = apUserService;
    }

    @PostMapping("/login_auth")
    @ApiOperation("用户登录")
    public ResponseResult login(@RequestBody LoginDTO loginDTO){
        return apUserService.login(loginDTO);
    }

}
