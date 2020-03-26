package com.jeonbuk.mchms.cont.DoCont;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.DataDomain;
import com.jeonbuk.mchms.domain.FileEventDomain;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ASearchCont {
    private static Logger logger = LoggerFactory.getLogger(SearchCont.class);
    @Autowired
    private DataService dataService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ClassificationService classificationService;
    @Autowired
    private FileEventService fileEventService;

    @RequestMapping(value = "/MCHMSSearch_process", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView base(ModelAndView mv, HttpServletRequest request) {
        int flag = 2;
        double avgLat = 0;
        double avgLong = 0;

        try {
            Map<String, String> Param = new HashMap<>();

            List<City> museums = cityService.getMuseums();

            mv.setViewName("Search/MCHMSSearch");
            HttpSession session = request.getSession();
            String LARGE_SEQ = request.getParameter("LARGE_SEQ");
            String MEDIUM_SEQ = request.getParameter("MEDIUM_SEQ");
            String SMALL_SEQ = request.getParameter("SMALL_SEQ");
            String SUB_SEQ = request.getParameter("SUB_SEQ");
            String SEL_SEQ = request.getParameter("SEL_SEQ");

            String CITY_SEQ = request.getParameter("CITY_SEQ");
            String MUSEUM_SEQ = request.getParameter("MUSEUM_SEQ");
            String SEL_CITY = request.getParameter("SEL_CITY");

            String SRCH_TITLE = request.getParameter("SRCH_TITLE");
            String SEL_TITLE = request.getParameter("SEL_TITLE");

            String SRCH_REMARK = request.getParameter("SRCH_REMARK");
            String SEL_REMARK = request.getParameter("SEL_REMARK");

            String SRCH_REG = request.getParameter("SRCH_REG");
            String SEL_REG = request.getParameter("SEL_REG");

            String SRCH_REG_SRT_DATE = request.getParameter("SRCH_REG_SRT_DATE");
            String SRCH_REG_END_DATE = request.getParameter("SRCH_REG_END_DATE");
            String SEL_DATE = request.getParameter("SEL_DATE");

            String SRCH_RELIC_NUM = request.getParameter("SRCH_RELIC_NUM");
            String SEL_RELIC_NUM = request.getParameter("SEL_RELIC_NUM");

            int clid = classificationService.getClassificationIdByCategory(LARGE_SEQ, MEDIUM_SEQ, SMALL_SEQ, SUB_SEQ);
            String ctid = cityService.getCityIdByCategory(CITY_SEQ, MUSEUM_SEQ);

            System.out.println("test : " + clid);

            Param.put("SEL_SEQ", SEL_SEQ);
            Param.put("SEL_CITY", SEL_CITY);
            Param.put("SRCH_TITLE", SRCH_TITLE);
            Param.put("SEL_TITLE", SEL_TITLE);
            Param.put("SRCH_REMARK", SRCH_REMARK);
            Param.put("SEL_REMARK", SEL_REMARK);
            Param.put("SRCH_REG", SRCH_REG);
            Param.put("SEL_REG", SEL_REG);
            Param.put("SRCH_REG_SRT_DATE", SRCH_REG_SRT_DATE);
            Param.put("SRCH_REG_END_DATE", SRCH_REG_END_DATE);
            Param.put("SEL_DATE", SEL_DATE);
            Param.put("SRCH_RELIC_NUM", SRCH_RELIC_NUM);
            Param.put("SEL_RELIC_NUM", SEL_RELIC_NUM);
            Param.put("clid", Integer.toString(clid));
            Param.put("ctid", ctid);

            List<DataDomain> totalList = dataService.getDataAdvancedSearch(Param);
            int totalLength = totalList.size();
            System.out.println("test : " + totalLength);
            if(totalLength == 0) {
                avgLat = 19.75056;
                avgLong = 96.10056;
                mv.addObject("total", totalLength);
                mv.addObject("avg_Lat", avgLat);
                mv.addObject("avg_Long", avgLong);
            }

            else {
                int index = 1;
                for(DataDomain dataDomain : totalList) {
                    Classification Category = classificationService.getCategoryFromClassification(dataDomain.getClassificationId());
                    dataDomain.setClResult(Category.getLarge());

                    City Cities = cityService.getRegionFromCityId(dataDomain.getCityId());
                    dataDomain.setCiResult(Cities.getCities());

                    FileEventDomain fileEventDomain = fileEventService.getFilesNameFromDataID(dataDomain.getId());



                    if (fileEventDomain != null){
                        String filesname = fileEventDomain.getFiles();
                        String[] filesArray = filesname.split("\\|");

                        String ImageFileInMap = filesArray[0];

                        dataDomain.setImageFileInMap(ImageFileInMap);
                    }

                    dataDomain.setIndex(index);

                    if (dataDomain.getLatitude() != 0){
                        avgLat = avgLat + dataDomain.getLatitude();
                    }
                    if (dataDomain.getLongitude() != 0) {
                        avgLong = avgLong + dataDomain.getLongitude();
                    }
                    index++;
                }
                if (totalLength != 0){
                    avgLat = avgLat / totalLength;
                    avgLong = avgLong / totalLength;
                }
                if (avgLat == 0 || avgLong == 0){
                    avgLat = 19.75056;
                    avgLong = 96.10056;
                }
                mv.addObject("map_latitude", avgLat);
                mv.addObject("map_longitude", avgLong);
                mv.addObject("total", totalLength);
                mv.addObject("lists", totalList);
                mv.addObject("Museum", museums);
                mv.addObject("Session", session);
                mv.addObject("flag", flag);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return mv;
    }
}
