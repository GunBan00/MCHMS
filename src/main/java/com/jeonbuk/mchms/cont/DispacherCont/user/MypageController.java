package com.jeonbuk.mchms.cont.DispacherCont.user;

import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.UserInfo;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MypageController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/MCHMSView", method = RequestMethod.GET)
    public ModelAndView mCHMSView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        mv.addObject("session", session);

        // UserInfo userInfo = userService.getUserInfo(id);
        return mv;
    }
}
