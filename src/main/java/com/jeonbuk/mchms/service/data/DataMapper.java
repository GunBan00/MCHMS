package com.jeonbuk.mchms.service.data;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.DataDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper {

    @Select("SELECT *, Serial_Number as serialNumber, Registration_Date as registrationDate, Remarks_en as RemarksEn, Remarks_my as RemarksMy, Reference_en as ReferenceEn, Reference_my as ReferenceMy FROM Data WHERE ID = #{id}")
    DataDomain selectData(String id);

    @Select("SELECT ID as id, Title as title, Serial_Number as serialnumber, Classification_id as classificationId, City_Id as cityId, Latitude as latitude, Longitude as longitude, Registration_Date as registrationDate FROM Data WHERE match(Title, Serial_Number, Remarks_en, Reference_en) against(#{keyword} in boolean mode)")
    List<DataDomain> getDataByKeyword(String keyword);

    @Select("<script><choose><when test=\"cityId != 0\"> " +
            "SELECT ID as id, Title as title, Serial_Number as serialnumber, Classification_id as classificationId, Latitude as latitude, Longitude as longitude, Registration_Date as registrationDate FROM Data WHERE City_id = #{cityId} ORDER BY Data.ID desc"
            +"</when><otherwise> "
            +"SELECT * FROM Data ORDER BY Data.ID desc"
            +"</otherwise></choose></script>"
    )
    List<DataDomain> getDataByCityId(int cityId);

    @Select("SELECT ID as id, Title as title, Serial_Number as serialnumber, Classification_id as classificationId, Data.Latitude as latitude, Data.Longitude as longitude, Registration_Date as registrationDate FROM Data LEFT OUTER JOIN City ON (Data.City_id = City.City_id) WHERE Data.City_id = ${cityId} ORDER BY City.Cities ${Order}")
    List<DataDomain> getDataByCityIdAndJoinCity(Map<String, Object> sqlParam);

    @Select("SELECT ID as id, Title as title, Serial_Number as serialnumber, Data.Classification_id as classificationId, Latitude as latitude, Longitude as longitude, Registration_Date as registrationDate FROM Data LEFT OUTER JOIN Classification ON (Data.Classification_id = Classification.classification_id) WHERE City_id = ${cityId} ORDER BY Classification.Large ${Order}")
    List<DataDomain> getDataByCityIdAndJoinClassifi(Map<String, Object> sqlParam);

    @Select("SELECT ID as id, Title as title, Serial_Number as serialnumber, Classification_id as classificationId, Latitude as latitude, Longitude as longitude, Registration_Date as registrationDate FROM Data WHERE City_id = ${cityId} ORDER BY Data.${TypeToSort} ${Order}")
    List<DataDomain> getDataByCityIdAndNotJoin(Map<String, Object> sqlParam);

    @Select("SELECT ID as id, Title as title, Serial_Number as serialnumber, Classification_id as classificationId, Data.Latitude as latitude, Data.Longitude as longitude, Registration_Date as registrationDate, City_id as cityId FROM Data LEFT OUTER JOIN City ON (Data.City_id = City.City_id) WHERE ${keywordQuery} ORDER BY City.Cities ${Order}")
    List<DataDomain> getDataByKeywordIdAndJoinCity(Map<String, Object> sqlParam);

    @Select("SELECT ID as id, Title as title, Serial_Number as serialnumber, Data.Classification_id as classificationId, Latitude as latitude, Longitude as longitude, Registration_Date as registrationDate, City_id as cityId FROM Data LEFT OUTER JOIN Classification ON (Data.Classification_id = Classification.classification_id) WHERE ${keywordQuery} ORDER BY Classification.Large ${Order}")
    List<DataDomain> getDataByKeywordIdAndJoinClassifi(Map<String, Object> sqlParam);

    @Select("SELECT ID as id, Title as title, Serial_Number as serialnumber, Classification_id as classificationId, Latitude as latitude, Longitude as longitude, Registration_Date as registrationDate, City_id as cityId FROM Data WHERE ${keywordQuery} ORDER BY Data.${TypeToSort} ${Order}")
    List<DataDomain> getDataByKeywordIdAndNotJoin(Map<String, Object> sqlParam);

    @Select("SELECT ID as id, Title as title, Serial_Number as serialnumber, Classification_id as classificationId, Latitude as latitude, Longitude as longitude, Registration_Date as registrationDate, City_id as cityId FROM Data WHERE (${sqlSentence})")
    List<DataDomain> getDataAdvancedSearch(String sqlSentence);

    @Select("SELECT classification_id as classificationId, Large as large, Middle as middle, Small as small FROM Classification WHERE classification_id = #{classId}")
    Classification getClassificationById(int classId);
}
