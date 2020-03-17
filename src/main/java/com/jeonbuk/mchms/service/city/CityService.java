package com.jeonbuk.mchms.service.city;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public City getRegionFromCityId(int CityId){
        return cityMapper.getRegionFromCityId(CityId);
    }

    public City getCityLocationFromCityid(int CityId){
        return cityMapper.getCityLocationFromCityid(CityId);
    }

    public int getCityIdByCategory(String Cities, String Museum){
        Map<String, Object> sqlParam = new HashMap<>();

        sqlParam.put("Cities", Cities);
        sqlParam.put("Museum", Museum);

        City city = cityMapper.getCityIdByCategory(sqlParam);

        int ctid;
        if(city == null){
            ctid = 0;
            return ctid;
        }
        else{
            ctid = city.getCityId();
            return ctid;
        }

    }
}
