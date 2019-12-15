package com.jeonbuk.mchms.cont.DispacherCont.view;

import com.jeonbuk.mchms.cont.DispacherCont.main.MainController;
import com.jeonbuk.mchms.domain.DataDomain;
import com.jeonbuk.mchms.service.data.DataService;
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
import java.io.PrintWriter;

@Controller
public class ViewController {

    private static Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/MCHMSView", method = RequestMethod.GET)
    public ModelAndView mCHMSView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        try {

            DataDomain dataDomain = dataService.getEventInfo(request.getParameter("ID"));

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
            String remarksMy = dataDomain.getRemarksMy();

            if(StringUtils.isEmpty(remarksEn)) {
                remarksEn = "There is no content.";
            }

            if(StringUtils.isEmpty(remarksMy)) {
                remarksMy = "There is no content.";
            }

            double xPoint = 0;
            double yPoint = 0;

            if(dataDomain.getLatitude() != 0 && dataDomain.getLongitude() != 0) {
                xPoint = dataDomain.getLatitude();
                yPoint = dataDomain.getLongitude();
            }










            mv.addObject("ResultView", dataDomain);
            mv.addObject("session",session);
            mv.setViewName("View/MCHMSView");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mv;
    }
}
