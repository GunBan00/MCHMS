package com.jeonbuk.mchms.service.data;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.DataDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper {

    @Select("SELECT City_id as cityId, Remarks_en as remarksEn , Latitude as latitude ,Longitude as longitude FROM Data WHERE ID = #{id}")
    DataDomain selectData(String id);

    @Select("SELECT ID as id, Title as title, Latitude as latitude, Longitude as longitude, Classification_id as classificationId, Registrant as registrant,Registration_Date as registrationDate  FROM Data WHERE match(Title, Serial_Number, Remarks_en, Reference_en) against(#{keyword} in boolean mode)")
    List<DataDomain> getDataByKeyword(String keyword);

    @Select("<script><choose><when test=\"cityId != 0\"> " +
            "SELECT ID as id, Title as title, Latitude as latitude, Longitude as longitude FROM Data WHERE City_id = #{cityId} ORDER BY Data.ID desc"
            +"</when><otherwise> "
            +"SELECT * FROM Data ORDER BY Data.ID desc"
            +"</otherwise></choose></script>"
    )
    List<DataDomain> getDataByCityId(int cityId);

    @Select("SELECT classification_id as classificationId, Large as large, Middle as middle, Small as small FROM Classification WHERE classification_id = #{classId}")
    Classification getClassficationById(int classId);
}
