package com.xhy.code;

import lombok.Data;

import java.io.Serializable;

@Data
public class PhoneCode implements Serializable {
    private Integer code;
    private String msg;
    private String obj;

}
