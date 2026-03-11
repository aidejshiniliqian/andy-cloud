package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemRoleMenuEditDto;
import com.andy.passport.vo.SystemRoleMenuVo;
import com.andy.passport.entity.SystemRoleMenu;
import com.andy.passport.service.SystemRoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色功能菜单表 前端控制器
 * </p>
 *
 * @author Andy.Liu
 * @since 2025-02-26
 */
@RestController
@RequestMapping("/role/menu")
@Tag(name = "角色功能菜单表", description = "角色功能菜单表")
public class SystemRoleMenuController {

    @Autowired
    private SystemRoleMenuService systemRoleMenuService;

    /**
     * 查询角色的菜单列表
     * @param roleId
     * @return
     */
    @GetMapping("/all")
    @Operation(summary = "查询角色的菜单列表")
    public CommonResult<List<SystemRoleMenuVo>> queryRoleMenus(@RequestParam("roleId") Integer roleId){
        List<SystemRoleMenu> systemRoleMenus = systemRoleMenuService.queryRoleMenus(roleId);
        return CommonResult.success(systemRoleMenus.stream().map(systemRoleMenu -> BeanUtil.copyProperties(systemRoleMenu, SystemRoleMenuVo.class)).collect(Collectors.toList()));
    }

    /**
     * 查询角色的菜单Id列表
     * @param roleId
     * @return
     */
    @GetMapping("/ids")
    @Operation(summary = "查询角色的菜单Id列表")
    public CommonResult<List<Integer>> queryRoleMenuIds(@RequestParam("roleId") Integer roleId){
        List<SystemRoleMenu> systemRoleMenus = systemRoleMenuService.queryRoleMenus(roleId);
        return CommonResult.success(systemRoleMenus.stream().map(SystemRoleMenu::getMenuId).collect(Collectors.toList()));
    }

    /**
     * 编辑角色菜单
     * @param systemRoleMenuEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "编辑角色菜单")
    public CommonResult<Boolean> editRoleMenu(@RequestBody SystemRoleMenuEditDto systemRoleMenuEditDto){
        return CommonResult.success(systemRoleMenuService.editRoleMenu(systemRoleMenuEditDto));
    }
}
