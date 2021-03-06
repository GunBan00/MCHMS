package com.jeonbuk.mchms.cont.DispacherCont.view;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.DataDomain;
import com.jeonbuk.mchms.domain.FileEventDomain;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
import com.jeonbuk.mchms.service.data.DataService;
import com.jeonbuk.mchms.service.fileevent.FileEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class ViewController {

    private static Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private DataService dataService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private FileEventService fileEventService;

    public static final String IS_MOBILE = "MOBILE";
    private static final String IS_PHONE = "PHONE";
    public static final String IS_TABLET = "TABLET";
    public static final String IS_PC = "PC";

    @RequestMapping(value = "/MCHMSView", method = RequestMethod.GET)
    public ModelAndView mCHMSView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        mv.addObject("session", session);

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
            mv.addObject("MID_Page", "View/MCHMSView.html");

            String id = request.getParameter("ID");
            DataDomain dataDomain = dataService.getDataInfo(id);

            if(dataDomain.getVisibility() == 1) {
                if(session != null || dataDomain.getRegistrant() != session.getAttribute("id")) {
                    response.setContentType("text/html; charset=UTF-8");

                    PrintWriter out = response.getWriter();

                    out.println("<script>alert('It is private data'); location.href='/MCHMSSearch/?City_id=" + dataDomain.getCityId() + "';</script>");
                    out.flush();
                    mv.setViewName("/MCHMSSearch/?City_id=" + dataDomain.getCityId());

                    return mv;
                }

            }

            String remarksEn = dataDomain.getRemarksEn();
            String referenceEn = dataDomain.getReferenceEn();

            if(StringUtils.isEmpty(remarksEn)) {
                remarksEn = "There is no content.";
            }

            if(StringUtils.isEmpty(referenceEn)) {
                referenceEn = "There is no content.";
            }

            double xPoint = 0;
            double yPoint = 0;

            if(dataDomain.getLatitude() != 0 && dataDomain.getLongitude() != 0) {
                xPoint = dataDomain.getLatitude();
                yPoint = dataDomain.getLongitude();
            }

            List<City> museums = cityService.getMuseums();

            FileEventDomain fileEventDomain = fileEventService.getFilesNameFromDataID(Integer.parseInt(id));

            String[] filesArray = {};
            String filesname =  "";
            int imgCount;
            if(fileEventDomain != null){
                filesname = fileEventDomain.getFiles();
                filesArray = filesname.split("\\|");
                imgCount = fileEventDomain.getCount();
            }
            else{
                imgCount = 0;
            }
            String ImgContents = "";

            City cityInfo = cityService.getCityInfoById(id);
            Classification clInfo = classificationService.getClassificationInfoById(id);

            String cityClInfo = cityInfo.getCities() + "-" + cityInfo.getMuseum() + "-" + clInfo.getLarge();
            if (!(clInfo.getMiddle().equals(""))){
                cityClInfo = cityClInfo + "-" + clInfo.getMiddle();
                if (!(clInfo.getSmall().equals(""))){
                    cityClInfo = cityClInfo + "-" + clInfo.getSmall();
                    if (!(clInfo.getSubSection().equals(""))){
                        cityClInfo = cityClInfo + "-" + clInfo.getSubSection();
                    }
                }
            }
            DataDomain dataDo = dataService.getDataInfo(id);
            int modifyFlag = 0;

            if(dataDomain.getRegistrant() == String.valueOf(String.valueOf(session.getAttribute("id"))))
            {
                System.out.println("asdf");

                modifyFlag = 1;
            }
            mv.addObject("DataId", id);
            mv.addObject("cityClInfo", cityClInfo);
            mv.addObject("modifyFlag", modifyFlag);
            mv.addObject("ResultView", dataDomain);
            mv.addObject("Remarks_en", remarksEn);
            mv.addObject("Reference_en",referenceEn);
            mv.addObject("City_id", dataDo.getCityId());
            mv.addObject("x", xPoint);
            mv.addObject("y", yPoint);
            mv.addObject("Museum", museums);
            mv.addObject("Clinfo", clInfo);
            mv.addObject("Cityinfo", cityInfo);
            mv.addObject("fileEventDomain", fileEventDomain);
            mv.addObject("imgCount", imgCount);
            mv.addObject("filesname", filesname);
            mv.addObject("file_length", filesArray.length);
            String userAgent;
            if(request.getHeader("User-Agent") != null) {
                userAgent = request.getHeader("User-Agent").toUpperCase();
                if (userAgent.indexOf(IS_MOBILE) > -1) {
                    for (int i = 0; i < filesArray.length; i++) {
                        ImgContents = ImgContents + "<div class=\"swiper-slide\" style=\"width: 400px;\"> <img id =\"viewimg\"src=\"http://mchms.gov.mm:8080/MCHMS/" + filesArray[i] + "\" style=\"width: 400px;\"></div>";
                    }
                    mv.addObject("MID_Page", "MView/View.html");
                    mv.setViewName("MView/Base");
                } else {
                    for (int i = 0; i < filesArray.length; i++) {
                        ImgContents = ImgContents + "<li class=\"bxslider\">\n" +
                                "<div id =" + "\"viewdiv" + filesArray[i] + "\"" + "style=\"width:95%; margin:0 auto;\">\n" +
                                "<a href =\"http://mchms.gov.mm:8080/MCHMS/" + filesArray[i] + "\">\n" +
                                "<img id =\"viewimg\" src=\"http://mchms.gov.mm:8080/MCHMS/" + filesArray[i] + "\"" + "style=\"cursor:pointer;\"/>\n" +
                                "</a>\n" +
                                "</div>\n" +
                                "</li>";
                    }
                    mv.addObject("ImgContents", ImgContents);
                    mv.addObject("MID_Page", "View/MCHMSView.html");

                    mv.setViewName("Contents_Base");
                }
            }
            else
            {
                for (int i = 0; i < filesArray.length; i++) {
                    ImgContents = ImgContents + "<li class=\"bxslider\">\n" +
                            "<div id =" + "\"viewdiv" + filesArray[i] + "\"" + "style=\"width:95%; margin:0 auto;\">\n" +
                            "<a href =\"/MCHMS/" + filesArray[i] + "\">\n" +
                            "<img id =\"viewimg\" src=\"http://mchms.net/Static/MCHMS/" + filesArray[i] + "\"" + "style=\"cursor:pointer;\"/>\n" +
                            "</a>\n" +
                            "</div>\n" +
                            "</li>";
                }
                mv.addObject("ImgContents", ImgContents);
                mv.addObject("MID_Page", "View/MCHMSView.html");

                mv.setViewName("Contents_Base");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }
    @RequestMapping(value = "/MCHMSDelete", method = RequestMethod.GET)
    public ModelAndView mchmsDelete(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        mv.addObject("session", session);

        try {
            String id = request.getParameter("ID");

            DataDomain dataDomain = dataService.getDataInfo(id);
            String CityId = dataDomain.getCityId();

            System.out.println(dataDomain.getRegistrant() + " " + session.getAttribute("id"));

            if((session == null) || !(dataDomain.getRegistrant().equals(session.getAttribute("id")))){
                response.setContentType("text/html; charset=UTF-8");

                PrintWriter out = response.getWriter();

                out.println("<script>alert('It cannot Delete'); location.href='/MCHMSSearch/?City_id=" + CityId + "';</script>");
                out.flush();

                return mv;
            }

            dataService.deleteData(id);

            FileEventDomain file = fileEventService.getFilesNameFromDataID(Integer.parseInt(id));

            String[] array = file.getFiles().split("\\|");
            String path = "C:\\image\\";//directory 수정해야됨
            String filesName = "";

            for (int i = 0; i < file.getCount(); i++) {
                filesName = path + array[i];
                File f = new File(filesName);

                f.delete();
            }

            fileEventService.deleteFileData(id);

            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('Deleted'); location.href='/MCHMSSearch/?City_id=" + CityId + "';</script>");
            out.flush();

            return mv;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }
}