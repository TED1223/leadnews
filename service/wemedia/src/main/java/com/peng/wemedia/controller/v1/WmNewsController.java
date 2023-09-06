package com.peng.wemedia.controller.v1;

import com.peng.model.common.dtos.ResponseResult;
import com.peng.model.wemedia.dto.WmNewsDTO;
import com.peng.model.wemedia.dto.WmNewsPageReqDTO;
import com.peng.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 13:57
 * @description:
 */
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {

    private WmNewsService wmNewsService;

    @Autowired
    public WmNewsController(WmNewsService wmNewsService) {
        this.wmNewsService = wmNewsService;
    }

    @GetMapping("/list")
    public ResponseResult list(@RequestParam WmNewsPageReqDTO wmNewsPageReqDTO) {
        return wmNewsService.list(wmNewsPageReqDTO);
    }

    @PostMapping("/save")
    public ResponseResult save(@RequestBody WmNewsDTO wmNewsDTO) {
        return wmNewsService.saveNews(wmNewsDTO);
    }
}
