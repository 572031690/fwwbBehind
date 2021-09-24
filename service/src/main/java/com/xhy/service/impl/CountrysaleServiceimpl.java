package com.xhy.service.impl;

import com.xhy.Mapper.CountrysaleMapper;
import com.xhy.domain.Countrysale;
import com.xhy.service.CountrysaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountrysaleServiceimpl implements CountrysaleService {
    @Autowired
    private CountrysaleMapper countrysaleMapper;
    @Override
    public List<Countrysale> findAll() {
        return countrysaleMapper.findAll();
    }
}
