package com.jeonbuk.mchms.cont.DoCont;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.DataDomain;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
import com.jeonbuk.mchms.service.data.DataService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class SearchCont {
    private static Logger logger = LoggerFactory.getLogger(SearchCont.class);

    @Autowired
    private DataService dataService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ClassificationService classificationService;

    @RequestMapping(value = "/MCHMSSearch", method = RequestMethod.GET)
    public ModelAndView base(ModelAndView mv, HttpServletRequest request) {

        try {
            mv.setViewName("Search/MCHMSSearch");

            HttpSession session = request.getSession();
            String cityId = request.getParameter("City_id");

            int flag = 0;
            double avgLat = 0;
            double avgLong = 0;
            List<City> museums = cityService.getMuseums();

           if(StringUtils.isEmpty(cityId)) {
                flag = 1;
                String keyWord = request.getParameter("Keyword");
                List<DataDomain> totalList = dataService.getDataByKeyword(keyWord);
                int totalLength = totalList.size();


//            if(totalLength != 0) {
//                avgLat = 0;
//                avgLong = 0;
//            }

                if(totalLength ==0) {
                    avgLat = 19.75056;
                    avgLong = 96.10056;

                    mv.addObject("total", totalLength);
                    mv.addObject("avg_Lat", avgLat);
                    mv.addObject("avg_Long", avgLong);
                }else {

                    int index = 1;
                    for(DataDomain dataDomain : totalList) {
                        Classification Category = classificationService.getCategoryFromClassification(dataDomain.getClassificationId());

                        dataDomain.setClResult(Category.getLarge());
                        dataDomain.setIndex(index);

                        avgLat = avgLat + dataDomain.getLatitude();
                        avgLong = avgLong + dataDomain.getLongitude();
                        index++;
                    }

                    avgLat = avgLat / totalLength;
                    avgLong = avgLong / totalLength;

                    mv.addObject("avg_Lat", avgLat);
                    mv.addObject("avg_Long", avgLong);
                    mv.addObject("Keyword", keyWord);
                    mv.addObject("total", totalLength);
                    mv.addObject("lists", totalList);
                    mv.addObject("Museum", museums);
                    mv.addObject("City_id", cityId);
                    mv.addObject("Session", session);
                    mv.addObject("flag", flag);
                }
            }
            else {
                List<DataDomain> totalList =  dataService.getDataByCityId(Integer.parseInt(cityId));

                City Rejeon = cityService.getRejeonFromCityId(Integer.parseInt(cityId));

                int totalLength = totalList.size();
                System.out.println("test : " + totalLength);
                int index = 1;
                for(DataDomain dataDomain : totalList) {
                    Classification Category = classificationService.getCategoryFromClassification(dataDomain.getClassificationId());
                    dataDomain.setClResult(Category.getLarge());

                    dataDomain.setIndex(index);

                    avgLat = avgLat + dataDomain.getLatitude();
                    avgLong = avgLong + dataDomain.getLongitude();
                    index++;
                }

                if(totalLength != 0) {
                    avgLat = avgLat / totalLength;
                    avgLong = avgLong / totalLength;
                }else {
                    avgLat = 19.75056;
                    avgLong = 96.10056;
                }

                mv.addObject("Rejeon", Rejeon);
                mv.addObject("avg_Lat", avgLat);
                mv.addObject("avg_Long", avgLong);
                mv.addObject("total", totalLength);
                mv.addObject("lists", totalList);
                mv.addObject("Museum", museums);
                mv.addObject("City_id", cityId);
                mv.addObject("Session", session);
                mv.addObject("flag", flag);

            }

        } catch (Exception e) {
           logger.error(e.toString());
        }


        return mv;
    }
}
