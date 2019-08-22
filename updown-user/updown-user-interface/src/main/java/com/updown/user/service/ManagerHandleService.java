package com.updown.user.service;

import com.updown.common.pojo.UpdownResult;

/**
 * 管理员操作
 */
public interface ManagerHandleService {

    /**
     * 用户删除
     * @param user_id
     * @return
     */
    UpdownResult deleteUserById(Long user_id);

    /**
     * 查询所有用户
     * @return
     */
    UpdownResult selectAllUser();

    /**
     * 查询用户类型
     * @param user_id
     * @return
     */
    UpdownResult getUserType(Long user_id);
}
