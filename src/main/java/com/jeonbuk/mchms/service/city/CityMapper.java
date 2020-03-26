package com.jeonbuk.mchms.service.city;

import com.jeonbuk.mchms.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CityMapper {

    @Select("SELECT DISTINCT Cities as cities FROM City")
    List<City> getCities();

    @Select("SELECT City_id as cityId, Cities as cities, Museum as museum FROM City")
    List<City> getMuseums();

    @Select("SELECT City.Cities, City.Museum FROM City LEFT OUTER JOIN Data ON (City.City_id = Data.City_id) WHERE Data.ID = #{id}")
    City getCityInfoById(String id);

    @Select("SELECT Cities as cities FROM City WHERE City_id = #{CityId}")
    City getRegionFromCityId(String CityId);

    @Select("SELECT Latitude as latitude, Longtitude as longitude FROM City WHERE City_id = #{CityId}")
    City getCityLocationFromCityid(String CityId);

    @Select("SELECT City_id as cityId FROM City WHERE Cities = #{Cities} And Museum = #{Museum}")
    City getCityIdByCategory(Map<String, Object> sqlParam);
}
