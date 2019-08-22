package com.updown.sso.controller;

import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.User;
import com.updown.sso.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user")
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    /**
     * 检查数据是否可用
     * @param data
     * @param type
     * @return
     */
   @RequestMapping(value = "check/{data}/{type}",method = RequestMethod.GET)
    public ResponseEntity<UpdownResult> checkUser(@PathVariable("data")String data, @PathVariable("type")Integer type){
        Boolean bool = this.userRegisterService.selectCheckUser(data,type);
        if (bool == null){
            return ResponseEntity.badRequest().body(UpdownResult.build(400,"数据不可用",false));
        }
        return ResponseEntity.ok(UpdownResult.build(200,bool ? "数据可用":"数据不可用" ,bool));
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public ResponseEntity<UpdownResult> register(User user){
        this.userRegisterService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UpdownResult.ok());
    }

}
