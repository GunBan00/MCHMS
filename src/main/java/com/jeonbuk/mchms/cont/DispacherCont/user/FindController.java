package com.jeonbuk.mchms.cont.DispacherCont.user;


import com.jeonbuk.mchms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class FindController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/MCHMSFind", method = RequestMethod.GET)
    public ModelAndView findIdAndPw(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        mv.addObject("session", session);

        String CFind = request.getParameter("Find");

        System.out.println("test : " + CFind);

        String CImport = request.getParameter("Import");
        System.out.println("test : " + CImport);

        try{
            if (StringUtils.isEmpty(CFind)){
                mv.setViewName("Find/Choice");
            }
            else {
                if (StringUtils.isEmpty(CImport)){
                    if (CFind.equals("PW")){
                        mv.setViewName("Find/Find_PW");
                    }

                    else {
                        mv.setViewName("Find/Find_ID");
                    }
                }
                else {

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }
}