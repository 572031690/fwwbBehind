package com.xhy.vo;

import lombok.Data;

import java.util.List;
@Data
public class RolePermVO {
    int roleId;
    List<Integer> permId;
}
