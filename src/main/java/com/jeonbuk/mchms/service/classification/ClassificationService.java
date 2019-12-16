package com.jeonbuk.mchms.service.classification;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.EventDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService {

    @Autowired
    private ClassificationMapper classificationMapper;

    public Classification getClassificationInfoById(String id) {
        return classificationMapper.getClassificationInfoById(id);
    }



}
