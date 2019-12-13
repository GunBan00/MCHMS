package com.jeonbuk.mchms.service.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper {

    @Select("SELECT * FROM Data WHERE ID = #{id}")
    Map<String, Object> selectData(String id);

    @Select("SELECT DISTINCT Cities  FROM City")
    List<Map<String, Object>> getCities();

    @Select("SELECT City_id, Cities, Museum FROM City")
    List<Map<String, Object>> getMuseums();


}
