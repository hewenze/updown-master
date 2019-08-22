package com.updown.sso.controller;

import com.updown.common.pojo.UpdownResult;
import com.updown.common.utils.CookieUtils;
import com.updown.common.utils.JsonUtils;
import com.updown.pojo.User;
import com.updown.sso.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @Value("${UP_TOKEN_KEY}")
    private String UP_TOKEN_KEY;

    /**
     * 用户登录,查询用户
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseEntity<UpdownResult> findUser(@RequestBody String params,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response){
        User user = JsonUtils.jsonToPojo(params, User.class);
        UpdownResult updownResult = this.userLoginService.findUser(user.getUser_name(),user.getUser_password());
        if (updownResult.getStatus() == 200){  //用户名密码都正确,登录成功
            //设置token到cookie中
            CookieUtils.setCookie(request,response,UP_TOKEN_KEY,updownResult.getData().toString());
            //登录成功后根据token获取用户信息返回给页面
            UpdownResult result = this.userLoginService.findUserByToken(updownResult.getData().toString());
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updownResult);
    }


    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    @RequestMapping(value = "token/{token}",method = RequestMethod.GET)
    public ResponseEntity<UpdownResult> getUserByToken(@PathVariable("token") String token){
        UpdownResult result = this.userLoginService.findUserByToken(token);
        if (result.getStatus() == 200){
            //将用户密码设置为空
            User user = (User) result.getData();
            user.setUser_password(null);
            result.setData(user);
            //响应
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

}
