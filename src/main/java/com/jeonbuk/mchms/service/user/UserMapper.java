package com.jeonbuk.mchms.service.user;

import com.jeonbuk.mchms.domain.UserDataDomain;
import com.jeonbuk.mchms.domain.UserInfo;
import com.jeonbuk.mchms.domain.UserWriteClassificationCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("SELECT ID, PW FROM User WHERE ID = #{id} AND PW =#{pw}")
    Map<String, Object> loginUser(Map<String, Object> sqlParam);

    @Select("SELECT ID, NAME, EMAIL, NICKNAME FROM User WHERE ID = #{id}")
    UserInfo selectUserInfo(String id);

    @Select("SELECT COUNT(Large) as count, Large as large FROM Data LEFT OUTER JOIN Classification ON (Data.Classification_id = Classification.classification_id) WHERE Data.Registrant = #{id} GROUP BY Large")
    List<UserWriteClassificationCount> getClassificationCountByUserId(String id);

    @Select("SELECT ID as id, Title as title, City_id as cityId, Classification_id as classificationId , Serial_Number as serialNumber, Registration_Date as registrationDate FROM Data WHERE Registrant = #{Registrant}")
    List<UserDataDomain> selectUserData(String Registrant);


}
