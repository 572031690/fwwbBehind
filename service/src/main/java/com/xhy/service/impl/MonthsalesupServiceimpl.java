package com.xhy.service.impl;

import com.xhy.Mapper.MonthsalesupMapper;
import com.xhy.domain.Monthsalesup;
import com.xhy.service.MonthsalesupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthsalesupServiceimpl implements MonthsalesupService {

@Autowired
MonthsalesupMapper monthsalesupMapper;
    @Override
    public List<Monthsalesup> findAll() {
        return monthsalesupMapper.findAll();
    }
}
