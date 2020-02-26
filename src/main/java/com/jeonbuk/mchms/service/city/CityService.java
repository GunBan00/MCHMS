package com.jeonbuk.mchms.service.city;

import com.jeonbuk.mchms.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityMapper cityMapper;

    public List<City> getCities() throws Exception {
        return cityMapper.getCities();
    }


    public List<City> getMuseums() throws Exception {
        return cityMapper.getMuseums();
    }

    public City getCityInfoById(String id) {
        return cityMapper.getCityInfoById(id);
    }

    public City getRejeonFromCityId(int CityId){
        return cityMapper.getRejeonFromCityId(CityId);
    }
}
