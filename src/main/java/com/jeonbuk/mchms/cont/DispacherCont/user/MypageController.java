package com.jeonbuk.mchms.cont.DispacherCont.user;

import com.jeonbuk.mchms.domain.*;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
import com.jeonbuk.mchms.service.user.UserService;
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
import java.util.List;

@Controller
public class MypageController {

    @Autowired
    UserService userService;

    @Autowired
    ClassificationService classificationService;

    @Autowired
    CityService cityService;
    private static Logger logger = LoggerFactory.getLogger(com.jeonbuk.mchms.cont.DispacherCont.main.StatisticsController.class);

    @RequestMapping(value = "/Mypage", method = RequestMethod.GET)
    public ModelAndView mypage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        mv.addObject("session", session);
        String id = (String) session.getAttribute("id");

        try {
            List<UserWriteClassificationCount> UsersClassificationCount = userService.getClassificationCountByUserId(id);
            for(UserWriteClassificationCount cc : UsersClassificationCount) {
                System.out.println("test : " + cc.getCount());
                System.out.println("test : " + cc.getLarge());
            }
            List<UserDataDomain> Userlists = userService.selectUserData(id);
            int index = 1;
            for(UserDataDomain UserData : Userlists) {
                Classification Category = classificationService.getCategoryFromClassification(UserData.getClassificationId());
                UserData.setClResult(Category.getLarge());

                City Rejeon = cityService.getRejeonFromCityId(UserData.getCityId());
                UserData.setCiResult(Rejeon.getCities());

                UserData.setIndex(index);
                index++;
            }

            mv.addObject("UsersClassificationCount", UsersClassificationCount);
            mv.addObject("lists", Userlists);

        } catch (Exception e) {
            e.printStackTrace();
        }

        UserInfo userinfo = userService.selectUserInfo(id);

        mv.addObject("userinfo", userinfo);
        mv.setViewName("Mypage/Mypage");

        return mv;
    }
}
