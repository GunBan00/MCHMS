package com.jeonbuk.mchms.cont.DoCont;

import com.jeonbuk.mchms.cont.DispacherCont.main.MainController;
import com.jeonbuk.mchms.domain.User;
import com.jeonbuk.mchms.service.data.DataService;
import com.jeonbuk.mchms.service.user.UserService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class UserDoCont {
    private static Logger logger = LoggerFactory.getLogger(UserDoCont.class);

    @Autowired
    UserService userService;

    @Autowired
    DataService dataService;


    @RequestMapping(value = "/MCHMSlogin_process", method = RequestMethod.POST)
    public ModelAndView mCHMSView(ModelAndView mv , HttpServletRequest request, @ModelAttribute User user) {

        mv.setViewName("Main/BASE");

        try {
            HttpSession session = request.getSession();

            Map<String, Object> userInfo = userService.loginUser(user.getUSER_ID(), makePassword(user.getUSER_PASS()));

            logger.info((String)userInfo.get("ID"));


            if(userInfo != null) {
                session.setAttribute("value", "1");
                session.setAttribute("id", (String)userInfo.get("ID"));
            }

            mv.addObject("session", session);

            return mv;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;

    }



    public String makePassword(String decPass) {

        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(decPass.getBytes("UTF-8"), 0, decPass.length());

            String sha1 =  byteToHex(msdDigest.digest());

            msdDigest.update(sha1.getBytes("UTF-8"), 0, sha1.length());
            String sha2 = byteToHex(msdDigest.digest());

            return "*" + sha2;

        }catch (Exception e) {

        }

        return "";
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


}
