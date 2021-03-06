package com.jeonbuk.mchms.cont.DispacherCont.main;


import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.DataDomain;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
import com.jeonbuk.mchms.service.data.DataService;

import com.jeonbuk.mchms.service.user.UserService;
import groovy.util.logging.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import org.springframework.mobile.device.*;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;



    @Controller
    @Slf4j
    public class MainController {

        @Autowired
        DataService dataService;

        @Autowired
        CityService cityService;

        @Autowired
        UserService userService;

        @Autowired
        ClassificationService classificationService;

        private static Logger logger = LoggerFactory.getLogger(MainController.class);
        public static final String IS_MOBILE = "MOBILE";
        private static final String IS_PHONE = "PHONE";
        public static final String IS_TABLET = "TABLET";
        public static final String IS_PC = "PC";
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView base(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        String userAgent;
        if(request.getHeader("User-Agent") != null) {
            userAgent = request.getHeader("User-Agent").toUpperCase();
            if (userAgent.indexOf(IS_MOBILE) > -1) {

                mv.addObject("MID_Page", "MView/Main.html");
                mv.setViewName("MView/Base");
            } else {
                mv.addObject("MID_Page", "Main/Main.html");

                mv.setViewName("Main/BASE");
            }
        }
        else
        {
            mv.addObject("MID_Page", "Main/Main.html");

            mv.setViewName("Main/BASE");
        }


        HttpSession session = request.getSession();

        String grade = userService.checkingUserGrade(String.valueOf(session.getAttribute("id")));
        System.out.println(grade);
        try {

            List<City> Cities = cityService.getCities();
            List<City> Museum = cityService.getMuseums();

            String CitiesContents = "<option value=\"\">:::: REGION ::::</option>";
            for(City cities : Cities) {
                CitiesContents = CitiesContents + "<option id=\"SEQ_CITY\"  value=\""+ cities.getCities() + "\">"+ cities.getCities() +"</option>";
            }

            String MuseumContents = "<option value=\"\" selected=\"selected\">:::: Division ::::</option>";
            for(City museum : Museum) {
                MuseumContents = MuseumContents + "<option class=\"" + museum.getCities() + "\" value=\""+ museum.getMuseum() + "\" style = \"display:none\">" + museum.getMuseum() +"</option>";
            }

            mv.addObject("CitiesContents", CitiesContents);
            mv.addObject("MuseumContents", MuseumContents);

            List<Classification> Large = classificationService.getLarge();
            List<Classification> Middle = classificationService.getMiddle();
            List<Classification> Small = classificationService.getSmall();
            List<Classification> SubSection = classificationService.getSubSection();

            String LargeContents = "<option value=\"\">:::: Category ::::</option>";
            for(Classification large : Large) {
                LargeContents = LargeContents + "<option id=\"SEL_LARGE\"  value=\""+ large.getLarge() + "\">"+ large.getLarge() +"</option>";
            }

            String MiddleContents = "<option value=\"\" selected=\"selected\">:::: Division ::::</option>";
            for(Classification middle : Middle) {
                if(!(middle.getMiddle().equals("")))
                MiddleContents = MiddleContents + "<option class=\"middle " + middle.getLarge() + "\" id=\"" + middle.getLarge() + "\" name=\"" + middle.getLarge() + "\" value=\""+ middle.getMiddle() + "\" style = \"display:none\">" + middle.getMiddle() +"</option>";
            }

            String SmallContents = "<option value=\"\">:::: Section ::::</option>";
            for(Classification small : Small) {
                if (!((small.getLarge()).equals(""))) {
                    SmallContents = SmallContents + "<option class=\"small " + small.getMiddle() + "\" value=\"" + small.getSmall() + "\" style = \"display:none\">" + small.getSmall() + "</option>";
                }
            }

            String SubSectionContents = "<option value=\"\">:::: Sub Section ::::</option>";
            for(Classification subSection : SubSection) {
                if (!((subSection.getLarge()).equals(""))) {
                    if ((subSection.getMiddle()).equals("Buddha_Museum")) {
                        SubSectionContents = SubSectionContents + "<option class=\"sub_small " + subSection.getSmall() + "\" value=\"" + subSection.getSubSection() + "\" style = \"display:none\">" + subSection.getSubSection() + "</option>";
                    }
                }
            }

            mv.addObject("LargeContents", LargeContents);
            mv.addObject("MiddleContents", MiddleContents);
            mv.addObject("SmallContents", SmallContents);
            mv.addObject("SubSectionContents", SubSectionContents);

            mv.addObject("City", Cities);
            mv.addObject("Museum", Museum);




        } catch (Exception e) {
            System.out.println("asdf");
            logger.error(e.toString());
        }

        return mv;
    }


}