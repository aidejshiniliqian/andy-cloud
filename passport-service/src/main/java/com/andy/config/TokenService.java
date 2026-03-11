package com.andy.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    public TokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeToken(OAuth2AccessToken token, OAuth2ClientAuthenticationToken authentication) {
        String key = "access:" + token.getTokenValue();
        redisTemplate.opsForValue().set(key, authentication);
        redisTemplate.expire(key, token.getExpiresAt().getEpochSecond() - System.currentTimeMillis() / 1000, TimeUnit.SECONDS);
    }

    public void removeToken(String tokenValue) {
        redisTemplate.delete("access:" + tokenValue);
    }
}