package com.jeonbuk.mchms.service.user;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.UserDataDomain;
import com.jeonbuk.mchms.domain.UserInfo;
import com.jeonbuk.mchms.domain.UserWriteClassificationCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public Map<String, Object> loginUser(String id, String encPw) throws Exception {

        Map<String, Object> sqlParam = new HashMap<>();

        sqlParam.put("id", id);
        sqlParam.put("pw", encPw);

        return userMapper.loginUser(sqlParam);
    }
    public UserInfo selectUserInfo(String id) {
        return userMapper.selectUserInfo(id);
    }
    public UserInfo selectUserIdNicknameInfo(String id){ return userMapper.selectUserIdNicknameInfo(id);}
    public List<UserWriteClassificationCount> getClassificationCountByUserId(String id) {
        return userMapper.getClassificationCountByUserId(id);
    }

    public List<UserDataDomain> selectUserData(String Registrant){
        return userMapper.selectUserData(Registrant);
    }
}
