package com.jeonbuk.mchms.service.city;

import com.jeonbuk.mchms.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CityMapper {

    @Select("SELECT DISTINCT Cities as cities FROM City")
    List<City> getCities();

    @Select("SELECT City_id as cityId, Cities as cities, Museum as museum FROM City")
    List<City> getMuseums();

    @Select("SELECT City.Cities, City.Museum FROM City natural join Data as D WHERE D.ID = #{id}")
    City getCityInfoById(String id);

    @Select("SELECT Cities as cities FROM City WHERE City_id = #{CityId}")
    City getRegionFromCityId(int CityId);

    @Select("SELECT Latitude as latitude, Longtitude as longitude FROM City WHERE City_id = #{CityId}")
    City getCityLocationFromCityid(int CityId);
}
