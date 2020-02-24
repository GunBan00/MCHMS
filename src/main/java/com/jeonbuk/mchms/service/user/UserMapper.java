package com.jeonbuk.mchms.service.user;

import com.jeonbuk.mchms.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("SELECT ID, PW FROM User WHERE ID = #{id} AND PW =#{pw}")
    Map<String, Object> loginUser(Map<String, Object> sqlParam);

    @Select("SELECT ID, NAME, EMAIL, NICKNAME FROM User WHERE ID = #{id}")
    UserInfo getUserInfo(String id);

}
