package com.andy.config;

import com.andy.feign.SystemUserFeignClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SystemUserFeignClient systemUserFeignClient;

    public UserDetailsServiceImpl(SystemUserFeignClient systemUserFeignClient) {
        this.systemUserFeignClient = systemUserFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过Feign客户端调用system-service的用户接口
        Map<String, Object> result = systemUserFeignClient.getByUserCode(username);
        if (result == null || !Boolean.TRUE.equals(result.get("success")) || result.get("data") == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Map<String, Object> userData = (Map<String, Object>) result.get("data");
        String userCode = (String) userData.get("userCode");
        String password = (String) userData.get("password");
        // 构建UserDetails对象
        return new User(userCode, password,
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }
}