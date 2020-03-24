package com.jeonbuk.mchms.cont.DoCont;

import com.jeonbuk.mchms.domain.User;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class FindProcessCont {
    private static Logger logger = LoggerFactory.getLogger(SearchCont.class);

    @RequestMapping(value = "/MCHMSlogin_process", method = RequestMethod.POST)
    public ModelAndView Findprocess(ModelAndView mv, HttpServletRequest request, @ModelAttribute User user) {
        try{

        }catch(Exception e){
            logger.error(e.toString());
        }
        return mv;
    }
}
