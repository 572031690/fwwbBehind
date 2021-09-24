package com.xhy.shiro;

import com.xhy.shiro.realm.UserRealm;

import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

public class Authorizer {
    public ModularRealmAuthorizer authorizer(UserRealm myRealm) {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        Collection<Realm> crealms = new ArrayList<>();
        crealms.add(myRealm);
        authorizer.setRealms(crealms);
        return authorizer;
    }


}
