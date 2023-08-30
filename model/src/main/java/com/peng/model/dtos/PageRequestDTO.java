package com.peng.model.dtos;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: pengshengfeng
 * @date: 2023/8/30 17:43
 * @description:
 */
@Data
@Slf4j
public class PageRequestDTO {
    protected Integer size;
    protected Integer page;

    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }
}
