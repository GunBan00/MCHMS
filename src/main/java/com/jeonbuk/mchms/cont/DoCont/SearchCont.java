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
            HttpSession session = request.getSession();

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
            mv.addObject("Session", session);

            mv.addObject("City", Cities);
            mv.addObject("Museum", Museum);
            mv.addObject("MID_Page", "Search/MCHMSSearch.html");

            mv.setViewName("Contents_Base.html");
            String cityId = request.getParameter("City_id");
            int flag = 0;
            int pageflag = 1;
            double avgLat = 0;
            double avgLong = 0;

            //////////////////////////////////////////////////////////////////////
            int pageNumber = 0;
            int dataForPage = 10;
            int startPage = 0;
            int intCurrentPage=0;
            int startNum=1;

            String currentPage = null;
            currentPage = request.getParameter("currentPage");
            if(currentPage == null)
            {
                currentPage = "0";
                startNum = 0;
                intCurrentPage =0;
            }
            else
            {
                intCurrentPage = Integer.parseInt(currentPage);
                startNum =(intCurrentPage-1)*10;
            }

            //////////////////////////////////////////////////////////////////////

            List<City> museums = cityService.getMuseums();
            if(StringUtils.isEmpty(cityId)) {
                int zoom = 5;
                mv.addObject("zoom", zoom);

                double map_latitude = 19.75056;
                double map_longitude = 96.10056;

                String TypeToSort = request.getParameter("TypeToSort");
                if (TypeToSort == null){
                    TypeToSort = "Title";
                }

                String SortOrder = request.getParameter("SortOrder");
                if (SortOrder == null){
                    SortOrder = "Title0";
                }
                System.out.println(SortOrder);
                flag = 1;
                String keyWord = request.getParameter("Keyword");

                System.out.println("test : " + keyWord);
                Integer OrderNum = Integer.parseInt(SortOrder.substring(SortOrder.length()-1, SortOrder.length()));
                String Previous_Sort = SortOrder.substring(0, SortOrder.length()-1);
                List<DataDomain> totalList;

                if ((Previous_Sort.equals(TypeToSort)) && (OrderNum == 0)){
                    SortOrder = TypeToSort + "1";
                    totalList = dataService.getDataByKeyword(keyWord, TypeToSort,"asc");
                }
                else {
                    SortOrder = TypeToSort + "0";
                    totalList = dataService.getDataByKeyword(keyWord, TypeToSort,"desc");
                }

                int totalLength = totalList.size();
                System.out.println(totalList.size());

                if(totalLength == 0) {
                    pageflag = 0;
                    String[] pageList = {};
                    int firstFlag = 0;
                    int pageNumberList[] = new int[10];

                    mv.addObject("map_longitude", map_longitude);
                    mv.addObject("map_latitude", map_latitude);

                    mv.addObject("Keyword", keyWord);
                    mv.addObject("total", totalLength);
                    mv.addObject("firstFlag", firstFlag);
                    mv.addObject("flag", flag);
                    mv.addObject("avg_Lat", avgLat);
                    mv.addObject("avg_Long", avgLong);
                    mv.addObject("lists", pageList);
                    mv.addObject("Session", session);
                    mv.addObject("currentPage", currentPage);
                    mv.addObject("pageNumberList", pageNumberList);
                    mv.addObject("pageflag", pageflag);

                    return mv;
                }

                ///////////////////////////////////////////////////////////////////
                double a = Double.valueOf(totalLength);
                double b = Double.valueOf(dataForPage);


                pageNumber = (int)Math.ceil(a/b);
                int pageNumberList[];
                if(pageNumber > 10){
                    if(intCurrentPage > 6){
                        if(intCurrentPage+5 < pageNumber) {
                            pageNumberList = new int[10];
                            int k = 0;
                            for (int i = intCurrentPage - 4; i <= intCurrentPage + 5; i++) {
                                pageNumberList[k] = i + 1;
                                k++;
                            }
                        }
                        else
                        {
                            pageNumberList = new int[(pageNumber-intCurrentPage+4)];
                            int k = 0;
                            for (int i = intCurrentPage - 4; i < pageNumber; i++) {
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
                    Classification clCategory = classificationService.getCategoryFromClassification(dataDomain.getClassificationId());
                    dataDomain.setClResult(clCategory.getLarge());
                    City ciCategory = cityService.getRegionFromCityId(dataDomain.getCityId());
                    dataDomain.setCiResult(ciCategory.getCities());
                    System.out.println(dataDomain.getCiResult());
                    dataDomain.setIndex(index);
                    index++;
                }


                System.out.println(avgLat + "" + avgLong);

                mv.addObject("SortOrder", SortOrder);
                mv.addObject("map_longitude", map_longitude);
                mv.addObject("map_latitude", map_latitude);
                mv.addObject("Keyword", keyWord);
                mv.addObject("total", totalLength);
                mv.addObject("lists", totalList);
                mv.addObject("dataDomain", totalList);
                mv.addObject("Museum", museums);
                mv.addObject("session", session);
                mv.addObject("flag", flag);
                mv.addObject("pageNumberList", pageNumberList);
                mv.addObject("pageflag", pageflag);

                return mv;

            }
            else {

                int zoom = 14;
                mv.addObject("zoom", zoom);

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

                City Region = cityService.getRegionFromCityId(cityId);
                String RegionName123 = Region.getMuseum();

                City selectCityLocation = cityService.getCityLocationFromCityid(cityId);
                City selectRegionName = cityService.getRegionFromCityId(cityId);
                String regionNameById = selectRegionName.getMuseum();
                String cityNameById = selectRegionName.getCities();
                String cityRegionName = cityNameById + " - " + regionNameById;

                String RegionName = Region.getCities();
                int totalLength = totalList.size();
                if(totalLength == 0) {

                    pageflag = 0;
                    String[] pageList = {};
                    int pageNumberList[] = new int[10];
                    mv.addObject("total", totalLength);
                    mv.addObject("cityRegionName", cityRegionName);
                    mv.addObject("map_longitude", selectCityLocation.getLongitude());
                    mv.addObject("map_latitude", selectCityLocation.getLatitude());
                    mv.addObject("lists", pageList);
                    mv.addObject("pageNumberList", pageNumberList);
                    mv.addObject("flag", flag);
                    mv.addObject("pageflag", pageflag);
                    return mv;
                }
                ///////////////////////////////////////////////////////////////////
                double a = Double.valueOf(totalLength);
                double b = Double.valueOf(dataForPage);


                pageNumber = (int)Math.ceil(a/b);
                int pageNumberList[];
                if(pageNumber > 10){
                    if(intCurrentPage > 6){
                        if(intCurrentPage+5 < pageNumber) {
                            pageNumberList = new int[10];
                            int k = 0;
                            for (int i = intCurrentPage - 4; i <= intCurrentPage + 5; i++) {
                                pageNumberList[k] = i + 1;
                                k++;
                            }
                        }
                        else
                        {
                            pageNumberList = new int[(pageNumber-intCurrentPage+4)];
                            int k = 0;
                            for (int i = intCurrentPage - 4; i < pageNumber; i++) {
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
                if(totalLength > 10) {
                    int as = totalLength - (intCurrentPage * 10) + 10;
                    System.out.println(as);
                    if (as < 10) {
                        pageList = new DataDomain[as];
                    } else {
                        pageList = new DataDomain[10];
                    }
                }
                else{
                    pageList = new DataDomain[totalLength];
                }
                for(int i = startNum; i < startNum + dataForPage; i++) {
                    if(i >= totalLength) break;
                    pageList[k] = totalList.get(i);
                    k++;
                }

                if(cityId.length() == 3)
                {
                    String zero = "0";
                    cityId = zero + cityId;
                }
                int firstFlag =  0;
                if (intCurrentPage > 7 && pageNumber > 10)
                {
                    firstFlag = 1;
                }

                if(currentPage == "0")
                    currentPage = "1";
                mv.addObject("SortOrder", SortOrder);
                mv.addObject("TypeToSort", TypeToSort);
                mv.addObject("Region", Region);
                mv.addObject("RegionName", RegionName);
                mv.addObject("cityRegionName", cityRegionName);
                mv.addObject("map_longitude", selectCityLocation.getLongitude());
                mv.addObject("map_latitude", selectCityLocation.getLatitude());
                mv.addObject("total", totalLength);
                mv.addObject("pageNumberList", pageNumberList);
                mv.addObject("pageNumber", pageNumber);
                mv.addObject("currentPage", currentPage);
                mv.addObject("lists", pageList);
                mv.addObject("Museum", museums);
                mv.addObject("City_id", cityId);
                mv.addObject("Session", session);
                mv.addObject("firstFlag", firstFlag);
                mv.addObject("flag", flag);
                mv.addObject("pageflag", pageflag);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return mv;
    }
}