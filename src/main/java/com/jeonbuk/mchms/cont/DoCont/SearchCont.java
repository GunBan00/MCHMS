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
    @Autowired
    private FileEventService fileEventService;

    @RequestMapping(value = "/MCHMSSearch", method = {RequestMethod.GET, RequestMethod.POST})
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

                String TypeToSort = request.getParameter("TypeToSort");
                if (TypeToSort == null){
                    TypeToSort = "Title";
                }
                String SortOrder = request.getParameter("SortOrder");
                if (SortOrder == null){
                    SortOrder = "Title0";
                }

                String keyWord = request.getParameter("Keyword");
                Integer OrderNum = Integer.parseInt(SortOrder.substring(SortOrder.length()-1, SortOrder.length()));
                String Previous_Sort = SortOrder.substring(0, SortOrder.length()-1);
                List<DataDomain> totalList;

                if ((Previous_Sort.equals(TypeToSort)) && (OrderNum == 0)){
                    SortOrder = TypeToSort + "1";
                    totalList = dataService.getDataByKeyword(keyWord, TypeToSort, "asc");
                }
                else {
                    SortOrder = TypeToSort + "0";
                    totalList = dataService.getDataByKeyword(keyWord, TypeToSort, "desc");
                }

                int totalLength = totalList.size();

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
                        System.out.println("test2 : " + Category.getLarge());
                        City Cities = cityService.getRegionFromCityId(dataDomain.getCityId());
                        dataDomain.setCiResult(Cities.getCities());
                        System.out.println("test2 : " + Cities.getCities());

                        FileEventDomain fileEventDomain = fileEventService.getFilesNameFromDataID(dataDomain.getId());



                        if (fileEventDomain != null){
                            String filesname = fileEventDomain.getFiles();
                            String[] filesArray = filesname.split("\\|");

                            String ImageFileInMap = filesArray[0];

                            dataDomain.setImageFileInMap(ImageFileInMap);
                        }

                        dataDomain.setIndex(index);
                        System.out.println("test2 : " + dataDomain.getLatitude());
                        System.out.println("test2 : " + dataDomain.getLongitude());
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

                    mv.addObject("SortOrder", SortOrder);
                    mv.addObject("map_latitude", avgLat);
                    mv.addObject("map_longitude", avgLong);
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
                String TypeToSort = request.getParameter("TypeToSort");
                if (TypeToSort == null){
                    TypeToSort = "Title";
                }
                String SortOrder = request.getParameter("SortOrder");
                if (SortOrder == null){
                    SortOrder = "Title0";
                }

                Integer OrderNum = Integer.parseInt(SortOrder.substring(SortOrder.length()-1, SortOrder.length()));
                String Previous_Sort = SortOrder.substring(0, SortOrder.length()-1);
                List<DataDomain> totalList;
                if ((Previous_Sort.equals(TypeToSort)) && (OrderNum == 0)){
                    SortOrder = TypeToSort + "1";
                    if(TypeToSort.equals("Classification")){
                        totalList = dataService.getDataByCityIdAndJoinClassifi(cityId, "asc");
                    }
                    else if(TypeToSort.equals("City")){
                        totalList = dataService.getDataByCityIdAndJoinCity(cityId, "asc");
                    }
                    else {
                        totalList = dataService.getDataByCityIdAndNotJoin(cityId, TypeToSort,"asc");
                    }
                }
                else {
                    SortOrder = TypeToSort + "0";
                    if(TypeToSort.equals("Classification")){
                        totalList = dataService.getDataByCityIdAndJoinClassifi(cityId, "desc");
                    }
                    else if(TypeToSort.equals("City")){
                        totalList = dataService.getDataByCityIdAndJoinCity(cityId, "desc");
                    }
                    else {
                        totalList = dataService.getDataByCityIdAndNotJoin(cityId, TypeToSort,"desc");
                    }
                }

                City Region = cityService.getRegionFromCityId(Integer.parseInt(cityId));

                String RegionName = Region.getCities();
                int totalLength = totalList.size();
                int index = 1;
                for(DataDomain dataDomain : totalList) {
                    Classification Category = classificationService.getCategoryFromClassification(dataDomain.getClassificationId());
                    dataDomain.setClResult(Category.getLarge());

                    System.out.println("test2 : " + dataDomain.getTitle() + index);

                    FileEventDomain fileEventDomain = fileEventService.getFilesNameFromDataID(dataDomain.getId());
                    dataDomain.setIndex(index);
                    index++;

                    if (fileEventDomain != null){
                        String filesname = fileEventDomain.getFiles();
                        String[] filesArray = filesname.split("\\|");

                        String ImageFileInMap = filesArray[0];

                        dataDomain.setImageFileInMap(ImageFileInMap);
                    }
                    else if(totalList.size() + 1 == index){
                        break;
                    }
                }
                System.out.println("test2 : " + totalList.size());

                City selectCityLocation = cityService.getCityLocationFromCityid(Integer.parseInt(cityId));
                if (cityId == "101"){
                    System.out.println("test : " + flag);
                }
                mv.addObject("SortOrder", SortOrder);
                mv.addObject("Region", Region);
                mv.addObject("RegionName", RegionName);
                mv.addObject("map_latitude", selectCityLocation.getLatitude());
                mv.addObject("map_longitude", selectCityLocation.getLongitude());
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