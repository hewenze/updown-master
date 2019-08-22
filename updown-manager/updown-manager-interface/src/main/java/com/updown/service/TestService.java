package com.updown.service;

import com.updown.pojo.User;

/**
 * 测试service
 */
public interface TestService {

    /**
     * 从数据库中获取当前的时间
     * @return
     */
    public String queryNow();

    /**
     * 根据id查询用户
     * @return
     */
    public User selectUserById(Long id);
}
