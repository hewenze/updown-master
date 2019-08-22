package com.updown.sso.service.impl;

import com.updown.common.pojo.UpdownResult;
import com.updown.mapper.UserMapper;
import com.updown.pojo.User;
import com.updown.sso.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean selectCheckUser(String data, Integer type) {
        User record = new User();
        if (type == 1){  //类型为user_name(用户名)
            record.setUser_name(data);
        }else if (type == 2){  //类型为user_number(工号或学号)
            record.setUser_number(data);
        }else {
            return null;
        }
        return this.userMapper.selectCount(record) == 0; //查询结果集数量为0返回true(说明数据并未使用),反之亦然.
    }

    @Override
    public void createUser(User user) {
        UpdownResult updownResult = new UpdownResult();
        //1.判断user是否为空,并验证数据的有效性
        if (user == null){
            return;
        }

        //2.补全属性
        user.setUser_id(null);  //防止黑客恶意攻击
        user.setUser_create_time(new Date());

        //3.加密存储
        user.setUser_password(DigestUtils.md5DigestAsHex(user.getUser_password().getBytes()));

        //4.注册用户
        this.userMapper.insert(user);
    }

}
