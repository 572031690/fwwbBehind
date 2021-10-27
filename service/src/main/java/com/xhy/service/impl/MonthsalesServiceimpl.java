package com.xhy.service.impl;

import com.xhy.Mapper.MonthsalesMapper;
import com.xhy.Utils.JedisUtil;
import com.xhy.domain.Monthsales;
import com.xhy.service.MonthsalesService;
import jdk.nashorn.internal.scripts.JD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

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
