package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRole implements Serializable {
    private int roleId;
    private int userId;

}
