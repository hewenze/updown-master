package com.updown.user.service.impl;

import com.updown.common.pojo.UpdownResult;
import com.updown.mapper.UserMapper;
import com.updown.pojo.User;
import com.updown.user.service.UserHandleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserHandleServiceImpl implements UserHandleService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UpdownResult updateUserPassword(Long user_id, String user_password) {
        //1.判断密码格式是否正确
        if (user_id == null || user_password == null){
            return UpdownResult.build(400,"数据格式有误",null);
        }
        if (user_password.length() < 6){
            return UpdownResult.build(400,"数据格式有误",null);
        }
        //2.判断密码是否和上次密码一致
        //2.1获取数据库中user
        User user = this.userMapper.selectByPrimaryKey(user_id);
        //2.2将参数user_password进行MD5加密
        String md5_password = DigestUtils.md5DigestAsHex(user_password.getBytes());
        //2.3比较密码是否一致
        if (StringUtils.equals(md5_password,user.getUser_password())){
            //3.一致,返回403
            return UpdownResult.build(403,"新旧密码不能保持一致",null);
        }
        //4.不一致,进行密码修改,返回201
        user.setUser_password(md5_password);
        this.userMapper.updateByPrimaryKeySelective(user);
        return UpdownResult.ok();
    }
}

