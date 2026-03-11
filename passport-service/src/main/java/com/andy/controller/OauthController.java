package com.andy.controller;

import com.andy.config.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    private final AuthenticationManager authenticationManager;
    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2AccessToken> tokenGenerator;
    private final TokenService tokenService;

    public OauthController(AuthenticationManager authenticationManager, 
                         RegisteredClientRepository registeredClientRepository, 
                         OAuth2AuthorizationService authorizationService, 
                         OAuth2TokenGenerator<? extends OAuth2AccessToken> tokenGenerator, 
                         TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.registeredClientRepository = registeredClientRepository;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // 验证用户名和密码
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 返回与OAuth2默认接口一致的格式
        return ResponseEntity.ok(Map.of(
                "access_token", "sample-access-token",
                "token_type", "Bearer",
                "expires_in", 3600,
                "refresh_token", "sample-refresh-token",
                "scope", "all"
        ));
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody Map<String, String> tokenRequest) {
        String token = tokenRequest.get("token");
        // 验证token的逻辑
        // 这里可以根据需要实现自定义的token验证
        return ResponseEntity.ok(Map.of(
                "active", true,
                "client_id", "client",
                "username", "sample-user",
                "scope", "all",
                "exp", Instant.now().getEpochSecond() + 3600
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestBody Map<String, String> tokenRequest) {
        String token = tokenRequest.get("token");
        // 移除token
        tokenService.removeToken(token);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Token revoked successfully"
        ));
    }
}
