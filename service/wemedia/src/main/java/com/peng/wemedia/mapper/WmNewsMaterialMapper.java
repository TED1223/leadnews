package com.peng.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peng.model.wemedia.pojo.WmNewsMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pengshengfeng
 * @date: 2023/9/6 16:11
 * @description:
 */
@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {

    void saveRelations(@Param("materialIdList") List<Integer> materialIdList, @Param("newsId") Integer newsId, @Param("type") Short type);

}
