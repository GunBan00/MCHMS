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
            mv.addObject("MID_Page", "Search/MCHMSSearch.html");

            mv.setViewName("Contents_BASE");

            HttpSession session = request.getSession();
            String cityId = request.getParameter("City_id");
            int flag = 0;
            double avgLat = 0;
            double avgLong = 0;

            //////////////////////////////////////////////////////////////////////
            int pageNumber = 0;
            int dataForPage = 10;
            int startPage = 0;
            int icp=0;
            int startNum=1;

            String currentPage = null;
            currentPage = request.getParameter("currentPage");
            System.out.print("currentPage : ");
            System.out.println(currentPage);
            if(currentPage == null)
            {
                currentPage = "0";
                startNum = 0;
                icp =0;
            }
            else
            {
                icp = Integer.parseInt(currentPage);
                startNum =(icp-1)*10;
            }

            //////////////////////////////////////////////////////////////////////

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
                System.out.println("test2 : " + totalList.size());

                City Region = cityService.getRegionFromCityId(Integer.parseInt(cityId));

                String RegionName = Region.getCities();
                int totalLength = totalList.size();

                ///////////////////////////////////////////////////////////////////
                double a = Double.valueOf(totalLength);
                double b = Double.valueOf(dataForPage);

                pageNumber = (int)Math.ceil(a/b);
                int pageNumberList[] = new int[10];
                System.out.println(pageNumber);
                if(pageNumber > 10){
                    if(icp > 6){
                        if(icp+5 < pageNumber) {
                            pageNumberList = new int[10];
                            int k = 0;
                            for (int i = icp - 4; i <= icp + 5; i++) {
                                pageNumberList[k] = i + 1;
                                k++;
                            }
                        }
                        else
                        {
                            pageNumberList = new int[(pageNumber-icp+4)];
                            int k = 0;
                            for (int i = icp - 4; i < pageNumber; i++) {
                                pageNumberList[k] = i + 1;
                                k++;
                            }

                        }
                    }
                    else{
                        pageNumberList = new int[10];
                        for(int i = 0; i < 10; i++){
                            pageNumberList[i] = i + 1;
                        }
                    }
                }
                else{
                    pageNumberList = new int[pageNumber];
                    for (int i = 0; i < pageNumber; i++) {
                        pageNumberList[i] = i + 1;
                    }
                }
                ///////////////////////////////////////////////////////////////////


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

                    avgLat = avgLat + dataDomain.getLatitude();
                    avgLong = avgLong + dataDomain.getLongitude();
                    index++;
                }
                int k = 0;
                DataDomain[] pageList;
                int as = totalLength - (icp*10) + 10;
                if(as < 10) {
                    pageList = new DataDomain[as];
                }
                else
                {
                    pageList = new DataDomain[10];
                }
                System.out.println(as);
                for(int i = startNum; i < startNum + dataForPage; i++) {
                    if(i >= totalLength) break;
                    pageList[k] = totalList.get(i);
                    k++;
                }
                City selectCityLocation = cityService.getCityLocationFromCityid(Integer.parseInt(cityId));

                mv.addObject("SortOrder", SortOrder);
                mv.addObject("TypeToSort", TypeToSort);
                mv.addObject("Region", Region);
                mv.addObject("RegionName", RegionName);
                mv.addObject("map_longitude", selectCityLocation.getLongitude());
                mv.addObject("map_latitude", selectCityLocation.getLatitude());
                mv.addObject("total", totalLength);
                mv.addObject("pageNumberList", pageNumberList);
                mv.addObject("pageNumber", pageNumber);
                mv.addObject("lists", pageList);
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