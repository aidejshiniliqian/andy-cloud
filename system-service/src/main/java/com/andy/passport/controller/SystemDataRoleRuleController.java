package com.andy.passport.controller;

import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemDataRoleRuleBindDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.service.SystemDataRoleRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Tag(name = "角色数据权限规则表", description = "角色数据权限规则表")
public class SystemDataRoleRuleController {

    @Autowired
    private SystemDataRoleRuleService systemDataRoleRuleService;

    /**
     * 查询角色数据权限列表
     * @param roleId 角色ID
     * @return 数据权限列表
     */
    @GetMapping("/rules/{roleId}")
    @Operation(summary = "查询角色数据权限列表")
    public CommonResult<List<SystemDataRoleRule>> getRoleDataRules(@PathVariable("roleId") Integer roleId){
        return CommonResult.success(systemDataRoleRuleService.getRoleDataRules(roleId));
    }

    /**
     * 绑定角色数据权限
     * @param dto 角色数据权限绑定DTO
     * @return 是否成功
     */
    @PostMapping("/bind-rule")
    @Operation(summary = "绑定角色数据权限")
    public CommonResult<Boolean> bindRoleDataRules(@RequestBody SystemDataRoleRuleBindDto dto){
        return CommonResult.success(systemDataRoleRuleService.bindRoleDataRules(dto));
    }

}
