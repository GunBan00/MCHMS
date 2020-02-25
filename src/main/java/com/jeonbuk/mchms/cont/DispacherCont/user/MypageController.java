package com.jeonbuk.mchms.cont.DispacherCont.user;

import com.jeonbuk.mchms.domain.City;
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
import java.util.List;

@Controller
public class MypageController {

    @Autowired
    UserService userService;

    @Autowired
    CityService cityService;

        @RequestMapping(value = "/Mypage", method = RequestMethod.GET)
        public ModelAndView mypage(HttpServletRequest request, HttpServletResponse response) {
            ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        mv.addObject("session", session);
        String id = (String) session.getAttribute("id");

        try {
            List<City> cities = cityService.getCities();
            List<City> museums = cityService.getMuseums();
            mv.addObject("City", cities);
            mv.addObject("Museum", museums);

        } catch (Exception e) {
            e.printStackTrace();
        }

        UserInfo userinfo = userService.selectUserInfo(id);

        mv.addObject("userinfo", userinfo);
        mv.setViewName("Mypage/Mypage");

        return mv;
    }
}
