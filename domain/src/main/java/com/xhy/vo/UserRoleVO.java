package com.xhy.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleVO {
    int userid;
    List<Integer> roleId;
}
