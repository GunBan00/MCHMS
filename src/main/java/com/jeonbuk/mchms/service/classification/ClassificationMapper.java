package com.jeonbuk.mchms.service.classification;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.ClassificationCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClassificationMapper {
    @Select("SELECT DISTINCT Large as large FROM Classification")
    List<Classification> getLarge();

    @Select("SELECT DISTINCT Large as large, Middle as middle FROM Classification")
    List<Classification> getMiddle();

    @Select("SELECT DISTINCT Large as large, Middle as middle, Small as small FROM Classification")
    List<Classification> getSmall();

    @Select("SELECT DISTINCT Large as large, Middle as middle, Small as small, Sub_Section as subSection FROM Classification")
    List<Classification> getSubSection();

    @Select("SELECT Large as large, Middle as middle, Small as small, Sub_Section as subSection FROM Classification natural join Data as D WHERE D.ID = #{id}")
    Classification getClassificationInfoById(String id);

    @Select("SELECT COUNT(Large) as count, Large as large FROM City LEFT OUTER JOIN (Data LEFT OUTER JOIN Classification ON (Data.Classification_id = Classification.classification_id)) ON (Data.City_id = City.City_id) WHERE City.Cities = #{Cities} GROUP BY Large;")
    List<ClassificationCount> getClassificationCountById(String Cities);

    @Select("SELECT Large as large FROM Classification WHERE classification_id = #{Classifi}")
    Classification getCategoryFromClassification(int Classifi);

    @Select("SELECT classification_id as classificationId FROM Classification WHERE Large = #{Large} And Middle = #{Middle} And Small = #{Small} And Sub_Section = #{Sub_Section}")
    Classification getClassificationIdByCategory(Map<String, Object> sqlParam);
}
