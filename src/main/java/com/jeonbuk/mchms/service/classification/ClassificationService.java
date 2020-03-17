package com.jeonbuk.mchms.service.classification;

import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.ClassificationCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassificationService {

    @Autowired
    private ClassificationMapper classificationMapper;

    public List<Classification> getLarge() {
        return classificationMapper.getLarge();
    }

    public List<Classification> getMiddle() {
        return classificationMapper.getMiddle();
    }

    public List<Classification> getSmall() {
        return classificationMapper.getSmall();
    }

    public List<Classification> getSubSection() {
        return classificationMapper.getSubSection();
    }

    public Classification getClassificationInfoById(String id) {
        return classificationMapper.getClassificationInfoById(id);
    }

    public List<ClassificationCount> getClassificationCountById(String Cities){
        return classificationMapper.getClassificationCountById(Cities);
    }

    public Classification getCategoryFromClassification(int Classifi){
        return classificationMapper.getCategoryFromClassification(Classifi);
    }

    public int getClassificationIdByCategory(String Large, String Middle, String Small, String Sub_Section){
        Map<String, Object> sqlParam = new HashMap<>();

        sqlParam.put("Large", Large);
        sqlParam.put("Middle", Middle);
        sqlParam.put("Small", Small);
        sqlParam.put("Sub_Section", Sub_Section);

        Classification classification = classificationMapper.getClassificationIdByCategory(sqlParam);

        int cli;
        if(classification == null){
            cli = 0;
            return cli;
        }
        else{
            cli = classification.getClassificationId();
            return cli;
        }

    }
}
