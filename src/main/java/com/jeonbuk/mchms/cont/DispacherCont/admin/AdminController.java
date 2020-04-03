package com.jeonbuk.mchms.cont.DispacherCont.admin;

import com.jeonbuk.mchms.cont.DispacherCont.main.MainController;
import com.jeonbuk.mchms.domain.UserInfo;
import com.jeonbuk.mchms.service.data.DataService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@Slf4j
public class AdminController {
    @Autowired
    UserService userService;

    private static Logger logger = LoggerFactory.getLogger(MainController.class);
    @RequestMapping(value = "/MCHMSAdmin", method = RequestMethod.GET)
    public ModelAndView base(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ModelAndView mv = new ModelAndView();

        String adminId = "kkjy33";
        HttpSession session = request.getSession();
        if(String.valueOf(session.getAttribute("id")) != adminId)
        {
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('It is private data'); location.href='/';</script>");
            out.flush();
            mv.setViewName("/");

            return mv;
        }
        try {
            List<UserInfo> userInfo = userService.selectNotPermittedUser();

            mv.addObject("users", userInfo);
            mv.setViewName("Admin/MCHMSAdmin.html");
        }
        catch (Exception e) {
            logger.error(e.toString());
        }

        return mv;
    }
}
