package com.xyq.shiro3.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    @Bean
    public JdbcRealm getJdbcRealm(DataSource dataSource){
        JdbcRealm jdbcRealm = new JdbcRealm();
        //JdbcRealm会自行从数据库查询用户及权限数据（数据库的表结构要符合JdbcRealm的规范）
        jdbcRealm.setDataSource(dataSource);
        //JdbcRealm默认开启认证功能，需要手动开启授权功能
        jdbcRealm.setPermissionsLookupEnabled(true);
        return  jdbcRealm;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(JdbcRealm jdbcRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(jdbcRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        //过滤器就是shiro就行权限校验的核心，进行认证和授权是需要SecurityManager的
        filter.setSecurityManager(securityManager);

        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("/","anon");
        filterMap.put("/index.html","anon");
        filterMap.put("/login.html","anon");
        filterMap.put("/regist.html","anon");
        filterMap.put("/user/login","anon");
        filterMap.put("/user/regist","anon");
        filterMap.put("/static/**","anon");
        filterMap.put("/**","anon");

        filter.setFilterChainDefinitionMap(filterMap);
        filter.setLoginUrl("/login.html");
        //设置未授权访问的页面路径
        filter.setUnauthorizedUrl("/login.html");
        return filter;
    }

}
