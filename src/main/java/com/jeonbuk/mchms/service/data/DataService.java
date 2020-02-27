package com.jeonbuk.mchms.service.data;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
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

    public DataDomain getDataInfo(String id) throws Exception {
        DataDomain dataDomain = dataMapper.selectData(id);
        return dataDomain;
    }


    public List<DataDomain> getDataByKeyword(String keyword) throws Exception{
        keyword = keyword + "*";
        return dataMapper.getDataByKeyword(keyword);
    }

    public Classification getClassificationById(int classId) throws Exception{

        return dataMapper.getClassificationById(classId);
    }

    public List<DataDomain> getDataByCityId(int cityId) throws Exception {
        return dataMapper.getDataByCityId(cityId);
    }



}
