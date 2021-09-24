package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
        private int roleId;
        private String rolename;
        private String description;
        private int isDeleted;
}
