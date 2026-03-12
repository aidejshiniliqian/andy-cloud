package com.andy.passport.controller;

import cn.hutool.core.bean.BeanUtil;
import com.andy.common.exception.CommonResult;
import com.andy.passport.dto.SystemDataUserRuleEditDto;
import com.andy.passport.dto.SystemDataUserRuleQueryDto;
import com.andy.passport.entity.SystemDataUserRule;
import com.andy.passport.service.SystemDataUserRuleService;
import com.andy.passport.vo.SystemDataUserRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private SystemDataUserRuleService systemDataUserRuleService;

    /**
     * 分页查询用户数据权限规则表
     * @param page
     * @param systemDataUserRuleQueryDto
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户数据权限规则表")
    public CommonResult<IPage<SystemDataUserRuleVo>> queryPage(Page<SystemDataUserRule> page,
                                                               SystemDataUserRuleQueryDto systemDataUserRuleQueryDto){
        IPage<SystemDataUserRule> systemDataUserRuleIPage = systemDataUserRuleService.page(page, systemDataUserRuleQueryDto);
        return CommonResult.success(systemDataUserRuleIPage.convert(systemDataUserRule -> BeanUtil.copyProperties(systemDataUserRule, SystemDataUserRuleVo.class)));
    }

    /**
     * 查询单个用户数据权限规则
     * @param id
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "查询单个用户数据权限规则")
    public CommonResult<SystemDataUserRuleVo> queryInfo(Integer id){
        SystemDataUserRule systemDataUserRule = systemDataUserRuleService.queryInfo(id);
        return CommonResult.success(BeanUtil.copyProperties(systemDataUserRule, SystemDataUserRuleVo.class));
    }

    /**
     * 新增或编辑用户数据权限规则
     * @param systemDataUserRuleEditDto
     * @return
     */
    @PostMapping("/edit")
    @Operation(summary = "新增或编辑用户数据权限规则")
    public CommonResult<Integer> edit(@RequestBody SystemDataUserRuleEditDto systemDataUserRuleEditDto){
        return CommonResult.success(systemDataUserRuleService.edit(systemDataUserRuleEditDto));
    }

    /**
     * 启用/禁用用户数据权限规则
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/enableOrDisable")
    @Operation(summary = "启用/禁用用户数据权限规则")
    public CommonResult<Boolean> enable(Integer id, Integer status){
        return CommonResult.success(systemDataUserRuleService.disable(id, status));
    }

    /**
     * 删除用户数据权限规则
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @Operation(summary = "删除用户数据权限规则")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id){
        return CommonResult.success(systemDataUserRuleService.delete(id));
    }

}
