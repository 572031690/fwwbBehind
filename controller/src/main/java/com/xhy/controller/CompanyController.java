package com.xhy.controller;

import com.xhy.domain.Company;
import com.xhy.service.CompanyService;
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
    @RequestMapping(value = "/findAllCompany",method = RequestMethod.GET)
    private @ResponseBody
    List<Company> findAllCompany(int page, int limit){
        List<Company> alllist = companyService.findAll(page,limit);

        return alllist;
    }
}
