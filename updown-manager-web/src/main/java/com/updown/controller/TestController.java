package com.updown.controller;

import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.User;
import com.updown.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试接口
 */
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 测试当前时间
     * @return
     */
    @RequestMapping("/test/queryNow")
    public ResponseEntity<UpdownResult> queryNow(){
        return ResponseEntity.ok(UpdownResult.ok(testService.queryNow()));
    }

    /**
     * 测试根据主键查询user
     * @param id
     * @return
     */
    @RequestMapping("/test/queryUserById/{id}")
    @ResponseBody
    public ResponseEntity<UpdownResult> queryUserById(@PathVariable("id") Long id){
        User user = testService.selectUserById(id);
        System.out.println("---"+user);
        return ResponseEntity.ok(UpdownResult.ok(user));
    }
}
