package com.xhy.controller;

import com.xhy.domain.audit;
import com.xhy.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/webaudit")
public class AuditController {

    @Autowired
    AuditService auditService;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<audit> findAllAudit(String upname){
       return auditService.findAll(upname);
    }



}
