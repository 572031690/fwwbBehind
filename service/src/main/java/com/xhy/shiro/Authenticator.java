package com.xhy.shiro;

import com.xhy.shiro.realm.UserRealm;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

public class Authenticator {

    public ModularRealmAuthenticator authenticator(UserRealm myRealm) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        Collection<Realm> crealms = new ArrayList<>();
        crealms.add(myRealm);
        authenticator.setRealms(crealms);
        return authenticator;
    }
}
