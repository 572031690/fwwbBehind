package com.xhy.shiro.Map;

import com.xhy.domain.Permission;
import com.xhy.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilterChainDefinitionMapBuilder {
    //权限
    @Autowired
    private PermissionService permissionService;

    public Map<String,String> createFilterChainDefinitionMap(){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("/web/shirologin","anon");
        map.put("/web/logon","anon");
        map.put("/web/updateUserPassword","anon");
        map.put("/webneed/getNeedCount","anon");
        map.put("/webbuy/getBuyCount","anon");
        map.put("/web/getUserRole","anon");
        map.put("/webitem/findAllitem","anon");

        map.put("/swagger-ui.html","anon");
        map.put("/swagger-resources/** " ,"anon");
        map.put("/v2/api-docs/**" ,"anon");
        map.put("/webjars/springfox-swagger-ui/**" ,"anon");
        map.put("/swagger/**","anon");

        //查询数据库中权限对应的资源路径
        List<Permission> permissions = permissionService.findPermission();
        //遍历,添加
        for (Permission permission : permissions) {
            map.put(permission.getUrl(),"perms["+permission.getPermission()+"]");
        }
        map.put("/**","user");
        System.out.println(map);
        return map;
    }
}
