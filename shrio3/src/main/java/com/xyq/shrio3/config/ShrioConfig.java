package com.xyq.shrio3.config;


import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShrioConfig {

    @Bean
    public JdbcRealm getJdbcRealm(DataSource dataSource) {
        JdbcRealm jdbcRealm = new JdbcRealm();
//        JdbcRealm会自行从数据库查询用户及用户权限（数据库必须符合JdbcRealm规范）
        jdbcRealm.setDataSource(dataSource);
//        JdbcRealm会默认开启认证功能，需要手动开启授权功能
        jdbcRealm.setPermissionsLookupEnabled(true);
        return jdbcRealm;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(JdbcRealm jdbcRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //2.securityManager要完成校验，需要realm
        securityManager.setRealm(jdbcRealm);
        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        //1.过滤器就是shrio权限校验的核心，进行认证和校验需要 SecurityManager
        filter.setSecurityManager(securityManager);

        //设置拦截规则
        /*
        anon:无需认证就可以访问
        authc:必须认证才能访问，
        user:必须拥有 记住我 功能才能用
        perms:拥有对某个资源的权限才能访问
        role:拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new HashMap<String, String>();
        filterMap.put("/", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/user/login.html", "anon");
        filterMap.put("/**", "anon");

        filter.setFilterChainDefinitionMap(filterMap);
        filter.setLoginUrl("/login.html");
        filter.setUnauthorizedUrl("/unAuthorized.html");
        return filter;
    }
}
