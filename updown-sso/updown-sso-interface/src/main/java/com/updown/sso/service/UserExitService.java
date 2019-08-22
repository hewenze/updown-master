package com.updown.sso.service;

import com.updown.common.pojo.UpdownResult;

/**
 * 用户退出
 */
public interface UserExitService {

    /**
     * 用户安全退出
     * @param token
     * @return
     */
    UpdownResult deleteUserStatusByToken(String token);
}
