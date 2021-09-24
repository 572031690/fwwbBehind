package com.xhy.controller;

import com.xhy.domain.Monthsalesup;

import com.xhy.service.MonthsalesupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/webMonthsalesup")
public class MonthsalesupController {
    @Autowired
    MonthsalesupService monthsalesupService;
    @RequestMapping(value = "/findAll" , method = RequestMethod.GET)
    public  @ResponseBody  List<Monthsalesup> findAll(){
        List<Monthsalesup> allist = monthsalesupService.findAll();
        return  allist;

    }
}
