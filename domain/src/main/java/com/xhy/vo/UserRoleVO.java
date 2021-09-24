package com.xhy.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleVO {
    int userId;
    List<Integer> roleId;
}
