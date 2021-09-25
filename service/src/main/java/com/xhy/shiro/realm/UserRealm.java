package com.xhy.shiro.realm;


import com.xhy.domain.User;
import com.xhy.service.UserServise;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {


    @Autowired
    private UserServise userServise;


    /*
     * 授权方法
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {


        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String username = (String)principals.getPrimaryPrincipal();
        //对当前用户进行角色授权
        simpleAuthorizationInfo.addRoles(userServise.findRoleByUserName(username));
        System.out.println(userServise.findRoleByUserName(username));
        // 对当前用户进行权限的授权
        simpleAuthorizationInfo.addStringPermissions(userServise.findPermissionByUserName(username));
        System.out.println(userServise.findPermissionByUserName(username));
        return simpleAuthorizationInfo;
    }


    /*
     * 认证方法
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取账号密码
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        System.out.println("账号:"+username+"   密码："+ password);

        //密码加密,通过加盐值，MD5加密方式，
        Object salt =ByteSource.Util.bytes(username);
        SimpleHash simpleHash = new SimpleHash("md5",password,salt,2);
        String mdpassword = simpleHash.toString();
        User user = userServise.findUser(username);
        System.out.println(user);
        if(!username.equals(user.getUsername()) || username == null){
            throw new UnknownAccountException("账号不存在或输入的账号不正确");
        }
        if(!mdpassword.equals(user.getPassword())){
            throw new IncorrectCredentialsException("您输入的密码不正确");
        }
//        账号被禁
        if (user.getIsDisabled()==1) {
            throw  new AuthenticationException("账号已被禁用");
        }
//        处理逻辑
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(username,simpleHash,ByteSource.Util.bytes(username),getName());

        return simpleAuthenticationInfo;
    }
}
