package com.updown.sso.service;

import com.updown.common.pojo.UpdownResult;

/**
 * 用户登录
 */
public interface UserLoginService {

    /**
     * 用户登录
     * @param user_name
     * @param user_password
     * @return
     */
    UpdownResult findUser(String user_name, String user_password);

    /**
     * 根据token进行查找用户
     * @param token
     * @return
     */
    UpdownResult findUserByToken(String token);
}
