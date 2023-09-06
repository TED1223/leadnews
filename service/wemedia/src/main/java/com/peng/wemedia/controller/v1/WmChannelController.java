package com.peng.wemedia.controller.v1;

import com.peng.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 13:50
 * @description:
 */
@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {

    @GetMapping("/channels")
    public ResponseResult findAll(){
        return null;
    }
}
