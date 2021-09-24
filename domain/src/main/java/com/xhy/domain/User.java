package com.xhy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private int userid;
    private String username;
    private String password;
    private String salt;
    private String telNum;
    private String employeeid;
    private int isDisabled;
    private int roleId;

    public User(){};

}
