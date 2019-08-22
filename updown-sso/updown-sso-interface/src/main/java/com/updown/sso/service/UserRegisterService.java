package com.updown.sso.service;

import com.updown.pojo.User;

/**
 * 用户注册
 */
public interface UserRegisterService {

    /**
     * 检查数据是否可用
     * @param data
     * @param type
     * @return
     */
    Boolean selectCheckUser(String data, Integer type);

    /**
     * 用户注册
     * @param user
     */
    void createUser(User user);
}
