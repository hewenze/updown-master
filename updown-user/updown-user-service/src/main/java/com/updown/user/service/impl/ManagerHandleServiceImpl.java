package com.updown.user.service.impl;

import com.updown.common.pojo.UpdownResult;
import com.updown.common.pojo.UserInfo;
import com.updown.mapper.UserMapper;
import com.updown.pojo.User;
import com.updown.user.service.ManagerHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerHandleServiceImpl implements ManagerHandleService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UpdownResult deleteUserById(Long user_id) {
        if (user_id == null){
            return UpdownResult.build(404,"删除错误");
        }
        int i = this.userMapper.deleteByPrimaryKey(user_id);
        if (i<=0){
            return UpdownResult.build(404,"删除错误");
        }
        return UpdownResult.ok();
    }

    @Override
    public UpdownResult selectAllUser() {
        List<User> users = this.userMapper.selectAll();
        List<UserInfo> userInfos = new ArrayList<>();
        users.forEach(user -> {
            //将user处理成userinfo,剔除不需要的信息
            UserInfo userInfo = new UserInfo();
            //设置每个字段的信息
            userInfo.setUser_id(user.getUser_id());
            userInfo.setUser_name(user.getUser_name());
            userInfo.setUser_number(user.getUser_number());
            userInfo.setUser_type(user.getUser_type());
            //添加到集合中
            userInfos.add(userInfo);
        });
        return UpdownResult.ok(userInfos);
    }

    @Override
    public UpdownResult getUserType(Long user_id) {
        User user = this.userMapper.selectByPrimaryKey(user_id);
        if (user == null){
            return UpdownResult.build(404,"请求错误");
        }
        return UpdownResult.ok(user.getUser_type());
    }
}
