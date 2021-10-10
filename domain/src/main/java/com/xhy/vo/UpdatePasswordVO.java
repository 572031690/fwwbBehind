package com.xhy.vo;

import lombok.Data;
@Data
public class UpdatePasswordVO {
    int userid;
    String oldpassword;
    String password;
}
