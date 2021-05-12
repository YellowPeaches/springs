package com.kuang.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro内置管理器
        /*
        anon:无需认证就可以访问
        authc:必须认证才能访问，
        user:必须拥有 记住我 功能才能用
        perms:拥有对某个资源的权限才能访问
        role:拥有某个角色权限才能访问
         */

        //拦截
        Map<String, String> filterMap= new LinkedHashMap<>();

///     授权
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        filterMap.put("/user/*","authc");

        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录页的请求
        bean.setLoginUrl("/toLogin");
        //设置跳转到未授权页面
        bean.setUnauthorizedUrl("/unauth");


        return bean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }


    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }


//    整合shiroDialect,用来整合shiro-thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
