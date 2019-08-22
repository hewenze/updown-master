package com.updown.service.impl;

import com.updown.mapper.TestMapper;
import com.updown.mapper.UserMapper;
import com.updown.pojo.User;
import com.updown.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper mapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String queryNow() {
        return mapper.queryNow();
    }

    @Override
    public User selectUserById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        System.out.println(user);
        return user;
    }
}
