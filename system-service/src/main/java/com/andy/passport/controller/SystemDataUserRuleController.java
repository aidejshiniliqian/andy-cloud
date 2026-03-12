package com.andy.passport.controller;

import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemDataUserRuleBindDto;
import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.service.SystemDataUserRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Tag(name = "用户数据权限规则表", description = "用户数据权限规则表")
public class SystemDataUserRuleController {

    @Autowired
    private SystemDataUserRuleService systemDataUserRuleService;

    /**
     * 查询用户数据权限列表
     * @param userId 用户ID
     * @return 数据权限列表
     */
    @GetMapping("/rules/{userId}")
    @Operation(summary = "查询用户数据权限列表")
    public CommonResult<List<SystemDataUserRule>> getUserDataRules(@PathVariable("userId") Integer userId){
        return CommonResult.success(systemDataUserRuleService.getUserDataRules(userId));
    }

    /**
     * 绑定用户数据权限
     * @param dto 用户数据权限绑定DTO
     * @return 是否成功
     */
    @PostMapping("/bind-rule")
    @Operation(summary = "绑定用户数据权限")
    public CommonResult<Boolean> bindUserDataRules(@RequestBody SystemDataUserRuleBindDto dto){
        return CommonResult.success(systemDataUserRuleService.bindUserDataRules(dto));
    }

}
