package com.peng.wemedia.controller.v1;

import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.wemedia.dto.WmLoginDTO;
import com.peng.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 10:07
 * @description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private WmUserService wmUserService;

    @PostMapping("/in")
    public ResponseResult login(@RequestBody WmLoginDTO wmLoginDTO) {
        return wmUserService.login(wmLoginDTO);
    }
}
