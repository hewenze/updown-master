package com.updown.sso.service.impl;

import com.updown.common.pojo.UpdownResult;
import com.updown.common.utils.JsonUtils;
import com.updown.mapper.UserMapper;
import com.updown.pojo.User;
import com.updown.sso.jedis.JedisClient;
import com.updown.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisClient client;

    @Value("${USER_INFO}")
    private String USER_INFO;

    @Value("${EXPIRE_TIME}")
    private Integer EXPIRE_TIME;

    @Override
    public UpdownResult findUser(String user_name, String user_password) {
        //1.数据校验
        //1.1判断用户名密码是否为空
        if (StringUtils.isBlank(user_name) || StringUtils.isBlank(user_password)){
            return UpdownResult.build(400,"登录失败,用户名或者密码不能为空");
        }

        //2.进行用户名密码的校验
        //2.1首先通过用户名进行数据库的查询,获取数据库中的user对象
        User record = new User();
        record.setUser_name(user_name);
        User user = this.userMapper.selectOne(record);
        //判断user是否为空
        if (user == null){
            return UpdownResult.build(400,"用户名或者密码错误");
        }
        //2.2将形参传来的密码进行MD5加密,进行和数据库中加密密码进行比较
        String goal_password= DigestUtils.md5DigestAsHex(user_password.getBytes());
        if (!StringUtils.equals(user.getUser_password(),goal_password)){  //校验密码是否相等
            //不相等
            //2.3校验失败
            return UpdownResult.build(400,"用户名或者密码错误");
        }
        //2.4校验成功
        //3.校验成功,生成token :uuid生成  需要设置token的有效期来模拟session  用户的数据存放在redis中(key:token,value:用户的数据JSON)
        String token = UUID.randomUUID().toString();
        //3.1存放数据到redis中,使用jedis的客户端,为了更好的方便管理  在前面加一个前缀"kkk:token"
        //3.2为了安全起见,在存入redis时,应该将密码去除,设置密码为空
        user.setUser_password(null);
        client.set(USER_INFO+":"+token, JsonUtils.objectToJson(user));
        //3.3设置过期时间,来模拟session
        client.expire(USER_INFO+":"+token,EXPIRE_TIME);
        //4把token设置到cookie当中,在表现层设置
        return UpdownResult.ok(token);
    }

    @Override
    public UpdownResult findUserByToken(String token) {
        //1.注入jedisclient
        //2.调用jedisclient根据token查询用户信息(JSON)的方法 get方法
        String strjson = this.client.get(USER_INFO + ":" + token);
        //3.判断是否查询到用户
        if (StringUtils.isNotBlank(strjson)){
            //4.如果查询到,需要返回200,并将用户信息转成对象,用户信息进行返回,并且需要重新设置过期时间
            //4.1将用户信息进行反序列化
            User user = JsonUtils.jsonToPojo(strjson, User.class);
            //4.2重新设置过期时间
            client.expire(USER_INFO+":"+token,EXPIRE_TIME);
            //4.3返回
            return UpdownResult.ok(user);
        }
        //5.如果查询不到,需要返回404,用户信息已过期
        return UpdownResult.build(404,"身份已过期,请重新登录");
    }
}
