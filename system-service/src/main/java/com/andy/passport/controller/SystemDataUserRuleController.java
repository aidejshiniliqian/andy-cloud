package com.andy.passport.controller;

import com.andy.passport.service.SystemDataUserRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户数据权限规则表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/data/user/rule")
public class SystemDataUserRuleController {

    @Autowired
    private SystemDataUserRuleService systemDataUserRuleService;


}
