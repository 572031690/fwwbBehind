package com.xhy.Utils;

import com.xhy.domain.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class CodeUtil {
    public User CodeHash(User user){
        Object salt = ByteSource.Util.bytes(user.getUsername());
        SimpleHash simpleHash =
                new SimpleHash(
                        "md5",
                        user.getPassword(),
                        salt,
                        2);
        String mdpassword = simpleHash.toString();
        user.setPassword(mdpassword);
        SimpleHash simpleHash_salt = new SimpleHash("md5",salt);
        String newSalt = simpleHash_salt.toString();
        user.setSalt(newSalt);
        return user;
    }
}
