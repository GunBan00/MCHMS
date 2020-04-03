package com.jeonbuk.mchms.cont.DoCont;

import com.jeonbuk.mchms.cont.DispacherCont.main.MainController;
import com.jeonbuk.mchms.service.user.UserService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Controller
public class ApprovalCont {

    @Autowired
    UserService userService;


    @ResponseBody
    @RequestMapping("/Approving_Process")
    public HashMap<String, Object> changeGrade(@RequestBody String User_ID) {

        HashMap<String, Object> map = new HashMap<>();
        int idl = User_ID.length();

        String userId = User_ID.substring(1,idl-1);
        String CHK_ID = userService.changeGrade(userId);

        System.out.println(CHK_ID);
        map.put("CHK_ID", CHK_ID);
        return map;

        }
}
