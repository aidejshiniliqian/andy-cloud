package com.andy.passport.controller;

import com.andy.passport.service.SystemDataRoleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色数据权限规则表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/data/role/rule")
public class SystemDataRoleRuleController {

    @Autowired
    private SystemDataRoleRuleService systemDataRoleRuleService;



}
