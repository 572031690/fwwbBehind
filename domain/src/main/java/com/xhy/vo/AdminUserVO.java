package com.xhy.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminUserVO implements Serializable {
    /*
    * 用来封装登陆页面的表单中的登录名和密码
    * */

    private String username;
    private String password;
    private boolean remember;//记住我
}
