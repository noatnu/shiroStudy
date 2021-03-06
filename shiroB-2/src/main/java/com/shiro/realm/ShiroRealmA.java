package com.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroRealmA implements Realm {//继承这个AuthenticatingRealm class也行
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String getName() {
        String name = this.getClass().getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
        logger.info("name:"+name);
        return name;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        logger.info("token:"+token+" hashCode:"+token.getClass().hashCode());
        boolean flag = token instanceof UsernamePasswordToken;
        return flag;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();//得到用户名
        String password = new String((char[]) token.getCredentials());//得到密码
        logger.info("username:"+username+" password:"+password);

        //这里是写死了的,但是平时我们需要从数据库取出数据
        if (!(username.equals("zhou") || username.equals("Lee"))) throw new UnknownAccountException("用户名错误");
        if (!(password.equals("123"))) throw new IncorrectCredentialsException("密码错误");

        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        return simpleAuthenticationInfo;
    }
}
