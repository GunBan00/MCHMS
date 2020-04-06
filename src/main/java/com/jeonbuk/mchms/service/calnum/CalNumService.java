package com.jeonbuk.mchms.service.calnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalNumService {

    @Autowired
    CalNumMapper calNumMapper;

    String selectDateById(String id) {
        return calNumMapper.selectDateById(id);
    }

    void setCalNum(String id, String today) {

    }

    void changeCalNum(String id, String today) {

    }

    public String selectRelicNumber(String id,String today){
        String calDate = calNumMapper.selectDateById(id);
        System.out.println(calDate);
        if(calDate == null) calNumMapper.setCalNum(id, today);
        else calNumMapper.changeCalNum(id, today);
        String relic =  calNumMapper.selectRelicNumber(id);
        int length = relic.length();
        String zero = "0";
        int j = 4 - length;
        String relicNumber = relic;
        for(int i = 1; i <= j; i++)
        {
            relicNumber = zero + relicNumber;
        }
        return relicNumber;
    }

    public String selectOnlyRelicNumber(String id)
    {
        String relic =  calNumMapper.selectRelicNumber(id);
        int length = relic.length();
        String zero = "0";
        int j = 4 - length;
        String relicNumber = relic;
        for(int i = 1; i <= j; i++)
        {
            relicNumber = zero + relicNumber;
        }
        return relicNumber;

    }


}
