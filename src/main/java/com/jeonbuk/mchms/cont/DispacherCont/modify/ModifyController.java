package com.jeonbuk.mchms.cont.DispacherCont.modify;

import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.DataDomain;
import com.jeonbuk.mchms.domain.FileEventDomain;
import com.jeonbuk.mchms.service.calnum.CalNumService;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
import com.jeonbuk.mchms.service.data.DataService;
import com.jeonbuk.mchms.service.fileevent.FileEventService;
import com.jeonbuk.mchms.service.user.UserService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class ModifyController {

    @Autowired
    private DataService dataService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private FileEventService fileEventService;

    @Autowired
    UserService userService;

    @Autowired
    CalNumService calNumService;
    @RequestMapping(value = "/MCHMSModify", method = RequestMethod.GET)
    public ModelAndView MCHMSModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        try{

        String id = request.getParameter("ID");
        String Url = "/MCHMSView/?ID=";
        String returnUrl = Url + id;

        DataDomain dataDomain = dataService.getDataInfo(id);
        String dataRegistrant = dataDomain.getRegistrant();
        FileEventDomain resultView = fileEventService.getFilesNameFromDataID(Integer.valueOf(id));
        String sessionId = String.valueOf(session.getAttribute("id"));

            System.out.println(sessionId);
            System.out.println(dataRegistrant);
        if(!dataRegistrant.equals(sessionId)){
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out_equals = response.getWriter();
            String outPrint = "<script type = 'text/javascript'> alert('It cannot Modify.');location.href='";
            String endOutPrint = "';</script>";

            out_equals.println(outPrint+returnUrl+endOutPrint);
            out_equals.flush();

        }

        List<City> Cities = cityService.getCities();
        List<City> Museum = cityService.getMuseums();

        String CitiesContents = "<option value=\"\">:::: REGION ::::</option>";
        for (City cities : Cities) {
            CitiesContents = CitiesContents + "<option id=\"SEQ_CITY\"  value=\"" + cities.getCities() + "\">" + cities.getCities() + "</option>";
        }

        String MuseumContents = "<option value=\"\" selected=\"selected\">:::: Division ::::</option>";
        for (City museum : Museum) {
            MuseumContents = MuseumContents + "<option class=\"" + museum.getCities() + "\" value=\"" + museum.getMuseum() + "\" style = \"display:none\">" + museum.getMuseum() + "</option>";
        }

        mv.addObject("CitiesContents", CitiesContents);
        mv.addObject("MuseumContents", MuseumContents);

        List<Classification> Large = classificationService.getLarge();
        List<Classification> Middle = classificationService.getMiddle();
        List<Classification> Small = classificationService.getSmall();
        List<Classification> SubSection = classificationService.getSubSection();

        String LargeContents = "<option value=\"\">:::: Category ::::</option>";
        for (Classification large : Large) {
            LargeContents = LargeContents + "<option id=\"SEL_LARGE\"  value=\"" + large.getLarge() + "\">" + large.getLarge() + "</option>";
        }

        String MiddleContents = "<option value=\"\" selected=\"selected\">:::: Division ::::</option>";
        for (Classification middle : Middle) {
            if (!(middle.getMiddle().equals("")))
                MiddleContents = MiddleContents + "<option class=\"middle " + middle.getLarge() + "\" id=\"" + middle.getLarge() + "\" name=\"" + middle.getLarge() + "\" value=\"" + middle.getMiddle() + "\" style = \"display:none\">" + middle.getMiddle() + "</option>";
        }

        String SmallContents = "<option value=\"\">:::: Section ::::</option>";
        for (Classification small : Small) {
            if (!((small.getLarge()).equals(""))) {
                SmallContents = SmallContents + "<option class=\"small " + small.getMiddle() + "\" value=\"" + small.getSmall() + "\" style = \"display:none\">" + small.getSmall() + "</option>";
            }
        }

        String SubSectionContents = "<option value=\"\">:::: Sub Section ::::</option>";
        for (Classification subSection : SubSection) {
            if (!((subSection.getLarge()).equals(""))) {
                if ((subSection.getMiddle()).equals("Buddha_Museum")) {
                    SubSectionContents = SubSectionContents + "<option class=\"sub_small " + subSection.getSmall() + "\" value=\"" + subSection.getSubSection() + "\" style = \"display:none\">" + subSection.getSubSection() + "</option>";
                }
            }
        }

        String userNickname = userService.selectUserInfo(sessionId).getNickname();

        SimpleDateFormat format2 = new SimpleDateFormat( "ddMMyyyy");
        Date time = new Date();
        String time2 = format2.format(time);

        String relicNumber = calNumService.selectRelicNumber(sessionId, time2);

        String serialNumber = dataDomain.getSerialNumber();
        String remarksEn = dataDomain.getRemarksEn();
        remarksEn = dataService.changeTagToBigbr(remarksEn);
        remarksEn = dataService.changeTagToSmallbr(remarksEn);

        String remarksMy = dataDomain.getRemarksMy();
        remarksMy = dataService.changeTagToBigbr(remarksMy);
        remarksMy = dataService.changeTagToSmallbr(remarksMy);

        String referenceMy = dataDomain.getReferenceMy();
        referenceMy = dataService.changeTagToBigbr(referenceMy);
        referenceMy = dataService.changeTagToSmallbr(referenceMy);

        String referenceEn = dataDomain.getReferenceEn();
        referenceEn = dataService.changeTagToBigbr(referenceEn);
        referenceEn = dataService.changeTagToSmallbr(referenceEn);


        String frontSerial = serialNumber.substring(3,8);
        String endSerial = serialNumber.substring(9,11);
        System.out.println(dataDomain.getTitleMy());
        dataDomain.setRemarksMy(remarksMy);
        dataDomain.setRemarksEn(remarksEn);
        dataDomain.setReferenceMy(referenceMy);
        dataDomain.setReferenceEn(referenceEn);
        String endSerialNumber2 = "-" + relicNumber + "-" + userNickname + "-" + time2;

        mv.addObject("LargeContents", LargeContents);
        mv.addObject("MiddleContents", MiddleContents);
        mv.addObject("SmallContents", SmallContents);
        mv.addObject("SubSectionContents", SubSectionContents);

        mv.addObject("City", Cities);
        mv.addObject("ResultView", dataDomain);
        mv.addObject("frontSerial", frontSerial);
        mv.addObject("endSerial", endSerial);
        mv.addObject("endSerialNumber2", endSerialNumber2);
        mv.addObject("Museum", Museum);
        mv.setViewName("View/MCHMSModify");

        return mv;
    }
        catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }

}
