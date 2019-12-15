package com.jeonbuk.mchms.cont.DispacherCont.main;


import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.DataDomain;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.data.DataService;

import groovy.util.logging.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class MainController {

    @Autowired
    DataService dataService;

    @Autowired
    CityService cityService;

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView base(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        try {

            List<City> cities = cityService.getCities();
            List<City> museums = cityService.getMuseums();

            mv.addObject("City", cities);
            mv.addObject("Museum", museums);
            mv.addObject("MID_Page", "Main/Main.html");

            mv.setViewName("Main/BASE");
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return mv;
    }


}