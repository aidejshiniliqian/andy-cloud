package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemDataRoleRuleEditDto;
import com.andy.passport.dto.SystemDataRoleRuleQueryDto;
import com.andy.passport.entity.SystemDataRoleRule;
import com.andy.passport.service.SystemDataRoleRuleService;
import com.andy.passport.vo.SystemDataRoleRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
     * 分页查询角色数据权限规则表
     * @param page
     * @param systemDataRoleRuleQueryDto
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询角色数据权限规则表")
    public CommonResult<IPage<SystemDataRoleRuleVo>> queryPage(Page<SystemDataRoleRule> page,
                                                               SystemDataRoleRuleQueryDto systemDataRoleRuleQueryDto){
        IPage<SystemDataRoleRule> systemDataRoleRuleIPage = systemDataRoleRuleService.page(page, systemDataRoleRuleQueryDto);
        return CommonResult.success(systemDataRoleRuleIPage.convert(systemDataRoleRule -> BeanUtil.copyProperties(systemDataRoleRule, SystemDataRoleRuleVo.class)));
    }

    /**
     * 查询单个角色数据权限规则
     * @param id
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "查询单个角色数据权限规则")
    public CommonResult<SystemDataRoleRuleVo> queryInfo(Integer id){
        SystemDataRoleRule systemDataRoleRule = systemDataRoleRuleService.queryInfo(id);
        return CommonResult.success(BeanUtil.copyProperties(systemDataRoleRule, SystemDataRoleRuleVo.class));
    }

    /**
     * 新增或编辑角色数据权限规则
     * @param systemDataRoleRuleEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "新增或编辑角色数据权限规则")
    public CommonResult<Integer> edit(@RequestBody SystemDataRoleRuleEditDto systemDataRoleRuleEditDto){
        return CommonResult.success(systemDataRoleRuleService.edit(systemDataRoleRuleEditDto));
    }

    /**
     * 启用/禁用角色数据权限规则
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/enableOrDisable")
    @Operation(summary = "启用/禁用角色数据权限规则")
    public CommonResult<Boolean> enable(Integer id, Integer status){
        return CommonResult.success(systemDataRoleRuleService.disable(id, status));
    }

    /**
     * 删除角色数据权限规则
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @Operation(summary = "删除角色数据权限规则")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id){
        return CommonResult.success(systemDataRoleRuleService.delete(id));
    }

}
