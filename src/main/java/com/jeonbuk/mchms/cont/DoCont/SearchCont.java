package com.jeonbuk.mchms.cont.DoCont;

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
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class SearchCont {
    private static Logger logger = LoggerFactory.getLogger(SearchCont.class);

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/MCHMSSearch", method = RequestMethod.GET)
    public ModelAndView base(ModelAndView mv, HttpServletRequest request) {

            String cityId = request.getParameter("City_id");
            String keyWord = request.getParameter("Keyword");

        try {
            List<Map<String, Object>> totalList = dataService.getDataByKeyword(keyWord);
            int totalLength = totalList.size();
            double avgLat;
            double avgLong;


            if(totalLength != 0) {
                avgLat = 0;
                avgLong = 0;
            }






            if(totalLength ==0) {
                avgLat = 19.75056;
                avgLong = 96.10056;

                mv.addObject("total", totalLength);
                mv.addObject("avg_Lat", avgLat);
                mv.addObject("avg_Long", avgLong);
            }else {
                int pi = 0;

                Object[][] desc= new Object[8][totalLength];

                for(int i = 0; i < totalList.size(); i++) {
                    desc[i][0] = i+1;
                    int classId = (Integer)totalList.get(i).get("City_id");





                }





            }





        } catch (Exception e) {
           logger.error(e.toString());
        }


        return null;
    }
}
