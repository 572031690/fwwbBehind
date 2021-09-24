package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePerm implements Serializable {

    private int roleId;
    private int permId;
}
