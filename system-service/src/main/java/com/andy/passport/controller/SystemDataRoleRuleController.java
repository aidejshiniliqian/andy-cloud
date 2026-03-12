package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemDataRoleRuleEditDto;
import com.andy.passport.dto.SystemDataRoleRuleQueryDto;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.andy.passport.vo.SystemDataRoleRuleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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

    @Resource
    private SystemDataRoleRuleService systemDataRoleRuleService;

    /**
     * 查询角色下的数据权限列表
     * @param systemDataRoleRuleQueryDto
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "查询角色下的数据权限列表")
    public CommonResult<List<SystemDataRoleRuleVo>> queryList(SystemDataRoleRuleQueryDto systemDataRoleRuleQueryDto){
        List<SystemDataRoleRuleVo> list = systemDataRoleRuleService.queryList(systemDataRoleRuleQueryDto);
        return CommonResult.success(list);
    }

    /**
     * 新增或编辑角色数据权限规则
     * @param systemDataRoleRuleEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "新增或编辑角色数据权限规则")
    public CommonResult<Boolean> edit(@RequestBody SystemDataRoleRuleEditDto systemDataRoleRuleEditDto){
        return CommonResult.success(systemDataRoleRuleService.edit(systemDataRoleRuleEditDto));
    }

}
