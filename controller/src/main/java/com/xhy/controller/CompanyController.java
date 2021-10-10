package com.xhy.controller;

import com.xhy.domain.Company;
import com.xhy.service.CompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/webCompany")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequiresPermissions("ecahrt:company")
    @RequestMapping(value = "/findAllCompany",method = RequestMethod.GET)
    private @ResponseBody
    List<Company> findAllCompany(){
        List<Company> alllist = companyService.findAll();

        return alllist;
    }
}
