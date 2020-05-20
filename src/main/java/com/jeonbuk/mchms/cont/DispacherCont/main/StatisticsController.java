package com.jeonbuk.mchms.cont.DispacherCont.main;

import com.jeonbuk.mchms.domain.*;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
import com.jeonbuk.mchms.service.data.DataService;
import com.jeonbuk.mchms.service.fileevent.FileEventService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class StatisticsController {

    @Autowired
    private DataService dataService;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private CityService cityService;

    @Autowired
    private FileEventService fileEventService;
    private static Logger logger = LoggerFactory.getLogger(com.jeonbuk.mchms.cont.DispacherCont.main.StatisticsController.class);

    @RequestMapping(value = "/Statistics", method = RequestMethod.GET)
    public ModelAndView base(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        try {

            String Cities = request.getParameter("Cities");
            System.out.println("Cities : " + Cities);
            List<ClassificationCount> classCount = classificationService.getClassificationCountById(Cities);
            List<ClassificationCount> temp = new ArrayList<ClassificationCount>();

            System.out.println("test : " + Cities);
            int count = 0;
            for(ClassificationCount cc : classCount) {

                if(cc.getCount() != 0) {
                    temp.add(cc);
                }
                System.out.println("test : " + cc.getCount());
                System.out.println("test : " + cc.getLarge());
                count += cc.getCount();
            }

            City locationCity = cityService.getCityLocationFromCitiesName(Cities);

            List<DataDomain> totalList = dataService.getDataByCityName(Cities);

            int index = 1;

            for(DataDomain dataDomain : totalList) {
                Classification Category = classificationService.getCategoryFromClassification(dataDomain.getClassificationId());
                dataDomain.setClResult(Category.getLarge());

                FileEventDomain fileEventDomain = fileEventService.getFilesNameFromDataID(dataDomain.getId());

                if (fileEventDomain != null){
                    String filesname = fileEventDomain.getFiles();
                    String[] filesArray = filesname.split("\\|");

                    String ImageFileInMap = filesArray[0];

                    dataDomain.setImageFileInMap(ImageFileInMap);
                }
                dataDomain.setIndex(index);

                index++;
            }

            mv.addObject("x", locationCity.getLatitude());
            mv.addObject("y", locationCity.getLongitude());
            mv.addObject("maplist", totalList);
            mv.addObject("Cities", Cities);
            mv.addObject("classCount", temp);
            mv.addObject("count1", count);

            mv.setViewName("Statistics/Statistics.html");
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return mv;
    }


}