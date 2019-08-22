package com.updown.sso.controller;

import com.updown.common.pojo.UpdownResult;
import com.updown.sso.service.UserExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户安全退出
 */
@Controller
@RequestMapping("user")
public class UserExitController {

    @Autowired
    private UserExitService userExitService;

    /**
     * 用户安全退出
     * @param token
     * @return
     */
    @RequestMapping(value = "logout/{token}",method = RequestMethod.GET)
    public ResponseEntity<UpdownResult> deleteUserStatusByToken(@PathVariable("token") String token){
        UpdownResult updownResult = this.userExitService.deleteUserStatusByToken(token);
        return ResponseEntity.ok(updownResult);
    }
}
