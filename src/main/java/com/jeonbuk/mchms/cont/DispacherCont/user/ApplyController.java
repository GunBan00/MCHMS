package com.jeonbuk.mchms.cont.DispacherCont.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ApplyController {
    @RequestMapping(value = "/MCHMSApply", method = RequestMethod.GET)
    public String Apply() {
        return "User/ApplyID";
    }
}