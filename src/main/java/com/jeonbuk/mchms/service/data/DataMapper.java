package com.jeonbuk.mchms.service.data;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.DataDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper {

    @Select("SELECT City_id as cityId, Remarks_en as remarksEn FROM Data WHERE ID = #{id}")
    DataDomain selectData(String id);

    @Select("SELECT DISTINCT Cities as cities FROM City")
    List<City> getCities();

    @Select("SELECT City_id as cityId, Cities as cities, Museum as museum FROM City")
    List<City> getMuseums();

    @Select("SELECT * FROM Data WHERE match(Title, Serial_Number, Remarks_en, Reference_en) against('#{keyword}*' in boolean mode)")
    List<Map<String, Object>> getDataByKeyword(String keyword);

    @Select("SELECT * FROM Classification WHERE classification_id = #{classId}")
    List<Map<String, Object>> getClassficationById(int classId);
}
