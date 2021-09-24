package com.xhy.Mapper;

import com.xhy.domain.Need;
import com.xhy.vo.NeedVO;

import java.util.List;

public interface NeedMapper {

    List<Need> findAll(NeedVO needVO);

    List<Need>  findbyid(int needid);


    Integer addNeed(Need need);

    Integer updateNeed(Need need);

    Integer deleteNeed(int needid);

    /*
    * 审批人员查看的请假条
    * */


}
