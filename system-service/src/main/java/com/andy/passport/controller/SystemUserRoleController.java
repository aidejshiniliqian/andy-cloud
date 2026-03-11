package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemUserRoleBindDto;
import com.andy.passport.entity.SystemRole;
import com.andy.passport.service.SystemUserRoleService;
import com.andy.passport.vo.SystemRoleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/user/role")
@Tag(name = "用户角色表", description = "用户角色表")
public class SystemUserRoleController {

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    /**
     * 查询用户角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    @GetMapping("/roles/{userId}")
    @Operation(summary = "查询用户角色列表")
    public CommonResult<List<SystemRoleVo>> getUserRoles(@PathVariable("userId") Integer userId){
        List<SystemRole> roles = systemUserRoleService.getUserRoles(userId);
        List<SystemRoleVo> roleVos = roles.stream()
                .map(role -> BeanUtil.copyProperties(role, SystemRoleVo.class))
                .collect(Collectors.toList());
        return CommonResult.success(roleVos);
    }

    /**
     * 绑定用户角色
     * @param dto 用户角色绑定DTO
     * @return 是否成功
     */
    @PostMapping("/bind-role")
    @Operation(summary = "绑定用户角色")
    public CommonResult<Boolean> bindUserRoles(@RequestBody SystemUserRoleBindDto dto){
        return CommonResult.success(systemUserRoleService.bindUserRoles(dto));
    }
}
