package com.jeonbuk.mchms.service.user;

import com.jeonbuk.mchms.domain.UserDataDomain;
import com.jeonbuk.mchms.domain.UserInfo;
import com.jeonbuk.mchms.domain.UserWriteClassificationCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("SELECT ID, PW FROM User WHERE ID = #{id} AND PW =#{pw}")
    Map<String, Object> loginUser(Map<String, Object> sqlParam);

    @Select("SELECT ID, NAME, EMAIL, NICKNAME FROM User WHERE ID = #{id}")
    UserInfo selectUserInfo(String id);

    @Select("SELECT ID, NICKNAME FROM User WHERE ID = #{id}")
    UserInfo selectUserIdNicknameInfo(String id);

    @Select("SELECT COUNT(Large) as count, Large as large FROM Data LEFT OUTER JOIN Classification ON (Data.Classification_id = Classification.classification_id) WHERE Data.Registrant = #{id} GROUP BY Large")
    List<UserWriteClassificationCount> getClassificationCountByUserId(String id);

    @Select("SELECT ID as id, Title as title, City_id as cityId, Classification_id as classificationId , Serial_Number as serialNumber, Registration_Date as registrationDate FROM Data WHERE Registrant = #{Registrant}")
    List<UserDataDomain> selectUserData(String Registrant);

    @Select("SELECT ID FROM User WHERE NAME = #{name}")
    UserInfo FindUserDataForIdAndName(String name);

    @Select("SELECT ID FROM User WHERE EMAIL = #{email}")
    UserInfo FindUserDataForIdAndEmail(String email);

    @Select("SELECT ID FROM User WHERE ID = #{ID} AND NAME = #{Kinds}")
    UserInfo FindUserDataForPWAndName(Map<String, Object> sqlParam);

    @Select("SELECT ID FROM User WHERE ID = #{ID} AND EMAIL = #{Kinds}")
    UserInfo FindUserDataForPWAndEmail(Map<String, Object> sqlParam);

    @Update("UPDATE User SET PW = #{encPw} WHERE ID = #{ID}")
    void changePassword(Map<String, Object> sqlParam);

}
