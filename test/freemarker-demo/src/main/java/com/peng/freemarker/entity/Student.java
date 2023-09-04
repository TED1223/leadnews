package com.peng.freemarker.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 14:42
 * @description:
 */
@Data
public class Student {
    private String name;
    private Integer age;
    private Date birthday;
    private Float money;
}
