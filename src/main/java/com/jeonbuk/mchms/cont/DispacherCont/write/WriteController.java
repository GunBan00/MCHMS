package com.jeonbuk.mchms.cont.DispacherCont.write;

import com.jeonbuk.mchms.cont.DispacherCont.main.MainController;
import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
import com.jeonbuk.mchms.service.calnum.CalNumService;
import com.jeonbuk.mchms.service.user.UserService;
import groovy.util.logging.Slf4j;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class WriteController {

    @Autowired
    ClassificationService classificationService;

    @Autowired
    CityService cityService;

    @Autowired
    UserService userService;

    @Autowired
    CalNumService calNumService;



    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/MCHMSWrite", method = RequestMethod.GET)
    public ModelAndView mypage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        try {
            HttpSession session = request.getSession();

            String City_id = request.getParameter("City_id");
            if(session.getAttribute("id") == null)
            {
                return new ModelAndView("redirect:/MCHMSSearch/?City_id="+City_id);
            }

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
            String id = String.valueOf(session.getAttribute("id"));
            String userNickname = userService.selectUserInfo(id).getNickname();

            SimpleDateFormat format2 = new SimpleDateFormat( "ddMMyyyy");
            Date time = new Date();
            String time2 = format2.format(time);

            String relicNumber = calNumService.selectRelicNumber(id, time2);

            String serialNumber = "-" + relicNumber + "-" + userNickname + "-" + time2;
            mv.addObject("LargeContents", LargeContents);
            mv.addObject("MiddleContents", MiddleContents);
            mv.addObject("SmallContents", SmallContents);
            mv.addObject("SubSectionContents", SubSectionContents);

            mv.addObject("City_id", City_id);
            mv.addObject("City", Cities);
            mv.addObject("Museum", Museum);
            mv.addObject("serialNumber", serialNumber);
            mv.setViewName("Write/MCHMSWrite.html");


        }
        catch (Exception e) {
            e.printStackTrace();

        }

        return mv;
    }
}
