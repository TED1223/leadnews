package com.peng.wemedia.controller.v1;

import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.wemedia.dto.WmMaterialDTO;
import com.peng.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 10:49
 * @description:
 */
@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    private WmMaterialService wmMaterialService;

    @Autowired
    public WmMaterialController(WmMaterialService wmMaterialService) {
        this.wmMaterialService = wmMaterialService;
    }

    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        return wmMaterialService.uploadPicture(multipartFile);
    }

    @GetMapping("/list")
    public ResponseResult list(@RequestParam WmMaterialDTO wmMaterialDTO){
        return wmMaterialService.list(wmMaterialDTO);
    }
}
