package com.xhy.test;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class TestPassword {

    @Test
    public void testCreatePassword(){

        String username = "admin";
        String pwd = "123";
        String salt = "admin";
        String simpleHash = new SimpleHash("MD5",username,salt,1).toString();

    }
}
