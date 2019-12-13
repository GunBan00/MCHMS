package com.jeonbuk.mchms.service.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataService {

    @Autowired
    private DataMapper dataMapper;

    public Map<String, Object> getEventInfo(String id) throws Exception {
        Map<String, Object> ret = dataMapper.selectData(id);
        return ret;
    }


    public List<Map<String, Object>> getCities() throws Exception {

        return dataMapper.getCities();
    }




    public List<Map<String, Object>> getMuseums() throws Exception {

        return dataMapper.getMuseums();
    }



}
