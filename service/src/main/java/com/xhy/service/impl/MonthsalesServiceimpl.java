package com.xhy.service.impl;

import com.xhy.Mapper.MonthsalesMapper;
import com.xhy.domain.Monthsales;
import com.xhy.service.MonthsalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthsalesServiceimpl implements MonthsalesService {
    @Autowired
    private MonthsalesMapper monthsalesMapper;
    @Override
    public List<Monthsales> findAll() {
        return monthsalesMapper.findAll();
    }
}
