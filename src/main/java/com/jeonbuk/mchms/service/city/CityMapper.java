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
}
