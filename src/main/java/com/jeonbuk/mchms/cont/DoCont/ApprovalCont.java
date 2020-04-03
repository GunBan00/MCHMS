package com.jeonbuk.mchms.cont.DoCont;

import com.jeonbuk.mchms.cont.DispacherCont.main.MainController;
import com.jeonbuk.mchms.service.user.UserService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class ApprovalCont {

    @Autowired
    UserService userService;

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/Approval_process", method = RequestMethod.POST)
    public ModelAndView mypage(MultipartHttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        try {

            String userId = request.getParameter("USER_ID");
            userService.changeGrade(userId);


            return new ModelAndView("redirect:/");

        }
        catch (Exception e) {
            logger.error(e.toString());
        }

        return new ModelAndView("redirect:/");
    }
}
