package com.xhy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private int userid;
    private String username;
    private String realname;
    private String password;
    private String salt;
    private String telNum;
    private String employeeid;
    private int isDisabled;
    private int isDisplayed;
    private List<Integer> roleId;

    public User(){};

}
