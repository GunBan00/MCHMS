package com.jeonbuk.mchms.cont.DoCont;

import com.jeonbuk.mchms.domain.UserInfo;
import com.jeonbuk.mchms.service.user.UserService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Formatter;

@Controller
@Slf4j
public class FindProcessCont {
    private static Logger logger = LoggerFactory.getLogger(FindProcessCont.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/FindProcess", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView findpro(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {

        String type = request.getParameter("Type");
        System.out.println("type : " + type);
        String userId = "";
        try{
            if (type.equals("ID")){
                String ID_Type = request.getParameter("ID_Type");
                System.out.println("ID_Type : " + ID_Type);
                if (ID_Type.equals("Name")){
                    String Find_ID_Name = request.getParameter("Find_ID_Name");
                    userId = userService.FindUserDataForID(ID_Type, Find_ID_Name);
                }
                else {
                    String Find_ID_Email = request.getParameter("Find_ID_Email");
                    userId = userService.FindUserDataForID(ID_Type, Find_ID_Email);
                }

                System.out.println("userInfo : " + userId);

                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out_equals = response.getWriter();

                if (userId == "none"){
                    System.out.println("userInfo : " + 0);
                    out_equals.println("<script>alert('There is no matching ID'); history.go(-1);</script>");
                    out_equals.flush();

                    return mv;
                }
                else {
                    System.out.println("userInfo : " + 1);
                    out_equals.println("<script>alert('The Users ID is " + userId + ".'); location.replace(\"/\");</script>");
                    out_equals.flush();

                    return mv;
                }
            }
            else if (type.equals("PW")){
                String PW_Type = request.getParameter("PW_Type");
                System.out.println("ID_Type : " + PW_Type);
                String Find_PW_ID = request.getParameter("Find_PW_ID");

                if (PW_Type.equals("Name")){
                    String Find_PW_Name = request.getParameter("Find_PW_Name");
                    userId = userService.FindUserDataForPW(PW_Type, Find_PW_ID, Find_PW_Name);
                }
                else {
                    String Find_PW_Email = request.getParameter("Find_PW_Email");
                    userId = userService.FindUserDataForPW(PW_Type, Find_PW_ID, Find_PW_Email);
                }

                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out_equals = response.getWriter();

                if (userId.equals("none")){

                    out_equals.println("<script>alert('There is no matching Password.'); history.go(-1);</script>");
                    out_equals.flush();

                    return mv;
                }
                else {
                    mv.setViewName("Find/ChangePW");
                    mv.addObject("userId", userId);

                    return mv;
                }
            }
            else{
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out_equals = response.getWriter();

                userId = request.getParameter("ID");
                String PW = request.getParameter("password");

                userService.changePassword(userId, makePassword(PW));

                out_equals.println("<script>alert('Change Password.'); location.replace(\"/\");</script>");
                out_equals.flush();

                return mv;

            }

        } catch(Exception e){
            logger.error(e.toString());
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
