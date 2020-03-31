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

    public String FindUserDataForID (String Type, String Kinds){
        if (Type.equals("Name")){
            UserInfo userInfo = userMapper.FindUserDataForIdAndName(Kinds);

            if (userInfo != null){
                return userInfo.getId();
            }
            else {
                return "none";
            }
        }
        else {
            UserInfo userInfo = userMapper.FindUserDataForIdAndEmail(Kinds);

            if (userInfo != null){
                return userInfo.getId();
            }
            else {
                return "none";
            }
        }
    }
    public String FindUserDataForPW (String Type, String ID, String Kinds){
        Map<String, Object> sqlParam = new HashMap<>();

        sqlParam.put("ID", ID);
        sqlParam.put("Kinds", Kinds);

        if (Type.equals("Name")){
            UserInfo userInfo = userMapper.FindUserDataForPWAndName(sqlParam);

            if (userInfo != null){
                return userInfo.getId();
            }
            else {
                return "none";
            }
        }
        else {
            UserInfo userInfo = userMapper.FindUserDataForPWAndEmail(sqlParam);

            if (userInfo != null){
                return userInfo.getId();
            }
            else {
                return "none";
            }
        }
    }
    public void changePassword(String ID, String encPw) throws Exception {

        Map<String, Object> sqlParam = new HashMap<>();

        sqlParam.put("ID", ID);
        sqlParam.put("encPw", encPw);

        userMapper.changePassword(sqlParam);
    }

    public int CheckingUserIDProcess(String ID){
        return userMapper.CheckingUserIDProcess(ID);
    }

    public int CheckingUserNicknameProcess(String NICKNAME){
        return userMapper.CheckingUserNicknameProcess(NICKNAME);
    }

    public void setUser(Map<String, Object> sqlParam) throws Exception {
        userMapper.setUser(sqlParam);
    }
}
