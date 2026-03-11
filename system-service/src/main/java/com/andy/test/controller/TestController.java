package com.andy.test.controller;

import com.andy.common.exception.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("error")
    public CommonResult<String> testError(){
        int a = 1/0;
        return CommonResult.success();
    }

}
