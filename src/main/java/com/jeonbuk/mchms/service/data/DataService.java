package com.jeonbuk.mchms.service.data;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.DataDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataService {

    @Autowired
    private DataMapper dataMapper;

    public DataDomain getEventInfo(String id) throws Exception {
        DataDomain dataDomain = dataMapper.selectData(id);
        return dataDomain;
    }


    public List<City> getCities() throws Exception {

        return dataMapper.getCities();
    }


    public List<City> getMuseums() throws Exception {

        return dataMapper.getMuseums();
    }

    public List<Map<String, Object>> getDataByKeyword(String keyword) throws Exception{

        return dataMapper.getDataByKeyword(keyword);
    }

    public List<Map<String, Object>> getClassficationById(int classId) throws Exception{

        return dataMapper.getClassficationById(classId);
    }



}
