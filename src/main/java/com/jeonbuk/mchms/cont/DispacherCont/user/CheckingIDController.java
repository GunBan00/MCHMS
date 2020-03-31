package com.jeonbuk.mchms.cont.DispacherCont.user;

import com.jeonbuk.mchms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CheckingIDController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/Checking_ID")
    public HashMap<String, Object> Checking_Nickname(@RequestBody String User_ID){
        HashMap<String, Object> map = new HashMap<>();

        System.out.println("test : " + User_ID);
        int checkID = userService.CheckingUserIDProcess(User_ID);
        
        String CHK_ID;

        if(checkID == 1){
            CHK_ID = "N";
        }
        else {
            CHK_ID = "Y";
        }

        map.put("CHK_ID", CHK_ID);
        return map;
    }
}
