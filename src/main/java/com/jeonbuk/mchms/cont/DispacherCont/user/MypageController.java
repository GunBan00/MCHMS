package com.jeonbuk.mchms.cont.DispacherCont.user;

import com.jeonbuk.mchms.domain.*;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
import com.jeonbuk.mchms.service.data.DataService;
import com.jeonbuk.mchms.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MypageController {

    @Autowired
    UserService userService;

    @Autowired
    ClassificationService classificationService;

    @Autowired
    CityService cityService;

    @Autowired
    DataService dataService;

    private static Logger logger = LoggerFactory.getLogger(com.jeonbuk.mchms.cont.DispacherCont.main.StatisticsController.class);

    @RequestMapping(value = "/Mypage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mypage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        mv.addObject("session", session);
        String id = (String) session.getAttribute("id");


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
            mv.addObject("Session", session);

            mv.addObject("City", Cities);
            mv.addObject("Museum", Museum);

            String TypeToSort = request.getParameter("TypeToSort");
            if (TypeToSort == null){
                TypeToSort = "Title";
            }

            String SortOrder = request.getParameter("SortOrder");
            if (SortOrder == null){
                SortOrder = "Title0";
            }

            List<UserWriteClassificationCount> UsersClassificationCount = userService.getClassificationCountByUserId(id);
            for(UserWriteClassificationCount cc : UsersClassificationCount) {
                System.out.println("test : " + cc.getCount());
                System.out.println("test : " + cc.getLarge());
            }

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

            Integer OrderNum = Integer.parseInt(SortOrder.substring(SortOrder.length()-1, SortOrder.length()));
            String Previous_Sort = SortOrder.substring(0, SortOrder.length()-1);
            List<DataDomain> totalList;

            if ((Previous_Sort.equals(TypeToSort)) && (OrderNum == 0)){
                SortOrder = TypeToSort + "1";
                totalList = dataService.getDataById(id, TypeToSort,"asc");
            }
            else {
                SortOrder = TypeToSort + "0";
                totalList = dataService.getDataById(id, TypeToSort,"desc");
            }
            List<UserDataDomain> Userlists = userService.selectUserData(id);

            int totalLength = totalList.size();

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

            for(DataDomain UserData : totalList) {
                Classification Category = classificationService.getCategoryFromClassification(UserData.getClassificationId());
                UserData.setClResult(Category.getLarge());

                City Rejeon = cityService.getRegionFromCityId(UserData.getCityId());
                UserData.setCiResult(Rejeon.getCities());

                UserData.setIndex(index);
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

            int firstFlag =  0;
            if (intCurrentPage > 7 && pageNumber > 10)
            {
                firstFlag = 1;
            }

            if(currentPage == "0") currentPage = "1";

            mv.addObject("currentPage", currentPage);
            mv.addObject("firstFlag", firstFlag);
            mv.addObject("pageNumberList", pageNumberList);
            mv.addObject("SortOrder", SortOrder);
            mv.addObject("TypeToSort", TypeToSort);
            mv.addObject("total", Userlists.size());
            mv.addObject("UsersClassificationCount", UsersClassificationCount);
            mv.addObject("lists", pageList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        UserInfo userinfo = userService.selectUserInfo(id);

        mv.addObject("userinfo", userinfo);
        mv.setViewName("Mypage/Mypage");

        return mv;
    }
}
