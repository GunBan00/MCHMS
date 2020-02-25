package com.jeonbuk.mchms.cont.DispacherCont.main;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.ClassificationCount;
import com.jeonbuk.mchms.domain.DataDomain;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
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

@Controller
@Slf4j
public class StatisticsController {

        @Autowired
        DataService dataService;

        @Autowired
        ClassificationService classificationService;

        private static Logger logger = LoggerFactory.getLogger(com.jeonbuk.mchms.cont.DispacherCont.main.StatisticsController.class);

        @RequestMapping(value = "/Statistics", method = RequestMethod.GET)
        public ModelAndView base(HttpServletRequest request) {
            System.out.println("test");
            ModelAndView mv = new ModelAndView();

            try {
                String Cities = request.getParameter("Cities");
                List<ClassificationCount> classCount = classificationService.getClassificationCountById(Cities);

                for(ClassificationCount cc : classCount) {
                    System.out.println("test : " + cc.getCount());
                    System.out.println("test : " + cc.getLarge());
                }
                mv.addObject("Cities", Cities);
                mv.addObject("classCount", classCount);


                mv.setViewName("Statistics/Statistics.html");
            } catch (Exception e) {
                logger.error(e.toString());
            }

            return mv;
        }


    }