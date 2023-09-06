package com.peng.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.wemedia.dto.WmMaterialDTO;
import com.peng.model.wemedia.pojo.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 10:52
 * @description:
 */
public interface WmMaterialService extends IService<WmMaterial> {

    ResponseResult uploadPicture(MultipartFile multipartFile);

    ResponseResult list(WmMaterialDTO wmMaterialDTO);
}
