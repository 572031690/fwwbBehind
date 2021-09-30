package com.xhy.Mapper;

import com.xhy.domain.Need;
import com.xhy.vo.NeedVO;

import java.util.List;

public interface NeedMapper {

    List<Need> findAll(NeedVO needVO);

    Need  findbyid(int needid);


    Integer addNeed(Need need);

    Integer updateNeed(Need need);

    Integer deleteNeed(int needid);

    Integer updateStatus(Need need);



}
