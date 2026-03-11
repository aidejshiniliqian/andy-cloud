package com.andy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "system-service", path = "/user")
public interface SystemUserFeignClient {

    /**
     * 根据用户账号查询用户信息
     * @param userCode 用户账号
     * @return 用户信息
     */
    @GetMapping("getByUserCode")
    Map<String, Object> getByUserCode(@RequestParam("userCode") String userCode);
}
