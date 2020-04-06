package com.jeonbuk.mchms.cont.DoCont;

import com.jeonbuk.mchms.cont.DispacherCont.main.MainController;
import com.jeonbuk.mchms.domain.City;
import com.jeonbuk.mchms.domain.Classification;
import com.jeonbuk.mchms.domain.DataDomain;
import com.jeonbuk.mchms.domain.UserInfo;
import com.jeonbuk.mchms.service.calnum.CalNumService;
import com.jeonbuk.mchms.service.city.CityService;
import com.jeonbuk.mchms.service.classification.ClassificationService;
import com.jeonbuk.mchms.service.data.DataService;
import com.jeonbuk.mchms.service.user.UserService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalTime.now;

@Controller
@Slf4j
public class WriteDoCont {

    @Autowired
    ClassificationService classificationService;

    @Autowired
    DataService dataservice;
    @Autowired
    UserService userService;
    @Autowired
    CalNumService calNumService;
    private static Logger logger = LoggerFactory.getLogger(MainController.class);


    @RequestMapping(value = "/MCHMSWrite_process", method = RequestMethod.POST)
    public ModelAndView mypage(MultipartHttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        try {
            String path = "C:\\image\\";//directory 수정해야됨

            HttpSession session = request.getSession();

            String redirectUrl = "MCHMSSearch/City_id/?";

            String cityId = request.getParameter("City_id");
            String LARGE_SEQ = request.getParameter("LARGE_SEQ_W");
            String MEDIUM_SEQ = request.getParameter("MEDIUM_SEQ_W");
            String SMALL_SEQ = request.getParameter("SMALL_SEQ_W");
            String SUB_SEQ1 = request.getParameter("SUB_SEQ_W");
            String FIELD_TITLE = request.getParameter("FIELD_TITLE");
            String FIELD_TITLE_MY = request.getParameter("FIELD_TITLE_MY");
            String FIELD_PERIOD = request.getParameter("FIELD_PERIOD");
            String FIELD_Location = request.getParameter("FIELD_Location");
            String FIELD_ORIGIN = request.getParameter("FIELD_ORIGIN");
            String FIELD_MATERIAL = request.getParameter("FIELD_MATERIAL");
            String Clas_City = request.getParameter("Clas_City");
            String FIELD_RELIC_ZIP = request.getParameter("FIELD_RELIC_ZIP");
            String FIELD_RELIC_ADD = request.getParameter("FIELD_RELIC_ADD");
            String FIELD_LTTD = request.getParameter("FIELD_LTTD");

            String Cl = "";
            System.out.println(cityId);
            if (LARGE_SEQ.equals("Archaeological Site"))
            Cl = "01";
            else if (LARGE_SEQ.equals("World Heritage Site"))
            Cl = "02";
            else if (LARGE_SEQ.equals("Museum"))
            Cl = "03";
            else if (LARGE_SEQ.equals("Colonial Building"))
            Cl = "04";
            else if (LARGE_SEQ.equals("Memorial of The World"))
            Cl = "05";
            else if (LARGE_SEQ.equals("ICH"))
            Cl = "06";
            else if (LARGE_SEQ.equals("National Library"))
            Cl = "07";


            if (FIELD_LTTD.equals(""))
            {
                FIELD_LTTD = "0";
            }
            System.out.print("FIELD_LTTD");
            System.out.println(FIELD_LTTD);
            String FIELD_LNGTD = request.getParameter("FIELD_LNGTD");
            if (FIELD_LNGTD.equals(""))
            {
                FIELD_LNGTD = "0";
            }
            String FIELD_LTTD2 = "0";
            String FIELD_LNGTD2 ="0";

            String FIELD_REMARK1 = request.getParameter("FIELD_REMARK1");
            FIELD_REMARK1 = dataservice.changeSmallTag(FIELD_REMARK1);
            FIELD_REMARK1 = dataservice.changeBigTag(FIELD_REMARK1);
            System.out.println(FIELD_REMARK1);
            String FIELD_REMARK1_MY = request.getParameter("FIELD_REMARK1_MY");
            FIELD_REMARK1_MY = dataservice.changeSmallTag(FIELD_REMARK1_MY);
            FIELD_REMARK1_MY = dataservice.changeBigTag(FIELD_REMARK1_MY);
            String FIELD_REMARK2 = request.getParameter("FIELD_REMARK2");
            FIELD_REMARK2 = dataservice.changeSmallTag(FIELD_REMARK2);
            FIELD_REMARK2 = dataservice.changeBigTag(FIELD_REMARK2);
            String FIELD_REMARK2_MY = request.getParameter("FIELD_REMARK2_MY");
            FIELD_REMARK2_MY = dataservice.changeSmallTag(FIELD_REMARK2_MY);
            FIELD_REMARK2_MY = dataservice.changeBigTag(FIELD_REMARK2_MY);
            String OPEN_YN = request.getParameter("OPEN_YN");
            String Filename = "";
            String Registrant = "";
            String Department = "Ministry of Religious Affairs and Culture Department of Archaeology and National Museum";


            String id = String.valueOf(session.getAttribute("id"));
            String userNickname = userService.selectUserInfo(id).getNickname();

            SimpleDateFormat format2 = new SimpleDateFormat( "ddMMyyyy");
            Date time = new Date();
            String time2 = format2.format(time);

            String relicNumber = calNumService.selectOnlyRelicNumber(id);


            List<MultipartFile> files = request.getFiles("files");
            System.out.println("files");
            String filesName = "";
            int fileCount = 1;


            int classificationId = classificationService.getClassificationIdByCategory(LARGE_SEQ,MEDIUM_SEQ,SMALL_SEQ,SUB_SEQ1);
            String clId = Integer.toString(classificationId);

            Map<String, String> sqlParam = new HashMap<>();
            SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd");

            String time1 = format1.format(time);


            String serialNumber = Cl +"-"+FIELD_RELIC_ZIP+"-"+FIELD_RELIC_ADD+"-"+relicNumber+"-"+userNickname+"-"+time2;

            System.out.println(Department);
            sqlParam.put("cityId", cityId);
            sqlParam.put("LARGE_SEQ", LARGE_SEQ);
            sqlParam.put("MEDIUM_SEQ", MEDIUM_SEQ);
            sqlParam.put("SMALL_SEQ", SMALL_SEQ);
            sqlParam.put("SUB_SEQ1", SUB_SEQ1);
            sqlParam.put("title", FIELD_TITLE);
            sqlParam.put("title_my", FIELD_TITLE_MY);
            sqlParam.put("period", FIELD_PERIOD);
            sqlParam.put("location", FIELD_Location);
            sqlParam.put("origin", FIELD_ORIGIN);
            sqlParam.put("material", FIELD_MATERIAL);
            sqlParam.put("serialNumber", serialNumber);
            sqlParam.put("latitude", FIELD_LTTD);
            sqlParam.put("longtitude", FIELD_LNGTD);
            sqlParam.put("latitude2", FIELD_LTTD2);
            sqlParam.put("longtitude2", FIELD_LNGTD2);
            sqlParam.put("remarksEn", FIELD_REMARK1);
            sqlParam.put("remarksMy", FIELD_REMARK1_MY);
            sqlParam.put("referenceEn", FIELD_REMARK2);
            sqlParam.put("referenceMy", FIELD_REMARK2_MY);
            sqlParam.put("visibility", OPEN_YN);
            sqlParam.put("classificationId", clId);
            sqlParam.put("filename", Filename);
            sqlParam.put("registrant", id);
            sqlParam.put("department", Department);
            sqlParam.put("visibility", OPEN_YN);
            sqlParam.put("department", OPEN_YN);
            sqlParam.put("registrationDate", time1);

            for (MultipartFile mf : files) {
                String originFileName = mf.getOriginalFilename(); // 원본 파일 명
                long fileSize = mf.getSize(); // 파일 사이즈
                if(fileSize != 0) {

                    String fileName = id + "_" + time2 + "_" + fileCount;
                    String safeFile = path + fileName;
                    try {
                        mf.transferTo(new File(safeFile));
                        fileCount++;
                        filesName = filesName + fileName + "|";
                        System.out.println("filesName : " + filesName);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            dataservice.setData(sqlParam);
            int maxId = dataservice.getMaxId(id);
            dataservice.setFiles(maxId, filesName, fileCount-1);

            calNumService.updateCalNum(relicNumber);

            return new ModelAndView("redirect:/");

        }
        catch (Exception e) {
            logger.error(e.toString());
        }

        System.out.print("Nasdfasdf");
        return new ModelAndView("redirect:/");
    }


}

