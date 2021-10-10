package com.xhy.controller;

import com.xhy.domain.Countrysale;
import com.xhy.service.CountrysaleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/webCountrysale")
public class CountrysaleController {
    @Autowired
    private CountrysaleService countrysaleService;

    @RequiresPermissions("echart:countrysale")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public @ResponseBody
    List<Countrysale> findAllCountrysale(){

       List<Countrysale> alllist = countrysaleService.findAll();
        return alllist;
    }
}
