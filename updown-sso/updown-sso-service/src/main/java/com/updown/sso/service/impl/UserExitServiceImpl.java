package com.updown.sso.service.impl;

import com.updown.common.pojo.UpdownResult;
import com.updown.sso.jedis.JedisClient;
import com.updown.sso.service.UserExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserExitServiceImpl implements UserExitService {

    @Autowired
    private JedisClient client;

    @Value("${USER_INFO}")
    private String USER_INFO;

    @Override
    public UpdownResult deleteUserStatusByToken(String token) {
        //1.注入jedisclient
        //2.设置session时间为0
        client.expire(USER_INFO+":"+token,0);
        return UpdownResult.ok();
    }
}
