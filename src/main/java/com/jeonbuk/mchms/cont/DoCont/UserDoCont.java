package com.jeonbuk.mchms.cont.DoCont;

import com.jeonbuk.mchms.cont.DispacherCont.main.MainController;
import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.User;
import com.jeonbuk.mchms.service.city.CityService;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
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

    @Autowired
    CityService cityService;


    @RequestMapping(value = "/MCHMSlogin_process", method = RequestMethod.POST)
    public ModelAndView mCHMSView(ModelAndView mv , HttpServletRequest request,HttpServletResponse response, @ModelAttribute User user) throws IOException {

        mv.setViewName("Main/BASE");

        try {
            HttpSession session = request.getSession();

            Map<String, Object> userInfo = userService.loginUser(user.getUSER_ID(), makePassword(user.getPassword()));
            System.out.println(userInfo);
            logger.info((String)userInfo.get("ID"));


            if(userInfo != null) {
                session.setAttribute("value", "1");
                session.setAttribute("id", (String)userInfo.get("ID"));
            }

            List<City> cities = cityService.getCities();
            List<City> museums = cityService.getMuseums();

            mv.addObject("City", cities);
            mv.addObject("Museum", museums);
            mv.addObject("MID_Page", "Main/Main.html");

            mv.addObject("session", session);

            return new ModelAndView("redirect:/");
        } catch (Exception e) {

            ModelAndView modelAndView = new ModelAndView("redirect:/MCHMSLogin");


            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out_equals = response.getWriter();
            out_equals.println("<script type = 'text/javascript'> alert('Login Failed! Invalid ID and/or Password, Please try again');location.href='/MCHMSLogin';</script>");

            out_equals.flush();

            return modelAndView;
        }


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

    @RequestMapping(value = "/MCHMSLogout")
    public ModelAndView mchmsLogout(ModelAndView mv , HttpServletRequest request, HttpServletResponse response, @ModelAttribute User user){
        try {
            request.getSession().removeAttribute("value");
            request.getSession().removeAttribute("id");

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out_equals = response.getWriter();

            out_equals.println("<script type = 'text/javascript'> alert('You have successfully logged out.');location.href='/';</script>");
            out_equals.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/Apply_process", method = RequestMethod.POST)
    public ModelAndView Apply_process(ModelAndView mv, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> obj = new HashMap<>();

            obj.put("USER_ID", request.getParameter("USER_ID"));
            obj.put("password", makePassword(request.getParameter("password")));
            obj.put("USER_NAME", request.getParameter("USER_NAME"));
            obj.put("USER_EMAIL", request.getParameter("USER_EMAIL"));
            obj.put("USER_NICK", request.getParameter("USER_NICK"));
            obj.put("NoPermission", 3);

            userService.setUser(obj);

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out_equals = response.getWriter();

            out_equals.println("<script type = 'text/javascript'> alert('Apply completed.');location.href='/';</script>");
            out_equals.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }


}
