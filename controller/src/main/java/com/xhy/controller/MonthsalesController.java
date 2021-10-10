package com.xhy.controller;


import com.xhy.domain.Monthsales;
import com.xhy.service.MonthsalesService;
import com.xhy.service.impl.MonthsalesServiceimpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/webMonthsales")
public class MonthsalesController {
    @Autowired
    MonthsalesService monthsalesService;

    @RequiresPermissions("echart:monthsale")
    @ResponseBody
    @RequestMapping(value = "/findAllMonthsales", method = RequestMethod.GET)
        public List<Monthsales> findAllMonthsales() {

        List<Monthsales> allist = monthsalesService.findAll();
        return allist;
    }
}
