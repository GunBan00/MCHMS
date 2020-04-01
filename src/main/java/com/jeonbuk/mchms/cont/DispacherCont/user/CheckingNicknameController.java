package com.jeonbuk.mchms.cont.DispacherCont.user;

import com.jeonbuk.mchms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class CheckingNicknameController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/Checking_Nickname")
    public HashMap<String, Object> Checking_Nickname(@RequestBody String User_Nickname){

        HashMap<String, Object> nicknameMap = new HashMap<>();

        System.out.println("test : " + User_Nickname);
        int checkNickname = userService.CheckingUserNicknameProcess(User_Nickname);

        String CHK_Nickname;

        if(checkNickname == 1){
            CHK_Nickname = "N";
        }
        else {
            CHK_Nickname = "Y";
        }

        nicknameMap.put("CHK_Nickname", CHK_Nickname);

        return nicknameMap;
    }
}
