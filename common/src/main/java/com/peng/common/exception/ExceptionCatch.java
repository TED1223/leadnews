package com.peng.common.exception;

import com.peng.model.dtos.ResponseResult;
import com.peng.model.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: pengshengfeng
 * @date: 2023/8/30 17:20
 * @description:
 */
@ControllerAdvice
@Slf4j
public class ExceptionCatch {


    /**
     * 处理不可控异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e){
        e.printStackTrace();
        log.error("catch exception: {}", e.getMessage());
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 处理可控异常  自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e){
        log.error("catch customException:{}",e);
        return ResponseResult.errorResult(e.getAppHttpCodeEnum());
    }

}
