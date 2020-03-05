package com.jeonbuk.mchms.service.classification;

import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.ClassificationCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassificationMapper {

    @Select("SELECT Large as large, Middle as middle, Small as small, Sub_Section as subSection FROM Classification natural join Data as D WHERE D.ID = #{id}")
    Classification getClassificationInfoById(String id);

    @Select("SELECT COUNT(Large) as count, Large as large FROM City LEFT OUTER JOIN (Data LEFT OUTER JOIN Classification ON (Data.Classification_id = Classification.classification_id)) ON (Data.City_id = City.City_id) WHERE City.Cities = #{Cities} GROUP BY Large;")
    List<ClassificationCount> getClassificationCountById(String Cities);

    @Select("SELECT Large as large FROM Classification WHERE classification_id = #{Classifi}")
    Classification getCategoryFromClassification(int Classifi);
}
