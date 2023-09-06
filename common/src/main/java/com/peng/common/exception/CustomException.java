package com.peng.common.exception;

import com.peng.model.common.enums.AppHttpCodeEnum;

/**
 * @author: pengshengfeng
 * @date: 2023/8/30 17:08
 * @description:
 */
public class CustomException extends RuntimeException {

    private AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum) {
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public AppHttpCodeEnum getAppHttpCodeEnum() {
        return appHttpCodeEnum;
    }
}
