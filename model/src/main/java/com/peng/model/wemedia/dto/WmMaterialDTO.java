package com.peng.model.wemedia.dto;

import com.peng.model.common.dtos.PageRequestDTO;
import lombok.Data;

@Data
public class WmMaterialDTO extends PageRequestDTO {

    /**
     * 1 收藏
     * 0 未收藏
     */
    private Short isCollection;
}
