package com.xhy.service.impl;


import com.xhy.Mapper.CompanyMapper;
import com.xhy.domain.Company;
import com.xhy.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceimpl implements CompanyService {
    @Autowired
    CompanyMapper companyMapper;
    @Override
    public List<Company> findAllCompany() {
        return companyMapper.findAllCompany();
    }
}

