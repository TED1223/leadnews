package com.peng.model.wemedia.dto;


import lombok.Data;

@Data
public class WmLoginDTO {

    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
}
