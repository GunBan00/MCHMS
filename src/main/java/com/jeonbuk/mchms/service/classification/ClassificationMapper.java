package com.jeonbuk.mchms.service.classification;

import com.jeonbuk.mchms.domain.Classification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassificationMapper {

    @Select("SELECT Large as large, Middle as middle, Small as small FROM Classification natural join Data as D WHERE D.ID = #{id}")
    Classification getClassificationInfoById(String id);

}
