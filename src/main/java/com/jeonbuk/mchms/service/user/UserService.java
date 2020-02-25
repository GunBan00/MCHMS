package com.jeonbuk.mchms.service.user;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.UserInfo;
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
}
